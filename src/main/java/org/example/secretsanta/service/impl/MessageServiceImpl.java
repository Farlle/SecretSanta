package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.mapper.MessageMapper;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.repository.MessageRepository;
import org.example.secretsanta.service.MessageService;
import org.example.secretsanta.service.TelegramService;
import org.example.secretsanta.service.UserInfoService;
import org.example.secretsanta.service.UserInfoTelegramChatsService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Сервис для работы с сообщениями
 */
@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserInfoService userInfoService;
    private final UserInfoTelegramChatsService userInfoTelegramChatsService;
    private final TelegramService telegramService;
    private static final String MESSAGE = "Тебе пришло новое сообщение";


    public MessageServiceImpl(UserInfoService userInfoService, MessageRepository messageRepository,
                              UserInfoTelegramChatsService userInfoTelegramChatsService, TelegramService telegramService) {
        this.messageRepository = messageRepository;
        this.userInfoService = userInfoService;
        this.userInfoTelegramChatsService = userInfoTelegramChatsService;
        this.telegramService = telegramService;

    }

    /**
     * Метод для создания сообщений
     *
     * @param dto Объект который необходимо создать
     * @return Созданный объект
     */
    @Override
    public MessageDTO create(MessageDTO dto) {
        MessageEntity message = new MessageEntity();
        message.setMessage(dto.getMessage());
        message.setSender(UserInfoMapper.toUserInfoEntity(dto.getSender()));
        message.setDepartureDate(dto.getDepartureDate());
        message.setIdRecipient(dto.getIdRecipient());
        Long idChat = userInfoTelegramChatsService.getIdChatByTelegramName(
                userInfoService.getTelegramUser(dto.getIdRecipient()));
        if (idChat != null) {
            telegramService.sendMessage(idChat, MESSAGE);
        }

        return MessageMapper.toMessageDTO(messageRepository.save(message));
    }

    /**
     * Метод для получения всех сообщений
     *
     * @return Список всех сообщений
     */
    @Override
    public List<MessageDTO> readAll() {
        return MessageMapper.toMessageDTOList(messageRepository.findAll());
    }

    /**
     * Метод для обновления сообщения
     *
     * @param id  Идентификатор сообщения
     * @param dto Сообщение которое надо обновить
     * @return Обновленное сообщение
     */
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

    /**
     * Метод для удаления сообщения
     *
     * @param id Идентификатор сообщения
     */
    @Override
    public void delete(int id) {
        messageRepository.deleteById(id);
    }

    /**
     * Метод для получения пришедших сообщений
     *
     * @param idRecipient Идентификатор пользователя
     * @return Список всех полученных сообщений
     */
    @Override
    public List<MessageDTO> getMessages(int idRecipient) {
        return MessageMapper.toMessageDTOList(messageRepository.findByIdRecipient(idRecipient));
    }

    /**
     * Метод для получения диалога двух пользователей
     *
     * @param idSender    Идентификатор отправителя
     * @param idRecipient Идентификатор получателя
     * @return Список всех сообщений между пользователями
     */
    @Override
    public List<MessageDTO> getConversation(int idSender, int idRecipient) {
        return MessageMapper.toMessageDTOList(messageRepository.findByIdSenderAndIdRecipient(idSender, idRecipient));
    }

    /**
     * Метод для получения последних сообщений от разных пользователей
     *
     * @param idRecipient Идентификатор пользователя
     * @return Список последних сообщений
     */
    @Override
    public List<MessageDTO> getDistinctDialog(int idRecipient) {
        return MessageMapper.toMessageDTOList(messageRepository.findDistinctDialog(idRecipient));
    }

}
