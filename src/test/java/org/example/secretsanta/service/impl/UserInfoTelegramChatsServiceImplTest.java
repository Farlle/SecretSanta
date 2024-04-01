package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.mapper.UserInfoTelegramChatsMapper;
import org.example.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import org.example.secretsanta.repository.UserInfoTelegramChatsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserInfoTelegramChatsServiceImplTest {
    @Mock
    private UserInfoTelegramChatsRepository userInfoTelegramChatsRepository;
    @InjectMocks
    private UserInfoTelegramChatsServiceImpl userInfoTelegramChatsService;
    @Mock
    private UserInfoTelegramChatsMapper userInfoTelegramChatsMapper;
    @Mock
    private UserInfoMapper userInfoMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        UserInfoTelegramChatsDTO dto = new UserInfoTelegramChatsDTO();
        UserInfoTelegramChatsEntity entity = new UserInfoTelegramChatsEntity();
        when(userInfoMapper.toUserInfoEntity(dto.getUserInfoDTO())).thenReturn(entity.getUserInfo());
        when(userInfoTelegramChatsRepository.save(entity)).thenReturn(entity);
        when(userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTO(entity)).thenReturn(dto);

        UserInfoTelegramChatsDTO result = userInfoTelegramChatsService.create(dto);
        assertEquals(dto, result);
        verify(userInfoTelegramChatsRepository).save(entity);
    }

    @Test
    void testReadAll() {
        List<UserInfoTelegramChatsEntity> entities = Arrays.asList(new UserInfoTelegramChatsEntity(), new UserInfoTelegramChatsEntity());
        when(userInfoTelegramChatsRepository.findAll()).thenReturn(entities);
        List<UserInfoTelegramChatsDTO> dtos = Arrays.asList(new UserInfoTelegramChatsDTO(), new UserInfoTelegramChatsDTO());
        when(userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(entities)).thenReturn(dtos);

        List<UserInfoTelegramChatsDTO> result = userInfoTelegramChatsService.readAll();
        assertEquals(dtos, result);
    }

    @Test
    void testUpdate() {
        int id = 1;
        UserInfoTelegramChatsDTO dto = new UserInfoTelegramChatsDTO();
        UserInfoTelegramChatsEntity entity = new UserInfoTelegramChatsEntity();
        when(userInfoTelegramChatsRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userInfoMapper.toUserInfoEntity(dto.getUserInfoDTO())).thenReturn(entity.getUserInfo());
        when(userInfoTelegramChatsRepository.save(entity)).thenReturn(entity);
        when(userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTO(entity)).thenReturn(dto);

        UserInfoTelegramChatsDTO result = userInfoTelegramChatsService.update(id, dto);
        assertEquals(dto, result);
        verify(userInfoTelegramChatsRepository).save(entity);
    }

    @Test
    void testDelete() {
        int id = 1;

        userInfoTelegramChatsService.delete(id);

        verify(userInfoTelegramChatsRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetRegisterUserByIdChats() {
        Long idChat = 1L;
        UserInfoTelegramChatsEntity entity = new UserInfoTelegramChatsEntity();

        when(userInfoTelegramChatsRepository.findFirstUserInfoTelegramChatsEntitiesByIdChat(idChat)).thenReturn(entity);

        UserInfoTelegramChatsDTO result = userInfoTelegramChatsService.getRegisterUserByIdChats(idChat);

        verify(userInfoTelegramChatsRepository, times(1)).findFirstUserInfoTelegramChatsEntitiesByIdChat(idChat);
        assertEquals(userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTO(entity), result);
    }

    @Test
    void testGetAllIdChatsUsersWhoNeedNotify() {
        int idRoom = 1;
        List<UserInfoTelegramChatsEntity> entities = Arrays.asList(new UserInfoTelegramChatsEntity(), new UserInfoTelegramChatsEntity());
        List<UserInfoTelegramChatsDTO> dtos = Arrays.asList(new UserInfoTelegramChatsDTO(), new UserInfoTelegramChatsDTO());
        when(userInfoTelegramChatsRepository.findAllUserChatsWhoNeedNotify(idRoom)).thenReturn(entities);
        when(userInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(entities)).thenReturn(dtos);

        List<UserInfoTelegramChatsDTO> result = userInfoTelegramChatsService.getAllUserChatsWhoNeedNotify(idRoom);
        assertEquals(dtos, result);
    }

    @Test
    void testGetIdChatByTelegramName() {
        String telegramName = "test_user";
        Long idChat = 1L;
        when(userInfoTelegramChatsRepository.getIdChatByTelegramName(telegramName)).thenReturn(idChat);

        Long result = userInfoTelegramChatsService.getIdChatByTelegramName(telegramName);

        verify(userInfoTelegramChatsRepository, times(1)).getIdChatByTelegramName(telegramName);
        assertEquals(idChat, result);
    }
}