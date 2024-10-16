package com.solscraper.model.solexplorer.response;

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

	@JsonProperty("blockTime")
	private Integer blockTime;
	@JsonProperty("meta")
	private Meta meta;
	@JsonProperty("slot")
	private Integer slot;
	@JsonProperty("transaction")
	private Transaction transaction;
	@JsonProperty("version")
	private String version;
	@JsonProperty("value")
	private List<Object> value;

}