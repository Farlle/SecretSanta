package ru.sberschool.secretsanta.mapper;

import ru.sberschool.secretsanta.dto.MessageDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.model.entity.MessageEntity;
import ru.sberschool.secretsanta.model.entity.UserInfoEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class MessageMapperTest {

    @Mock
    private UserInfoMapper userInfoMapper;
    @InjectMocks
    private MessageMapper messageMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testToMessageDTO() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        when(userInfoMapper.toUserInfoDTO(any())).thenReturn(userInfoDTO);

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setIdMessage(1);
        messageEntity.setMessage("Test Message");
        messageEntity.setDepartureDate(new Date(1L));
        messageEntity.setIdRecipient(2);

        MessageDTO messageDTO = messageMapper.toMessageDTO(messageEntity);

        assertEquals(messageEntity.getIdMessage(), messageDTO.getIdMessage());
        assertEquals(messageEntity.getMessage(), messageDTO.getMessage());
        assertEquals(messageEntity.getDepartureDate(), messageDTO.getDepartureDate());
        assertEquals(userInfoDTO, messageDTO.getSender());
        assertEquals(messageEntity.getIdRecipient(), messageDTO.getIdRecipient());
    }

    @Test
    void testToMessageEntity() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        when(userInfoMapper.toUserInfoEntity(any())).thenReturn(userInfoEntity);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setIdMessage(1);
        messageDTO.setMessage("Test Message");
        messageDTO.setDepartureDate(new Date(1L));
        messageDTO.setIdRecipient(2);

        MessageEntity messageEntity = messageMapper.toMessageEntity(messageDTO);

        assertEquals(messageDTO.getIdMessage(), messageEntity.getIdMessage());
        assertEquals(messageDTO.getMessage(), messageEntity.getMessage());
        assertEquals(messageDTO.getDepartureDate(), messageEntity.getDepartureDate());
        assertEquals(userInfoEntity, messageEntity.getSender());
        assertEquals(messageDTO.getIdRecipient(), messageEntity.getIdRecipient());
    }

    @Test
    void testToMessageDTOList() {
        MessageEntity messageEntity1 = new MessageEntity();
        messageEntity1.setIdMessage(1);
        MessageEntity messageEntity2 = new MessageEntity();
        messageEntity2.setIdMessage(2);
        List<MessageEntity> messageEntityList = Arrays.asList(messageEntity1, messageEntity2);

        List<MessageDTO> messageDTOList = messageMapper.toMessageDTOList(messageEntityList);

        assertEquals(messageEntityList.size(), messageDTOList.size());
        assertEquals(messageEntityList.get(0).getIdMessage(), messageDTOList.get(0).getIdMessage());
        assertEquals(messageEntityList.get(1).getIdMessage(), messageDTOList.get(1).getIdMessage());
    }

    @Test
    void testToMessageDTO_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            messageMapper.toMessageDTO(null);
        });
    }

    @Test
    void testToMessageEntity_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            messageMapper.toMessageEntity(null);
        });
    }

    @Test
    void testToMessageDTOList_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            messageMapper.toMessageDTOList(null);
        });
    }

}