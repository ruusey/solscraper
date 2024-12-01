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
public class JsonRpcRequest {
    private static final String RAYDIUM_TOKEN_POOL = "7YttLkHDoNj9wyDur5pM1ejNaAvT9X4eqaYcHQqtj2G5";
    @JsonProperty("method")
    private String method;
    @JsonProperty("jsonrpc")
    private String jsonrpc;
    @JsonProperty("params")
    private List<Object> params;
    @JsonProperty("id")
    private String id;

    public static JsonRpcRequest getRaydiumSignaturesRequest() {
       return getSignatureRequest(RAYDIUM_TOKEN_POOL);
    }

    public static JsonRpcRequest getSignatureRequest(String addr) {
    	return getSignatureRequest(addr, null, 10);
    }
    
    public static JsonRpcRequest getSignatureRequest(String addr, Integer limit) {
    	return getSignatureRequest(addr, null, limit);
    }
    
    public static JsonRpcRequest getSignatureRequest(String addr, String startSignature, Integer limit) {
        final Map<String, Object> extraParams = new HashMap<String, Object>();
        if(limit!=null) {
            extraParams.put("limit", limit);
        }
        if(startSignature!=null) {
            extraParams.put("before", startSignature);
        }

        return JsonRpcRequest.builder().method("getSignaturesForAddress").jsonrpc("2.0").params(Arrays.asList(addr, extraParams))
                .id(UUID.randomUUID().toString()).build();
    }

    public static JsonRpcRequest getTokenAccountRequest(String mintAddress) {
        final Map<String, Object> extraParams = new HashMap<String, Object>();
        extraParams.put("encoding", "jsonParsed");
        extraParams.put("commitment", "confirmed");
        return JsonRpcRequest.builder().method("getMultipleAccounts").jsonrpc("2.0").params(Arrays.asList(Arrays.asList(mintAddress), extraParams))
                .id(UUID.randomUUID().toString()).build();
    }

    public static JsonRpcRequest getTransactionRequest(String transactionSig) {
        final Map<String, Object> extraParams = new HashMap<String, Object>();
        extraParams.put("encoding", "jsonParsed");
        extraParams.put("commitment", "confirmed");
        extraParams.put("maxSupportedTransactionVersion", 0);
        return JsonRpcRequest.builder().method("getTransaction").jsonrpc("2.0").params(Arrays.asList(transactionSig, extraParams))
                .id(UUID.randomUUID().toString()).build();
    }
}