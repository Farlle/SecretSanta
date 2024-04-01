package ru.sberschool.secretsanta.mapper;

import org.springframework.stereotype.Component;
import ru.sberschool.secretsanta.dto.UserInfoTelegramChatsDTO;
import ru.sberschool.secretsanta.model.entity.UserInfoTelegramChatsEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserInfoTelegramChatsMapper {

    private final UserInfoMapper userInfoMapper;

    public UserInfoTelegramChatsMapper(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    public UserInfoTelegramChatsDTO toUserInfoTelegramChatsDTO
            (UserInfoTelegramChatsEntity userInfoTelegramChatsEntity) {
        UserInfoTelegramChatsDTO userInfoTelegramChatsDTO = new UserInfoTelegramChatsDTO();

        if (userInfoTelegramChatsEntity == null) {
            throw new IllegalArgumentException("UserInfoTelegramChatsEntity cannot be null");
        }

        userInfoTelegramChatsDTO.setIdChat(userInfoTelegramChatsEntity.getIdChat());
        userInfoTelegramChatsDTO.setUserInfoDTO(userInfoMapper.toUserInfoDTO(userInfoTelegramChatsEntity.getUserInfo()));
        userInfoTelegramChatsDTO.setIdUserInfoTelegramChat(userInfoTelegramChatsEntity.getIdUserInfoTelegramChat());

        return userInfoTelegramChatsDTO;
    }

    public UserInfoTelegramChatsEntity toUserInfoTelegramChatsEntity
            (UserInfoTelegramChatsDTO userInfoTelegramChatsDTO) {
        UserInfoTelegramChatsEntity userInfoTelegramChatsEntity = new UserInfoTelegramChatsEntity();

        if (userInfoTelegramChatsDTO == null) {
            throw new IllegalArgumentException("UserInfoTelegramChatsDTO cannot be null");
        }

        userInfoTelegramChatsEntity.setIdChat(userInfoTelegramChatsDTO.getIdChat());
        userInfoTelegramChatsEntity.setUserInfo(userInfoMapper
                .toUserInfoEntity(userInfoTelegramChatsDTO.getUserInfoDTO()));
        userInfoTelegramChatsEntity.setIdUserInfoTelegramChat(userInfoTelegramChatsDTO.getIdUserInfoTelegramChat());

        return userInfoTelegramChatsEntity;
    }


    public List<UserInfoTelegramChatsDTO> toUserInfoTelegramChatsDTOList
            (List<UserInfoTelegramChatsEntity> userInfoTelegramChatsEntityList) {

        if (userInfoTelegramChatsEntityList == null) {
            throw new IllegalArgumentException("UserInfoTelegramChatsEntityList cannot be null");
        }

        return userInfoTelegramChatsEntityList.stream()
                .map(this::toUserInfoTelegramChatsDTO)
                .collect(Collectors.toList());

    }

}
