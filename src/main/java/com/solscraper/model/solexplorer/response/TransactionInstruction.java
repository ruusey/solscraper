package com.solscraper.model.solexplorer.response;

import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
public class TransactionInstruction {

    @JsonProperty("parsed")
    public Object parsed;
    @JsonProperty("program")
    public String program;
    @JsonProperty("programId")
    public String programId;
    @JsonProperty("stackHeight")
    public Object stackHeight;
    @JsonProperty("accounts")
    public List<String> accounts;
    @JsonProperty("data")
    public String data;

}