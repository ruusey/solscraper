
package com.solscraper.model.helius.response.tx;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "description", "type", "source", "fee", "feePayer", "signature", "slot", "timestamp",
		"nativeTransfers", "tokenTransfers", "accountData", "transactionError", "instructions", "events" })
@Generated("jsonschema2pojo")
public class AddressTxLookupResponse {

	@JsonProperty("description")
	public String description;
	@JsonProperty("type")
	public String type;
	@JsonProperty("source")
	public String source;
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
	@JsonProperty("nativeTransfers")
	public List<NativeTransfer> nativeTransfers;
	@JsonProperty("tokenTransfers")
	public List<TokenTransfer> tokenTransfers;
	@JsonProperty("accountData")
	public List<AccountDatum> accountData;
	@JsonProperty("transactionError")
	public TransactionError transactionError;
	@JsonProperty("instructions")
	public List<Instruction> instructions;
	@JsonProperty("events")
	public Events events;

}