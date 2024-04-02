package ru.sberschool.secretsanta.service;

import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.exception.UserAlreadyExistsException;

import java.util.List;

public interface UserInfoService {

    UserInfoDTO getUserInfoById(int id);

    UserInfoDTO update(int id, UserInfoDTO dto);

    UserInfoDTO create(UserInfoDTO dto);

    List<UserInfoDTO> readAll();

    void delete(int id);

    void registerNewUserInfoAccount(UserInfoDTO dto) throws UserAlreadyExistsException;

    List<UserInfoDTO> getUsersInfoById(List<Integer> idUsers);

    UserInfoDTO getUsersInfoByTelegram(String telegram);

    String getTelegramUser(int idUser);
}
