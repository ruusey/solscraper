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
public class Info {

	@JsonProperty("destination")
	public String destination;
	@JsonProperty("lamports")
	public Integer lamports;
	@JsonProperty("source")
	public String source;
	@JsonProperty("account")
	public String account;
	@JsonProperty("space")
	public Integer space;
	@JsonProperty("owner")
	public String owner;
	@JsonProperty("decimals")
	public Integer decimals;
	@JsonProperty("mint")
	public String mint;
	@JsonProperty("mintAuthority")
	public String mintAuthority;
	@JsonProperty("rentSysvar")
	public String rentSysvar;
	@JsonProperty("systemProgram")
	public String systemProgram;
	@JsonProperty("tokenProgram")
	public String tokenProgram;
	@JsonProperty("wallet")
	public String wallet;
	@JsonProperty("extensionTypes")
	public List<String> extensionTypes;
	@JsonProperty("newAccount")
	public String newAccount;
	@JsonProperty("amount")
	public String amount;
	@JsonProperty("authority")
	public String authority;

}