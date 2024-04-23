package com.solscraper.strategy;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.joda.time.format.ISODateTimeFormat;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TimeSeriesManager;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

import com.solscraper.model.gecko.response.PoolOhlcResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RSI2Strategy {

	public static Strategy buildStrategy(TimeSeries series) {
		if (series == null)
			throw new IllegalArgumentException("Series cannot be null");

		ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
		SMAIndicator shortSma = new SMAIndicator(closePrice, 5);
		SMAIndicator longSma = new SMAIndicator(closePrice, 200);

		// We use a 2-period RSI indicator to identify buying
		// or selling opportunities within the bigger trend.
		RSIIndicator rsi = new RSIIndicator(closePrice, 10);

		// Entry rule
		// The long-term trend is up when a security is above its 200-period SMA.
		Rule entryRule = 
				new CrossedDownIndicatorRule(rsi, 20);
				
		// Exit rule
		// The long-term trend is down when a security is below its 200-period SMA.
		Rule exitRule = new CrossedUpIndicatorRule(rsi, 75);
				

		// TODO: Finalize the strategy

		return new BaseStrategy(entryRule, exitRule);
	}

	public static void run(PoolOhlcResponse history) {
		TimeSeries series = candleListToTimeseries(history, "my-timeseries");

		// Building the trading strategy
		Strategy strategy = RSI2Strategy.buildStrategy(series);

		// Running the strategy
		TimeSeriesManager seriesManager = new TimeSeriesManager(series);
		TradingRecord tradingRecord = seriesManager.run(strategy);
		printTradingRecord(tradingRecord);
		log.info("Trading record: {}",tradingRecord);
		log.info("Number of trades for the strategy: {}",tradingRecord.getTradeCount());

		// Analysis
		log.info("Total profit for the strategy: {}" ,(new TotalProfitCriterion().calculate(series, tradingRecord)));
	}

	public static TimeSeries candleListToTimeseries(PoolOhlcResponse history, String name) {
		TimeSeries series = new BaseTimeSeries.SeriesBuilder().withName(name).build();
		for(int i = history.getTicks().size()-1; i>-1; i--) {
			List<Double> candle = history.getTicks().get(i);
			String parsedDate = epochToUTCString(candle.get(0).longValue()*1000);
			ZonedDateTime time = ZonedDateTime.parse(parsedDate);
			try {
				series.addBar(time, candle.get(1), candle.get(2), candle.get(3), candle.get(4), candle.get(5));

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		log.info(series.getBarCount()+"");
		return series;
	}

	public static String epochToUTCString(long epoch) {
		org.joda.time.format.DateTimeFormatter fmt = ISODateTimeFormat.dateTime();
		return fmt.print(epoch);
	}

	public static void printTradingRecord(TradingRecord records) {

		records.getTrades().forEach(trade -> {
			log.info(trade.getEntry().getType() + " " + trade.getEntry().getPrice());
			if (trade.getExit() == null)
				return;
			log.info(trade.getExit().getType() + " " + trade.getExit().getPrice());
		});
	}

}