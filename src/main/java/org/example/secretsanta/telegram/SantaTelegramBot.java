package org.example.secretsanta.telegram;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class SantaTelegramBot extends TelegramLongPollingBot {

    private final String BOT_TOKEN = "6558818403:AAFhFUYBMW7cGhTF00HIcgspZ-9nngZeB-8";
    private final String BOT_USERNAME = "HomeSecretSantaBot";

    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public void onUpdatesReceived(List<Update> updates) {
        super.onUpdatesReceived(updates);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    public void SendMessage(String idChat, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(idChat);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
