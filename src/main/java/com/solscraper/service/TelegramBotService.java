package com.solscraper.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InputMediaPhoto;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.EditMessageMedia;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMediaGroup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.MessagesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import com.solscraper.util.PropertiesUtils;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class TelegramBotService {
	private final transient TelegramBot telegramBot;
	private  transient Properties localState;

	private int lastMessageIdTxt = -1;
	private int lastMessageIdImg = -1;
	private int lastMessageIdBasic = -1;
	@Value("${service.telegram.channel-id}")
	private String telegramChannelId;
	public TelegramBotService(@Autowired final TelegramBot telegramBot) {
		this.telegramBot = telegramBot;
		
		try {
			this.localState = PropertiesUtils.loadProperties();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		this.lastMessageIdTxt = Integer.parseInt(this.localState.getProperty("tl.last-message-id-txt"));
		this.lastMessageIdImg = Integer.parseInt(this.localState.getProperty("tl.last-message-id-img"));
		this.lastMessageIdBasic = Integer.parseInt(this.localState.getProperty("tl.last-message-id-basic"));

	}
	
	public boolean sendGroupMessage(String text, String imageUrl) throws IOException {
		boolean ok = false;
		final URL tokenImageUrl = new URL(imageUrl);
		final InputStream imageStream = tokenImageUrl.openStream();
		if(this.lastMessageIdTxt==-1) {
			final SendMessage request = new SendMessage(this.telegramChannelId, text)
			        .parseMode(ParseMode.HTML)
			        .disableWebPagePreview(true)
			        .disableNotification(true)
			        .replyToMessageId(1)
			        .replyMarkup(new InlineKeyboardMarkup());

			// sync
			final SendResponse sendResponse = this.telegramBot.execute(request);
			ok = sendResponse.isOk();
			final Message message = sendResponse.message();
			this.lastMessageIdTxt = message.messageId();
			
			final SendMediaGroup image = new SendMediaGroup(this.telegramChannelId, new InputMediaPhoto(imageStream.readAllBytes()));
			final MessagesResponse msgResponse = this.telegramBot.execute(image);
			ok = sendResponse.isOk();
			this.lastMessageIdImg = msgResponse.messages()[0].messageId();
			this.localState.setProperty("tl.last-message-id-txt", this.lastMessageIdTxt+"");
			this.localState.setProperty("tl.last-message-id-img", this.lastMessageIdImg+"");

			PropertiesUtils.saveProperties(localState);
		}else {
			final EditMessageText editMessageText = new EditMessageText(this.telegramChannelId, this.lastMessageIdTxt, text)
			        .parseMode(ParseMode.HTML)
			        .disableWebPagePreview(true)
			        .replyMarkup(new InlineKeyboardMarkup());
			
			final EditMessageMedia editMessageImg = new EditMessageMedia(this.telegramChannelId, this.lastMessageIdImg, new InputMediaPhoto(imageStream.readAllBytes()))
			        .replyMarkup(new InlineKeyboardMarkup());
			final BaseResponse sendResponse = this.telegramBot.execute(editMessageText);
			final BaseResponse msgResponse = this.telegramBot.execute(editMessageImg);
			ok = sendResponse.isOk();
			ok = msgResponse.isOk();
		}
		imageStream.close();
		return ok;
		
	}
	
	public void sendSimpleMessage(String text) throws IOException {
		if(this.lastMessageIdBasic==-1) {
			final SendMessage request = new SendMessage(this.telegramChannelId , text)
			        .parseMode(ParseMode.HTML)
			        .disableWebPagePreview(true)
			        .disableNotification(true)
			        .replyToMessageId(1)
			        .replyMarkup(new InlineKeyboardMarkup());
			final SendResponse sendResponse = this.telegramBot.execute(request);
			final Message message = sendResponse.message();
			this.lastMessageIdBasic = message.messageId();
			this.localState.setProperty("tl.last-message-id-basic", this.lastMessageIdBasic+"");
			PropertiesUtils.saveProperties(this.localState);

		}else {
			final EditMessageText editMessageText = new EditMessageText(this.telegramChannelId, this.lastMessageIdBasic, text)
			        .parseMode(ParseMode.HTML)
			        .disableWebPagePreview(true)
			        .replyMarkup(new InlineKeyboardMarkup());
			final BaseResponse sendResponse = this.telegramBot.execute(editMessageText);
			sendResponse.isOk();
		}
	}
}
