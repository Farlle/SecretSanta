package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.mapper.ResultMapper;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.model.entity.ResultEntity;
import org.example.secretsanta.repository.ResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.*;

import static org.junit.Assert.assertTrue;
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
    void createTest() {
        ResultDTO dto = new ResultDTO();

        ResultEntity result = new ResultEntity();

        when(resultRepository.save(any(ResultEntity.class))).thenReturn(result);

        ResultDTO createdDTO = resultService.create(dto);

        verify(resultRepository, times(1)).save(any(ResultEntity.class));
        assertEquals(ResultMapper.toResultDTO(result), createdDTO);
    }

    @Test
    void readAllTest() {
        List<ResultEntity> results = new ArrayList<>();

        when(resultRepository.findAll()).thenReturn(results);

        List<ResultDTO> resultDTOs = resultService.readAll();

        verify(resultRepository, times(1)).findAll();
        assertEquals(ResultMapper.toResultDTOList(results), resultDTOs);
    }

    @Test
    void updateTest() {
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
    void deleteTest() {
        int id = 1;

        resultService.delete(id);

        verify(resultRepository, times(1)).deleteById(id);
    }

    @Test
    void performDrawTest() {
        RoomDTO room = new RoomDTO();
        UserInfoDTO user1 = new UserInfoDTO(1,"name1","pas1","tg1");
        UserInfoDTO user2 = new UserInfoDTO(2,"name2","pas2","tg2");
        UserInfoDTO user3 = new UserInfoDTO(3,"name3","pas3","tg3");

        List<UserInfoDTO> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);

        List<UserInfoTelegramChatsDTO> userInfoTelegramChatsDTO = new ArrayList<>();

        when(userInfoServiceImpl.getUsersInfoById(anyList())).thenReturn(users);
        when(userInfoTelegramChatsServiceImpl.getAllIdChatsUsersWhoNeedNotify(anyInt()))
                .thenReturn(userInfoTelegramChatsDTO);
        when(resultRepository.findByRoomIdRoom(anyInt())).thenReturn(new ArrayList<>());
        when(resultRepository.save(any(ResultEntity.class))).thenReturn(new ResultEntity());

        resultService.performDraw(room);

        verify(userInfoServiceImpl, times(1)).getUsersInfoById(anyList());
        verify(userInfoTelegramChatsServiceImpl, times(1)).getAllIdChatsUsersWhoNeedNotify(anyInt());
        verify(resultRepository, times(users.size())).save(any(ResultEntity.class));
        verify(telegramServiceImpl, times(userInfoTelegramChatsDTO.size())).sendMessage(anyLong(), anyString());
        assertEquals("Была проведена жеребьевка, смотри результат по сслыке " +
                " http://localhost:8080/result/show/1", resultService.generatedMessageDraw(1));
    }

    @Test
    void showDrawInRoomTest() {
        int idRoom = 1;
        RoomDTO roomDTO = new RoomDTO(1,"name",1,new Date(864000L),new Date(764000L),"qwe");
        ResultDTO result = new ResultDTO(1,1,2, roomDTO);
        ResultDTO result1 = new ResultDTO(2,3,4, roomDTO);
        List<ResultEntity> resultEntities =
                Arrays.asList(ResultMapper.toResultEntity(result), ResultMapper.toResultEntity(result1));
        when(resultRepository.findAll()).thenReturn(resultEntities);

        List<ResultDTO> results = resultService.showDrawInRoom(roomDTO.getIdRoom());

        verify(resultRepository, times(1)).findAll();
        assertEquals(resultEntities.size(), results.size());
        assertTrue(results.stream().allMatch(res -> res.getRoomDTO().getIdRoom() == idRoom));
    }
}