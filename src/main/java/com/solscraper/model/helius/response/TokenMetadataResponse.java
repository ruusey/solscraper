package com.solscraper.model.helius.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenMetadataResponse {

	@JsonProperty("account")
	private String account;
	@JsonProperty("onChainAccountInfo")
	private OnChainAccountInfo onChainAccountInfo;
	@JsonProperty("onChainMetadata")
	private OnChainMetadata onChainMetadata;
	@JsonProperty("legacyMetadata")
	private Object legacyMetadata;

}