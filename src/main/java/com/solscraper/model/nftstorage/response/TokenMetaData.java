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
	private String name;
	@JsonProperty("symbol")
	private String symbol;
	@JsonProperty("image")
	private String image;
	@JsonProperty("description")
	private String description;
	@JsonProperty("extensions")
	private Extensions extensions;
	@JsonProperty("tags")
	private List<String> tags;
	@JsonProperty("creator")
	private Creator creator;

}