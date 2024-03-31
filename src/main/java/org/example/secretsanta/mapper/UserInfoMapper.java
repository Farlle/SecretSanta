package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserInfoMapper {

    public static UserInfoDTO toUserInfoDTO(UserInfoEntity userInfoEntity) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();

        if (userInfoEntity == null) {
            return userInfoDTO;
        }

        userInfoDTO.setIdUserInfo(userInfoEntity.getId());
        userInfoDTO.setName(userInfoEntity.getName());
        userInfoDTO.setPassword(userInfoEntity.getPassword());
        userInfoDTO.setTelegram(userInfoEntity.getTelegram());

        return userInfoDTO;
    }

    public static UserInfoEntity toUserInfoEntity(UserInfoDTO userInfoDTO) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();

        if (userInfoDTO == null) {
            return userInfoEntity;
        }

        userInfoEntity.setId(userInfoDTO.getIdUserInfo());
        userInfoEntity.setName(userInfoDTO.getName());
        userInfoEntity.setPassword(userInfoDTO.getPassword());
        userInfoEntity.setTelegram(userInfoDTO.getTelegram());

        return userInfoEntity;
    }

    public static List<UserInfoDTO> toUserInfoDTOList(List<UserInfoEntity> userInfoEntityList) {
        if (userInfoEntityList == null) {
            return Collections.emptyList();
        }

        return userInfoEntityList.stream()
                .map(UserInfoMapper::toUserInfoDTO)
                .collect(Collectors.toList());

    }

}
