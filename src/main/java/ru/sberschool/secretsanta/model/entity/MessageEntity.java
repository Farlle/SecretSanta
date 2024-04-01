package ru.sberschool.secretsanta.model.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "message")
public class MessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_message")
    private int idMessage;
    @ManyToOne
    @JoinColumn(name = "id_sender", nullable = false)
    private UserInfoEntity sender;
    @Column(name = "id_recipient", nullable = false)
    private int idRecipient;
    @Column(name = "message", nullable = false)
    private String message;
    @Column(name = "departure_date", nullable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageEntity)) return false;
        MessageEntity that = (MessageEntity) o;
        return idMessage == that.idMessage && idRecipient == that.idRecipient && Objects.equals(sender, that.sender)
                && Objects.equals(message, that.message) && Objects.equals(departureDate, that.departureDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMessage, sender, idRecipient, message, departureDate);
    }
}
