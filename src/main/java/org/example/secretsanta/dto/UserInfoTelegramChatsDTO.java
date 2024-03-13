package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.UserInfoEntity;

public class UserInfoTelegramChatsDTO {

    private int idUserInfoTelegramChat;
    private Long idChat;
    private UserInfoEntity userInfoEntity;

    public int getIdUserInfoTelegramChat() {
        return idUserInfoTelegramChat;
    }

    public void setIdUserInfoTelegramChat(int idUserInfoTelegramChat) {
        this.idUserInfoTelegramChat = idUserInfoTelegramChat;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public UserInfoEntity getUserInfoEntity() {
        return userInfoEntity;
    }

    public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
        this.userInfoEntity = userInfoEntity;
    }

}
