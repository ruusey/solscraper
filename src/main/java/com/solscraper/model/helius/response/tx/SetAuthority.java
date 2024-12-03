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