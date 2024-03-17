package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.RoleEntity;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.entity.WishEntity;

import java.util.Objects;

public class UserRoleWishRoomDTO {

    private int idUserRoleWishRoom;
    private UserInfoEntity userInfoEntity;
    private RoleEntity roleEntity;
    private RoomEntity roomEntity;
    private WishEntity wishEntity;

    public UserRoleWishRoomDTO(int idUserRoleWishRoom, UserInfoEntity userInfoEntity, RoleEntity roleEntity, RoomEntity roomEntity, WishEntity wishEntity) {
        this.idUserRoleWishRoom = idUserRoleWishRoom;
        this.userInfoEntity = userInfoEntity;
        this.roleEntity = roleEntity;
        this.roomEntity = roomEntity;
        this.wishEntity = wishEntity;
    }

    public UserRoleWishRoomDTO() {
    }

    public int getIdUserRoleWishRoom() {
        return idUserRoleWishRoom;
    }

    public void setIdUserRoleWishRoom(int idUserRoleWishRoom) {
        this.idUserRoleWishRoom = idUserRoleWishRoom;
    }

    public UserInfoEntity getUserInfoEntity() {
        return userInfoEntity;
    }

    public void setUserInfoEntity(UserInfoEntity userInfoEntity) {
        this.userInfoEntity = userInfoEntity;
    }

    public RoleEntity getRoleEntity() {
        return roleEntity;
    }

    public void setRoleEntity(RoleEntity roleEntity) {
        this.roleEntity = roleEntity;
    }

    public RoomEntity getRoomEntity() {
        return roomEntity;
    }

    public void setRoomEntity(RoomEntity roomEntity) {
        this.roomEntity = roomEntity;
    }

    public WishEntity getWishEntity() {
        return wishEntity;
    }

    public void setWishEntity(WishEntity wishEntity) {
        this.wishEntity = wishEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleWishRoomDTO)) return false;
        UserRoleWishRoomDTO that = (UserRoleWishRoomDTO) o;
        return idUserRoleWishRoom == that.idUserRoleWishRoom && Objects.equals(userInfoEntity, that.userInfoEntity) && Objects.equals(roleEntity, that.roleEntity) && Objects.equals(roomEntity, that.roomEntity) && Objects.equals(wishEntity, that.wishEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserRoleWishRoom, userInfoEntity, roleEntity, roomEntity, wishEntity);
    }
}
