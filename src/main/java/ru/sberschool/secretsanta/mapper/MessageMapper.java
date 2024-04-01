package ru.sberschool.secretsanta.mapper;

import ru.sberschool.secretsanta.dto.MessageDTO;
import ru.sberschool.secretsanta.model.entity.MessageEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper {

    private final UserInfoMapper userInfoMapper;

    public MessageMapper(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    public MessageDTO toMessageDTO(MessageEntity messageEntity) {
        MessageDTO messageDTO = new MessageDTO();

        if (messageEntity == null) {
            throw new IllegalArgumentException("MessageEntity cannot be null");
        }

        messageDTO.setIdMessage(messageEntity.getIdMessage());
        messageDTO.setMessage(messageEntity.getMessage());
        messageDTO.setDepartureDate(messageEntity.getDepartureDate());
        messageDTO.setSender(userInfoMapper.toUserInfoDTO(messageEntity.getSender()));
        messageDTO.setIdRecipient(messageEntity.getIdRecipient());

        return messageDTO;
    }

    public MessageEntity toMessageEntity(MessageDTO messageDTO) {
        MessageEntity messageEntity = new MessageEntity();

        if (messageDTO == null) {
            throw new IllegalArgumentException("MessageDTO cannot be null");
        }

        messageEntity.setIdMessage(messageDTO.getIdMessage());
        messageEntity.setMessage(messageDTO.getMessage());
        messageEntity.setDepartureDate(messageDTO.getDepartureDate());
        messageEntity.setSender(userInfoMapper.toUserInfoEntity(messageDTO.getSender()));
        messageEntity.setIdRecipient(messageDTO.getIdRecipient());

        return messageEntity;
    }

    public List<MessageDTO> toMessageDTOList(List<MessageEntity> messageEntityList) {
        if (messageEntityList == null) {
            throw new IllegalArgumentException("MessageEntityList cannot be null");
        }

        return messageEntityList.stream()
                .map(this::toMessageDTO)
                .collect(Collectors.toList());

    }

}
