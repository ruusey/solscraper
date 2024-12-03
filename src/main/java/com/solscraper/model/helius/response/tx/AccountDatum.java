package com.solscraper.model.helius.response.tx;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "account", "nativeBalanceChange", "tokenBalanceChanges" })
@Generated("jsonschema2pojo")
public class AccountDatum {

	@JsonProperty("account")
	public String account;
	@JsonProperty("nativeBalanceChange")
	public Integer nativeBalanceChange;
	@JsonProperty("tokenBalanceChanges")
	public List<TokenBalanceChange> tokenBalanceChanges;

}