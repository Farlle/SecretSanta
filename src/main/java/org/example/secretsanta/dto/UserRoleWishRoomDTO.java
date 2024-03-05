package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.RoleEntity;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.entity.WishEntity;

public class UserRoleWishRoomDTO {

    private UserInfoEntity userInfoEntity;
    private RoleEntity roleEntity;
    private RoomEntity roomEntity;
    private WishEntity wishEntity;

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
}
