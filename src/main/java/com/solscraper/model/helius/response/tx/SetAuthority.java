package com.solscraper.model.helius.response.tx;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "account", "from", "to", "instructionIndex", "innerInstructionIndex" })
@Generated("jsonschema2pojo")
public class SetAuthority {

	@JsonProperty("account")
	public String account;
	@JsonProperty("from")
	public String from;
	@JsonProperty("to")
	public String to;
	@JsonProperty("instructionIndex")
	public Integer instructionIndex;
	@JsonProperty("innerInstructionIndex")
	public Integer innerInstructionIndex;

}