package org.example.secretsanta.dto;

import org.example.secretsanta.model.entity.RoleEntity;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.entity.WishEntity;

import java.util.Objects;

public class UserRoleWishRoomDTO {

    private int idUserRoleWishRoom;
    private UserInfoDTO userInfoDTO;
    private RoleDTO roleDTO;
    private RoomDTO roomDTO;
    private WishDTO wishDTO;

    public UserRoleWishRoomDTO(int idUserRoleWishRoom, UserInfoDTO userInfoDTO, RoleDTO roleDTO, RoomDTO roomDTO, WishDTO wishDTO) {
        this.idUserRoleWishRoom = idUserRoleWishRoom;
        this.userInfoDTO = userInfoDTO;
        this.roleDTO = roleDTO;
        this.roomDTO = roomDTO;
        this.wishDTO = wishDTO;
    }

    public UserRoleWishRoomDTO() {
    }

    public int getIdUserRoleWishRoom() {
        return idUserRoleWishRoom;
    }

    public void setIdUserRoleWishRoom(int idUserRoleWishRoom) {
        this.idUserRoleWishRoom = idUserRoleWishRoom;
    }

    public UserInfoDTO getUserInfoDTO() {
        return userInfoDTO;
    }

    public void setUserInfoDTO(UserInfoDTO userInfoDTO) {
        this.userInfoDTO = userInfoDTO;
    }

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

    public RoomDTO getRoomDTO() {
        return roomDTO;
    }

    public void setRoomDTO(RoomDTO roomDTO) {
        this.roomDTO = roomDTO;
    }

    public WishDTO getWishDTO() {
        return wishDTO;
    }

    public void setWishDTO(WishDTO wishDTO) {
        this.wishDTO = wishDTO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleWishRoomDTO)) return false;
        UserRoleWishRoomDTO that = (UserRoleWishRoomDTO) o;
        return idUserRoleWishRoom == that.idUserRoleWishRoom && Objects.equals(userInfoDTO, that.userInfoDTO) && Objects.equals(roleDTO, that.roleDTO) && Objects.equals(roomDTO, that.roomDTO) && Objects.equals(wishDTO, that.wishDTO);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserRoleWishRoom, userInfoDTO, roleDTO, roomDTO, wishDTO);
    }
}
