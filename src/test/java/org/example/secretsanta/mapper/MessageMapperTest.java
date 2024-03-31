package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MessageMapperTest {

    @Test
    void testToMessageDTO() {
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setIdMessage(1);
        messageEntity.setMessage("Test message");
        messageEntity.setDepartureDate(new Date(1L));
        messageEntity.setSender(new UserInfoEntity());
        messageEntity.setIdRecipient(2);

        MessageDTO messageDTO = MessageMapper.toMessageDTO(messageEntity);

        assertEquals(messageEntity.getIdMessage(), messageDTO.getIdMessage());
        assertEquals(messageEntity.getMessage(), messageDTO.getMessage());
        assertEquals(messageEntity.getDepartureDate(), messageDTO.getDepartureDate());
        assertEquals(messageEntity.getSender(), UserInfoMapper.toUserInfoEntity(messageDTO.getSender()));
        assertEquals(messageEntity.getIdRecipient(), messageDTO.getIdRecipient());
    }

    @Test
    void testToMessageEntity() {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setIdMessage(1);
        messageDTO.setMessage("Test message");
        messageDTO.setDepartureDate(new Date(1L));
        messageDTO.setSender(new UserInfoDTO());
        messageDTO.setIdRecipient(2);

        MessageEntity messageEntity = MessageMapper.toMessageEntity(messageDTO);

        assertEquals(messageDTO.getIdMessage(), messageEntity.getIdMessage());
        assertEquals(messageDTO.getMessage(), messageEntity.getMessage());
        assertEquals(messageDTO.getDepartureDate(), messageEntity.getDepartureDate());
        assertEquals(messageDTO.getSender(), UserInfoMapper.toUserInfoDTO(messageEntity.getSender()));
        assertEquals(messageDTO.getIdRecipient(), messageEntity.getIdRecipient());
    }

    @Test
    void testToMessageDTOList() {
        MessageEntity messageEntity1 = new MessageEntity();
        messageEntity1.setIdMessage(1);
        MessageEntity messageEntity2 = new MessageEntity();
        messageEntity2.setIdMessage(2);
        List<MessageEntity> messageEntityList = Arrays.asList(messageEntity1, messageEntity2);

        List<MessageDTO> messageDTOList = MessageMapper.toMessageDTOList(messageEntityList);

        assertEquals(messageEntityList.size(), messageDTOList.size());
        assertEquals(messageEntityList.get(0).getIdMessage(), messageDTOList.get(0).getIdMessage());
        assertEquals(messageEntityList.get(1).getIdMessage(), messageDTOList.get(1).getIdMessage());
    }

    @Test
    void testToMessageDTO_Null() {
        MessageDTO messageDTO = MessageMapper.toMessageDTO(null);

        assertNotNull(messageDTO);
    }

    @Test
    void testToMessageEntity_Null() {
        MessageEntity messageEntity = MessageMapper.toMessageEntity(null);

        assertNotNull(messageEntity);
    }

    @Test
    void testToMessageDTOList_Null() {
        List<MessageDTO> messageDTOList = MessageMapper.toMessageDTOList(null);

        assertNotNull(messageDTOList);
    }

}