package org.example.secretsanta.repository;

import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<InviteEntity, Integer> {

    @Query("select invite " +
            "from UserInfoEntity  userInfo join InviteEntity invite " +
            "on userInfo.idUserInfo = invite.userInfo.idUserInfo " +
            "where userInfo.telegram =:telegram")
    List<InviteEntity> getAllUsersInvite(String telegram);

    @Query("select invite " +
            "from UserInfoEntity  userInfo join InviteEntity invite " +
            "on userInfo.idUserInfo = invite.userInfo.idUserInfo " +
            "where userInfo.telegram =:telegram and invite.text =:inviteText")
    List<InviteEntity> getAllInviteUsersInRoom(String telegram, String inviteText);

}
