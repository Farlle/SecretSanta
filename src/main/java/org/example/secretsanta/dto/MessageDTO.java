package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.UserInfoEntity;

import java.sql.Date;

public class MessageDTO {

    private UserInfoEntity sender;
    private int idRedirect;
    private int idRecipient;
    private String message;
    private Date departureDate;

    public UserInfoEntity getSender() {
        return sender;
    }

    public void setSender(UserInfoEntity sender) {
        this.sender = sender;
    }

    public int getIdRedirect() {
        return idRedirect;
    }

    public void setIdRedirect(int idRedirect) {
        this.idRedirect = idRedirect;
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
