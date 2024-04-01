package ru.sberschool.secretsanta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.sberschool.secretsanta.model.entity.UserRoleWishRoomEntity;

@Repository
public interface UserRoleWishRoomRepository extends JpaRepository<UserRoleWishRoomEntity, Integer> {

    @Query("SELECT userRoleWishRoomEntity FROM UserRoleWishRoomEntity userRoleWishRoomEntity " +
            "WHERE userRoleWishRoomEntity.userInfoEntity.idUserInfo = :idUserInfo" +
            " AND userRoleWishRoomEntity.room.idRoom = :idRoom")
    UserRoleWishRoomEntity findByIdUserInfoAndIdRoom(@Param("idUserInfo") int idUserInfo,
                                                     @Param("idRoom") int idRoom);

}
