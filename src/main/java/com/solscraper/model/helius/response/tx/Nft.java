
package com.solscraper.model.helius.response.tx;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Nft {

	@JsonProperty("description")
	public String description;
	@JsonProperty("type")
	public String type;
	@JsonProperty("source")
	public String source;
	@JsonProperty("amount")
	public Integer amount;
	@JsonProperty("fee")
	public Integer fee;
	@JsonProperty("feePayer")
	public String feePayer;
	@JsonProperty("signature")
	public String signature;
	@JsonProperty("slot")
	public Integer slot;
	@JsonProperty("timestamp")
	public Integer timestamp;
	@JsonProperty("saleType")
	public String saleType;
	@JsonProperty("buyer")
	public String buyer;
	@JsonProperty("seller")
	public String seller;
	@JsonProperty("staker")
	public String staker;
	@JsonProperty("nfts")
	public List<Nft> nfts;
	@JsonProperty("mint")
	public String mint;
	@JsonProperty("tokenStandard")
	public String tokenStandard;
}