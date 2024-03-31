package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.*;
import org.example.secretsanta.model.entity.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserRoleWishRoomMapperTest {

    @Test
    void testToUserRoleWishRoomDTO() {
        UserRoleWishRoomEntity userRoleWishRoomEntity = new UserRoleWishRoomEntity();
        userRoleWishRoomEntity.setIdUserRoleWishRoom(1);
        userRoleWishRoomEntity.setUserInfoEntity(new UserInfoEntity());
        userRoleWishRoomEntity.setRole(new RoleEntity());
        userRoleWishRoomEntity.setRoom(new RoomEntity());
        userRoleWishRoomEntity.setWish(new WishEntity());

        UserRoleWishRoomDTO userRoleWishRoomDTO = UserRoleWishRoomMapper.toUserRoleWishRoomDTO(userRoleWishRoomEntity);

        assertEquals(userRoleWishRoomEntity.getIdUserRoleWishRoom(), userRoleWishRoomDTO.getIdUserRoleWishRoom());
        assertEquals(userRoleWishRoomEntity.getUserInfoEntity(), UserInfoMapper.toUserInfoEntity(
                userRoleWishRoomDTO.getUserInfoDTO()));
        assertEquals(userRoleWishRoomEntity.getRole(), RoleMapper.toRoleEntity(userRoleWishRoomDTO.getRoleDTO()));
        assertEquals(userRoleWishRoomEntity.getRoom(), RoomMapper.toRoomEntity(userRoleWishRoomDTO.getRoomDTO()));
        assertEquals(userRoleWishRoomEntity.getWish(), WishMapper.toWishEntity(userRoleWishRoomDTO.getWishDTO()));
    }

    @Test
    void testToUserRoleWishRoomEntity() {
        UserRoleWishRoomDTO userRoleWishRoomDTO = new UserRoleWishRoomDTO();
        userRoleWishRoomDTO.setIdUserRoleWishRoom(1);
        userRoleWishRoomDTO.setUserInfoDTO(new UserInfoDTO());
        userRoleWishRoomDTO.setRoleDTO(new RoleDTO());
        userRoleWishRoomDTO.setRoomDTO(new RoomDTO());
        userRoleWishRoomDTO.setWishDTO(new WishDTO());

        UserRoleWishRoomEntity userRoleWishRoomEntity =
                UserRoleWishRoomMapper.toUserRoleWishRoomEntity(userRoleWishRoomDTO);

        assertEquals(userRoleWishRoomDTO.getIdUserRoleWishRoom(), userRoleWishRoomEntity.getIdUserRoleWishRoom());
        assertEquals(userRoleWishRoomDTO.getUserInfoDTO(),
                UserInfoMapper.toUserInfoDTO(userRoleWishRoomEntity.getUserInfoEntity()));
        assertEquals(userRoleWishRoomDTO.getRoleDTO(), RoleMapper.toRoleDTO(userRoleWishRoomEntity.getRole()));
        assertEquals(userRoleWishRoomDTO.getRoomDTO(), RoomMapper.toRoomDTO(userRoleWishRoomEntity.getRoom()));
        assertEquals(userRoleWishRoomDTO.getWishDTO(), WishMapper.toWishDTO(userRoleWishRoomEntity.getWish()));
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
                = UserRoleWishRoomMapper.toUserRoleWishRoomDTOList(userRoleWishRoomEntityList);

        assertEquals(userRoleWishRoomEntityList.size(), userRoleWishRoomDTOList.size());
        assertEquals(userRoleWishRoomEntityList.get(0).getIdUserRoleWishRoom(),
                userRoleWishRoomDTOList.get(0).getIdUserRoleWishRoom());
        assertEquals(userRoleWishRoomEntityList.get(1).getIdUserRoleWishRoom(),
                userRoleWishRoomDTOList.get(1).getIdUserRoleWishRoom());
    }

    @Test
    void testToUserRoleWishRoomDTO_Null() {
        UserRoleWishRoomDTO userRoleWishRoomDTO = UserRoleWishRoomMapper.toUserRoleWishRoomDTO(null);

        assertNotNull(userRoleWishRoomDTO);
    }

    @Test
    void testToUserRoleWishRoomEntity_Null() {
        UserRoleWishRoomEntity userRoleWishRoomEntity = UserRoleWishRoomMapper.toUserRoleWishRoomEntity(null);

        assertNotNull(userRoleWishRoomEntity);
    }

    @Test
    void testToUserRoleWishRoomDTOList_Null() {
        List<UserRoleWishRoomDTO> userRoleWishRoomDTOList = UserRoleWishRoomMapper.toUserRoleWishRoomDTOList(null);

        assertNotNull(userRoleWishRoomDTOList);
    }

}