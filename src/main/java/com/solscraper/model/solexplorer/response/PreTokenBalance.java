package com.solscraper.model.solexplorer.response;

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
public class PreTokenBalance {

	@JsonProperty("accountIndex")
	public Integer accountIndex;
	@JsonProperty("mint")
	public String mint;
	@JsonProperty("owner")
	public String owner;
	@JsonProperty("programId")
	public String programId;
}
