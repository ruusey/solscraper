package com.solscraper.model.helius.response.tx;

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
public class AccountDatum {

	@JsonProperty("account")
	public String account;
	@JsonProperty("nativeBalanceChange")
	public Integer nativeBalanceChange;
	@JsonProperty("tokenBalanceChanges")
	public List<TokenBalanceChange> tokenBalanceChanges;

}