package ru.sberschool.secretsanta.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_role_wish_room")
public class UserRoleWishRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user_role_wish_room")
    private int idUserRoleWishRoom;

    @ManyToOne
    @JoinColumn(name = "id_user_info", nullable = false)
    private UserInfoEntity userInfoEntity;
    @ManyToOne
    @JoinColumn(name = "id_role", nullable = false)
    private RoleEntity role;

    @ManyToOne
    @JoinColumn(name = "id_room", nullable = false)
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "id_wish", nullable = false)
    private WishEntity wish;

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

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public WishEntity getWish() {
        return wish;
    }

    public void setWish(WishEntity wish) {
        this.wish = wish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleWishRoomEntity)) return false;
        UserRoleWishRoomEntity that = (UserRoleWishRoomEntity) o;
        return idUserRoleWishRoom == that.idUserRoleWishRoom && Objects.equals(userInfoEntity, that.userInfoEntity)
                && Objects.equals(role, that.role) && Objects.equals(room, that.room) && Objects.equals(wish, that.wish);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idUserRoleWishRoom, userInfoEntity, role, room, wish);
    }
}
