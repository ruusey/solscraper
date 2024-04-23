package com.solscraper.model.solexplorer.response;

import java.util.List;
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
public class Meta {

	@JsonProperty("computeUnitsConsumed")
	public Integer computeUnitsConsumed;
	@JsonProperty("err")
	public Object err;
	@JsonProperty("fee")
	public Integer fee;
	@JsonProperty("innerInstructions")
	public List<InnerInstruction> innerInstructions;
	@JsonProperty("logMessages")
	public List<String> logMessages;
	@JsonProperty("postBalances")
	public List<Long> postBalances;
	@JsonProperty("postTokenBalances")
	public List<PostTokenBalance> postTokenBalances;
	@JsonProperty("preBalances")
	public List<Long> preBalances;
	@JsonProperty("preTokenBalances")
	public List<PreTokenBalance> preTokenBalances;
	@JsonProperty("rewards")
	public List<Object> rewards;


}