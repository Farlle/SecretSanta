package ru.sberschool.secretsanta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberschool.secretsanta.model.entity.UserInfoEntity;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
    UserInfoEntity findByTelegram(String telegram);

}
