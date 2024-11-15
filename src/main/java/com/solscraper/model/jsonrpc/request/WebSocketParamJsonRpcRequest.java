package com.solscraper.model.jsonrpc.request;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebSocketParamJsonRpcRequest {
	@JsonProperty("method")
	private String method;
	@JsonProperty("jsonrpc")
	private String jsonrpc;
	@JsonProperty("params")
	private List<Object> params;
	@JsonProperty("id")
	private Integer id;

	
	
	
	
	public static Map<String, Object> getExtraParams() {
		Map<String, Object> map = new HashMap<>();
		map.put("commitment", "confirmed");
		map.put("encoding", "jsonParsed");
		map.put("transactionDetails", "full");
		map.put("showRewards", true);
		map.put("maxSupportedTransactionVersion", 0);
		return map;
	}

	public static Map<String, Object> getParams(String incAccount, String sig, String reqAccount) {
		Map<String, Object> map = new HashMap<>();
		map.put("vote", false);
		map.put("failed", false);
		if (sig != null) {

			map.put("signature", sig);

		}
		if (incAccount != null) {

			map.put("accountInclude", Arrays.asList(incAccount));

		}
		if (sig != null) {

			map.put("accountRequired", Arrays.asList(reqAccount));

		}

		return map;
	}

	public static WebSocketParamJsonRpcRequest getSubscribeAccount(String accountKey) {
		return WebSocketParamJsonRpcRequest.builder()
				.method("logsSubscribe")
				.id(420)
				.params(Arrays.asList(Map.of("mentions", Arrays.asList(accountKey)), Map.of("commitment", "finalized")))
				.jsonrpc("2.0")
				.build();
	}
	
	public static WebSocketParamJsonRpcRequest getRequesst(String accountKey, String method) {
		return WebSocketParamJsonRpcRequest.builder()
				.method(method)
				.id(420)
				.params(Arrays.asList(accountKey, Map.of("encoding", "jsonParsed", "commitment", "finalized")))
				.jsonrpc("2.0")
				.build();
	}

}