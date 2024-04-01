package ru.sberschool.secretsanta.dto;

import ru.sberschool.secretsanta.model.enums.Status;

import java.util.Objects;

public class InviteDTO {

    private int idInvite;
    private UserInfoDTO userInfoDTO;
    private String telegram;
    private Status status;
    private String text;

    public InviteDTO() {
    }

    public InviteDTO(int idInvite, UserInfoDTO userInfoDTO, String telegram, Status status, String text) {
        this.idInvite = idInvite;
        this.userInfoDTO = userInfoDTO;
        this.telegram = telegram;
        this.status = status;
        this.text = text;
    }

    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }

    public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
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
        if (!(o instanceof InviteDTO)) return false;
        InviteDTO inviteDTO = (InviteDTO) o;
        return idInvite == inviteDTO.idInvite && Objects.equals(userInfoDTO, inviteDTO.userInfoDTO)
                && Objects.equals(telegram, inviteDTO.telegram) && status == inviteDTO.status && Objects.equals(text, inviteDTO.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idInvite, userInfoDTO, telegram, status, text);
    }
}
