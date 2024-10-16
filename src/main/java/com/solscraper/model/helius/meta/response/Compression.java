package com.solscraper.model.helius.meta.response;

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
public class Compression {

	@JsonProperty("eligible")
	private Boolean eligible;
	@JsonProperty("compressed")
	private Boolean compressed;
	@JsonProperty("data_hash")
	private String dataHash;
	@JsonProperty("creator_hash")
	private String creatorHash;
	@JsonProperty("asset_hash")
	private String assetHash;
	@JsonProperty("tree")
	private String tree;
	@JsonProperty("seq")
	private Integer seq;
	@JsonProperty("leaf_id")
	private Integer leafId;

}