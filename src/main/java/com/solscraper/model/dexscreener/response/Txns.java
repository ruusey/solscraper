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
public class Txns {

	@JsonProperty("m5")
	public TimeFrame m5;
	@JsonProperty("h1")
	public TimeFrame h1;
	@JsonProperty("h6")
	public TimeFrame h6;
	@JsonProperty("h24")
	public TimeFrame h24;

}