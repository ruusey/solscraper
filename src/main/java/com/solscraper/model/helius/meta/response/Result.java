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
	public String _interface;
	@JsonProperty("id")
	public String id;
	@JsonProperty("content")
	public Content content;
	@JsonProperty("authorities")
	public List<Authority> authorities;
	@JsonProperty("compression")
	public Compression compression;
	@JsonProperty("grouping")
	public List<Object> grouping;
	@JsonProperty("royalty")
	public Royalty royalty;
	@JsonProperty("creators")
	public List<Object> creators;
	@JsonProperty("ownership")
	public Ownership ownership;
	@JsonProperty("supply")
	public Object supply;
	@JsonProperty("mutable")
	public Boolean mutable;
	@JsonProperty("burnt")
	public Boolean burnt;
	@JsonProperty("token_info")
	public TokenInfo tokenInfo;

}