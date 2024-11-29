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
public class TransactionSignaturesResult {

	@JsonProperty("blockTime")
	private Integer blockTime;
	@JsonProperty("confirmationStatus")
	private String confirmationStatus;
	@JsonProperty("err")
	private Object err;
	@JsonProperty("memo")
	private Object memo;
	@JsonProperty("signature")
	private String signature;
	@JsonProperty("slot")
	private Integer slot;

}