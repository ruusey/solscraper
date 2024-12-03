package com.solscraper.model.helius.response.tx;

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
public class TokenBalanceChange {

	@JsonProperty("userAccount")
	public String userAccount;
	@JsonProperty("tokenAccount")
	public String tokenAccount;
	@JsonProperty("mint")
	public String mint;
	@JsonProperty("rawTokenAmount")
	public RawTokenAmount rawTokenAmount;

}