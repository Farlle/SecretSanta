package org.example.secretsanta.repository;

import org.example.secretsanta.model.entity.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<ResultEntity, Integer> {
    List<ResultEntity> findByRoomIdRoom(int idRoom);
}
