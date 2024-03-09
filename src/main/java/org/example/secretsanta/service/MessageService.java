package org.example.secretsanta.service;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public MessageEntity create(MessageDTO dto) {
        MessageEntity message = new MessageEntity();
        message.setMessage(dto.getMessage());
        message.setSender(dto.getSender());
        message.setDepartureDate(dto.getDepartureDate());
        message.setIdRecipient(dto.getIdRecipient());

        return messageRepository.save(message);
    }

    public List<MessageEntity> readAll() {
        return messageRepository.findAll();
    }

    public MessageEntity update(int id, MessageDTO dto) {
        MessageEntity message = messageRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Message not found with id: " + id));

        message.setMessage(dto.getMessage());
        message.setSender(dto.getSender());
        message.setDepartureDate(dto.getDepartureDate());
        message.setIdRecipient(dto.getIdRecipient());

        return messageRepository.save(message);
    }

    public  void delete(int id){
        messageRepository.deleteById(id);
    }

}