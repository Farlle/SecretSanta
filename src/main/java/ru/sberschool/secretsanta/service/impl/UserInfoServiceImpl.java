package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.exception.UserAlreadyExistsException;
import ru.sberschool.secretsanta.mapper.UserInfoMapper;
import ru.sberschool.secretsanta.model.entity.UserInfoEntity;
import ru.sberschool.secretsanta.repository.UserInfoRepository;
import ru.sberschool.secretsanta.service.UserInfoService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Сервис для работы с пользователем
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder, UserInfoMapper userInfoMapper) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * Метод для получения пользователя по id
     *
     * @param id Идентификатор пользователя
     * @return Найденый пользователь
     */
    @Override
    public UserInfoDTO getUserInfoById(int id) {
        return userInfoMapper.toUserInfoDTO(userInfoRepository.findById(id).orElseThrow());
    }

    /**
     * Метод для обновления пользователя
     *
     * @param id Идентификатор пользователя
     * @param dto Объект который необходимо обновить
     * @return Обновленный объект
     */
    @Override
    public UserInfoDTO update(int id, UserInfoDTO dto) {
        UserInfoEntity userInfoEntity = userInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));

        userInfoEntity.setName(dto.getName());
        userInfoEntity.setTelegram(dto.getTelegram());
        userInfoEntity.setPassword(passwordEncoder.encode(dto.getPassword()));

        return userInfoMapper.toUserInfoDTO(userInfoRepository.save(userInfoEntity));
    }

    /**
     * Метод для создания пользователя
     *
     * @param dto Объект который необходимо создать
     * @return Созданнный объект
     */
    @Override
    public UserInfoDTO create(UserInfoDTO dto) {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setName(dto.getName());
        userInfo.setPassword(dto.getPassword());
        userInfo.setTelegram(dto.getTelegram());
        return userInfoMapper.toUserInfoDTO(userInfoRepository.save(userInfo));
    }

    /**
     * Метод получающий список всех пользователей
     *
     * @return Список всех пользоватлей
     */
    @Override
    public List<UserInfoDTO> readAll() {
        return userInfoMapper.toUserInfoDTOList(userInfoRepository.findAll());
    }

    /**
     * Метод для ужадения пользователя по id
     *
     * @param id Идентификатор пользователя
     */
    @Override
    public void delete(int id) {
        userInfoRepository.deleteById(id);
    }

    /**
     * Метод для регистрации нового пользовател
     *
     * @param dto Пользователь которого надо зарегистрировать
     * @throws Exception Если пользователь использует занятое имя
     */
    @Override
    public void registerNewUserInfoAccount(UserInfoDTO dto) throws Exception {
        if (userInfoRepository.findAll().stream().anyMatch(userInfo -> userInfo.getName().equals(dto.getName()))) {
            throw new UserAlreadyExistsException("User already exists with this name:" + dto.getName());
        }
        dto.setTelegram(dto.getTelegram().replace("@", ""));
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        create(dto);
    }

    /**
     * Метод для получения списка пользователй по их id
     *
     * @param idUsers Список идентификаторов пользователей
     * @return Список пользователей
     */
    @Override
    public List<UserInfoDTO> getUsersInfoById(List<Integer> idUsers) {
        return userInfoMapper.toUserInfoDTOList(userInfoRepository.findAllById(idUsers));
    }

    /**
     * Метод для получения информации о пользователе по его нику в телеграм
     *
     * @param telegram Ник пользователя
     * @return Пользователь
     */
    @Override
    public UserInfoDTO getUsersInfoByTelegram(String telegram) {
        return userInfoMapper.toUserInfoDTO(userInfoRepository.findByTelegram(telegram));
    }

    /**
     * Метод для получения ника телеграм пользователя
     *
     * @param idUser Идентификатор пользователя
     * @return Телеграм пользователя
     */
    @Override
    public String getTelegramUser(int idUser) {
        UserInfoEntity userInfoEntity = userInfoRepository.findById(idUser).orElseThrow();
        return  userInfoEntity.getTelegram();
    }

}
