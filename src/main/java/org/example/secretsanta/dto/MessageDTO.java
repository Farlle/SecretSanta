package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.UserInfoEntity;

import java.sql.Date;

public class MessageDTO {

    private int idMessage;
    private UserInfoEntity sender;
    private int idRecipient;
    private String message;
    private Date departureDate;

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public UserInfoEntity getSender() {
        return sender;
    }

    public void setSender(UserInfoEntity sender) {
        this.sender = sender;
    }

    public int getIdRecipient() {
        return idRecipient;
    }

    public void setIdRecipient(int idRecipient) {
        this.idRecipient = idRecipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
}
