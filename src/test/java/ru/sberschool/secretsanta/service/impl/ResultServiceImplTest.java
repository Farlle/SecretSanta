package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.dto.ResultDTO;
import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.dto.UserInfoTelegramChatsDTO;
import ru.sberschool.secretsanta.mapper.ResultMapper;
import ru.sberschool.secretsanta.mapper.RoomMapper;
import ru.sberschool.secretsanta.model.entity.ResultEntity;
import ru.sberschool.secretsanta.repository.ResultRepository;
import ru.sberschool.secretsanta.service.RoomService;
import ru.sberschool.secretsanta.service.TelegramService;
import ru.sberschool.secretsanta.service.UserInfoService;
import ru.sberschool.secretsanta.service.UserInfoTelegramChatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;
import java.util.stream.Collectors;


import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ResultServiceImplTest {
    @Mock
    private ResultRepository resultRepository;
    @Mock
    private UserInfoService userInfoService;
    @Mock
    private UserInfoTelegramChatsService userInfoTelegramChatsService;
    @Mock
    private TelegramService telegramService;
    @Mock
    private RoomService roomService;
    @InjectMocks
    private ResultServiceImpl resultService;
    @Mock
    private ResultMapper resultMapper;
    @Mock
    private RoomMapper roomMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        ResultDTO dto = new ResultDTO();
        ResultEntity entity = new ResultEntity();
        when(roomMapper.toRoomEntity(dto.getRoomDTO())).thenReturn(entity.getRoom());
        when(resultRepository.save(entity)).thenReturn(entity);
        when(resultMapper.toResultDTO(entity)).thenReturn(dto);

        ResultDTO result = resultService.create(dto);
        assertEquals(dto, result);
        verify(resultRepository).save(entity);
    }

    @Test
    void testReadAll() {
        List<ResultEntity> results = new ArrayList<>();

        when(resultRepository.findAll()).thenReturn(results);

        List<ResultDTO> resultDTOs = resultService.readAll();

        verify(resultRepository, times(1)).findAll();
        assertEquals(resultMapper.toResultDTOList(results), resultDTOs);
    }

    @Test
    void testUpdate() {
        int id = 1;
        ResultDTO dto = new ResultDTO();
        ResultEntity entity = new ResultEntity();
        when(resultRepository.findById(id)).thenReturn(java.util.Optional.of(entity));
        when(roomMapper.toRoomEntity(dto.getRoomDTO())).thenReturn(entity.getRoom());
        when(resultRepository.save(entity)).thenReturn(entity);
        when(resultMapper.toResultDTO(entity)).thenReturn(dto);

        ResultDTO result = resultService.update(id, dto);
        assertEquals(dto, result);
        verify(resultRepository).save(entity);
    }

    @Test
    void testDelete() {
        int id = 1;

        resultService.delete(id);

        verify(resultRepository, times(1)).deleteById(id);
    }

    @Test
    void testPerformDraw() {
        int idRoom = 1;
        RoomDTO room = new RoomDTO();
        room.setIdRoom(idRoom);

        UserInfoDTO user1 = new UserInfoDTO();
        user1.setIdUserInfo(1);
        UserInfoDTO user2 = new UserInfoDTO();
        user2.setIdUserInfo(2);
        List<UserInfoDTO> users = Arrays.asList(user1, user2);

        UserInfoTelegramChatsDTO chat1 = new UserInfoTelegramChatsDTO();
        chat1.setIdChat(1L);
        List<UserInfoTelegramChatsDTO> userInfoTelegramChatsDTO = Collections.singletonList(chat1);

        when(roomService.getUserInfoIdInRoom(idRoom)).thenReturn(Arrays.asList(1, 2));
        when(userInfoService.getUsersInfoById(anyList())).thenReturn(users);
        when(resultRepository.findByRoomIdRoom(idRoom)).thenReturn(Collections.emptyList());
        when(userInfoTelegramChatsService.getAllUserChatsWhoNeedNotify(idRoom)).thenReturn(userInfoTelegramChatsDTO);

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setIdSanta(1);
        resultDTO.setIdWard(2);
        resultDTO.setRoomDTO(room);
        ResultEntity resultEntity = new ResultEntity();
        when(resultMapper.toResultEntity(any(ResultDTO.class))).thenReturn(resultEntity);

        resultService.performDraw(room);

        verify(resultRepository, times(2)).save(any(ResultEntity.class));
        verify(telegramService, times(1)).sendMessage(anyLong(), anyString());
    }

    @Test
    void testShowDrawInRoom() {
        int idRoom = 1;
        RoomDTO roomDTO1 = new RoomDTO();
        roomDTO1.setIdRoom(idRoom);
        ResultDTO resultDTO1 = new ResultDTO();
        resultDTO1.setRoomDTO(roomDTO1);

        RoomDTO roomDTO2 = new RoomDTO();
        roomDTO2.setIdRoom(idRoom);
        ResultDTO resultDTO2 = new ResultDTO();
        resultDTO2.setRoomDTO(roomDTO2);


        List<ResultDTO> resultDTOs = Arrays.asList(resultDTO1, resultDTO2);
        when(resultMapper.toResultDTOList(resultRepository.findAll())).thenReturn(resultDTOs);

        List<ResultDTO> result = resultService.showDrawInRoom(idRoom);
        assertEquals(2, result.size());
    }

}