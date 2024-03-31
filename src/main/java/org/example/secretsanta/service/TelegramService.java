package org.example.secretsanta.service.serviceinterface;

public interface TelegramService {

    void sendMessage(Long idChat, String message);

}
