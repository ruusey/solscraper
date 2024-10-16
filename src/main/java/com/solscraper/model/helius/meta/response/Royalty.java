package com.solscraper.model.helius.meta.response;

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
public class Royalty {

	@JsonProperty("royalty_model")
	private String royaltyModel;
	@JsonProperty("target")
	private Object target;
	@JsonProperty("percent")
	private Integer percent;
	@JsonProperty("basis_points")
	private Integer basisPoints;
	@JsonProperty("primary_sale_happened")
	private Boolean primarySaleHappened;
	@JsonProperty("locked")
	private Boolean locked;

}