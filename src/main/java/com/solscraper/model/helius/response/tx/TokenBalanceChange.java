package com.solscraper.model.helius.response.tx;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "userAccount", "tokenAccount", "mint", "rawTokenAmount" })
@Generated("jsonschema2pojo")
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