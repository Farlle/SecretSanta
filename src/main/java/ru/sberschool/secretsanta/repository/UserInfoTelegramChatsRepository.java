package ru.sberschool.secretsanta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sberschool.secretsanta.model.entity.UserInfoTelegramChatsEntity;

import java.util.List;

@Repository
public interface UserInfoTelegramChatsRepository extends JpaRepository<UserInfoTelegramChatsEntity, Integer> {
    UserInfoTelegramChatsEntity findFirstUserInfoTelegramChatsEntitiesByIdChat(Long idChats);

    /**
     * Запрос возвращает все чаты в телеграм, которым требуется уведомление о проведении жеребьевки
     *
     * @param idRoom Идентификатор комнаты
     * @return Чаты пользователей в телеграм
     */
    @Query("select userTelegram " +
            "from UserInfoEntity userInfo join UserInfoTelegramChatsEntity userTelegram " +
            "on userInfo.idUserInfo= userTelegram.userInfo.idUserInfo " +
            "join UserRoleWishRoomEntity userRoom  " +
            "on userInfo.idUserInfo = userRoom.userInfoEntity.idUserInfo " +
            "join RoomEntity room on room.idRoom = userRoom.room.idRoom " +
            "where room.idRoom =:idRoom")
    List<UserInfoTelegramChatsEntity> findAllUserChatsWhoNeedNotify(int idRoom);

    /**
     * Запрос возвращает id телеграмовского чата по нику пользователя в тг
     *
     * @param telegramName Имя пользователя в тг
     * @return Идентификаторы чатов пользователя в телеграм
     */
    @Query("select userInfoTelegramChatsEntity.idChat " +
            "from UserInfoEntity userInfoEntity join UserInfoTelegramChatsEntity userInfoTelegramChatsEntity " +
            "on userInfoTelegramChatsEntity.userInfo.idUserInfo = userInfoEntity.idUserInfo " +
            "where userInfoEntity.telegram =:telegramName")
    Long getIdChatByTelegramName(String telegramName);
}
