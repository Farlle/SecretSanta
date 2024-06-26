package ru.sberschool.secretsanta.service.impl;

import org.springframework.stereotype.Service;
import ru.sberschool.secretsanta.dto.MessageDTO;
import ru.sberschool.secretsanta.mapper.MessageMapper;
import ru.sberschool.secretsanta.mapper.UserInfoMapper;
import ru.sberschool.secretsanta.model.entity.MessageEntity;
import ru.sberschool.secretsanta.repository.MessageRepository;
import ru.sberschool.secretsanta.service.MessageService;
import ru.sberschool.secretsanta.service.TelegramService;
import ru.sberschool.secretsanta.service.UserInfoService;
import ru.sberschool.secretsanta.service.UserInfoTelegramChatsService;

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
    private final MessageMapper messageMapper;
    private final UserInfoMapper userInfoMapper;
    private static final String MESSAGE = "Тебе пришло новое сообщение";


    public MessageServiceImpl(UserInfoService userInfoService, MessageRepository messageRepository,
                              UserInfoTelegramChatsService userInfoTelegramChatsService,
                              TelegramService telegramService, MessageMapper messageMapper, UserInfoMapper userInfoMapper) {
        this.messageRepository = messageRepository;
        this.userInfoService = userInfoService;
        this.userInfoTelegramChatsService = userInfoTelegramChatsService;
        this.telegramService = telegramService;
        this.messageMapper = messageMapper;
        this.userInfoMapper = userInfoMapper;
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
        message.setSender(userInfoMapper.toUserInfoEntity(dto.getSender()));
        message.setDepartureDate(dto.getDepartureDate());
        message.setIdRecipient(dto.getIdRecipient());
        Long idChat = userInfoTelegramChatsService.getIdChatByTelegramName(
                userInfoService.getTelegramUser(dto.getIdRecipient()));
        if (idChat != null) {
            telegramService.sendMessage(idChat, MESSAGE);
        }

        return messageMapper.toMessageDTO(messageRepository.save(message));
    }

    /**
     * Метод для получения всех сообщений
     *
     * @return Список всех сообщений
     */
    @Override
    public List<MessageDTO> readAll() {
        return messageMapper.toMessageDTOList(messageRepository.findAll());
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
        message.setSender(userInfoMapper.toUserInfoEntity(dto.getSender()));
        message.setDepartureDate(dto.getDepartureDate());
        message.setIdRecipient(dto.getIdRecipient());

        return messageMapper.toMessageDTO(messageRepository.save(message));
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
        return messageMapper.toMessageDTOList(messageRepository.findByIdRecipient(idRecipient));
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
        return messageMapper.toMessageDTOList(messageRepository.findByIdSenderAndIdRecipient(idSender, idRecipient));
    }

    /**
     * Метод для получения последних сообщений от разных пользователей
     *
     * @param idRecipient Идентификатор пользователя
     * @return Список последних сообщений
     */
    @Override
    public List<MessageDTO> getDistinctDialog(int idRecipient) {
        return messageMapper.toMessageDTOList(messageRepository.findDistinctDialog(idRecipient));
    }

}
