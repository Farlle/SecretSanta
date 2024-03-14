package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.enums.Status;

import java.util.Objects;

public class InviteDTO {

    private int idInvite;
    private UserInfoEntity userInfoEntity;
    private String telegram;
    private Status status;
    private String text;

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

    public int getIdInvite() {
        return idInvite;
    }

    public void setIdInvite(int idInvite) {
        this.idInvite = idInvite;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InviteDTO inviteDTO = (InviteDTO) o;
        return idInvite == inviteDTO.idInvite && Objects.equals(userInfoEntity, inviteDTO.userInfoEntity) && Objects.equals(telegram, inviteDTO.telegram) && status == inviteDTO.status && Objects.equals(text, inviteDTO.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInvite, userInfoEntity, telegram, status, text);
    }
}
