package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.*;
import org.example.secretsanta.mapper.ResultMapper;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.ResultEntity;
import org.example.secretsanta.repository.ResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ResultServiceImplTest {
    @Mock
    private ResultRepository resultRepository;

    @Mock
    private UserInfoServiceImpl userInfoServiceImpl;

    @Mock
    private UserInfoTelegramChatsServiceImpl userInfoTelegramChatsServiceImpl;

    @Mock
    private TelegramServiceImpl telegramServiceImpl;

    @Mock
    private RoomServiceImpl roomServiceImpl;

    @InjectMocks
    private ResultServiceImpl resultService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create() {
        ResultDTO dto = new ResultDTO();

        ResultEntity result = new ResultEntity();

        when(resultRepository.save(any(ResultEntity.class))).thenReturn(result);

        ResultDTO createdDTO = resultService.create(dto);

        verify(resultRepository, times(1)).save(any(ResultEntity.class));
        assertEquals(ResultMapper.toResultDTO(result), createdDTO);
    }

    @Test
    void readAll() {
        List<ResultEntity> results = new ArrayList<>();

        when(resultRepository.findAll()).thenReturn(results);

        List<ResultDTO> resultDTOs = resultService.readAll();

        verify(resultRepository, times(1)).findAll();
        assertEquals(ResultMapper.toResultDTOList(results), resultDTOs);
    }

    @Test
    void update() {
        int id = 1;
        ResultDTO dto = new ResultDTO();

        ResultEntity result = new ResultEntity();

        when(resultRepository.findById(id)).thenReturn(Optional.of(result));
        when(resultRepository.save(any(ResultEntity.class))).thenReturn(result);

        ResultDTO updatedDTO = resultService.update(id, dto);

        verify(resultRepository, times(1)).findById(id);
        verify(resultRepository, times(1)).save(any(ResultEntity.class));
        assertEquals(ResultMapper.toResultDTO(result), updatedDTO);
    }

    @Test
    void delete() {
        int id = 1;

        resultService.delete(id);

        verify(resultRepository, times(1)).deleteById(id);
    }

    @Test
    void performDraw() {
        RoomDTO room = new RoomDTO();
        UserInfoDTO user1 = new UserInfoDTO();
        UserInfoDTO user2 = new UserInfoDTO();
        UserInfoDTO user3 = new UserInfoDTO();

        List<UserInfoDTO> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);


        List<UserInfoTelegramChatsDTO> userInfoTelegramChatsDTO = new ArrayList<>();

        when(userInfoServiceImpl.getUsersInfoById(anyList())).thenReturn(users);
        when(userInfoTelegramChatsServiceImpl.getAllIdChatsUsersWhoNeedNotify(anyInt())).thenReturn(userInfoTelegramChatsDTO);
        when(resultRepository.findByRoomIdRoom(anyInt())).thenReturn(new ArrayList<>());
        when(resultRepository.save(any(ResultEntity.class))).thenReturn(new ResultEntity());

        resultService.performDraw(room);

        // Then
        verify(userInfoServiceImpl, times(1)).getUsersInfoById(anyList());
        verify(userInfoTelegramChatsServiceImpl, times(1)).getAllIdChatsUsersWhoNeedNotify(anyInt());
        verify(resultRepository, times(users.size())).save(any(ResultEntity.class));
        verify(telegramServiceImpl, times(userInfoTelegramChatsDTO.size())).sendMessage(anyLong(), anyString());
    }

    @Test
    void showDrawInRoom() {
        int idRoom = 1;
        List<ResultEntity> results = new ArrayList<>();

        when(resultRepository.findAll()).thenReturn(results);

        List<ResultDTO> resultDTOs = resultService.showDrawInRoom(idRoom);

        verify(resultRepository, times(1)).findAll();
    }
}