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
public class Metadata {

	@JsonProperty("tokenStandard")
	public String tokenStandard;
	@JsonProperty("key")
	public String key;
	@JsonProperty("updateAuthority")
	public String updateAuthority;
	@JsonProperty("mint")
	public String mint;
	@JsonProperty("data")
	public Datas data;
	@JsonProperty("primarySaleHappened")
	public Boolean primarySaleHappened;
	@JsonProperty("isMutable")
	public Boolean isMutable;
	@JsonProperty("editionNonce")
	public BigDecimal editionNonce;
	@JsonProperty("uses")
	public Uses uses;
	@JsonProperty("collection")
	public Object collection;
	@JsonProperty("collectionDetails")
	public Object collectionDetails;

}