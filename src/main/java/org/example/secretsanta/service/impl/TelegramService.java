package org.example.secretsanta.service.impl;

import org.example.secretsanta.telegram.SantaTelegramBot;
import org.springframework.stereotype.Service;

@Service
public class TelegramService {
    private final SantaTelegramBot bot;

    public TelegramService(SantaTelegramBot bot) {
        this.bot = bot;
    }

    public void sendMessage(Long idChat, String message) {
        bot.sendMessage(idChat,message);
    }

}
