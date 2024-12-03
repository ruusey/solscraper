package com.solscraper.model.helius.response.tx;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "type", "treeId", "assetId", "leafIndex", "instructionIndex", "innerInstructionIndex",
		"newLeafOwner", "oldLeafOwner" })
@Generated("jsonschema2pojo")
public class Compressed {

	@JsonProperty("type")
	public String type;
	@JsonProperty("treeId")
	public String treeId;
	@JsonProperty("assetId")
	public String assetId;
	@JsonProperty("leafIndex")
	public Integer leafIndex;
	@JsonProperty("instructionIndex")
	public Integer instructionIndex;
	@JsonProperty("innerInstructionIndex")
	public Integer innerInstructionIndex;
	@JsonProperty("newLeafOwner")
	public String newLeafOwner;
	@JsonProperty("oldLeafOwner")
	public String oldLeafOwner;

}