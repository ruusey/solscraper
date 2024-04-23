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
	public String address;
	@JsonProperty("name")
	public String name;
	@JsonProperty("symbol")
	public String symbol;
	@JsonProperty("coingecko_coin_id")
	public String coingeckoCoinId;

}