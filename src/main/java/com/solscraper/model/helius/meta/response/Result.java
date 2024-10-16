package com.solscraper.model.helius.meta.response;

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
public class Result {

	@JsonProperty("interface")
	private String _interface;
	@JsonProperty("id")
	private String id;
	@JsonProperty("content")
	private Content content;
	@JsonProperty("authorities")
	private List<Authority> authorities;
	@JsonProperty("compression")
	private Compression compression;
	@JsonProperty("grouping")
	private List<Object> grouping;
	@JsonProperty("royalty")
	private Royalty royalty;
	@JsonProperty("creators")
	private List<Object> creators;
	@JsonProperty("ownership")
	private Ownership ownership;
	@JsonProperty("supply")
	private Object supply;
	@JsonProperty("mutable")
	private Boolean mutable;
	@JsonProperty("burnt")
	private Boolean burnt;
	@JsonProperty("token_info")
	private TokenInfo tokenInfo;

}