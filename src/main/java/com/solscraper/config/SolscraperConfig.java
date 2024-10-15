package com.solscraper.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pengrad.telegrambot.TelegramBot;

@Configuration
public class SolscraperConfig {
	@Value("${service.telegram.bot-token}")
	private String telegramBotToken;
	@Bean
	public TelegramBot telegramBot() {
		TelegramBot bot = new TelegramBot(this.telegramBotToken);
		return bot;
	}
	
	public void setTelegramBotToken(String token) {
	    this.telegramBotToken = token;
	}
}	
