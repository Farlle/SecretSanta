package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.model.entity.MessageEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper {

    public static MessageDTO toMessageDTO(MessageEntity messageEntity) {
        MessageDTO messageDTO = new MessageDTO();

        if (messageEntity == null) {
            throw new IllegalArgumentException("MessageEntity cannot be null");
        }

        messageDTO.setIdMessage(messageEntity.getIdMessage());
        messageDTO.setMessage(messageEntity.getMessage());
        messageDTO.setDepartureDate(messageEntity.getDepartureDate());
        messageDTO.setSender(UserInfoMapper.toUserInfoDTO(messageEntity.getSender()));
        messageDTO.setIdRecipient(messageEntity.getIdRecipient());

        return messageDTO;
    }

    public static MessageEntity toMessageEntity(MessageDTO messageDTO) {
        MessageEntity messageEntity = new MessageEntity();

        if (messageDTO == null) {
            throw new IllegalArgumentException("MessageDTO cannot be null");
        }

        messageEntity.setIdMessage(messageDTO.getIdMessage());
        messageEntity.setMessage(messageDTO.getMessage());
        messageEntity.setDepartureDate(messageDTO.getDepartureDate());
        messageEntity.setSender(UserInfoMapper.toUserInfoEntity(messageDTO.getSender()));
        messageEntity.setIdRecipient(messageDTO.getIdRecipient());

        return messageEntity;
    }

    public static List<MessageDTO> toMessageDTOList(List<MessageEntity> messageEntityList) {
        if (messageEntityList == null) {
            throw new IllegalArgumentException("MessageEntityList cannot be null");
        }

        return messageEntityList.stream()
                .map(MessageMapper::toMessageDTO)
                .collect(Collectors.toList());

    }

}
