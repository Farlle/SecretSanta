package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.model.entity.RoomEntity;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {

    public static MessageDTO toMessageDTO(MessageEntity messageEntity) {
        if (messageEntity == null) {
            return null;
        }

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setIdMessage(messageEntity.getIdMessage());
        messageDTO.setMessage(messageEntity.getMessage());
        messageDTO.setDepartureDate(messageEntity.getDepartureDate());
        messageDTO.setSender(messageEntity.getSender());
        messageDTO.setIdRecipient(messageEntity.getIdRecipient());

        return messageDTO;
    }

    public static MessageEntity toMessageEntity(MessageDTO messageDTO) {
        if (messageDTO == null) {
            return null;
        }

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setIdMessage(messageDTO.getIdMessage());
        messageEntity.setMessage(messageDTO.getMessage());
        messageEntity.setDepartureDate(messageDTO.getDepartureDate());
        messageEntity.setSender(messageDTO.getSender());
        messageEntity.setIdRecipient(messageDTO.getIdRecipient());

        return messageEntity;
    }

}
