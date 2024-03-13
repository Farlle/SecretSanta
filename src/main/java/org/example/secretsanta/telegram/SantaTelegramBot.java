package org.example.secretsanta.telegram;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.service.impl.UserInfoService;
import org.example.secretsanta.service.impl.UserInfoTelegramChatsService;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class SantaTelegramBot extends TelegramLongPollingBot {
    private final UserInfoTelegramChatsService userInfoTelegramChatsService;
    private final CustomUserDetailsService userDetailsService;
    private final UserInfoService userInfoService;

    private final String BOT_TOKEN = System.getenv("token");
    private final String BOT_USERNAME = "HomeSecretSantaBot";

    public SantaTelegramBot(UserInfoTelegramChatsService userInfoTelegramChatsService,
                            CustomUserDetailsService userDetailsService, UserInfoService userInfoService) {
        this.userInfoTelegramChatsService = userInfoTelegramChatsService;
        this.userDetailsService = userDetailsService;
        this.userInfoService = userInfoService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {

            Long idChat = update.getMessage().getChatId();
            if(userInfoTelegramChatsService.getRegisterUserByIdChats(idChat)==null) {
                String telegram = update.getMessage().getFrom().getUserName();
                UserInfoDTO currentUser = userInfoService.getUsersInfoByTelegram(telegram);
                if(currentUser==null){
                    sendMessage(idChat, "Не зарегистрирован");
                    throw new IllegalArgumentException("не зарегистрирован");
                }
                UserInfoTelegramChatsDTO userInfoTelegramChatsDTO = new UserInfoTelegramChatsDTO();
                userInfoTelegramChatsDTO.setIdChat(idChat);
                userInfoTelegramChatsDTO.setUserInfoEntity(UserInfoMapper.toUserInfoEntity(currentUser));
                userInfoTelegramChatsService.create(userInfoTelegramChatsDTO);
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
