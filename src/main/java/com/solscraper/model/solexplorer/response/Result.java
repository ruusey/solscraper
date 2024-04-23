package com.solscraper.model.solexplorer.response;

import java.util.List;
import java.util.Map;

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
	public Integer blockTime;
	@JsonProperty("meta")
	public Meta meta;
	@JsonProperty("slot")
	public Integer slot;
	@JsonProperty("transaction")
	public Transaction transaction;
	@JsonProperty("version")
	public String version;
	@JsonProperty("value")
	public List<Object> value;

}