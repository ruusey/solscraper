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
	public String chainId;
	@JsonProperty("dexId")
	public String dexId;
	@JsonProperty("url")
	public String url;
	@JsonProperty("pairAddress")
	public String pairAddress;
	@JsonProperty("baseToken")
	public BaseToken baseToken;
	@JsonProperty("quoteToken")
	public QuoteToken quoteToken;
	@JsonProperty("priceNative")
	public String priceNative;
	@JsonProperty("priceUsd")
	public String priceUsd;
	@JsonProperty("txns")
	public Txns txns;
	@JsonProperty("volume")
	public Volume volume;
	@JsonProperty("priceChange")
	public PriceChange priceChange;
	@JsonProperty("liquidity")
	public Liquidity liquidity;
	@JsonProperty("fdv")
	public Long fdv;
	@JsonProperty("pairCreatedAt")
	public Long pairCreatedAt;
}