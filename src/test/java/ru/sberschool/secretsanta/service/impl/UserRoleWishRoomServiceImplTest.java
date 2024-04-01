package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.dto.UserRoleWishRoomDTO;
import ru.sberschool.secretsanta.mapper.*;
import ru.sberschool.secretsanta.model.entity.UserRoleWishRoomEntity;
import ru.sberschool.secretsanta.repository.UserRoleWishRoomRepository;
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

class UserRoleWishRoomServiceImplTest {

    @Mock
    private UserRoleWishRoomRepository userRoleWishRoomRepository;
    @InjectMocks
    private UserRoleWishRoomServiceImpl userRoleWishRoomService;
    @Mock
    private UserRoleWishRoomMapper userRoleWishRoomMapper;
    @Mock
    private UserInfoMapper userInfoMapper;
    @Mock
    private RoomMapper roomMapper;
    @Mock
    private RoleMapper roleMapper;
    @Mock
    private WishMapper wishMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        UserRoleWishRoomDTO dto = new UserRoleWishRoomDTO();
        UserRoleWishRoomEntity entity = new UserRoleWishRoomEntity();
        when(roleMapper.toRoleEntity(dto.getRoleDTO())).thenReturn(entity.getRole());
        when(wishMapper.toWishEntity(dto.getWishDTO())).thenReturn(entity.getWish());
        when(roomMapper.toRoomEntity(dto.getRoomDTO())).thenReturn(entity.getRoom());
        when(userInfoMapper.toUserInfoEntity(dto.getUserInfoDTO())).thenReturn(entity.getUserInfoEntity());
        when(userRoleWishRoomRepository.save(entity)).thenReturn(entity);
        when(userRoleWishRoomMapper.toUserRoleWishRoomDTO(entity)).thenReturn(dto);

        UserRoleWishRoomDTO result = userRoleWishRoomService.create(dto);
        assertEquals(dto, result);
        verify(userRoleWishRoomRepository).save(entity);
    }

    @Test
    void testReadAll() {
        List<UserRoleWishRoomEntity> entities = Arrays.asList(new UserRoleWishRoomEntity(), new UserRoleWishRoomEntity());
        when(userRoleWishRoomRepository.findAll()).thenReturn(entities);
        List<UserRoleWishRoomDTO> dtos = Arrays.asList(new UserRoleWishRoomDTO(), new UserRoleWishRoomDTO());
        when(userRoleWishRoomMapper.toUserRoleWishRoomDTOList(entities)).thenReturn(dtos);

        List<UserRoleWishRoomDTO> result = userRoleWishRoomService.readAll();
        assertEquals(dtos, result);
    }

    @Test
    void testUpdate() {
        int id = 1;
        UserRoleWishRoomDTO dto = new UserRoleWishRoomDTO();
        UserRoleWishRoomEntity entity = new UserRoleWishRoomEntity();
        when(userRoleWishRoomRepository.findById(id)).thenReturn(Optional.of(entity));
        when(roleMapper.toRoleEntity(dto.getRoleDTO())).thenReturn(entity.getRole());
        when(wishMapper.toWishEntity(dto.getWishDTO())).thenReturn(entity.getWish());
        when(roomMapper.toRoomEntity(dto.getRoomDTO())).thenReturn(entity.getRoom());
        when(userInfoMapper.toUserInfoEntity(dto.getUserInfoDTO())).thenReturn(entity.getUserInfoEntity());
        when(userRoleWishRoomRepository.save(entity)).thenReturn(entity);
        when(userRoleWishRoomMapper.toUserRoleWishRoomDTO(entity)).thenReturn(dto);

        UserRoleWishRoomDTO result = userRoleWishRoomService.update(id, dto);
        assertEquals(dto, result);
        verify(userRoleWishRoomRepository).save(entity);
    }

    @Test
    void testDelete() {
        int id = 1;

        userRoleWishRoomService.delete(id);

        verify(userRoleWishRoomRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteUserFromRoom() {
        int idRoom = 1;
        int idUserInfo = 2;
        UserRoleWishRoomEntity entity = new UserRoleWishRoomEntity();
        entity.setIdUserRoleWishRoom(1);
        when(userRoleWishRoomRepository.findByIdUserInfoAndIdRoom(idUserInfo, idRoom)).thenReturn(entity);

        userRoleWishRoomService.deleteUserFromRoom(idRoom, idUserInfo);

        verify(userRoleWishRoomRepository, times(1)).findByIdUserInfoAndIdRoom(idUserInfo, idRoom);
        verify(userRoleWishRoomRepository, times(1)).deleteById(entity.getIdUserRoleWishRoom());
    }
}