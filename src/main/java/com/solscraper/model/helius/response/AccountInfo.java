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
	public String key;
	@JsonProperty("isSigner")
	public Boolean isSigner;
	@JsonProperty("isWritable")
	public Boolean isWritable;
	@JsonProperty("lamports")
	public BigDecimal lamports;
	@JsonProperty("data")
	public Datas data;
	@JsonProperty("owner")
	public String owner;
	@JsonProperty("executable")
	public Boolean executable;
	@JsonProperty("rentEpoch")
	public BigDecimal rentEpoch;

}