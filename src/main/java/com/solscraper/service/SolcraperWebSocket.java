package com.solscraper.service;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.solscraper.model.helius.response.tx.AddressTxLookupResponse;
import com.solscraper.model.helius.response.tx.InnerInstruction;
import com.solscraper.model.helius.response.tx.Instruction;
import com.solscraper.model.helius.response.tx.TokenTransfer;
import com.solscraper.model.jsonrpc.request.WebSocketParamJsonRpcRequest;
import com.solscraper.model.solexplorer.response.AccountKey;
import com.solscraper.model.solexplorer.response.TransactionLookupResponse;
import com.solscraper.util.Base58;
import com.solscraper.util.BoshDecoder;
import com.solscraper.util.MapUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

@Service
@Slf4j
@Data
public class SolcraperWebSocket extends WebSocketListener {
	public static final String DUMP_LOC = SolcraperWebSocket.isWindows() ? "C:\\temp\\dump.json"
			: "/home/temp/dump.json";
	public static final String DUMP_LOC_SIG = SolcraperWebSocket.isWindows() ? "C:\\temp\\dump-sig.json"
			: "/home/temp/dump-sig.json";

	public static final String[] CA_TO_MONITOR = { "DEALERKFspSo5RoXNnKAhRPhTcvJeqeEgAgZsNSjCx5E" };
	public static final ObjectMapper MAPPER = new ObjectMapper();
	@Value("${service.helius.websocket}")
	private String heliusUrl;

	@Autowired
	private SolscraperService service;
	public static Map<String, ConcurrentHashMap<String, BigDecimal>> adrressAccounts = new ConcurrentHashMap<>();
	public static Map<String , ConcurrentLinkedDeque<String>> seenTransactionSignatures = new ConcurrentHashMap<>();
	private WebSocket webSocket;

	@EventListener(ApplicationReadyEvent.class)
	public void run() {
		log.info("Running Helius WebSocket transaction Listener");
		this.loadBalanceStates();
		this.loadSignatureStates();
		try {
			long start = Instant.now().toEpochMilli();
	    	final List<AddressTxLookupResponse> responseList = this.service.getTransactions(CA_TO_MONITOR[0], null, null);
	    	log.info("Fetched {} parsed transactions for addr {} in {}ms", responseList.size(), CA_TO_MONITOR[0], (Instant.now().toEpochMilli()-start));
	    	for(AddressTxLookupResponse response : responseList) {
		    	for(Instruction instruction : response.instructions) {
		    		if(instruction.getInnerInstructions()!=null) { 
		    			log.info("Found dealer interaction instructions in tx {}", response.getSignature());
		    			for(InnerInstruction inner : instruction.getInnerInstructions()) {
		    				log.info("instruction data: {}", inner.getData());
				    		log.info("Instruction {}",inner);
				    		try {
				    			//byte[] data1 = Base64.getDecoder().decode(inner.getData());
				    			byte[] data0 = Base58.decode(inner.getData());
					    		DataInputStream in = new DataInputStream(new ByteArrayInputStream(data0));
					    		int index = in.readByte();
					    		long val = in.readLong();
					    		log.info("TX : {} : {}", index, val);
				    		}catch(Exception e) {
				    			log.error(e.getMessage());
				    		}
		    			}
		    			
			    		//List<InnerInstruction> inner = instruction.innerInstructions;
		    		}
		    		
		    		

		    	}
	    	}

		}catch(Exception e) {
			log.error("Failed to fetch parsed transactions from helius. Reason: {}", e);
		}
    	this.backFillUntil(CA_TO_MONITOR[0]);
    	this.backFillFrom(CA_TO_MONITOR[0]);
    	
		writeStateToFile();

		final OkHttpClient client = new OkHttpClient.Builder().readTimeout(3000, TimeUnit.MILLISECONDS).build();
		final Request request = new Request.Builder().url(heliusUrl).build();
		this.webSocket = client.newWebSocket(request, this);
	}

	public void backFill(String address) {
		log.info("Beginning transaction backfill for addrress {}", address);
		List<String> sigTransactions = null;
		try {
			sigTransactions = this.service.backFillAddressTransactions(address);
		} catch (Exception e) {
			log.error("Failed to backfill DCF transactions. Reason: {}", e);
		}
		
		if (sigTransactions != null) {
			log.info("Fetching transaction data for {} signatures", sigTransactions.size());
			for (int i = 0; i < sigTransactions.size(); i++) {
				try {
					TransactionLookupResponse txResponse = this.service.getTransactionBySignature(sigTransactions.get(i));
					handleTransactionLookupResponse(txResponse);
				} catch (Exception e) {
					log.error("Failed to lookup backfiilled transaction. Reason: {}", e);
				}
			}
		}
	}
	
	public void backFillFrom(String address) {
		final String lastTransactionSig = seenTransactionSignatures.get(address).peekFirst();
		if(lastTransactionSig==null) {
			log.error("No last transaction signature available beginnning backfill from latest signature");
			this.backFill(address);
			return;
		}
		log.info("Beginning transaction backfill from signature {} for addrres {}", lastTransactionSig, address );
		try {
			final List<String> transactionSignatures = this.service.backFillAddressTransactionsFrom(address, lastTransactionSig, 200000);
			log.info("Successfully backfilled {} transactions. Fetching transaction data", transactionSignatures.size());
			for (int i = 0; i < transactionSignatures.size(); i++) {
				try {
					TransactionLookupResponse txResponse = this.service.getTransactionBySignature(transactionSignatures.get(i));
					handleTransactionLookupResponse(txResponse);
					Thread.sleep(20);
				} catch (Exception e) {
					log.error("Failed to lookup backfiilled transaction. Reason: {}", e);
				}
			}
		} catch (Exception e) {
			log.error("Failed to backfill DCF transactions. Reason: {}", e);
		}
	}
	
	public void backFillUntil(String address) {
		final String lastTransactionSig = seenTransactionSignatures.get(address).peekLast();
		if(lastTransactionSig==null) {
			log.error("No last transaction signature available beginnning backfill from latest signature");

			this.backFill(address);
			return;
		}
		log.info("Beginning transaction backfill until signature {} for addrres {}", lastTransactionSig, address);
		//List<TransactionLookupResponse> sigTransactions = null;
		try {
			List<String> transactionSignatures = this.service.backFillAddressTransactionsUntil(address, lastTransactionSig);
			log.info("Successfully backfilled {} transactions. Fetching transaction data", transactionSignatures.size());
			for (int i = 0; i < transactionSignatures.size(); i++) {
				try {
					TransactionLookupResponse txResponse = this.service.getTransactionBySignature(transactionSignatures.get(i));
					handleTransactionLookupResponse(txResponse);
					Thread.sleep(20);
				} catch (Exception e) {
					log.error("Failed to lookup backfiilled transaction. Reason: {}", e);
				}
			}
		} catch (Exception e) {
			log.error("Failed to backfill DCF transactions. Reason: {}", e);
		}
	}
	
	public static void handleSignature(String address, String signature, boolean appendStart) {
		ConcurrentLinkedDeque<String> existingSigs = seenTransactionSignatures.get(address);
		if(existingSigs == null) {
			ConcurrentLinkedDeque<String> newSignatures= new ConcurrentLinkedDeque<>();
			newSignatures.add(signature);
			seenTransactionSignatures.put(address, newSignatures);
		}else {
			if(appendStart) {
				existingSigs.addFirst(signature);
			}else {
				existingSigs.addLast(signature);
			}
			seenTransactionSignatures.put(address, existingSigs);
		}
	}

	public static void handleTransactionLookupResponse(TransactionLookupResponse txResponse) {
		final List<AccountKey> txIns = txResponse.getResult().getTransaction().getMessage().getAccountKeys();
		for (int j = 0; j < txIns.size(); j++) {
			final String account = txIns.get(j).getPubkey();
			if (account.equals("ComputeBudget111111111111111111111111111111")
					|| account.equals("11111111111111111111111111111111")
					|| account.equals("Sysvar1nstructions1111111111111111111111111")) {
				continue;
			}
			final Long preBalance = txResponse.getResult().getMeta().getPreBalances().get(j);
			final Long postBalance = txResponse.getResult().getMeta().getPostBalances().get(j);
			final double preBal = preBalance / (1000000000d);
			final double postBal = postBalance / (1000000000d);
			final Double diffBal = postBal - preBal;
			final String balString = diffBal.toString();
			if (!balString.contains("E") && !balString.equals("0.0")) {
				log.info("Account {} balanceChange={}", account.substring(0, 10), diffBal);
				// final Optional<AccountKey> signer = tx.getTxSigner();
				handleAddressPurchase(CA_TO_MONITOR[0], account,
						new BigDecimal(diffBal).setScale(8, RoundingMode.HALF_EVEN));
			}
		}
	}
	
	public void loadSignatureStates() {
		try {
			final TypeReference<HashMap<String, ConcurrentLinkedDeque<String>>> typeRef = new TypeReference<HashMap<String, ConcurrentLinkedDeque<String>>>() {};
			final String json = Files.readString(Path.of(DUMP_LOC_SIG));

			if (json.length() == 0) {
				seenTransactionSignatures = new HashMap<>();
				seenTransactionSignatures.put(CA_TO_MONITOR[0], new ConcurrentLinkedDeque<>());
			} else {
				seenTransactionSignatures = MAPPER.readValue(json, typeRef);
			}
			log.info("Successfully loaded {} signature", seenTransactionSignatures.get(CA_TO_MONITOR[0]).size());
		} catch (Exception e) {
			log.error("Failed to load signatures from dump-sig.json. Reason: {}", e);
		}
		printStats();
	}

	public void loadBalanceStates() {
		try {
			final TypeReference<HashMap<String, ConcurrentHashMap<String, BigDecimal>>> typeRef = new TypeReference<HashMap<String, ConcurrentHashMap<String, BigDecimal>>>() {};
			final String json = Files.readString(Path.of(DUMP_LOC));

			if (json.length() == 0) {
				adrressAccounts = new HashMap<>();
				adrressAccounts.put(CA_TO_MONITOR[0], new ConcurrentHashMap<>());
			} else {
				adrressAccounts = MAPPER.readValue(json, typeRef);
			}
			log.info("Successfully loaded {} account balancess", adrressAccounts.get(CA_TO_MONITOR[0]).size());
		} catch (Exception e) {
			log.error("Failed to write balances to dump.json. Reason: {}", e);
		}
		printStats();
	}

	private static void printStats() {
		BigDecimal totalBought = BigDecimal.ZERO;
		BigDecimal totalSold = BigDecimal.ZERO;

		final Set<String> sellerAddr = new HashSet<>();
		final Set<String> buyerAddr = new HashSet<>();

		for (Entry<String, ConcurrentHashMap<String, BigDecimal>> entry : adrressAccounts.entrySet()) {
			for (Entry<String, BigDecimal> e : entry.getValue().entrySet()) {
				// Buyer
				if (e.getValue().compareTo(new BigDecimal(0.002)) == -1) {
					buyerAddr.add(e.getKey());
					totalBought = totalBought.add(e.getValue().abs());
				}
				// Seller
				if (e.getValue().compareTo(new BigDecimal(-0.002)) == 1) {
					sellerAddr.add(e.getKey());
					totalSold = totalSold.add(e.getValue());
				}
			}
		}
		log.info("CA {} had \n{} Unique Buyers \n {} Unique Sellers \n Total Sold (SOL): {} \n Total Bought (SOL): {}",
				CA_TO_MONITOR[0], buyerAddr.size(), sellerAddr.size(), totalSold, totalBought);
	}

	@Override
	public void onOpen(WebSocket webSocket, Response response) {
		// for (String ca : this.adrressAccounts.keySet()) {
		WebSocketParamJsonRpcRequest req = WebSocketParamJsonRpcRequest.getSubscribeAccount(CA_TO_MONITOR[0]);
		try {
			String json = MAPPER.writeValueAsString(req);
			webSocket.send(json);
		} catch (Exception e) {
			log.error("Failed to send WebSocket JSON. Reason: {}", e);
		}
		// }
	}

	public static void handleAddressPurchase(String account, String addr, BigDecimal amount) {
		ConcurrentHashMap<String, BigDecimal> curr = adrressAccounts.get(account);
		if (curr == null) {
			curr = new ConcurrentHashMap<>();
		}
		BigDecimal currentBal = curr.get(addr);
		if (currentBal == null) {
			currentBal = amount.setScale(8, RoundingMode.HALF_EVEN);
		} else {
			currentBal = currentBal.add(amount).setScale(8, RoundingMode.HALF_EVEN);
			log.info("Address {} Trade volume is now {} SOL", addr.substring(0, 10), currentBal);

		}
		curr.put(addr, currentBal);
		adrressAccounts.put(account, curr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(WebSocket webSocket, String text) {
		try {
			final Map<String, Object> results = MAPPER.readValue(text, Map.class);
			final Map<String, Object> params = (Map<String, Object>) results.get("params");
			final Map<String, Object> res = (Map<String, Object>) params.get("result");
			final Map<String, Object> sig = (Map<String, Object>) res.get("value");
			if (sig.get("err") == null) {
				final TransactionLookupResponse tx = this.service
						.getTransactionBySignature(sig.get("signature").toString());
				handleTransactionLookupResponse(tx);
				
			}
		} catch (Exception e) {
			log.error("Failed to parse Solana Transaction. Reason: {}", e.getMessage());
		}
	}

	@Override
	// NO-OP
	public void onMessage(WebSocket webSocket, ByteString bytes) {
	}

	@Override
	public void onClosing(WebSocket webSocket, int code, String reason) {
		webSocket.close(1000, null);
		log.info("CLOSE: {} {}", code, reason);
		try {
			this.webSocket.close(1000, "Remote socket disconnected");
		} catch (Exception e) {
			log.info("Remote websocket terminated session. Reason: {}", e);
		}
		this.run();
	}

	@Override
	public void onFailure(WebSocket webSocket, Throwable t, Response response) {
		log.info("FAILURE: {}. Reason: {} " + response + " " + t.getMessage());
		try {
			this.webSocket.close(1000, "Remote socket disconnected");
			t.printStackTrace();
		} catch (Exception e) {
			log.info("Remote websocket terminated session. Reason: {}", e);
		}
		this.run();
	}

	public static Map<String, HashMap<String, BigDecimal>> getSortedMap() {
		final Map<String, HashMap<String, BigDecimal>> sortedMap = new HashMap<>();
		final Map<String, Map<String, BigDecimal>> deepCopy = SerializationUtils
				.clone(new HashMap<>(adrressAccounts));
		final Map<String, BigDecimal> data = new HashMap<>();
		final Map<String, BigDecimal> dat0 = new HashMap<>();
		for (Entry<String, BigDecimal> e : deepCopy.get(CA_TO_MONITOR[0]).entrySet()) {

			if (e.getValue().compareTo(new BigDecimal(0.005d)) == 1) {
				data.put(e.getKey(), e.getValue());
			}
			if (e.getValue().compareTo(new BigDecimal(-0.005)) == -1) {
				dat0.put(e.getKey(), e.getValue());
			}
		}
		dat0.putAll(data);
		final HashMap<String, BigDecimal> sortedAccounts = (HashMap<String, BigDecimal>) MapUtil.sortByValue(dat0);
		sortedMap.put(CA_TO_MONITOR[0], sortedAccounts);
		return sortedMap;
	}

	@PreDestroy
	@Scheduled(fixedDelay = 15000, initialDelay = 10000l)
	public static void writeStateToFile() {
		try {

			MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
			String json = MAPPER.writeValueAsString(getSortedMap());
			MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
			Files.writeString(Path.of(DUMP_LOC), json);
			log.info("Wrote Profits file successfully");
			printStats();
		} catch (Exception e) {
			log.error("Failed to write balances to dump.json. Reason: {}", e);
		}
	}
	
	@PreDestroy
	@Scheduled(fixedDelay = 15000, initialDelay = 10000l)
	public static void writeSigStateToFile() {
		try {

			MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
			String json = MAPPER.writeValueAsString(seenTransactionSignatures);
			MAPPER.disable(SerializationFeature.INDENT_OUTPUT);
			Files.writeString(Path.of(DUMP_LOC_SIG), json);
			log.info("Wrote Signatures file successfully");
			printStats();
		} catch (Exception e) {
			log.error("Failed to Signatures to dump-sig.json. Reason: {}", e);
		}
	}

	public static boolean isWindows() {
		if (System.getProperty("os.name").toLowerCase().contains("windows"))
			return true;
		else
			return false;
	}

	public static void main(String... args) {
		SolcraperWebSocket ws = new SolcraperWebSocket();
		ws.run();
	}
}