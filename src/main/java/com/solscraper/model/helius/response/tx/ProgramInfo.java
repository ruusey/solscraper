package com.solscraper.model.helius.response.tx;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "source", "account", "programName", "instructionName" })
@Generated("jsonschema2pojo")
public class ProgramInfo {

	@JsonProperty("source")
	public String source;
	@JsonProperty("account")
	public String account;
	@JsonProperty("programName")
	public String programName;
	@JsonProperty("instructionName")
	public String instructionName;

}