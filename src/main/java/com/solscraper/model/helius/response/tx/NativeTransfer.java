package com.solscraper.model.helius.response.tx;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"fromUserAccount",
"toUserAccount",
"amount"
})
@Generated("jsonschema2pojo")
public class NativeTransfer {

@JsonProperty("fromUserAccount")
public String fromUserAccount;
@JsonProperty("toUserAccount")
public String toUserAccount;
@JsonProperty("amount")
public Integer amount;

}