package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.mapper.RoomMapper;
import ru.sberschool.secretsanta.model.entity.RoomEntity;
import ru.sberschool.secretsanta.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {
    @Mock
    private RoomRepository roomRepository;
    @Mock
    private UserInfoServiceImpl userInfoServiceImpl;
    @InjectMocks
    private RoomServiceImpl roomService;
    @Mock
    private RoomMapper roomMapper;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testCreate() {
        RoomDTO dto = new RoomDTO();

        RoomEntity room = new RoomEntity();

        when(roomRepository.save(any(RoomEntity.class))).thenReturn(room);


        RoomDTO result = roomService.create(dto);

        verify(roomRepository, times(1)).save(any(RoomEntity.class));
        assertEquals(roomMapper.toRoomDTO(room), result);
    }

    @Test
    void testReadAll() {
        List<RoomEntity> roomEntities = Arrays.asList(new RoomEntity(), new RoomEntity());
        when(roomRepository.findAll()).thenReturn(roomEntities);

        List<RoomDTO> result = roomService.readAll();

        verify(roomRepository, times(1)).findAll();
        assertEquals(roomMapper.toRoomDTOList(roomEntities), result);
    }

    @Test
    void testUpdate() {
        int id = 1;
        RoomDTO dto = new RoomDTO();

        RoomEntity room = new RoomEntity();
        room.setIdRoom(id);

        when(roomRepository.findById(id)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(RoomEntity.class))).thenReturn(room);

        RoomDTO result = roomService.update(id, dto);

        verify(roomRepository, times(1)).findById(id);
        verify(roomRepository, times(1)).save(any(RoomEntity.class));
        assertEquals(roomMapper.toRoomDTO(room), result);
    }

    @Test
    void testDelete() {
        int id = 1;

        roomService.delete(id);

        verify(roomRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetRoomById() {
        int id = 1;
        RoomEntity room = new RoomEntity();

        when(roomRepository.findById(id)).thenReturn(Optional.of(room));

        RoomDTO result = roomService.getRoomById(id);

        verify(roomRepository, times(1)).findById(id);
        assertEquals(roomMapper.toRoomDTO(room), result);
    }

    @Test
    void testGetRoomOrganizer() {
        RoomDTO dto = new RoomDTO();
        dto.setIdOrganizer(1);

        UserInfoDTO userInfoDTO = new UserInfoDTO();

        when(userInfoServiceImpl.getUserInfoById(dto.getIdOrganizer())).thenReturn(userInfoDTO);

        UserInfoDTO result = roomService.getRoomOrganizer(dto);

        verify(userInfoServiceImpl, times(1)).getUserInfoById(dto.getIdOrganizer());
        assertEquals(userInfoDTO, result);
    }

    @Test
    void testGetUsersAndRolesByRoomId() {
        int idRoom = 1;
        List<Object[]> usersAndRoles = Arrays.asList(new Object[]{"User1", "Role1"}, new Object[]{"User2", "Role2"});
        when(roomRepository.findUserRoleInRoom(idRoom)).thenReturn(usersAndRoles);

        List<Object[]> result = roomService.getUsersAndRolesByRoomId(idRoom);

        verify(roomRepository, times(1)).findUserRoleInRoom(idRoom);
        assertEquals(usersAndRoles, result);
    }

    @Test
    void testGetRoomsWhereUserJoin() {
        int idUserInfo = 1;
        List<Integer> roomIds = Arrays.asList(1, 2);
        List<RoomEntity> roomEntities = Arrays.asList(new RoomEntity(), new RoomEntity());
        when(roomRepository.findRoomsWhereUserJoin(idUserInfo)).thenReturn(roomIds);
        when(roomRepository.findAllById(roomIds)).thenReturn(roomEntities);

        List<RoomDTO> result = roomService.getRoomsWhereUserJoin(idUserInfo);

        verify(roomRepository, times(1)).findRoomsWhereUserJoin(idUserInfo);
        verify(roomRepository, times(1)).findAllById(roomIds);
        assertEquals(roomMapper.toRoomDTOList(roomEntities), result);
    }

    @Test
    void testGetRoomByName() {
        String name = "Test Room";
        RoomEntity room = new RoomEntity();
        room.setName(name);

        when(roomRepository.findRoomEntitiesByName(name)).thenReturn(room);

        RoomDTO result = roomService.getRoomByName(name);

        verify(roomRepository, times(1)).findRoomEntitiesByName(name);
        assertEquals(roomMapper.toRoomDTO(room), result);
    }

    @Test
    void testGetUserIndoIdInRoom() {
        int idRoom = 1;
        List<Integer> userInfoIds = Arrays.asList(1, 2);
        when(roomRepository.findUserInfoIdInRoom(idRoom)).thenReturn(userInfoIds);

        List<Integer> result = roomService.getUserInfoIdInRoom(idRoom);

        verify(roomRepository, times(1)).findUserInfoIdInRoom(idRoom);
        assertEquals(userInfoIds, result);
    }

    @Test
    void testGetByDrawDateLessThanEqual() {
        Date date = new Date(11L);
        List<RoomEntity> roomEntities = Arrays.asList(new RoomEntity(), new RoomEntity());
        when(roomRepository.findByDrawDateLessThanEqual(date)).thenReturn(roomEntities);

        List<RoomDTO> result = roomService.getByDrawDateLessThanEqual(date);

        verify(roomRepository, times(1)).findByDrawDateLessThanEqual(date);
        assertEquals(roomMapper.toRoomDTOList(roomEntities), result);
    }
}