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