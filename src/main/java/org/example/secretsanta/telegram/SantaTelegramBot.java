package org.example.secretsanta.telegram;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.example.secretsanta.service.impl.UserInfoTelegramChatsServiceImpl;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class SantaTelegramBot extends TelegramLongPollingBot {
    private final UserInfoTelegramChatsServiceImpl userInfoTelegramChatsServiceImpl;
    private final CustomUserDetailsService userDetailsService;
    private final UserInfoServiceImpl userInfoServiceImpl;

    private final String BOT_TOKEN = System.getenv("token");
    private final String BOT_USERNAME = "HomeSecretSantaBot";

    public SantaTelegramBot(UserInfoTelegramChatsServiceImpl userInfoTelegramChatsServiceImpl,
                            CustomUserDetailsService userDetailsService, UserInfoServiceImpl userInfoServiceImpl) {
        this.userInfoTelegramChatsServiceImpl = userInfoTelegramChatsServiceImpl;
        this.userDetailsService = userDetailsService;
        this.userInfoServiceImpl = userInfoServiceImpl;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            Long idChat = update.getMessage().getChatId();
            if(userInfoTelegramChatsServiceImpl.getRegisterUserByIdChats(idChat)==null) {
                String telegram = update.getMessage().getFrom().getUserName();
                UserInfoDTO currentUser = userInfoServiceImpl.getUsersInfoByTelegram(telegram);
                if(currentUser==null){
                    sendMessage(idChat, "Не зарегистрирован");
                    throw new IllegalArgumentException("не зарегистрирован");
                }
                UserInfoTelegramChatsDTO userInfoTelegramChatsDTO = new UserInfoTelegramChatsDTO();
                userInfoTelegramChatsDTO.setIdChat(idChat);
                userInfoTelegramChatsDTO.setUserInfoDTO(currentUser);
                userInfoTelegramChatsServiceImpl.create(userInfoTelegramChatsDTO);
            }
            String messageText = update.getMessage().getText();
            String telegram = update.getMessage().getFrom().getUserName();

            System.out.println(messageText + "  " + idChat + " " + telegram + " ");
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

    public void sendMessage(Long idChat, String message) {
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
