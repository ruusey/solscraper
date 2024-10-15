package com.solscraper.model.helius.meta.response;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"description",
"name",
"symbol",
"token_standard"
})
@Generated("jsonschema2pojo")
public class Metadata {

@JsonProperty("description")
public String description;
@JsonProperty("name")
public String name;
@JsonProperty("symbol")
public String symbol;
@JsonProperty("token_standard")
public String tokenStandard;

}