package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        UserInfoTelegramChatsDTO dto = new UserInfoTelegramChatsDTO();

        UserInfoTelegramChatsEntity entity = new UserInfoTelegramChatsEntity();

        when(userInfoTelegramChatsRepository.save(any(UserInfoTelegramChatsEntity.class))).thenReturn(entity);

        UserInfoTelegramChatsDTO result = userInfoTelegramChatsService.create(dto);

        verify(userInfoTelegramChatsRepository, times(1)).save(any(UserInfoTelegramChatsEntity
                .class));
        assertEquals(UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTO(entity), result);
    }

    @Test
    void testReadAll() {
        List<UserInfoTelegramChatsEntity> entities = Arrays.asList(new UserInfoTelegramChatsEntity(),
                new UserInfoTelegramChatsEntity());
        when(userInfoTelegramChatsRepository.findAll()).thenReturn(entities);

        List<UserInfoTelegramChatsDTO> result = userInfoTelegramChatsService.readAll();

        verify(userInfoTelegramChatsRepository, times(1)).findAll();
        assertEquals(entities.size(), result.size());
    }

    @Test
    void testUpdate() {
        int id = 1;
        UserInfoTelegramChatsDTO dto = new UserInfoTelegramChatsDTO();

        UserInfoTelegramChatsEntity entity = new UserInfoTelegramChatsEntity();

        when(userInfoTelegramChatsRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userInfoTelegramChatsRepository.save(any(UserInfoTelegramChatsEntity.class))).thenReturn(entity);

        UserInfoTelegramChatsDTO result = userInfoTelegramChatsService.update(id, dto);

        verify(userInfoTelegramChatsRepository, times(1)).findById(id);
        verify(userInfoTelegramChatsRepository, times(1))
                .save(any(UserInfoTelegramChatsEntity.class));
        assertEquals(UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTO(entity), result);
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
        assertEquals(UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTO(entity), result);
    }

    @Test
    void testGetAllIdChatsUsersWhoNeedNotify() {
        int idRoom = 1;
        List<UserInfoTelegramChatsEntity> entities = Arrays.asList(new UserInfoTelegramChatsEntity(), new UserInfoTelegramChatsEntity());
        when(userInfoTelegramChatsRepository.findAllUserChatsWhoNeedNotify(idRoom)).thenReturn(entities);

        List<UserInfoTelegramChatsDTO> result = userInfoTelegramChatsService.getAllUserChatsWhoNeedNotify(idRoom);

        verify(userInfoTelegramChatsRepository, times(1)).findAllUserChatsWhoNeedNotify(idRoom);
        assertEquals(entities.size(), result.size());
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