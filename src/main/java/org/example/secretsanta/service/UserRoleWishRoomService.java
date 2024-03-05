package org.example.secretsanta.service;

import org.example.secretsanta.dto.UserRoleWishRoomDTO;
import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.entity.UserRoleWishRoomEntity;
import org.example.secretsanta.repository.UserInfoRepository;
import org.example.secretsanta.repository.UserRoleWishRoomRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserRoleWishRoomService {

    private final UserRoleWishRoomRepository userRoleWishRoomRepository;

    public UserRoleWishRoomService(UserRoleWishRoomRepository userRoleWishRoomRepository) {
        this.userRoleWishRoomRepository = userRoleWishRoomRepository;
    }

    public UserRoleWishRoomEntity create(UserRoleWishRoomDTO dto) {
        UserRoleWishRoomEntity userRoleWishRoom = new UserRoleWishRoomEntity();
        userRoleWishRoom.setRole(dto.getRoleEntity());
        userRoleWishRoom.setWish(dto.getWishEntity());
        userRoleWishRoom.setRoom(dto.getRoomEntity());
        userRoleWishRoom.setUserInfoEntity(dto.getUserInfoEntity());

        return userRoleWishRoomRepository.save(userRoleWishRoom);
    }

    public List<UserRoleWishRoomEntity> readAll() {
        return userRoleWishRoomRepository.findAll();
    }

    public UserRoleWishRoomEntity update(int id, UserRoleWishRoomDTO dto) {
        UserRoleWishRoomEntity userRoleWishRoom = userRoleWishRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("userRoleWishRoom not found with id: " + id));

        userRoleWishRoom.setRole(dto.getRoleEntity());
        userRoleWishRoom.setWish(dto.getWishEntity());
        userRoleWishRoom.setRoom(dto.getRoomEntity());
        userRoleWishRoom.setUserInfoEntity(dto.getUserInfoEntity());

        return userRoleWishRoomRepository.save(userRoleWishRoom);
    }

    public  void delete(int id){
        userRoleWishRoomRepository.deleteById(id);
    }

}
