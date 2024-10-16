package com.solscraper.model.dexscreener.response;

import java.math.BigDecimal;

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
public class PriceChange {

	@JsonProperty("m5")
	private BigDecimal m5;
	@JsonProperty("h1")
	private BigDecimal h1;
	@JsonProperty("h6")
	private BigDecimal h6;
	@JsonProperty("h24")
	private BigDecimal h24;

}