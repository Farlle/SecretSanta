package org.example.secretsanta.repository;

import org.example.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoTelegramChatsRepository extends JpaRepository<UserInfoTelegramChatsEntity, Integer> {
}
