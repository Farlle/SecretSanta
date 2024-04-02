package ru.sberschool.secretsanta.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.dto.UserInfoTelegramChatsDTO;
import ru.sberschool.secretsanta.service.UserInfoService;
import ru.sberschool.secretsanta.service.UserInfoTelegramChatsService;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SantaTelegramBot extends TelegramLongPollingBot {
    private final UserInfoTelegramChatsService userInfoTelegramChatsService;
    private final UserInfoService userInfoService;
    private static final Logger logger = Logger.getLogger(SantaTelegramBot.class.getName());

    private static final String BOT_TOKEN = System.getenv("token");
    private static final String BOT_USERNAME = "HomeSecretSantaBot";

    public SantaTelegramBot(UserInfoTelegramChatsService userInfoTelegramChatsService,
                            UserInfoService userInfoService) {
        this.userInfoTelegramChatsService = userInfoTelegramChatsService;
        this.userInfoService = userInfoService;

    }

    /**
     * Метод который обрабатывает принимамое сообщение от пользователя в тг и запоминает id чата в телеграм
     * у нового пользователя
     *
     * @param update Входящее сообщение
     */
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            Long idChat = update.getMessage().getChatId();
            if (userInfoTelegramChatsService.getRegisterUserByIdChats(idChat) == null) {
                String telegram = update.getMessage().getFrom().getUserName();
                UserInfoDTO currentUser = userInfoService.getUsersInfoByTelegram(telegram);
                if (currentUser == null) {
                    sendMessage(idChat, "Не зарегистрирован");
                    throw new IllegalArgumentException("User is not registered");
                }
                UserInfoTelegramChatsDTO userInfoTelegramChatsDTO = new UserInfoTelegramChatsDTO();
                userInfoTelegramChatsDTO.setIdChat(idChat);
                userInfoTelegramChatsDTO.setUserInfoDTO(currentUser);
                userInfoTelegramChatsService.create(userInfoTelegramChatsDTO);
            }

        }
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

    /**
     * Метод отправки сообщения пользователю в тг
     *
     * @param idChat  Идентификатор чата
     * @param message Отправляемое сообщение
     */
    public void sendMessage(Long idChat, String message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(idChat);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            logger.log(Level.SEVERE, "Произошла ошибка при отправки сообщения в телеграм", e);
        }
    }

}
