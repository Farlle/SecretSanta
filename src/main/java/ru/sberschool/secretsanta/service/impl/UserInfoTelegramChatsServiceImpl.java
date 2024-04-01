package ru.sberschool.secretsanta.service.impl;

import org.springframework.stereotype.Service;
import ru.sberschool.secretsanta.dto.UserInfoTelegramChatsDTO;
import ru.sberschool.secretsanta.mapper.UserInfoMapper;
import ru.sberschool.secretsanta.mapper.UserInfoTelegramChatsMapper;
import ru.sberschool.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import ru.sberschool.secretsanta.repository.UserInfoTelegramChatsRepository;
import ru.sberschool.secretsanta.service.UserInfoTelegramChatsService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Сервис для работы с чатом пользователя в телегарм
 */
@Service
public class UserInfoTelegramChatsServiceImpl implements UserInfoTelegramChatsService {

    private final UserInfoTelegramChatsRepository userInfoTelegramChatsRepository;
    private final UserInfoTelegramChatsMapper userInfoTelegramChatsMapper;
    private final UserInfoMapper userInfoMapper;

    public UserInfoTelegramChatsServiceImpl(UserInfoTelegramChatsRepository userInfoTelegramChatsRepository, UserInfoTelegramChatsMapper userInfoTelegramChatsMapper, UserInfoMapper userInfoMapper) {
        this.userInfoTelegramChatsRepository = userInfoTelegramChatsRepository;
        this.userInfoTelegramChatsMapper = userInfoTelegramChatsMapper;
        this.userInfoMapper = userInfoMapper;
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
        userInfoTelegramChats.setUserInfo(userInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));
        userInfoTelegramChats.setIdUserInfoTelegramChat(dto.getIdUserInfoTelegramChat());
        userInfoTelegramChats.setIdChat(dto.getIdChat());
        return userInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsRepository.save(userInfoTelegramChats));
    }

    /**
     * Метод получающий все чаты в телеграм
     *
     * @return Возвращает все чаты со всеми пользователями
     */
    @Override
    public List<UserInfoTelegramChatsDTO> readAll() {
        return userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(userInfoTelegramChatsRepository.findAll());
    }

    /**
     * Метод для обновления чата пользователя в телеграм
     *
     * @param id  Идентификатор чато
     * @param dto Обект для обновления
     * @return Обновленный чат
     */
    @Override
    public UserInfoTelegramChatsDTO update(int id, UserInfoTelegramChatsDTO dto) {

        UserInfoTelegramChatsEntity userInfoTelegramChats = userInfoTelegramChatsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("userInfoTelegramChats not found with id: " + id));

        userInfoTelegramChats.setUserInfo(userInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));
        userInfoTelegramChats.setIdUserInfoTelegramChat(dto.getIdUserInfoTelegramChat());
        userInfoTelegramChats.setIdChat(dto.getIdChat());
        return userInfoTelegramChatsMapper
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
        return userInfoTelegramChatsMapper
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
        return userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(
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
