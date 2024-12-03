package com.solscraper.model.helius.response.tx;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"tokenAmount",
"decimals"
})
@Generated("jsonschema2pojo")
public class RawTokenAmount {

@JsonProperty("tokenAmount")
public String tokenAmount;
@JsonProperty("decimals")
public Integer decimals;

}