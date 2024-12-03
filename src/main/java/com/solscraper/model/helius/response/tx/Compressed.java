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