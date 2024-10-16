package com.solscraper.model.helius.response;

import java.math.BigDecimal;

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
public class AccountInfo {

	@JsonProperty("key")
	private String key;
	@JsonProperty("isSigner")
	private Boolean isSigner;
	@JsonProperty("isWritable")
	private Boolean isWritable;
	@JsonProperty("lamports")
	private BigDecimal lamports;
	@JsonProperty("data")
	private Datas data;
	@JsonProperty("owner")
	private String owner;
	@JsonProperty("executable")
	private Boolean executable;
	@JsonProperty("rentEpoch")
	private BigDecimal rentEpoch;

}