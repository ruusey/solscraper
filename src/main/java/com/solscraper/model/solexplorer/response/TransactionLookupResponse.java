package com.solscraper.model.solexplorer.response;

import java.util.Optional;

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
public class TransactionLookupResponse {

	@JsonProperty("jsonrpc")
	private String jsonrpc;
	@JsonProperty("result")
	private Result result;
	@JsonProperty("id")
	private String id;
	
	
	public Optional<AccountKey> getTxSigner() {
		return this.result.getTransaction().getMessage().getAccountKeys().stream().filter(key->key.signer).findAny();
	}

}