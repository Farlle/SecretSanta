package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.exception.UserAlreadyExistsException;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.UserInfoRepository;
import org.example.secretsanta.service.UserInfoService;
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

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Метод для получения пользователя по id
     *
     * @param id Идентификатор пользователя
     * @return Найденый пользователь
     */
    @Override
    public UserInfoDTO getUserInfoById(int id) {
        return UserInfoMapper.toUserInfoDTO(userInfoRepository.findById(id).orElseThrow());
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

        return UserInfoMapper.toUserInfoDTO(userInfoRepository.save(userInfoEntity));
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
        return UserInfoMapper.toUserInfoDTO(userInfoRepository.save(userInfo));
    }

    /**
     * Метод получающий список всех пользователей
     *
     * @return Список всех пользоватлей
     */
    @Override
    public List<UserInfoDTO> readAll() {
        return UserInfoMapper.toUserInfoDTOList(userInfoRepository.findAll());
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
        return UserInfoMapper.toUserInfoDTOList(userInfoRepository.findAllById(idUsers));
    }

    /**
     * Метод для получения информации о пользователе по его нику в телеграм
     *
     * @param telegram Ник пользователя
     * @return Пользователь
     */
    @Override
    public UserInfoDTO getUsersInfoByTelegram(String telegram) {
        return UserInfoMapper.toUserInfoDTO(userInfoRepository.findByTelegram(telegram));
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
