package ru.sberschool.secretsanta.mapper;

import org.springframework.stereotype.Component;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.model.entity.UserInfoEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserInfoMapper {

    public UserInfoDTO toUserInfoDTO(UserInfoEntity userInfoEntity) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();

        if (userInfoEntity == null) {
            throw new IllegalArgumentException("UserInfoEntity cannot be null");
        }

        userInfoDTO.setIdUserInfo(userInfoEntity.getId());
        userInfoDTO.setName(userInfoEntity.getName());
        userInfoDTO.setPassword(userInfoEntity.getPassword());
        userInfoDTO.setTelegram(userInfoEntity.getTelegram());

        return userInfoDTO;
    }

    public UserInfoEntity toUserInfoEntity(UserInfoDTO userInfoDTO) {
        UserInfoEntity userInfoEntity = new UserInfoEntity();

        if (userInfoDTO == null) {
            throw new IllegalArgumentException("UserInfoDTO cannot be null");
        }

        userInfoEntity.setId(userInfoDTO.getIdUserInfo());
        userInfoEntity.setName(userInfoDTO.getName());
        userInfoEntity.setPassword(userInfoDTO.getPassword());
        userInfoEntity.setTelegram(userInfoDTO.getTelegram());

        return userInfoEntity;
    }

    public List<UserInfoDTO> toUserInfoDTOList(List<UserInfoEntity> userInfoEntityList) {
        if (userInfoEntityList == null) {
            throw new IllegalArgumentException("UserInfoEntityList cannot be null");
        }

        return userInfoEntityList.stream()
                .map(this::toUserInfoDTO)
                .collect(Collectors.toList());

    }

}
