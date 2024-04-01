package ru.sberschool.secretsanta.repository;

import ru.sberschool.secretsanta.model.entity.InviteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<InviteEntity, Integer> {

    /**
     * Запрос получает все приглашения в комнате по его тг
     *
     * @param telegram Ник в телеграме пользователя
     * @return Список приглашений пользователя
     */
    @Query("select invite " +
            "from UserInfoEntity  userInfo join InviteEntity invite " +
            "on userInfo.idUserInfo = invite.userInfo.idUserInfo " +
            "where userInfo.telegram =:telegram")
    List<InviteEntity> getAllUsersInvite(String telegram);

    /**
     * Запрос возвращает все приглашения пользователя в определенную комнату
     *
     * @param telegram Ник пользователя в телеграм
     * @param inviteText Текст приглашения ппользователя в тг
     * @return Все приглашения пользователя в определенную комнату
     */
    @Query("select invite " +
            "from UserInfoEntity  userInfo join InviteEntity invite " +
            "on userInfo.idUserInfo = invite.userInfo.idUserInfo " +
            "where userInfo.telegram =:telegram and invite.text =:inviteText")
    List<InviteEntity> getAllInviteUsersInRoom(String telegram, String inviteText);

}
