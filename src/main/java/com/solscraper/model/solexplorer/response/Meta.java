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
	private Integer computeUnitsConsumed;
	@JsonProperty("err")
	private Object err;
	@JsonProperty("fee")
	private Integer fee;
	@JsonProperty("innerInstructions")
	private List<InnerInstruction> innerInstructions;
	@JsonProperty("logMessages")
	private List<String> logMessages;
	@JsonProperty("postBalances")
	private List<Long> postBalances;
	@JsonProperty("postTokenBalances")
	private List<PostTokenBalance> postTokenBalances;
	@JsonProperty("preBalances")
	private List<Long> preBalances;
	@JsonProperty("preTokenBalances")
	private List<PreTokenBalance> preTokenBalances;
	@JsonProperty("rewards")
	private List<Object> rewards;


}