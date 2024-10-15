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
	public String royaltyModel;
	@JsonProperty("target")
	public Object target;
	@JsonProperty("percent")
	public Integer percent;
	@JsonProperty("basis_points")
	public Integer basisPoints;
	@JsonProperty("primary_sale_happened")
	public Boolean primarySaleHappened;
	@JsonProperty("locked")
	public Boolean locked;

}