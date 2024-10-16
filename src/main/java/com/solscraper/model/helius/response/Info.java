package com.solscraper.model.helius.response;

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
public class Info {

	@JsonProperty("decimals")
	private BigDecimal decimals;
	@JsonProperty("freezeAuthority")
	private String freezeAuthority;
	@JsonProperty("isInitialized")
	private Boolean isInitialized;
	@JsonProperty("mintAuthority")
	private String mintAuthority;
	@JsonProperty("supply")
	private String supply;

}