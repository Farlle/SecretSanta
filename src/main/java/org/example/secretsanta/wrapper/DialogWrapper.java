package org.example.secretsanta.wrapper;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;

public class DialogWrapper {
    private UserInfoDTO sender;
    private UserInfoDTO recipient;
    private String lastMessage;

    public DialogWrapper(UserInfoDTO sender, UserInfoDTO recipient, String lastMessage) {
        this.sender = sender;
        this.recipient = recipient;
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public UserInfoDTO getSender() {
        return sender;
    }

    public void setSender(UserInfoDTO sender) {
        this.sender = sender;
    }

    public UserInfoDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(UserInfoDTO recipient) {
        this.recipient = recipient;
    }
}
