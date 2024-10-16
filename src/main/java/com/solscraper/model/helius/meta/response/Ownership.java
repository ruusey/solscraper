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
public class Ownership {

	@JsonProperty("frozen")
	private Boolean frozen;
	@JsonProperty("delegated")
	private Boolean delegated;
	@JsonProperty("delegate")
	private Object delegate;
	@JsonProperty("ownership_model")
	private String ownershipModel;
	@JsonProperty("owner")
	private String owner;

}