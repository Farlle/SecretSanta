package ru.sberschool.secretsanta.dto;

import java.sql.Date;
import java.util.Objects;

public class MessageDTO implements Comparable<MessageDTO> {

    private int idMessage;
    private UserInfoDTO sender;
    private int idRecipient;
    private String message;
    private Date departureDate;

    public int getIdMessage() {
        return idMessage;
    }

    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
    }

    public UserInfoDTO getSender() {
        return sender;
    }

    public void setSender(UserInfoDTO sender) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageDTO)) return false;
        MessageDTO that = (MessageDTO) o;
        return idMessage == that.idMessage && idRecipient == that.idRecipient && Objects.equals(sender, that.sender) && Objects.equals(message, that.message) && Objects.equals(departureDate, that.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMessage, sender, idRecipient, message, departureDate);
    }

    @Override
    public int compareTo(MessageDTO o) {
        return Integer.compare(this.idMessage, o.idMessage);
    }
}
