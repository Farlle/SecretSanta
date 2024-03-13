package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserRoleWishRoomDTO;
import org.example.secretsanta.mapper.UserRoleWishRoomMapper;
import org.example.secretsanta.model.entity.UserRoleWishRoomEntity;
import org.example.secretsanta.repository.UserRoleWishRoomRepository;
import org.example.secretsanta.service.serviceinterface.UserRoleWishRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserRoleWishRoomServiceImpl implements UserRoleWishRoomService {

    private final UserRoleWishRoomRepository userRoleWishRoomRepository;

    public UserRoleWishRoomServiceImpl(UserRoleWishRoomRepository userRoleWishRoomRepository) {
        this.userRoleWishRoomRepository = userRoleWishRoomRepository;
    }

    @Override
    public UserRoleWishRoomDTO create(UserRoleWishRoomDTO dto) {
        UserRoleWishRoomEntity userRoleWishRoom = new UserRoleWishRoomEntity();
        userRoleWishRoom.setRole(dto.getRoleEntity());
        userRoleWishRoom.setWish(dto.getWishEntity());
        userRoleWishRoom.setRoom(dto.getRoomEntity());
        userRoleWishRoom.setUserInfoEntity(dto.getUserInfoEntity());

        return UserRoleWishRoomMapper.toUserRoleWishRoomDTO(userRoleWishRoomRepository.save(userRoleWishRoom));
    }

    @Override
    public List<UserRoleWishRoomDTO> readAll() {
        return UserRoleWishRoomMapper.toUserRoleWishRoomDTOList(userRoleWishRoomRepository.findAll());
    }

    @Override
    public UserRoleWishRoomDTO update(int id, UserRoleWishRoomDTO dto) {
        UserRoleWishRoomEntity userRoleWishRoom = userRoleWishRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("userRoleWishRoom not found with id: " + id));

        userRoleWishRoom.setRole(dto.getRoleEntity());
        userRoleWishRoom.setWish(dto.getWishEntity());
        userRoleWishRoom.setRoom(dto.getRoomEntity());
        userRoleWishRoom.setUserInfoEntity(dto.getUserInfoEntity());

        return UserRoleWishRoomMapper.toUserRoleWishRoomDTO(userRoleWishRoomRepository.save(userRoleWishRoom));
    }

    @Override
    public void delete(int id) {
        userRoleWishRoomRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteUserFromRoom(int idRoom, int idUserInfo) {
        UserRoleWishRoomEntity userRoleWishRoom =
                userRoleWishRoomRepository.findByIdUserInfoAndIdRoom(idUserInfo, idRoom);
        delete(userRoleWishRoom.getIdUserRoleWishRoom());
    }

}
