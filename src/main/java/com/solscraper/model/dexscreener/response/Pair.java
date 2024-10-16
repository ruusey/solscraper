package com.solscraper.model.dexscreener.response;

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
public class Pair {

	@JsonProperty("chainId")
	private String chainId;
	@JsonProperty("dexId")
	private String dexId;
	@JsonProperty("url")
	private String url;
	@JsonProperty("pairAddress")
	private String pairAddress;
	@JsonProperty("baseToken")
	private BaseToken baseToken;
	@JsonProperty("quoteToken")
	private QuoteToken quoteToken;
	@JsonProperty("priceNative")
	private String priceNative;
	@JsonProperty("priceUsd")
	private String priceUsd;
	@JsonProperty("txns")
	private Txns txns;
	@JsonProperty("volume")
	private Volume volume;
	@JsonProperty("priceChange")
	private PriceChange priceChange;
	@JsonProperty("liquidity")
	private Liquidity liquidity;
	@JsonProperty("fdv")
	private Long fdv;
	@JsonProperty("pairCreatedAt")
	private Long pairCreatedAt;
}