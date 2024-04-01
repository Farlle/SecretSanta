package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.MessageMapper;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.MessageRepository;
import org.example.secretsanta.service.TelegramService;
import org.example.secretsanta.service.UserInfoService;
import org.example.secretsanta.service.UserInfoTelegramChatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserInfoService userInfoService;
    @Mock
    private UserInfoTelegramChatsService userInfoTelegramChatsService;
    @Mock
    private TelegramService telegramService;
    @Mock
    private MessageMapper messageMapper;
    @Mock
    private UserInfoMapper userInfoMapper;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testCreate() {
        String senderTelegram = "sender";
        String recipientTelegram = "recipient";
        String messageText = "Hello, recipient!";
        Date departureDate = new Date(8600000L);
        int idRecipient = 1;
        String message = "Тебе пришло новое сообщение";

        UserInfoDTO senderDTO = new UserInfoDTO();
        senderDTO.setTelegram(senderTelegram);

        UserInfoEntity senderEntity = new UserInfoEntity();
        senderEntity.setTelegram(senderTelegram);

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setSender(senderDTO);
        messageDTO.setMessage(messageText);
        messageDTO.setDepartureDate(departureDate);
        messageDTO.setIdRecipient(idRecipient);

        MessageEntity savedMessageEntity = new MessageEntity();
        savedMessageEntity.setIdMessage(1);
        savedMessageEntity.setMessage(messageText);
        savedMessageEntity.setSender(senderEntity);
        savedMessageEntity.setDepartureDate(departureDate);
        savedMessageEntity.setIdRecipient(idRecipient);

        when(userInfoService.getTelegramUser(idRecipient)).thenReturn(recipientTelegram);
        when(userInfoTelegramChatsService.getIdChatByTelegramName(recipientTelegram)).thenReturn(2L);
        when(messageRepository.save(any(MessageEntity.class))).thenReturn(savedMessageEntity);
        when(userInfoMapper.toUserInfoEntity(senderDTO)).thenReturn(senderEntity);
        when(messageMapper.toMessageDTO(savedMessageEntity)).thenReturn(messageDTO);

        MessageDTO result = messageService.create(messageDTO);

        verify(telegramService).sendMessage(2L, message);
        verify(messageRepository).save(any(MessageEntity.class));
        assertEquals(messageDTO, result);
    }

    @Test
    void testReadAll() {
        when(messageRepository.findAll()).thenReturn(Collections.emptyList());

        List<MessageDTO> result = messageService.readAll();

        assertNotNull(result);
        verify(messageRepository).findAll();
    }

    @Test
    void testUpdate() {
        int id = 1;
        MessageDTO dto = new MessageDTO();
        dto.setMessage("Updated message");
        dto.setDepartureDate(new Date(1L));
        dto.setIdRecipient(2);

        UserInfoDTO senderDTO = new UserInfoDTO();
        senderDTO.setTelegram("sender");
        dto.setSender(senderDTO);

        UserInfoEntity senderEntity = new UserInfoEntity();
        senderEntity.setTelegram("sender");

        MessageEntity existingMessage = new MessageEntity();
        existingMessage.setIdMessage(id);
        existingMessage.setMessage("Original message");
        existingMessage.setDepartureDate(new Date(1L));
        existingMessage.setIdRecipient(1);
        existingMessage.setSender(senderEntity);

        MessageEntity updatedMessage = new MessageEntity();
        updatedMessage.setIdMessage(id);
        updatedMessage.setMessage(dto.getMessage());
        updatedMessage.setDepartureDate(dto.getDepartureDate());
        updatedMessage.setIdRecipient(dto.getIdRecipient());
        updatedMessage.setSender(userInfoMapper.toUserInfoEntity(dto.getSender()));

        MessageDTO updatedDTO = new MessageDTO();
        updatedDTO.setMessage(dto.getMessage());
        updatedDTO.setDepartureDate(dto.getDepartureDate());
        updatedDTO.setIdRecipient(dto.getIdRecipient());
        updatedDTO.setSender(dto.getSender());

        when(messageRepository.findById(id)).thenReturn(Optional.of(existingMessage));
        when(messageRepository.save(any(MessageEntity.class))).thenReturn(updatedMessage);
        when(messageMapper.toMessageDTO(updatedMessage)).thenReturn(updatedDTO);
        when(userInfoMapper.toUserInfoEntity(senderDTO)).thenReturn(senderEntity);

        MessageDTO result = messageService.update(id, dto);

        verify(messageRepository).findById(id);
        verify(messageRepository).save(any(MessageEntity.class));
        assertEquals(updatedDTO, result);
    }

    @Test
    void testDelete() {
        int id = 1;

        messageService.delete(id);

        verify(messageRepository).deleteById(id);
    }

    @Test
    void testGetMessages() {
        int idRecipient = 1;
        when(messageRepository.findByIdRecipient(idRecipient)).thenReturn(Collections.emptyList());

        List<MessageDTO> result = messageService.getMessages(idRecipient);

        assertNotNull(result);
        verify(messageRepository).findByIdRecipient(idRecipient);
    }

    @Test
    void testGetConversation() {
        int idSender = 1;
        int idRecipient = 2;
        when(messageRepository.findByIdSenderAndIdRecipient(idSender, idRecipient)).thenReturn(Collections.emptyList());

        List<MessageDTO> result = messageService.getConversation(idSender, idRecipient);

        assertNotNull(result);
        verify(messageRepository).findByIdSenderAndIdRecipient(idSender, idRecipient);
    }
    
    @Test
    void testGetDistinctDialog() {
        int idRecipient = 1;
        when(messageRepository.findDistinctDialog(idRecipient)).thenReturn(Collections.emptyList());

        List<MessageDTO> result = messageService.getDistinctDialog(idRecipient);

        assertNotNull(result);
        verify(messageRepository).findDistinctDialog(idRecipient);
    }
}