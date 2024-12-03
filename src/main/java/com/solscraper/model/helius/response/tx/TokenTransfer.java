package com.solscraper.model.helius.response.tx;

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
public class TokenTransfer {

	@JsonProperty("fromUserAccount")
	public String fromUserAccount;
	@JsonProperty("toUserAccount")
	public String toUserAccount;
	@JsonProperty("fromTokenAccount")
	public String fromTokenAccount;
	@JsonProperty("toTokenAccount")
	public String toTokenAccount;
	@JsonProperty("tokenAmount")
	public Integer tokenAmount;
	@JsonProperty("mint")
	public String mint;

}