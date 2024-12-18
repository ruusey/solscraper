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
public class Datas {

	@JsonProperty("parsed")
	private Parsed parsed;
	@JsonProperty("program")
	private String program;
	@JsonProperty("space")
	private BigDecimal space;
	@JsonProperty("name")
	private String name;
	@JsonProperty("symbol")
	private String symbol;
	@JsonProperty("uri")
	private String uri;
	@JsonProperty("sellerFeeBasisPoints")
	private BigDecimal sellerFeeBasisPoints;
	@JsonProperty("creators")
	private Object creators;

}