package com.solscraper.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.solscraper.model.jsonrpc.request.WebSocketParamJsonRpcRequest;
import com.solscraper.model.solexplorer.response.AccountKey;
import com.solscraper.model.solexplorer.response.TransactionLookupResponse;
import com.solscraper.service.SolscraperService;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

@Service
@Slf4j
public class SolcraperWebSocket extends WebSocketListener {

	private static final String[] CA_TO_MONITOR = { "3psH1Mj1f7yUfaD5gh6Zj7epE8hhrMkMETgv5TshQA4o", "" };
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Value("${service.helius.websocket}")
	private String heliusUrl;

	@Autowired
	private SolscraperService service;
	private Map<String, HashMap<String, BigDecimal>> adrressAccounts = new HashMap<>();

	@EventListener(ApplicationReadyEvent.class)
	public void run() {
		log.info("Running Helius WebSocket transaction Listener");
		final OkHttpClient client = new OkHttpClient.Builder().readTimeout(3000, TimeUnit.MILLISECONDS).build();
		final Request request = new Request.Builder().url(heliusUrl).build();
		client.newWebSocket(request, this);
	}

	@PostConstruct
	public void loadBalanceStates() {
		try {
			final TypeReference<HashMap<String, HashMap<String, BigDecimal>>> typeRef = new TypeReference<HashMap<String, HashMap<String, BigDecimal>>>() {
			};
			final String json = Files.readString(Path.of("C:/temp/dump.json"));
			if(json.length()==0) {
				this.adrressAccounts = new HashMap<>();
			}else {
				this.adrressAccounts = MAPPER.readValue(json, typeRef);

			}
			log.info("Successfully loaded {} account balancess", this.adrressAccounts.size());
		} catch (Exception e) {
			log.error("Failed to write balances to dump.json. Reason: {}", e);
		}
	}

	@Override
	public void onOpen(WebSocket webSocket, Response response) {
		for (String ca : this.adrressAccounts.keySet()) {
			WebSocketParamJsonRpcRequest req = WebSocketParamJsonRpcRequest.getSubscribeAccount(ca);
			try {
				String json = MAPPER.writeValueAsString(req);
				webSocket.send(json);
			} catch (Exception e) {
				log.error("Failed to send WebSocket JSON. Reason: {}", e);
			}
		}
	}

	private void handleAddressPurchase(String account, String addr, BigDecimal amount) {
		HashMap<String, BigDecimal> curr = this.adrressAccounts.get(account);
		if(curr==null) {
			curr = new HashMap<>();
		}
		BigDecimal currentBal = curr.get(addr);
		if (currentBal == null) {
			currentBal = amount.setScale(4, RoundingMode.HALF_EVEN);
		} else {
			currentBal = currentBal.add(amount).setScale(4, RoundingMode.HALF_EVEN);
			//this.adrressAccounts.put(account, curr);
			log.info("Address {} Trade volume is now {} SOL", addr, currentBal);

		}
		curr.put(addr, currentBal);
		this.adrressAccounts.put(account, curr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onMessage(WebSocket webSocket, String text) {
		try {
			// System.out.println("MESSAGE: " + text);
			final Map<String, Object> results = MAPPER.readValue(text, Map.class);
			final Map<String, Object> params = (Map<String, Object>) results.get("params");
			final Map<String, Object> res = (Map<String, Object>) params.get("result");
			final Map<String, Object> sig = (Map<String, Object>) res.get("value");
			if (sig.get("err") == null) {
				final TransactionLookupResponse tx = this.service
						.getTransactionBySignature(sig.get("signature").toString());

				final List<AccountKey> txIns = tx.getResult().getTransaction().getMessage().getAccountKeys();
				for (int i = 0; i < txIns.size(); i++) {

					final String account = txIns.get(i).getPubkey();
					if (account.equals("ComputeBudget111111111111111111111111111111")
							|| account.equals("11111111111111111111111111111111")
							|| account.equals("Sysvar1nstructions1111111111111111111111111")) {
						continue;
					}
					final Long preBalance = tx.getResult().getMeta().getPreBalances().get(i);
					final Long postBalance = tx.getResult().getMeta().getPostBalances().get(i);
					final double preBal = preBalance / (1000000000d);
					final double postBal = postBalance / (1000000000d);
					final Double diffBal = postBal - preBal;
					final String balString = diffBal.toString();
					if (!balString.contains("E") && !balString.equals("0.0")) {
						log.info("Account {} balanceChange={}", account, diffBal);
						final Optional<AccountKey> signer = tx.getTxSigner();
						this.handleAddressPurchase((signer.isPresent() ? signer.get().pubkey : "UNKOWN_CA"), account,
								new BigDecimal(diffBal).setScale(4, RoundingMode.HALF_EVEN));
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed to parse Solana Transaction. Reason: {}", e.getMessage());
		}
	}

	@Override
	public void onMessage(WebSocket webSocket, ByteString bytes) {
		// System.out.println("MESSAGE: " + bytes.hex());
	}

	@Override
	public void onClosing(WebSocket webSocket, int code, String reason) {
		webSocket.close(1000, null);
		System.out.println("CLOSE: " + code + " " + reason);
	}

	@Override
	public void onFailure(WebSocket webSocket, Throwable t, Response response) {
		t.printStackTrace();
	}

	@PreDestroy
	@Scheduled(fixedDelay=30000l)
	public void writeSstateToFile() {
		try {
			String json = MAPPER.writeValueAsString(this.adrressAccounts);
			Files.writeString(Path.of("C:/temp/dump.json"), json);

		} catch (Exception e) {
			log.error("Failed to write balances to dump.json. Reason: {}", e);
		}
	}

	public static void main(String... args) {
		new SolcraperWebSocket().run();
	}
}