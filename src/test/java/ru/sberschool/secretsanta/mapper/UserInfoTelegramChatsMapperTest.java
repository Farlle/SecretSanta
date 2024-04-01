package ru.sberschool.secretsanta.mapper;

import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.dto.UserInfoTelegramChatsDTO;
import ru.sberschool.secretsanta.model.entity.UserInfoEntity;
import ru.sberschool.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserInfoTelegramChatsMapperTest {

    @Mock
    private UserInfoMapper userInfoMapper;
    @InjectMocks
    private UserInfoTelegramChatsMapper userInfoTelegramChatsMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testToUserInfoTelegramChatsDTO() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        when(userInfoMapper.toUserInfoDTO(any())).thenReturn(userInfoDTO);

        UserInfoTelegramChatsEntity userInfoTelegramChatsEntity = new UserInfoTelegramChatsEntity();
        userInfoTelegramChatsEntity.setIdChat(1L);
        userInfoTelegramChatsEntity.setIdUserInfoTelegramChat(2);

        UserInfoTelegramChatsDTO userInfoTelegramChatsDTO = userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTO(userInfoTelegramChatsEntity);

        assertEquals(userInfoTelegramChatsEntity.getIdChat(), userInfoTelegramChatsDTO.getIdChat());
        assertEquals(userInfoDTO, userInfoTelegramChatsDTO.getUserInfoDTO());
        assertEquals(userInfoTelegramChatsEntity.getIdUserInfoTelegramChat(), userInfoTelegramChatsDTO.getIdUserInfoTelegramChat());
    }

    @Test
    void testToUserInfoTelegramChatsEntity() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        when(userInfoMapper.toUserInfoEntity(any())).thenReturn(userInfoEntity);

        UserInfoTelegramChatsDTO userInfoTelegramChatsDTO = new UserInfoTelegramChatsDTO();
        userInfoTelegramChatsDTO.setIdChat(1L);
        userInfoTelegramChatsDTO.setIdUserInfoTelegramChat(2);

        UserInfoTelegramChatsEntity userInfoTelegramChatsEntity = userInfoTelegramChatsMapper.toUserInfoTelegramChatsEntity(userInfoTelegramChatsDTO);

       assertEquals(userInfoTelegramChatsDTO.getIdChat(), userInfoTelegramChatsEntity.getIdChat());
       assertEquals(userInfoEntity, userInfoTelegramChatsEntity.getUserInfo());
       assertEquals(userInfoTelegramChatsDTO.getIdUserInfoTelegramChat(), userInfoTelegramChatsEntity.getIdUserInfoTelegramChat());
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
                userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(userInfoTelegramChatsEntityList);

        assertEquals(userInfoTelegramChatsEntityList.size(), userInfoTelegramChatsDTOList.size());
        assertEquals(userInfoTelegramChatsEntityList.get(0).getIdChat(), userInfoTelegramChatsDTOList.get(0).getIdChat());
        assertEquals(userInfoTelegramChatsEntityList.get(1).getIdChat(), userInfoTelegramChatsDTOList.get(1).getIdChat());
    }

    @Test
    void testToUserInfoTelegramChatsDTO_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTO(null);
        });
    }

    @Test
    void testToUserInfoTelegramChatsEntity_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            userInfoTelegramChatsMapper.toUserInfoTelegramChatsEntity(null);
        });
    }

    @Test
    void testToUserInfoTelegramChatsDTOList_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(null);
        });
    }
}