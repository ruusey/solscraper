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
	public Parsed parsed;
	@JsonProperty("program")
	public String program;
	@JsonProperty("space")
	public BigDecimal space;
	@JsonProperty("name")
	public String name;
	@JsonProperty("symbol")
	public String symbol;
	@JsonProperty("uri")
	public String uri;
	@JsonProperty("sellerFeeBasisPoints")
	public BigDecimal sellerFeeBasisPoints;
	@JsonProperty("creators")
	public Object creators;

}