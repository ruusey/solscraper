package com.solscraper.service;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Date;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.solscraper.model.dexscreener.response.BaseToken;
import com.solscraper.model.dexscreener.response.DexScreenerResponse;
import com.solscraper.model.dexscreener.response.Pair;
import com.solscraper.model.discord.webhook.WebhookPostRequest;
import com.solscraper.model.helius.meta.response.Authority;
import com.solscraper.model.helius.meta.response.HeliusMetadataResponse;
import com.solscraper.model.helius.meta.response.Result;
import com.solscraper.model.helius.request.TokenMetadataRequest;
import com.solscraper.model.helius.response.Metadata;
import com.solscraper.model.helius.response.TokenMetadataResponse;
import com.solscraper.model.jsonrpc.request.JsonRpcRequest;
import com.solscraper.model.jsonrpc.request.MapParamJsonRpcRequest;
import com.solscraper.model.nftstorage.response.TokenMetaData;
import com.solscraper.model.solexplorer.response.InnerInstruction;
import com.solscraper.model.solexplorer.response.Instruction;
import com.solscraper.model.solexplorer.response.SolExplorerResponse;
import com.solscraper.model.solexplorer.response.SolExplorerResult;
import com.solscraper.model.solexplorer.response.TransactionLookupResponse;
import com.solscraper.util.ApiSessionOkHttp;
import com.solscraper.util.WorkerThread;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@SuppressWarnings("unused")
public class SolscraperService {
	private static final transient String TOKEN_BUY_MSG = "** BUY THIS ** Token pair base information located: \n<b>{0}\n{1}\n{2}\nMCAP:{3}\n{4}\n{5}</b>";
	private static final transient String TOKEN_MSG = "New token mint found but did not meet purchase criteria: \n{0}*\n{1}\n{2}\n{3}\n{4}";
	
	private transient ApiSessionOkHttp solExplorerApi;
	private transient ApiSessionOkHttp dexScreenerApi;
	private transient ApiSessionOkHttp discordApi;
	private transient ApiSessionOkHttp heliusApi;
	@SuppressWarnings("unused")
	private transient ApiSessionOkHttp heliusApi2;
	private transient ApiSessionOkHttp genericApi;
	
	private final transient TelegramBotService telegramService;
	private transient boolean shutdown;
	private final transient List<String> seenTransactions;
	private transient boolean firstFetch = true;
	
	@Value("${service.discord.webhook.raw}")
	private String rawDiscordWebhook;
	@Value("${service.discord.webhook.token}")
	private String tokenDiscordWebhook;
	@Value("${service.helius0}")
	private String helius0Url;
	@Value("${service.helius1}")
	private String helius1Url;
	
	public SolscraperService(@Autowired final TelegramBotService telegramService) {
		this.telegramService = telegramService;
		this.shutdown = false;
		this.seenTransactions = new ArrayList<>();
	}
	
	@PostConstruct
	private void initApis() {
		this.solExplorerApi = new ApiSessionOkHttp("https://explorer-api.mainnet-beta.solana.com");
		this.dexScreenerApi = new ApiSessionOkHttp("https://api.dexscreener.com/latest");
		this.discordApi = new ApiSessionOkHttp("https://discordapp.com");
		this.heliusApi = new ApiSessionOkHttp(this.helius0Url);
		this.heliusApi2 = new ApiSessionOkHttp(this.helius1Url);
		this.genericApi = new ApiSessionOkHttp("");
		Runtime.getRuntime().addShutdownHook(this.getShutdownManager());
	}

	@EventListener(ApplicationReadyEvent.class)
	public void start() throws Exception {
		// Loop while we haven't shutdown
		while (!this.shutdown) {
			try {
				// Execute a POST to SolExplorer to get the latest transactions against the
				// Raydium Token Program Contract
				final String response = this.solExplorerApi.executePost("", JsonRpcRequest.getSignatureRequest());
				final SolExplorerResponse solscan = this.solExplorerApi.parseResponse(response, SolExplorerResponse.class);
				// For each row in the transactions table
				if(this.firstFetch) {
					this.firstFetch=false;
					solscan.getResult().forEach(result->this.seenTransactions.add(result.getSignature()));
				}
				for(final SolExplorerResult result : solscan.getResult()) {
					if (this.seenTransactions.contains(result.getSignature()) || result.getErr()!=null) {
						continue;
					} else {
						this.seenTransactions.add(result.getSignature());
					}

					final String signature = result.getSignature();
					final JsonRpcRequest tx = JsonRpcRequest.getTransactionRequest(signature);
					final String transaction = this.solExplorerApi.executePost("", tx);

					// See if we can find successful mint information in the transaction
					final int startIdx = transaction.indexOf("\"mint\"");
					if (startIdx == -1) {
						log.info("No Mint found in transaction");
						continue;
					}

					final TransactionLookupResponse transactionParsed = this.solExplorerApi.parseResponse(transaction,
							TransactionLookupResponse.class);
					String mintAccount = null;
					// Loop through all the instructions from the transaction to find the
					// initializeTokenAccount instruction (Has Mint, New Pair Information)
					for (final InnerInstruction instruction : transactionParsed.result.meta.innerInstructions) {
						for (final Instruction innerInstruction : instruction.instructions) {
							if (innerInstruction == null || innerInstruction.parsed == null
									|| innerInstruction.parsed.getType() == null)
								continue;
							if (innerInstruction.parsed.getType().contains("initializeAccount")) {
								mintAccount = innerInstruction.parsed.info.get("mint").toString();
								break;
							}
						}
					}
					if(mintAccount.equalsIgnoreCase("So11111111111111111111111111111111111111112")) {
						continue;
					}
					// If we found newly minted token information
					final String mintAccountFinal = mintAccount;
					final long blockTimeFinal = transactionParsed.getResult().getBlockTime()*1000l;
					if (mintAccount != null) {
						final Runnable quickTokenData = () ->{
							try {
								final TokenMetadataRequest metaDataRequest = TokenMetadataRequest.builder().mintAccounts(Arrays.asList(mintAccountFinal)).includeOffChain(false).build();
//
								final MapParamJsonRpcRequest assetRequest = MapParamJsonRpcRequest.getHeliusAssetLookupReqest(mintAccountFinal);
								final String assetResponse = this.heliusApi2.executePost("", assetRequest);
								final HeliusMetadataResponse heliusAssetInfo = this.heliusApi2.parseResponse(assetResponse, HeliusMetadataResponse.class);
								final Result assetResult = heliusAssetInfo.result;
								// TODO: Add back in socials and more in depth authority information
								final StringBuilder builder = new StringBuilder();
								builder.append("<u>LATEST MINT @"+new Date(blockTimeFinal)+"</u>\n");
								builder.append("<b>" + assetResult.content.getMetadata().name + " (" + assetResult.content.getMetadata().symbol + ")</b>\n");
								builder.append("<b>CA: </b>"+assetResult.id + "\n");
								for(Authority o : assetResult.authorities) {
									builder.append("<b>Authority Addr: </b> "+ o.getAddress() + "\n");
									builder.append("<b>	Scope: </b> "+ o.getScopes().stream().collect(Collectors.joining(", ")) + "\n");
								}
								final String imageUrl = assetResult.content.getFiles().get(0).uri;
								final String imageUrl0 = assetResult.content.getFiles().get(0).cdnUri;
								this.telegramService.sendGroupMessage(builder.toString(), imageUrl0);
							}catch(Exception e) {
								log.error("Failed to get quick data from Solana Explorer for quick token data, {}",e);
							}
							
						};
						WorkerThread.submitAndForkRun(quickTokenData);

						final Runnable getDexScreenerData = () ->{
							try {
								Thread.sleep(100000);
								final DexScreenerResponse dexScreenerResponse = this.searchTokenPoolInformation(mintAccountFinal);
								if (dexScreenerResponse != null && dexScreenerResponse.pairs != null
										&& dexScreenerResponse.pairs.size() > 0) {
									final Pair pair = dexScreenerResponse.pairs.get(0);
									final BaseToken token = pair.getBaseToken();
						
									if(new BigDecimal(pair.getFdv()).compareTo(new BigDecimal(80000))==1) {
										final String msg = MessageFormat.format(TOKEN_BUY_MSG, token.getName()+" ("+token.getSymbol()+") "+token.getAddress(), pair.getVolume(), pair.getLiquidity(), pair.getFdv(), pair.getUrl(), "Mint Datetime: "+new Date(blockTimeFinal));
										log.info(msg);
										//this.postToDiscordWebhook(msg);
										this.telegramService.sendSimpleMessage(msg);

										
									}else {
										final String msg = MessageFormat.format(TOKEN_MSG, token.getName()+" ("+token.getSymbol()+") "+token.getAddress(), pair.getVolume(), pair.getLiquidity(), pair.getFdv(), pair.getUrl());
										log.info(msg);
										//this.telegramService.sendGroupMessage(msg);
										//this.postToDiscordWebhook(msg);
									}
								}else {
									log.error("No pair information found for Address {}", mintAccountFinal);
								}
							}catch(Exception e) {
								log.error("Failed to get quick data from Solana Explorer for quick token data, {}",e);
							}
						};
						WorkerThread.submitAndForkRun(getDexScreenerData);
					}
					Thread.sleep(1200);
				}
			}catch(Exception e) {
				log.error("Solscraper failed. Error: {}", e);
			}
			Thread.sleep(3000);

		}	
	}
	
	private void postToDiscordWebhook(final String content) throws Exception {
		final WebhookPostRequest request = WebhookPostRequest.builder().content(content).build();
		final String response = this.discordApi.executePost(this.rawDiscordWebhook, request);
		//log.info("Discord Webhook Response: {}", response);
	}
	

	private void postToDiscordWebhookRaw(final String content) throws Exception {
		final WebhookPostRequest request = WebhookPostRequest.builder().content(content).build();
		final String response = this.discordApi.executePost(this.tokenDiscordWebhook, request);
		//log.info("Discord Webhook Response: {}", response);
	}
	
	@SuppressWarnings("unused")
	private DexScreenerResponse getTokenPoolInformation(String pairAddress) throws Exception {
		//log.info("Looking up new Token Pair {} on DexScreener", pairAddress);
		final String tokenUrl = "/dex/tokens/"+pairAddress;
		final String response = this.dexScreenerApi.executeGet(tokenUrl);
		return this.dexScreenerApi.parseResponse(response, DexScreenerResponse.class);
	}
	
	private DexScreenerResponse searchTokenPoolInformation(String pairAddress) throws Exception {
		//log.info("Looking up new Token Pair {} on DexScreener", pairAddress);
		final String tokenUrl = "/dex/search/?q="+pairAddress;
		final String response = this.dexScreenerApi.executeGet(tokenUrl);
		return this.dexScreenerApi.parseResponse(response, DexScreenerResponse.class);
	}
	
	private Thread getShutdownManager() {
		Runnable shutdownRoutine = () ->{
			this.shutdown=true;
		};
		return new Thread(shutdownRoutine);
	}
}
