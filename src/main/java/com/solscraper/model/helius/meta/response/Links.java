package com.solscraper.model.helius.meta.response;

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"image"
})
@Generated("jsonschema2pojo")
public class Links {

@JsonProperty("image")
public String image;

}