package ru.sberschool.secretsanta.service;

public interface TelegramService {

    void sendMessage(Long idChat, String message);

}
