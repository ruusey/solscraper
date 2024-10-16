package com.solscraper.model.helius.meta.response;

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
public class Metadata {

	@JsonProperty("description")
	private String description;
	@JsonProperty("name")
	private String name;
	@JsonProperty("symbol")
	private String symbol;
	@JsonProperty("token_standard")
	private String tokenStandard;

}