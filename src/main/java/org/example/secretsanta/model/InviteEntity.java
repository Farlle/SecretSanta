package org.example.secretsanta.model;

import org.example.secretsanta.model.enums.Status;

public class InviteEntity {

    private int idInvite;
    private int idUser;
    private int telegram;
    private Status status;

    public int getIdInvite() {
        return idInvite;
    }

    public void setIdInvite(int idInvite) {
        this.idInvite = idInvite;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getTelegram() {
        return telegram;
    }

    public void setTelegram(int telegram) {
        this.telegram = telegram;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
