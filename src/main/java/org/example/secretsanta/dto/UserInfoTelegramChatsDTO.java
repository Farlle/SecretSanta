package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.UserInfoEntity;

public class UserInfoTelegramChatsDTO {

    private int idUserInfoTelegramChat;
    private int idUserInfo;
    private int idChat;
    private UserInfoEntity userInfoEntity;

    public int getIdUserInfoTelegramChat() {
        return idUserInfoTelegramChat;
    }

    public void setIdUserInfoTelegramChat(int idUserInfoTelegramChat) {
        this.idUserInfoTelegramChat = idUserInfoTelegramChat;
    }

    public int getIdUserInfo() {
        return idUserInfo;
    }

    public void setIdUserInfo(int idUserInfo) {
        this.idUserInfo = idUserInfo;
    }

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public UserInfoEntity getUserInfoEntity() {
        return userInfoEntity;
    }

    public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
        this.userInfoEntity = userInfoEntity;
    }
}