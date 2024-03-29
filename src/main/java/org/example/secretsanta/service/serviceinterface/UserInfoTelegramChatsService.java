package org.example.secretsanta.service.serviceinterface;

import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;

import java.util.List;

public interface UserInfoTelegramChatsService {

    UserInfoTelegramChatsDTO create(UserInfoTelegramChatsDTO dto);

    List<UserInfoTelegramChatsDTO> readAll();

    UserInfoTelegramChatsDTO update(int id, UserInfoTelegramChatsDTO dto);

    void delete(int id);

    UserInfoTelegramChatsDTO getRegisterUserByIdChats(Long idChat);

    List<UserInfoTelegramChatsDTO> getAllIdChatsUsersWhoNeedNotify(int idRoom);

    Long getIdChatByTelegramName(String telegramName);

}
