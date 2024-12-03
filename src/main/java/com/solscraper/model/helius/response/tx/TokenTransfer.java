package com.solscraper.model.helius.response.tx;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "fromUserAccount", "toUserAccount", "fromTokenAccount", "toTokenAccount", "tokenAmount", "mint" })
@Generated("jsonschema2pojo")
public class TokenTransfer {

	@JsonProperty("fromUserAccount")
	public String fromUserAccount;
	@JsonProperty("toUserAccount")
	public String toUserAccount;
	@JsonProperty("fromTokenAccount")
	public String fromTokenAccount;
	@JsonProperty("toTokenAccount")
	public String toTokenAccount;
	@JsonProperty("tokenAmount")
	public Integer tokenAmount;
	@JsonProperty("mint")
	public String mint;

}