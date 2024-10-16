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
	private String destination;
	@JsonProperty("lamports")
	private Integer lamports;
	@JsonProperty("source")
	private String source;
	@JsonProperty("account")
	private String account;
	@JsonProperty("space")
	private Integer space;
	@JsonProperty("owner")
	private String owner;
	@JsonProperty("decimals")
	private Integer decimals;
	@JsonProperty("mint")
	private String mint;
	@JsonProperty("mintAuthority")
	private String mintAuthority;
	@JsonProperty("rentSysvar")
	private String rentSysvar;
	@JsonProperty("systemProgram")
	private String systemProgram;
	@JsonProperty("tokenProgram")
	private String tokenProgram;
	@JsonProperty("wallet")
	private String wallet;
	@JsonProperty("extensionTypes")
	private List<String> extensionTypes;
	@JsonProperty("newAccount")
	private String newAccount;
	@JsonProperty("amount")
	private String amount;
	@JsonProperty("authority")
	private String authority;

}