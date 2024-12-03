package com.solscraper.model.helius.response.tx;

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
public class InnerSwap {

	@JsonProperty("tokenInputs")
	public List<TokenInput> tokenInputs;
	@JsonProperty("tokenOutputs")
	public List<TokenOutput> tokenOutputs;
	@JsonProperty("tokenFees")
	public List<TokenFee> tokenFees;
	@JsonProperty("nativeFees")
	public List<NativeFee> nativeFees;
	@JsonProperty("programInfo")
	public ProgramInfo programInfo;

}