package ru.sberschool.secretsanta.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sberschool.secretsanta.model.entity.RoomEntity;

import java.sql.Date;
import java.util.List;

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
    List<Integer> findUserInfoIdInRoom(@Param("idRoom") int idRoom);

    @Query("select room.idRoom " +
            "from RoomEntity room join UserRoleWishRoomEntity user_role_wish_room on" +
            " room.idRoom = user_role_wish_room.room.idRoom " +
            "join UserInfoEntity user_info on user_role_wish_room.userInfoEntity.idUserInfo = user_info.idUserInfo " +
            "where user_info.idUserInfo= :idUserInfo")
    List<Integer> findRoomsWhereUserJoin(@Param("idUserInfo") int idUserInfo);

    @Query("select room " +
            "from RoomEntity room join UserRoleWishRoomEntity user_role_wish_room on" +
            " room.idRoom = user_role_wish_room.room.idRoom " +
            "join UserInfoEntity user_info on user_role_wish_room.userInfoEntity.idUserInfo = user_info.idUserInfo " +
            "where user_info.idUserInfo= :idUserInfo")
    Page<RoomEntity> findRoomsWhereUserJoinPage(@Param("idUserInfo") int idUserInfo, Pageable pageable);

    List<RoomEntity> findByDrawDateLessThanEqual(Date drawDate);

    RoomEntity findRoomEntitiesByName(String name);

}
