package com.solscraper.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

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
	private static final ObjectMapper MAPPER = new ObjectMapper();
	@Value("${service.helius.websocket}")
	private String heliusUrl;

	@Autowired
	private SolscraperService service;

	@EventListener(ApplicationReadyEvent.class)
	public void run() {
		log.info("Running Helius WebSocket transaction Listener");
		OkHttpClient client = new OkHttpClient.Builder().readTimeout(3000, TimeUnit.MILLISECONDS).build();

		Request request = new Request.Builder().url(heliusUrl).build();
		client.newWebSocket(request, this);

//		// Trigger shutdown of the dispatcher's executor so this process exits
//		// immediately.
//		client.dispatcher().executorService().shutdown();
	}

	@Override
	public void onOpen(WebSocket webSocket, Response response) {
		WebSocketParamJsonRpcRequest req = WebSocketParamJsonRpcRequest
				.getSubscribeAccount("DEALERKFspSo5RoXNnKAhRPhTcvJeqeEgAgZsNSjCx5E");

		try {
			String json = MAPPER.writeValueAsString(req);
			webSocket.send(json);
		} catch (Exception e) {
			log.error("Failed to send WebSocket JSON. Reason: {}", e);

		}

	}

	@Override
	public void onMessage(WebSocket webSocket, String text) {
		try {
			//System.out.println("MESSAGE: " + text);
			Map<String, Object> results = MAPPER.readValue(text, Map.class);
			Map<String, Object> params = (Map<String, Object>) results.get("params");
			Map<String, Object> res = (Map<String, Object>) params.get("result");
			Map<String, Object> sig = (Map<String, Object>) res.get("value");
			if (sig.get("err") == null) {
				TransactionLookupResponse tx = this.service.getTransactionBySignature(sig.get("signature").toString());

				List<AccountKey> txIns = tx.getResult().getTransaction().getMessage().getAccountKeys();
				for (int i = 0; i < txIns.size(); i++) {

					final String account = txIns.get(i).getPubkey();
					if (account.equals("ComputeBudget111111111111111111111111111111")
							|| account.equals("11111111111111111111111111111111")
							|| account.equals("Sysvar1nstructions1111111111111111111111111")) {
						continue;
					}
					Long preBalance = tx.getResult().getMeta().getPreBalances().get(i);
					Long postBalance = tx.getResult().getMeta().getPostBalances().get(i);
					double preBal = preBalance / (1000000000d);
					double postBal = postBalance / (1000000000d);
					Double diffBal = postBal - preBal;
					String balString = diffBal.toString();
					if (!balString.contains("E") && !balString.equals("0.0")) {
						log.info("Account {} balanceChange={}", account, diffBal);
					}
				}
				//log.info("Found Transaction {}", tx);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onMessage(WebSocket webSocket, ByteString bytes) {
		//System.out.println("MESSAGE: " + bytes.hex());
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

	public static void main(String... args) {
		new SolcraperWebSocket().run();
	}
}