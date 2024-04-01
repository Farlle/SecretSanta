package ru.sberschool.secretsanta.mapper;

import ru.sberschool.secretsanta.dto.*;
import ru.sberschool.secretsanta.model.entity.*;
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

class UserRoleWishRoomMapperTest {

    @InjectMocks
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
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testToUserRoleWishRoomDTO() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        when(userInfoMapper.toUserInfoDTO(any())).thenReturn(userInfoDTO);

        RoleDTO roleDTO = new RoleDTO();
        when(roleMapper.toRoleDTO(any())).thenReturn(roleDTO);

        RoomDTO roomDTO = new RoomDTO();
        when(roomMapper.toRoomDTO(any())).thenReturn(roomDTO);

        WishDTO wishDTO = new WishDTO();
        when(wishMapper.toWishDTO(any())).thenReturn(wishDTO);

        UserRoleWishRoomEntity userRoleWishRoomEntity = new UserRoleWishRoomEntity();
        userRoleWishRoomEntity.setIdUserRoleWishRoom(1);

        UserRoleWishRoomDTO userRoleWishRoomDTO = userRoleWishRoomMapper.toUserRoleWishRoomDTO(userRoleWishRoomEntity);

        assertEquals(userRoleWishRoomEntity.getIdUserRoleWishRoom(), userRoleWishRoomDTO.getIdUserRoleWishRoom());
        assertEquals(userInfoDTO, userRoleWishRoomDTO.getUserInfoDTO());
        assertEquals(roleDTO, userRoleWishRoomDTO.getRoleDTO());
        assertEquals(roomDTO, userRoleWishRoomDTO.getRoomDTO());
        assertEquals(wishDTO, userRoleWishRoomDTO.getWishDTO());
    }

    @Test
    void testToUserRoleWishRoomEntity() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        when(userInfoMapper.toUserInfoEntity(any())).thenReturn(userInfoEntity);

        RoleEntity roleEntity = new RoleEntity();
        when(roleMapper.toRoleEntity(any())).thenReturn(roleEntity);

        RoomEntity roomEntity = new RoomEntity();
        when(roomMapper.toRoomEntity(any())).thenReturn(roomEntity);

        WishEntity wishEntity = new WishEntity();
        when(wishMapper.toWishEntity(any())).thenReturn(wishEntity);

        UserRoleWishRoomDTO userRoleWishRoomDTO = new UserRoleWishRoomDTO();
        userRoleWishRoomDTO.setIdUserRoleWishRoom(1);

        UserRoleWishRoomEntity userRoleWishRoomEntity = userRoleWishRoomMapper.toUserRoleWishRoomEntity(userRoleWishRoomDTO);

        assertEquals(userRoleWishRoomDTO.getIdUserRoleWishRoom(), userRoleWishRoomEntity.getIdUserRoleWishRoom());
        assertEquals(userInfoEntity, userRoleWishRoomEntity.getUserInfoEntity());
        assertEquals(roleEntity, userRoleWishRoomEntity.getRole());
        assertEquals(roomEntity, userRoleWishRoomEntity.getRoom());
        assertEquals(wishEntity, userRoleWishRoomEntity.getWish());
    }

    @Test
    void testToUserRoleWishRoomDTOList() {
        UserRoleWishRoomEntity userRoleWishRoomEntity1 = new UserRoleWishRoomEntity();
        userRoleWishRoomEntity1.setIdUserRoleWishRoom(1);
        UserRoleWishRoomEntity userRoleWishRoomEntity2 = new UserRoleWishRoomEntity();
        userRoleWishRoomEntity2.setIdUserRoleWishRoom(2);
        List<UserRoleWishRoomEntity> userRoleWishRoomEntityList
                = Arrays.asList(userRoleWishRoomEntity1, userRoleWishRoomEntity2);

        List<UserRoleWishRoomDTO> userRoleWishRoomDTOList
                = userRoleWishRoomMapper.toUserRoleWishRoomDTOList(userRoleWishRoomEntityList);

        assertEquals(userRoleWishRoomEntityList.size(), userRoleWishRoomDTOList.size());
        assertEquals(userRoleWishRoomEntityList.get(0).getIdUserRoleWishRoom(),
                userRoleWishRoomDTOList.get(0).getIdUserRoleWishRoom());
        assertEquals(userRoleWishRoomEntityList.get(1).getIdUserRoleWishRoom(),
                userRoleWishRoomDTOList.get(1).getIdUserRoleWishRoom());
    }

    @Test
    void testToUserRoleWishRoomDTO_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            userRoleWishRoomMapper.toUserRoleWishRoomDTO(null);
        });
    }

    @Test
    void testToUserRoleWishRoomEntity_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            userRoleWishRoomMapper.toUserRoleWishRoomEntity(null);
        });
    }

    @Test
    void testToUserRoleWishRoomDTOList_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            userRoleWishRoomMapper.toUserRoleWishRoomDTOList(null);
        });
    }

}