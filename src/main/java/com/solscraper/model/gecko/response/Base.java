package com.solscraper.model.gecko.response;

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
public class Base {

	@JsonProperty("address")
	private String address;
	@JsonProperty("name")
	private String name;
	@JsonProperty("symbol")
	private String symbol;
	@JsonProperty("coingecko_coin_id")
	private String coingeckoCoinId;

}