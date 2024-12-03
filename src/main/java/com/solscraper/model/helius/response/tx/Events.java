package com.solscraper.model.helius.response.tx;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "nft", "swap", "compressed", "distributeCompressionRewards", "setAuthority" })
@Generated("jsonschema2pojo")
public class Events {

	@JsonProperty("nft")
	public Nft nft;
	@JsonProperty("swap")
	public Swap swap;
	@JsonProperty("compressed")
	public Compressed compressed;
	@JsonProperty("distributeCompressionRewards")
	public DistributeCompressionRewards distributeCompressionRewards;
	@JsonProperty("setAuthority")
	public SetAuthority setAuthority;

}