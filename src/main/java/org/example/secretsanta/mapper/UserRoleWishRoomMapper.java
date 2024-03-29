package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.UserRoleWishRoomDTO;
import org.example.secretsanta.model.entity.UserRoleWishRoomEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoleWishRoomMapper {

    public static UserRoleWishRoomDTO toUserRoleWishRoomDTO(UserRoleWishRoomEntity userRoleWishRoomEntity) {

        if (userRoleWishRoomEntity == null) {
            return null;
        }

        UserRoleWishRoomDTO userRoleWishRoomDTO = new UserRoleWishRoomDTO();

        userRoleWishRoomDTO.setIdUserRoleWishRoom(userRoleWishRoomEntity.getIdUserRoleWishRoom());
        userRoleWishRoomDTO.setUserInfoDTO(UserInfoMapper.toUserInfoDTO(userRoleWishRoomEntity.getUserInfoEntity()));
        userRoleWishRoomDTO.setRoleDTO(RoleMapper.toRoleDTO(userRoleWishRoomEntity.getRole()));
        userRoleWishRoomDTO.setRoomDTO(RoomMapper.toRoomDTO(userRoleWishRoomEntity.getRoom()));
        userRoleWishRoomDTO.setWishDTO(WishMapper.toWishDTO(userRoleWishRoomEntity.getWish()));
        return userRoleWishRoomDTO;
    }

    public static UserRoleWishRoomEntity toUserRoleWishRoomEntity(UserRoleWishRoomDTO userRoleWishRoomDTO) {

        if (userRoleWishRoomDTO == null) {
            return null;
        }

        UserRoleWishRoomEntity userRoleWishRoomEntity = new UserRoleWishRoomEntity();

        userRoleWishRoomEntity.setIdUserRoleWishRoom(userRoleWishRoomDTO.getIdUserRoleWishRoom());
        userRoleWishRoomEntity.setUserInfoEntity(UserInfoMapper.toUserInfoEntity(userRoleWishRoomDTO.getUserInfoDTO()));
        userRoleWishRoomEntity.setRole(RoleMapper.toRoleEntity(userRoleWishRoomDTO.getRoleDTO()));
        userRoleWishRoomEntity.setRoom(RoomMapper.toRoomEntity(userRoleWishRoomDTO.getRoomDTO()));
        userRoleWishRoomEntity.setWish(WishMapper.toWishEntity(userRoleWishRoomDTO.getWishDTO()));
        return userRoleWishRoomEntity;
    }

    public static List<UserRoleWishRoomDTO> toUserRoleWishRoomDTOList(List<UserRoleWishRoomEntity> userRoleWishRoomEntityList) {
        if (userRoleWishRoomEntityList == null) {
            return null;
        }

        return userRoleWishRoomEntityList.stream()
                .map(UserRoleWishRoomMapper::toUserRoleWishRoomDTO)
                .collect(Collectors.toList());
    }
}
