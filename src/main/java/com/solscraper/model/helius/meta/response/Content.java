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
public class Content {

	@JsonProperty("$schema")
	private String $schema;
	@JsonProperty("json_uri")
	private String jsonUri;
	@JsonProperty("files")
	private List<File> files;
	@JsonProperty("metadata")
	private Metadata metadata;
	@JsonProperty("links")
	private Links links;

}