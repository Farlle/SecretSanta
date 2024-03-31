package org.example.secretsanta.service;

public interface TelegramService {

    void sendMessage(Long idChat, String message);

}
