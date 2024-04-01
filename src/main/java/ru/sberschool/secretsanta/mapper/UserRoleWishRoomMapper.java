package ru.sberschool.secretsanta.mapper;

import org.springframework.stereotype.Component;
import ru.sberschool.secretsanta.dto.UserRoleWishRoomDTO;
import ru.sberschool.secretsanta.model.entity.UserRoleWishRoomEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoleWishRoomMapper {

    private final WishMapper wishMapper;
    private final UserInfoMapper userInfoMapper;
    private final RoleMapper roleMapper;
    private final RoomMapper roomMapper;

    public UserRoleWishRoomMapper(WishMapper wishMapper, UserInfoMapper userInfoMapper, RoleMapper roleMapper, RoomMapper roomMapper) {

        this.wishMapper = wishMapper;
        this.userInfoMapper = userInfoMapper;
        this.roleMapper = roleMapper;
        this.roomMapper = roomMapper;
    }

    public UserRoleWishRoomDTO toUserRoleWishRoomDTO(UserRoleWishRoomEntity userRoleWishRoomEntity) {
        UserRoleWishRoomDTO userRoleWishRoomDTO = new UserRoleWishRoomDTO();

        if (userRoleWishRoomEntity == null) {
            throw new IllegalArgumentException("UserRoleWishRoomEntity cannot be null");
        }

        userRoleWishRoomDTO.setIdUserRoleWishRoom(userRoleWishRoomEntity.getIdUserRoleWishRoom());
        userRoleWishRoomDTO.setUserInfoDTO(userInfoMapper.toUserInfoDTO(userRoleWishRoomEntity.getUserInfoEntity()));
        userRoleWishRoomDTO.setRoleDTO(roleMapper.toRoleDTO(userRoleWishRoomEntity.getRole()));
        userRoleWishRoomDTO.setRoomDTO(roomMapper.toRoomDTO(userRoleWishRoomEntity.getRoom()));
        userRoleWishRoomDTO.setWishDTO(wishMapper.toWishDTO(userRoleWishRoomEntity.getWish()));
        return userRoleWishRoomDTO;
    }

    public UserRoleWishRoomEntity toUserRoleWishRoomEntity(UserRoleWishRoomDTO userRoleWishRoomDTO) {
        UserRoleWishRoomEntity userRoleWishRoomEntity = new UserRoleWishRoomEntity();

        if (userRoleWishRoomDTO == null) {
            throw new IllegalArgumentException("UserRoleWishRoomDTO cannot be null");
        }

        userRoleWishRoomEntity.setIdUserRoleWishRoom(userRoleWishRoomDTO.getIdUserRoleWishRoom());
        userRoleWishRoomEntity.setUserInfoEntity(userInfoMapper.toUserInfoEntity(userRoleWishRoomDTO.getUserInfoDTO()));
        userRoleWishRoomEntity.setRole(roleMapper.toRoleEntity(userRoleWishRoomDTO.getRoleDTO()));
        userRoleWishRoomEntity.setRoom(roomMapper.toRoomEntity(userRoleWishRoomDTO.getRoomDTO()));
        userRoleWishRoomEntity.setWish(wishMapper.toWishEntity(userRoleWishRoomDTO.getWishDTO()));
        return userRoleWishRoomEntity;
    }

    public List<UserRoleWishRoomDTO> toUserRoleWishRoomDTOList(List<UserRoleWishRoomEntity> userRoleWishRoomEntityList) {
        if (userRoleWishRoomEntityList == null) {
            throw new IllegalArgumentException("UserRoleWishRoomEntityList cannot be null");
        }

        return userRoleWishRoomEntityList.stream()
                .map(this::toUserRoleWishRoomDTO)
                .collect(Collectors.toList());
    }
}
