package ru.sberschool.secretsanta.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.sberschool.secretsanta.telegram.SantaTelegramBot;

import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class TelegramBotConfig {

    private static final Logger logger = Logger.getLogger(TelegramBotConfig.class.getName());

    @Bean
    public TelegramBotsApi telegramBotsApi(SantaTelegramBot santaTelegramBot) {
        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(santaTelegramBot);
        } catch (TelegramApiException e) {
            logger.log(Level.SEVERE, "Произошла ошибка при регистрации бота в телеграм", e);
        }
        return telegramBotsApi;
    }
}
