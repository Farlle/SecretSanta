package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.service.TelegramService;
import ru.sberschool.secretsanta.telegram.SantaTelegramBot;
import org.springframework.stereotype.Service;

/**
 * Сервис для работы с телеграмом
 */
@Service
public class TelegramServiceImpl implements TelegramService {
    private final SantaTelegramBot bot;

    public TelegramServiceImpl(SantaTelegramBot bot) {
        this.bot = bot;
    }

    /**
     * Метод отправляющий сообщение пользователю
     *
     * @param idChat Идентификатор чата
     * @param message Сообщение
     */
    @Override
    public void sendMessage(Long idChat, String message) {
        bot.sendMessage(idChat, message);
    }

}
