package org.example.secretsanta.model.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_role_wish_room")
public class UserRoleWishRoom {

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
}
