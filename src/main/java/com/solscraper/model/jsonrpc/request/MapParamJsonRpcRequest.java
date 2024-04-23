package com.solscraper.model.jsonrpc.request;

import java.util.HashMap;
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
public class MapParamJsonRpcRequest {
	@JsonProperty("method")
	private String method;
	@JsonProperty("jsonrpc")
	private String jsonrpc;
	@JsonProperty("params")
	private Map<String, Object> params;
	@JsonProperty("id")
	private String id;

	
	public static MapParamJsonRpcRequest getHeliusAssetLookupReqest(String mintAddress) {
		final Map<String, Object> extraParams = new HashMap<String, Object>();
		extraParams.put("id", mintAddress);

		return MapParamJsonRpcRequest.builder().method("getAsset")
				.jsonrpc("2.0")
				.params(extraParams)
				.id(UUID.randomUUID().toString())
				.build();
	}
	
	
}