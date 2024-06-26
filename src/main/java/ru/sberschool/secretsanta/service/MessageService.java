package ru.sberschool.secretsanta.service;

import ru.sberschool.secretsanta.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    MessageDTO create(MessageDTO dto);

    List<MessageDTO> readAll();

    MessageDTO update(int id, MessageDTO dto);

    void delete(int id);

    List<MessageDTO> getMessages(int idRecipient);

    List<MessageDTO> getConversation(int idSender, int idRecipient);

    List<MessageDTO> getDistinctDialog(int idSender);
}
