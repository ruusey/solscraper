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
	public Parsed parsed;
	@JsonProperty("program")
	public String program;
	@JsonProperty("programId")
	public String programId;
	@JsonProperty("stackHeight")
	public Integer stackHeight;
	@JsonProperty("accounts")
	public List<String> accounts;
	@JsonProperty("data")
	public String data;

}