package com.solscraper.model.solexplorer.response;

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
public class Instruction {

	@JsonProperty("parsed")
	private Parsed parsed;
	@JsonProperty("program")
	private String program;
	@JsonProperty("programId")
	private String programId;
	@JsonProperty("stackHeight")
	private Integer stackHeight;
	@JsonProperty("accounts")
	private List<String> accounts;
	@JsonProperty("data")
	private String data;

}