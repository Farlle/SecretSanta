package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.UserRoleWishRoomDTO;
import org.example.secretsanta.model.entity.UserRoleWishRoomEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UserRoleWishRoomMapper {

    public static UserRoleWishRoomDTO toUserRoleWishRoomDTO(UserRoleWishRoomEntity userRoleWishRoomEntity) {

        if(userRoleWishRoomEntity ==null) {
            return null;
        }

        UserRoleWishRoomDTO userRoleWishRoomDTO = new UserRoleWishRoomDTO();

        userRoleWishRoomDTO.setIdUserRoleWishRoom(userRoleWishRoomEntity.getIdUserRoleWishRoom());
        userRoleWishRoomDTO.setUserInfoEntity(userRoleWishRoomEntity.getUserInfoEntity());
        userRoleWishRoomDTO.setRoleEntity(userRoleWishRoomEntity.getRole());
        userRoleWishRoomDTO.setRoomEntity(userRoleWishRoomEntity.getRoom());
        userRoleWishRoomDTO.setWishEntity(userRoleWishRoomEntity.getWish());
        return userRoleWishRoomDTO;
    }

    public static UserRoleWishRoomEntity toUserRoleWishRoomEntity(UserRoleWishRoomDTO userRoleWishRoomDTO) {

        if(userRoleWishRoomDTO ==null) {
            return null;
        }

        UserRoleWishRoomEntity userRoleWishRoomEntity = new UserRoleWishRoomEntity();

        userRoleWishRoomEntity.setIdUserRoleWishRoom(userRoleWishRoomDTO.getIdUserRoleWishRoom());
        userRoleWishRoomEntity.setUserInfoEntity(userRoleWishRoomDTO.getUserInfoEntity());
        userRoleWishRoomEntity.setRole(userRoleWishRoomDTO.getRoleEntity());
        userRoleWishRoomEntity.setRoom(userRoleWishRoomDTO.getRoomEntity());
        userRoleWishRoomEntity.setWish(userRoleWishRoomDTO.getWishEntity());
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
