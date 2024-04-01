package ru.sberschool.secretsanta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sberschool.secretsanta.model.entity.ResultEntity;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Integer> {
    List<ResultEntity> findByRoomIdRoom(int idRoom);
}
