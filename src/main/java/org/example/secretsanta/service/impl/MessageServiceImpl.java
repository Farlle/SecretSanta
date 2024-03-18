package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.mapper.MessageMapper;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.repository.MessageRepository;
import org.example.secretsanta.service.serviceinterface.MessageService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public MessageDTO create(MessageDTO dto) {
        MessageEntity message = new MessageEntity();
        message.setMessage(dto.getMessage());
        message.setSender(UserInfoMapper.toUserInfoEntity(dto.getSender()));
        message.setDepartureDate(dto.getDepartureDate());
        message.setIdRecipient(dto.getIdRecipient());

        return MessageMapper.toMessageDTO(messageRepository.save(message));
    }

    @Override
    public List<MessageDTO> readAll() {
        return MessageMapper.toMessageDTOList(messageRepository.findAll());
    }

    @Override
    public MessageDTO update(int id, MessageDTO dto) {
        MessageEntity message = messageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Message not found with id: " + id));

        message.setMessage(dto.getMessage());
        message.setSender(UserInfoMapper.toUserInfoEntity(dto.getSender()));
        message.setDepartureDate(dto.getDepartureDate());
        message.setIdRecipient(dto.getIdRecipient());

        return MessageMapper.toMessageDTO(messageRepository.save(message));
    }

    @Override
    public void delete(int id) {
        messageRepository.deleteById(id);
    }

    @Override
    public List<MessageDTO> getMessages(int idRecipient) {
        return MessageMapper.toMessageDTOList(messageRepository.findByIdRecipient(idRecipient));
    }

    @Override
    public List<MessageDTO> getConversation(int idSender, int idRecipient) {
        return MessageMapper.toMessageDTOList(messageRepository.findByIdSenderAndIdRecipient(idSender, idRecipient));
    }

    @Override
    public List<Integer> getAllUserDialog(int idUserInfo) {
        return messageRepository.findDistinctRecipientsByIdSender(idUserInfo);
    }

    @Override
    public List<MessageDTO> getDistinctDialog(int idRecipient) {
        return MessageMapper.toMessageDTOList(messageRepository.findDistinctDialog(idRecipient));
    }

}
