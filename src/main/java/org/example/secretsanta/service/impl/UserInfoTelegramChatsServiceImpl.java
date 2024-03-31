package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.mapper.UserInfoTelegramChatsMapper;
import org.example.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import org.example.secretsanta.repository.UserInfoTelegramChatsRepository;
import org.example.secretsanta.service.UserInfoTelegramChatsService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Сервис для работы с чатом пользователя в телегарм
 */
@Service
public class UserInfoTelegramChatsServiceImpl implements UserInfoTelegramChatsService {

    private final UserInfoTelegramChatsRepository userInfoTelegramChatsRepository;

    public UserInfoTelegramChatsServiceImpl(UserInfoTelegramChatsRepository userInfoTelegramChatsRepository) {
        this.userInfoTelegramChatsRepository = userInfoTelegramChatsRepository;
    }

    /**
     * Метод для создания чата с пользоватлем в телеграм
     *
     * @param dto Объект который надо создать
     * @return Созданный объект
     */
    @Override
    public UserInfoTelegramChatsDTO create(UserInfoTelegramChatsDTO dto) {
        UserInfoTelegramChatsEntity userInfoTelegramChats = new UserInfoTelegramChatsEntity();
        userInfoTelegramChats.setUserInfo(UserInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));
        userInfoTelegramChats.setIdUserInfoTelegramChat(dto.getIdUserInfoTelegramChat());
        userInfoTelegramChats.setIdChat(dto.getIdChat());
        return UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsRepository.save(userInfoTelegramChats));
    }

    /**
     * Метод получающий все чаты в телеграм
     *
     * @return Возвращает все чаты со всеми пользователями
     */
    @Override
    public List<UserInfoTelegramChatsDTO> readAll() {
        return UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(userInfoTelegramChatsRepository.findAll());
    }

    /**
     * Метод для обновления чата пользователя в телеграм
     *
     * @param id Идентификатор чато
     * @param dto Обект для обновления
     * @return Обновленный чат
     */
    @Override
    public UserInfoTelegramChatsDTO update(int id, UserInfoTelegramChatsDTO dto) {

        UserInfoTelegramChatsEntity userInfoTelegramChats = userInfoTelegramChatsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("userInfoTelegramChats not found with id: " + id));

        userInfoTelegramChats.setUserInfo(UserInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));
        userInfoTelegramChats.setIdUserInfoTelegramChat(dto.getIdUserInfoTelegramChat());
        userInfoTelegramChats.setIdChat(dto.getIdChat());
        return UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsRepository.save(userInfoTelegramChats));
    }

    /**
     * Метод для удаленяи чата с пользоватлем в телеграм
     *
     * @param id Идентификатор чата
     */
    @Override
    public void delete(int id) {
        userInfoTelegramChatsRepository.deleteById(id);
    }

    /**
     * Метод для получения получения чата с пользователем в телеграм по id чата
     *
     * @param idChat Идентификатор чата выданный телеграм
     * @return Чат с пользоватлем в телеграм
     */
    @Override
    public UserInfoTelegramChatsDTO getRegisterUserByIdChats(Long idChat) {
        return UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsRepository
                        .findFirstUserInfoTelegramChatsEntitiesByIdChat(idChat));
    }

    /**
     * Метод для получения всех чатов, которым надо отправить уведомление в телеграм
     *
     * @param idRoom Идентификатор комнаты
     * @return Список всех чатов пользователя в телеграм
     */
    @Override
    public List<UserInfoTelegramChatsDTO> getAllUserChatsWhoNeedNotify(int idRoom) {
        return UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(
                userInfoTelegramChatsRepository.findAllUserChatsWhoNeedNotify(idRoom));
    }

    /**
     * Метод для поиска идентификатора чата от телеграм
     *
     * @param telegramName имя пользователя в телеграм
     * @return id чата выданный телеграм
     */
    @Override
    public Long getIdChatByTelegramName(String telegramName) {
        return userInfoTelegramChatsRepository.getIdChatByTelegramName(telegramName);
    }

}
