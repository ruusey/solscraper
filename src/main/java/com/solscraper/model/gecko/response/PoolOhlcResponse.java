package com.solscraper.model.gecko.response;

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
public class PoolOhlcResponse {
	@JsonProperty("data")
	public Datas data;
	@JsonProperty("meta")
	public Meta meta;
	
	public List<List<Double>> getTicks(){
		return this.data.attributes.ohlcvList;
	}
}
