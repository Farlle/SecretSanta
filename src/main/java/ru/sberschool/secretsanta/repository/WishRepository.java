package ru.sberschool.secretsanta.repository;

import ru.sberschool.secretsanta.model.entity.WishEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<WishEntity, Integer> {

    /**
     * Запрос возвращает желание пользователя в комнате
     *
     * @param idRoom Идентификатор комнаты
     * @param idUserInfo Идентификатор пользователя
     * @return Желание пользователя в комнате
     */
    @Query("SELECT wishEntity FROM WishEntity wishEntity " +
            "JOIN UserRoleWishRoomEntity userRoleWishRoomEntity " +
            "ON wishEntity.idWish = userRoleWishRoomEntity.wish.idWish " +
            "JOIN UserInfoEntity userInfoEntity " +
            "ON userRoleWishRoomEntity.userInfoEntity.idUserInfo = userInfoEntity.idUserInfo " +
            "WHERE userRoleWishRoomEntity.room.idRoom = :idRoom AND userInfoEntity.idUserInfo = :idUserInfo")
    WishEntity findWishesByRoomAndUser(@Param("idRoom") int idRoom, @Param("idUserInfo") int idUserInfo);

}
