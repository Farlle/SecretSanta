package org.example.secretsanta.repository;

import org.example.secretsanta.model.entity.UserRoleWishRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleWishRoomRepository extends JpaRepository<UserRoleWishRoomEntity, Long> {
}
