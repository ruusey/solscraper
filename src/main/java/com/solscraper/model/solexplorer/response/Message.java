package com.solscraper.model.solexplorer.response;

import java.util.List;
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
public class Message {

    @JsonProperty("accountKeys")
    public List<AccountKey> accountKeys;
    @JsonProperty("addressTableLookups")
    public List<Object> addressTableLookups;
    @JsonProperty("instructions")
    public List<TransactionInstruction> instructions;
    @JsonProperty("recentBlockhash")
    public String recentBlockhash;

}