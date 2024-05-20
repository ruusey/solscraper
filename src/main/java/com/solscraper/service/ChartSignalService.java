package com.solscraper.service;

import java.text.MessageFormat;

import org.springframework.stereotype.Service;

import com.solscraper.model.gecko.response.PoolOhlcResponse;
import com.solscraper.strategy.RSI2Strategy;
import com.solscraper.util.ApiSessionOkHttp;

import lombok.extern.slf4j.Slf4j;

//@Service
@Slf4j
public class ChartSignalService {
	private final transient ApiSessionOkHttp solExplorerApi;
	private final transient boolean shutdown;

	public ChartSignalService() {
		this.solExplorerApi = new ApiSessionOkHttp(	"https://api.geckoterminal.com/api/v2");
		this.shutdown = false;
	}
	
	//@EventListener(ApplicationReadyEvent.class)
	public void start() throws Exception {
		while (!this.shutdown) {
			try {
				String query = this.buildQueryString("6UYbX1x8YUcFj8YstPYiZByG7uQzAq2s46ZWphUMkjg5", "day", "1");
				String response = this.solExplorerApi.executeGet(query);
				PoolOhlcResponse ticks = this.solExplorerApi.parseResponse(response, PoolOhlcResponse.class);
				RSI2Strategy.run(ticks);
				log.info("Got ticks {}", ticks);
			}catch(Exception e) {
				log.error("Solscraper failed. Error: {}", e);
			}
			Thread.sleep(3000);
		}
	}
	
	private String buildQueryString(String pairAddress, String period, String grouping) {
		return MessageFormat.format("/networks/solana/pools/{0}/ohlcv/{1}?aggregate={2}", pairAddress, period, grouping);
	}
	//networks/solana/pools/
}
