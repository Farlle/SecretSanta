package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserRoleWishRoomDTO;
import org.example.secretsanta.mapper.UserRoleWishRoomMapper;
import org.example.secretsanta.model.entity.UserRoleWishRoomEntity;
import org.example.secretsanta.repository.UserRoleWishRoomRepository;
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

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        UserRoleWishRoomDTO dto = new UserRoleWishRoomDTO();

        UserRoleWishRoomEntity entity = new UserRoleWishRoomEntity();

        when(userRoleWishRoomRepository.save(any(UserRoleWishRoomEntity.class))).thenReturn(entity);

        UserRoleWishRoomDTO result = userRoleWishRoomService.create(dto);

        verify(userRoleWishRoomRepository, times(1)).save(any(UserRoleWishRoomEntity.class));
        assertEquals(UserRoleWishRoomMapper.toUserRoleWishRoomDTO(entity), result);
    }

    @Test
    void testReadAll() {
        List<UserRoleWishRoomEntity> entities = Arrays.asList(new UserRoleWishRoomEntity(), new UserRoleWishRoomEntity());
        when(userRoleWishRoomRepository.findAll()).thenReturn(entities);

        List<UserRoleWishRoomDTO> result = userRoleWishRoomService.readAll();

        verify(userRoleWishRoomRepository, times(1)).findAll();
        assertEquals(entities.size(), result.size());
    }

    @Test
    void testUpdate() {
        int id = 1;
        UserRoleWishRoomDTO dto = new UserRoleWishRoomDTO();

        UserRoleWishRoomEntity entity = new UserRoleWishRoomEntity();

        when(userRoleWishRoomRepository.findById(id)).thenReturn(Optional.of(entity));
        when(userRoleWishRoomRepository.save(any(UserRoleWishRoomEntity.class))).thenReturn(entity);

        UserRoleWishRoomDTO result = userRoleWishRoomService.update(id, dto);

        verify(userRoleWishRoomRepository, times(1)).findById(id);
        verify(userRoleWishRoomRepository, times(1)).save(any(UserRoleWishRoomEntity.class));
        assertEquals(UserRoleWishRoomMapper.toUserRoleWishRoomDTO(entity), result);
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