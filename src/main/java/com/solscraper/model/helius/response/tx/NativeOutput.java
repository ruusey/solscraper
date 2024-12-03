package com.solscraper.model.helius.response.tx;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "account", "amount" })
@Generated("jsonschema2pojo")
public class NativeOutput {

	@JsonProperty("account")
	public String account;
	@JsonProperty("amount")
	public String amount;

}
