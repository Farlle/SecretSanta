package org.example.secretsanta.repository;

import org.example.secretsanta.model.entity.MessageEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    List<MessageEntity> findByIdRecipient(int idRecipient);

    @Query("SELECT messageEntity FROM MessageEntity messageEntity" +
            " WHERE messageEntity.sender.idUserInfo =:idSender " +
            "and messageEntity.idRecipient =:idRecipient")
    List<MessageEntity> findByIdSenderAndIdRecipient(int idSender, int idRecipient);

    @Query("select distinct messageEntity.idRecipient " +
            "from MessageEntity messageEntity " +
            "where messageEntity.sender.idUserInfo  =: idSender")
    List<Integer> findDistinctRecipientsByIdSender(@PathVariable("idSender") int idSender);

}
