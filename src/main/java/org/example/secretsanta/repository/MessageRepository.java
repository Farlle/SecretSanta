package org.example.secretsanta.repository;

import org.example.secretsanta.model.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    List<MessageEntity> findByIdRecipient(int idRecipient);

    /**
     * Запрос получает все сообщения между двумя пользователями
     *
     * @param idSender Идентификатор отправителя
     * @param idRecipient Идентификатор получателя
     * @return Список сообщений
     */
    @Query("SELECT messageEntity FROM MessageEntity messageEntity" +
            " WHERE messageEntity.sender.idUserInfo =:idSender " +
            "and messageEntity.idRecipient =:idRecipient")
    List<MessageEntity> findByIdSenderAndIdRecipient(int idSender, int idRecipient);

    /**
     * Запрос получает последние сообщения пользователю от разных пользователей
     *
     * @param idRecipient Идентификатор получаетеля
     * @return Список всех сообщений
     */
    @Query("SELECT m1 FROM MessageEntity m1 " +
            "WHERE m1.idMessage = " +
            "(SELECT MAX(m2.idMessage) FROM MessageEntity m2" +
            " WHERE m2.sender.idUserInfo = m1.sender.idUserInfo " +
            "AND m2.idRecipient = m1.idRecipient)" +
            " AND m1.idRecipient = :idRecipient")
    List<MessageEntity> findDistinctDialog(@PathVariable("idRecipient") int idRecipient);

}
