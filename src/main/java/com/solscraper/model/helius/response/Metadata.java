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
	private String tokenStandard;
	@JsonProperty("key")
	private String key;
	@JsonProperty("updateAuthority")
	private String updateAuthority;
	@JsonProperty("mint")
	private String mint;
	@JsonProperty("data")
	private Datas data;
	@JsonProperty("primarySaleHappened")
	private Boolean primarySaleHappened;
	@JsonProperty("isMutable")
	private Boolean isMutable;
	@JsonProperty("editionNonce")
	private BigDecimal editionNonce;
	@JsonProperty("uses")
	private Uses uses;
	@JsonProperty("collection")
	private Object collection;
	@JsonProperty("collectionDetails")
	private Object collectionDetails;

}