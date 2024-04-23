package com.solscraper.model.solexplorer.response;

import java.util.List;
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
public class SolExplorerResponse {

	@JsonProperty("jsonrpc")
	public String jsonrpc;
	@JsonProperty("result")
	public List<SolExplorerResult> result;
	@JsonProperty("id")
	public String id;

}