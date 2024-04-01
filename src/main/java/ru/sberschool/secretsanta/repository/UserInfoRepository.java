package ru.sberschool.secretsanta.repository;

import ru.sberschool.secretsanta.model.entity.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Integer> {
    UserInfoEntity findByTelegram(String telegram);

}
