package com.solscraper.model.nftstorage.response;

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
public class TokenMetaData {

	@JsonProperty("name")
	public String name;
	@JsonProperty("symbol")
	public String symbol;
	@JsonProperty("image")
	public String image;
	@JsonProperty("description")
	public String description;
	@JsonProperty("extensions")
	public Extensions extensions;
	@JsonProperty("tags")
	public List<String> tags;
	@JsonProperty("creator")
	public Creator creator;

}