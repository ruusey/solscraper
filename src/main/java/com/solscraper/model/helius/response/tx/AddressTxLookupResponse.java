
package com.solscraper.model.helius.response.tx;

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