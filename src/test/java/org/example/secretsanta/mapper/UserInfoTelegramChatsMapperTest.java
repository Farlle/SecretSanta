package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoTelegramChatsMapperTest {

    @Test
    void testToUserInfoTelegramChatsDTO() {
        UserInfoTelegramChatsEntity userInfoTelegramChatsEntity = new UserInfoTelegramChatsEntity();
        userInfoTelegramChatsEntity.setIdChat(1L);
        userInfoTelegramChatsEntity.setUserInfo(new UserInfoEntity());
        userInfoTelegramChatsEntity.setIdUserInfoTelegramChat(2);

        UserInfoTelegramChatsDTO userInfoTelegramChatsDTO = UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsEntity);

        assertEquals(userInfoTelegramChatsEntity.getIdChat(), userInfoTelegramChatsDTO.getIdChat());
        assertEquals(userInfoTelegramChatsEntity.getUserInfo(), UserInfoMapper
                .toUserInfoEntity(userInfoTelegramChatsDTO.getUserInfoDTO()));
        assertEquals(userInfoTelegramChatsEntity.getIdUserInfoTelegramChat(),
                userInfoTelegramChatsDTO.getIdUserInfoTelegramChat());
    }

    @Test
    void testToUserInfoTelegramChatsEntity() {
        UserInfoTelegramChatsDTO userInfoTelegramChatsDTO = new UserInfoTelegramChatsDTO();
        userInfoTelegramChatsDTO.setIdChat(1L);
        userInfoTelegramChatsDTO.setUserInfoDTO(new UserInfoDTO());
        userInfoTelegramChatsDTO.setIdUserInfoTelegramChat(2);

        UserInfoTelegramChatsEntity userInfoTelegramChatsEntity = UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsEntity(userInfoTelegramChatsDTO);

        assertEquals(userInfoTelegramChatsDTO.getIdChat(), userInfoTelegramChatsEntity.getIdChat());
        assertEquals(userInfoTelegramChatsDTO.getUserInfoDTO(),
                UserInfoMapper.toUserInfoDTO(userInfoTelegramChatsEntity.getUserInfo()));
        assertEquals(userInfoTelegramChatsDTO.getIdUserInfoTelegramChat(), userInfoTelegramChatsEntity
                .getIdUserInfoTelegramChat());
    }

    @Test
    void testToUserInfoTelegramChatsDTOList() {
        UserInfoTelegramChatsEntity userInfoTelegramChatsEntity1 = new UserInfoTelegramChatsEntity();
        userInfoTelegramChatsEntity1.setIdChat(1L);
        UserInfoTelegramChatsEntity userInfoTelegramChatsEntity2 = new UserInfoTelegramChatsEntity();
        userInfoTelegramChatsEntity2.setIdChat(2L);
        List<UserInfoTelegramChatsEntity> userInfoTelegramChatsEntityList =
                Arrays.asList(userInfoTelegramChatsEntity1, userInfoTelegramChatsEntity2);

        List<UserInfoTelegramChatsDTO> userInfoTelegramChatsDTOList =
                UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(userInfoTelegramChatsEntityList);

        assertEquals(userInfoTelegramChatsEntityList.size(), userInfoTelegramChatsDTOList.size());
        assertEquals(userInfoTelegramChatsEntityList.get(0).getIdChat(), userInfoTelegramChatsDTOList.get(0).getIdChat());
        assertEquals(userInfoTelegramChatsEntityList.get(1).getIdChat(), userInfoTelegramChatsDTOList.get(1).getIdChat());
    }

    @Test
    void testToUserInfoTelegramChatsDTO_Null() {
        UserInfoTelegramChatsDTO userInfoTelegramChatsDTO
                = UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTO(null);

        assertNotNull(userInfoTelegramChatsDTO);
    }

    @Test
    void testToUserInfoTelegramChatsEntity_Null() {
        UserInfoTelegramChatsEntity userInfoTelegramChatsEntity
                = UserInfoTelegramChatsMapper.toUserInfoTelegramChatsEntity(null);

        assertNotNull(userInfoTelegramChatsEntity);
    }

    @Test
    void testToUserInfoTelegramChatsDTOList_Null() {
        List<UserInfoTelegramChatsDTO> userInfoTelegramChatsDTOList
                = UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(null);

        assertNotNull(userInfoTelegramChatsDTOList);
    }
}