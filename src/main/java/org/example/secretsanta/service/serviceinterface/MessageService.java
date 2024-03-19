package org.example.secretsanta.service.serviceinterface;

import org.example.secretsanta.dto.MessageDTO;

import java.util.List;

public interface MessageService {
    MessageDTO create(MessageDTO dto);

    List<MessageDTO> readAll();

    MessageDTO update(int id, MessageDTO dto);

    void delete(int id);

    List<MessageDTO> getMessages(int idRecipient);

    List<MessageDTO> getConversation(int idSender, int idRecipient);

    List<Integer> getAllUserDialog(int idUserInfo);

    List<MessageDTO> getDistinctDialog(int idSender);
}
