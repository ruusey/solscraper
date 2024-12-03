package com.solscraper.model.helius.response.tx;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nativeInput", "nativeOutput", "tokenInputs", "tokenOutputs", "tokenFees", "nativeFees",
		"innerSwaps" })
@Generated("jsonschema2pojo")
public class Swap {

	@JsonProperty("nativeInput")
	public NativeInput nativeInput;
	@JsonProperty("nativeOutput")
	public NativeOutput nativeOutput;
	@JsonProperty("tokenInputs")
	public List<TokenInput> tokenInputs;
	@JsonProperty("tokenOutputs")
	public List<TokenOutput> tokenOutputs;
	@JsonProperty("tokenFees")
	public List<TokenFee> tokenFees;
	@JsonProperty("nativeFees")
	public List<NativeFee> nativeFees;
	@JsonProperty("innerSwaps")
	public List<InnerSwap> innerSwaps;

}