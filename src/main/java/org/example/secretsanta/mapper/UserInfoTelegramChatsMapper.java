package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserInfoTelegramChatsMapper {

    public static UserInfoTelegramChatsDTO toUserInfoTelegramChatsDTO
            (UserInfoTelegramChatsEntity userInfoTelegramChatsEntity) {

        if (userInfoTelegramChatsEntity == null) {
            return null;
        }

        UserInfoTelegramChatsDTO userInfoTelegramChatsDTO = new UserInfoTelegramChatsDTO();
        userInfoTelegramChatsDTO.setIdChat(userInfoTelegramChatsEntity.getIdChat());
        userInfoTelegramChatsDTO.setUserInfoDTO(UserInfoMapper.toUserInfoDTO(userInfoTelegramChatsEntity.getUserInfo()));
        userInfoTelegramChatsDTO.setIdUserInfoTelegramChat(userInfoTelegramChatsEntity.getIdUserInfoTelegramChat());

        return userInfoTelegramChatsDTO;
    }

    public static UserInfoTelegramChatsEntity toUserInfoTelegramChatsEntity
            (UserInfoTelegramChatsDTO userInfoTelegramChatsDTO) {

        if (userInfoTelegramChatsDTO == null) {
            return null;
        }

        UserInfoTelegramChatsEntity userInfoTelegramChatsEntity = new UserInfoTelegramChatsEntity();
        userInfoTelegramChatsEntity.setIdChat(userInfoTelegramChatsDTO.getIdChat());
        userInfoTelegramChatsEntity.setUserInfo(UserInfoMapper
                .toUserInfoEntity(userInfoTelegramChatsDTO.getUserInfoDTO()));
        userInfoTelegramChatsEntity.setIdUserInfoTelegramChat(userInfoTelegramChatsDTO.getIdUserInfoTelegramChat());

        return userInfoTelegramChatsEntity;
    }


    public static List<UserInfoTelegramChatsDTO> toUserInfoTelegramChatsDTOList
            (List<UserInfoTelegramChatsEntity> userInfoTelegramChatsEntityList) {

        if (userInfoTelegramChatsEntityList == null) {
            return null;
        }

        return userInfoTelegramChatsEntityList.stream()
                .map(UserInfoTelegramChatsMapper::toUserInfoTelegramChatsDTO)
                .collect(Collectors.toList());

    }

}
