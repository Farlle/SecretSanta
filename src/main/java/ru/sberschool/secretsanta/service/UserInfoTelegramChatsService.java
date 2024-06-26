package ru.sberschool.secretsanta.service;

import ru.sberschool.secretsanta.dto.UserInfoTelegramChatsDTO;

import java.util.List;

public interface UserInfoTelegramChatsService {

    UserInfoTelegramChatsDTO create(UserInfoTelegramChatsDTO dto);

    List<UserInfoTelegramChatsDTO> readAll();

    UserInfoTelegramChatsDTO update(int id, UserInfoTelegramChatsDTO dto);

    void delete(int id);

    UserInfoTelegramChatsDTO getRegisterUserByIdChats(Long idChat);

    List<UserInfoTelegramChatsDTO> getAllUserChatsWhoNeedNotify(int idRoom);

    Long getIdChatByTelegramName(String telegramName);

}
