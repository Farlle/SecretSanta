package org.example.secretsanta.repository;

import org.example.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoTelegramChatsRepository extends JpaRepository<UserInfoTelegramChatsEntity, Integer> {
    UserInfoTelegramChatsEntity findFirstUserInfoTelegramChatsEntitiesByIdChat(Long idChats);

    @Query("select userTelegram " +
            "from UserInfoEntity userInfo join UserInfoTelegramChatsEntity userTelegram " +
            "on userInfo.idUserInfo= userTelegram.userInfo.idUserInfo " +
            "join UserRoleWishRoomEntity userRoom  " +
            "on userInfo.idUserInfo = userRoom.userInfoEntity.idUserInfo " +
            "join RoomEntity room on room.idRoom = userRoom.room.idRoom " +
            "where room.idRoom =:idRoom")
    List<UserInfoTelegramChatsEntity> findAllUserIdChatsWhoNeedNotify(int idRoom);

    @Query("select userInfoTelegramChatsEntity.idChat " +
            "from UserInfoEntity userInfoEntity join UserInfoTelegramChatsEntity userInfoTelegramChatsEntity " +
            "on userInfoTelegramChatsEntity.userInfo.idUserInfo = userInfoEntity.idUserInfo " +
            "where userInfoEntity.telegram =:telegramName")
    Long getIdChatByTelegramName(String telegramName);
}
