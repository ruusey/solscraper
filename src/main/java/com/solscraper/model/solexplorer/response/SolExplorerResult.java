package com.solscraper.model.solexplorer.response;

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
public class SolExplorerResult {

	@JsonProperty("blockTime")
	public Integer blockTime;
	@JsonProperty("confirmationStatus")
	public String confirmationStatus;
	@JsonProperty("err")
	public Object err;
	@JsonProperty("memo")
	public Object memo;
	@JsonProperty("signature")
	public String signature;
	@JsonProperty("slot")
	public Integer slot;

}