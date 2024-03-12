package org.example.secretsanta.repository;

import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Integer> {

    @Query("SELECT wishEntity FROM WishEntity wishEntity " +
            "JOIN UserRoleWishRoomEntity userRoleWishRoomEntity " +
            "ON wishEntity.idWish = userRoleWishRoomEntity.wish.idWish " +
            "JOIN UserInfoEntity userInfoEntity " +
            "ON userRoleWishRoomEntity.userInfoEntity.idUserInfo = userInfoEntity.idUserInfo " +
            "WHERE userRoleWishRoomEntity.room.idRoom = :idRoom AND userInfoEntity.idUserInfo = :idUserInfo")
    WishEntity findWishesByRoomAndUser(@Param("idRoom") int idRoom, @Param("idUserInfo") int idUserInfo);

}
