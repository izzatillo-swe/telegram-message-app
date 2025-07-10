package com.company.telegrammessageapp.telegram;


import com.company.telegrammessageapp.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class MyTelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Value("${telegram.bot.token}")
    private String botToken;

    private final UserService userService;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = update.getMessage().getChatId();
            String text = message.getText();

            boolean isUserConnected = userService.existsByChatId(chatId);

            if (!isUserConnected) {
                if (text.equals("/start")) {
                    sendMessage(chatId, "Пожалуйста, сгенерируйте токен и отправьте его сюда.");
                } else {
                    if (userService.registerChatIdForUser(text, chatId)) {
                        sendMessage(chatId, "✅ Токен был принят и подключен.");
                    } else {
                        sendMessage(chatId, "Токен недействителен, пожалуйста, сгенерируйте другой.");
                    }
                }
            }
        }

    }

    @SneakyThrows
    private void sendMessage(Long chatId, String text) {
        execute(new SendMessage(chatId.toString(), text));
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

}
