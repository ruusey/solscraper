package com.solscraper.model.helius.response.tx;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "accounts", "data", "programId" })
@Generated("jsonschema2pojo")
public class InnerInstruction {

	@JsonProperty("accounts")
	public List<String> accounts;
	@JsonProperty("data")
	public String data;
	@JsonProperty("programId")
	public String programId;

}