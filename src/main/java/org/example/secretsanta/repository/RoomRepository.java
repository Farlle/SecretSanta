package org.example.secretsanta.repository;

import org.example.secretsanta.model.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.sql.Date;

@Repository
public interface RoomRepository extends JpaRepository<RoomEntity, Integer> {

    @Query("select user_info.name, role.role, room.name " +
            "from RoomEntity room join UserRoleWishRoomEntity user_role_wish_room on" +
            " room.idRoom = user_role_wish_room.room.idRoom " +
            "join UserInfoEntity user_info on user_role_wish_room.userInfoEntity.idUserInfo = user_info.idUserInfo " +
            "join RoleEntity role on user_role_wish_room.role.idRole = role.idRole " +
            "where room.idRoom= :idRoom")
    List<Object[]> findUserRoleInRoom(@Param("idRoom") int idRoom);

    @Query("select user_info.idUserInfo " +
            "from RoomEntity room join UserRoleWishRoomEntity user_role_wish_room on" +
            " room.idRoom = user_role_wish_room.room.idRoom " +
            "join UserInfoEntity user_info on user_role_wish_room.userInfoEntity.idUserInfo = user_info.idUserInfo " +
            "where room.idRoom= :idRoom")
    List<Integer> findUserInfoInRoom(@Param("idRoom") int idRoom);



    List<RoomEntity> findByDrawDateLessThanEqual(Date drawDate);

}
