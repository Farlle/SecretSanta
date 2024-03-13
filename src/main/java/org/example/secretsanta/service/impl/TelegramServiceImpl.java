package org.example.secretsanta.service.impl;

import org.example.secretsanta.service.serviceinterface.TelegramService;
import org.example.secretsanta.telegram.SantaTelegramBot;
import org.springframework.stereotype.Service;

@Service
public class TelegramServiceImpl implements TelegramService {
    private final SantaTelegramBot bot;

    public TelegramServiceImpl(SantaTelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(Long idChat, String message) {
        bot.sendMessage(idChat, message);
    }

}
