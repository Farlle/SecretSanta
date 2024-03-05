package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.enums.Status;

public class InviteDTO {

    private UserInfoEntity userInfoEntity;
    private String telegram;
    private Status status;

    public UserInfoEntity getUserInfoEntity() {
        return userInfoEntity;
    }

    public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
        this.userInfoEntity = userInfoEntity;
    }

    public String getTelegram() {
        return telegram;
    }

    public void setTelegram(String telegram) {
        this.telegram = telegram;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
