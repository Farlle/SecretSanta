package org.example.secretsanta.service.serviceinterface;

import org.example.secretsanta.dto.UserInfoDTO;

import java.util.List;

public interface UserInfoService {

    UserInfoDTO getUserInfoById(int id);

    UserInfoDTO update(int id, UserInfoDTO dto);

    UserInfoDTO create(UserInfoDTO dto);

    List<UserInfoDTO> readAll();

    void delete(int id);

    void registerNewUserInfoAccount(UserInfoDTO dto) throws Exception;

    List<UserInfoDTO> getUsersInfoById(List<Integer> usersIds);

    UserInfoDTO getUsersInfoByTelegram(String telegram);
}
