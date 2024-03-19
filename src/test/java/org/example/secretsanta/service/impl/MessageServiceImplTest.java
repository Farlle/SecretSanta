package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.MessageMapper;
import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.MessageRepository;
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
    private UserInfoServiceImpl userInfoServiceImpl;
    @Mock
    private UserInfoTelegramChatsServiceImpl userInfoTelegramChatsServiceImpl;
    @Mock
    private TelegramServiceImpl telegramServiceImpl;

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

        when(userInfoServiceImpl.getTelegramUser(idRecipient)).thenReturn(recipientTelegram);
        when(userInfoTelegramChatsServiceImpl.getIdChatByTelegramName(recipientTelegram)).thenReturn(2L);
        when(messageRepository.save(any(MessageEntity.class))).thenReturn(savedMessageEntity);

        MessageDTO result = messageService.create(messageDTO);

        verify(telegramServiceImpl).sendMessage(2L, message);
        verify(messageRepository).save(any(MessageEntity.class));
        assertEquals(MessageMapper.toMessageDTO(savedMessageEntity), result);
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
        MessageEntity existingMessage = new MessageEntity();
        when(messageRepository.findById(id)).thenReturn(Optional.of(existingMessage));
        when(messageRepository.save(any(MessageEntity.class))).thenReturn(existingMessage);

        MessageDTO result = messageService.update(id, dto);

        assertNotNull(result);
        verify(messageRepository).findById(id);
        verify(messageRepository).save(existingMessage);
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
    void testGetAllUserDialog() {
        int idUserInfo = 1;
        when(messageRepository.findDistinctRecipientsByIdSender(idUserInfo)).thenReturn(Collections.emptyList());

        // Act
        List<Integer> result = messageService.getAllUserDialog(idUserInfo);

        // Assert
        assertNotNull(result);
        verify(messageRepository).findDistinctRecipientsByIdSender(idUserInfo);
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