package com.company.telegrammessageapp;

import com.company.telegrammessageapp.telegram.MyTelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@RequiredArgsConstructor
public class TelegramMessageApplication implements CommandLineRunner {

    private final MyTelegramBot telegramBot;

    public static void main(String[] args) {
        SpringApplication.run(TelegramMessageApplication.class, args);
    }

    @Override
    public void run(String... args) {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(telegramBot);
            System.out.println("Bot running");
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
