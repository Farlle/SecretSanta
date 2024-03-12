package org.example.secretsanta.wrapper;

import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;

public class DialogWrapper {
    private UserInfoEntity sender;
    private UserInfoEntity recipient;

    public DialogWrapper(UserInfoEntity sender, UserInfoEntity recipient) {
        this.sender = sender;
        this.recipient = recipient;
    }

    public UserInfoEntity getSender() {
        return sender;
    }

    public void setSender(UserInfoEntity sender) {
        this.sender = sender;
    }

    public UserInfoEntity getRecipient() {
        return recipient;
    }

    public void setRecipient(UserInfoEntity recipient) {
        this.recipient = recipient;
    }
}
