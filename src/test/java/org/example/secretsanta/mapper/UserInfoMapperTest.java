package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoMapperTest {

    @Test
    void testToUserInfoDTO() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setId(1);
        userInfoEntity.setName("Test User");
        userInfoEntity.setPassword("password");
        userInfoEntity.setTelegram("@testuser");

        UserInfoDTO userInfoDTO = UserInfoMapper.toUserInfoDTO(userInfoEntity);

        assertEquals(userInfoEntity.getId(), userInfoDTO.getIdUserInfo());
        assertEquals(userInfoEntity.getName(), userInfoDTO.getName());
        assertEquals(userInfoEntity.getPassword(), userInfoDTO.getPassword());
        assertEquals(userInfoEntity.getTelegram(), userInfoDTO.getTelegram());
    }

    @Test
    void testToUserInfoEntity() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setIdUserInfo(1);
        userInfoDTO.setName("Test User");
        userInfoDTO.setPassword("password");
        userInfoDTO.setTelegram("@testuser");

        UserInfoEntity userInfoEntity = UserInfoMapper.toUserInfoEntity(userInfoDTO);

        assertEquals(userInfoDTO.getIdUserInfo(), userInfoEntity.getId());
        assertEquals(userInfoDTO.getName(), userInfoEntity.getName());
        assertEquals(userInfoDTO.getPassword(), userInfoEntity.getPassword());
        assertEquals(userInfoDTO.getTelegram(), userInfoEntity.getTelegram());
    }

    @Test
    void testToUserInfoDTOList() {
        UserInfoEntity userInfoEntity1 = new UserInfoEntity();
        userInfoEntity1.setId(1);
        UserInfoEntity userInfoEntity2 = new UserInfoEntity();
        userInfoEntity2.setId(2);
        List<UserInfoEntity> userInfoEntityList = Arrays.asList(userInfoEntity1, userInfoEntity2);

        List<UserInfoDTO> userInfoDTOList = UserInfoMapper.toUserInfoDTOList(userInfoEntityList);

        assertEquals(userInfoEntityList.size(), userInfoDTOList.size());
        assertEquals(userInfoEntityList.get(0).getId(), userInfoDTOList.get(0).getIdUserInfo());
        assertEquals(userInfoEntityList.get(1).getId(), userInfoDTOList.get(1).getIdUserInfo());
    }

    @Test
    void testToUserInfoDTO_Null() {
        UserInfoDTO userInfoDTO = UserInfoMapper.toUserInfoDTO(null);

        assertNotNull(userInfoDTO);
    }

    @Test
    void testToUserInfoEntity_Null() {
        UserInfoEntity userInfoEntity = UserInfoMapper.toUserInfoEntity(null);

        assertNotNull(userInfoEntity);
    }

    @Test
    void testToUserInfoDTOList_Null() {
        List<UserInfoDTO> userInfoDTOList = UserInfoMapper.toUserInfoDTOList(null);

        assertNotNull(userInfoDTOList);
    }

}