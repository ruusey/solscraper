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
	public BigDecimal decimals;
	@JsonProperty("freezeAuthority")
	public String freezeAuthority;
	@JsonProperty("isInitialized")
	public Boolean isInitialized;
	@JsonProperty("mintAuthority")
	public String mintAuthority;
	@JsonProperty("supply")
	public String supply;

}