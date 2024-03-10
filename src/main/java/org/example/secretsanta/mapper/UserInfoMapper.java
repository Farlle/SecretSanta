package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.springframework.stereotype.Component;

@Component
public class UserInfoMapper {

    public static UserInfoDTO toUserInfoDTO(UserInfoEntity userInfoEntity) {
        if (userInfoEntity == null) {
            return null;
        }

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setIdUserInfo(userInfoEntity.getId());
        userInfoDTO.setName(userInfoEntity.getName());
        userInfoDTO.setPassword(userInfoEntity.getPassword());
        userInfoDTO.setTelegram(userInfoEntity.getTelegram());

        return userInfoDTO;
    }

    public static UserInfoEntity toUserInfoEntity(UserInfoDTO userInfoDTO) {
        if (userInfoDTO == null) {
            return null;
        }

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setId(userInfoDTO.getIdUserInfo());
        userInfoEntity.setName(userInfoDTO.getName());
        userInfoEntity.setPassword(userInfoDTO.getPassword());
        userInfoEntity.setTelegram(userInfoDTO.getTelegram());

        return userInfoEntity;
    }

}
