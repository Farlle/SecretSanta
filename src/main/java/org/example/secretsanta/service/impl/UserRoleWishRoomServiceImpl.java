package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserRoleWishRoomDTO;
import org.example.secretsanta.mapper.*;
import org.example.secretsanta.model.entity.UserRoleWishRoomEntity;
import org.example.secretsanta.repository.UserRoleWishRoomRepository;
import org.example.secretsanta.service.UserRoleWishRoomService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Сервис для работы с пользователями в комнате
 */
@Service
public class UserRoleWishRoomServiceImpl implements UserRoleWishRoomService {

    private final UserRoleWishRoomRepository userRoleWishRoomRepository;

    public UserRoleWishRoomServiceImpl(UserRoleWishRoomRepository userRoleWishRoomRepository) {
        this.userRoleWishRoomRepository = userRoleWishRoomRepository;
    }

    /**
     * Метод для создания пользователя в комнате
     *
     * @param dto ОБъект который необходимо создать
     * @return Созданный объект
     */
    @Override
    public UserRoleWishRoomDTO create(UserRoleWishRoomDTO dto) {
        UserRoleWishRoomEntity userRoleWishRoom = new UserRoleWishRoomEntity();
        userRoleWishRoom.setRole(RoleMapper.toRoleEntity(dto.getRoleDTO()));
        userRoleWishRoom.setWish(WishMapper.toWishEntity(dto.getWishDTO()));
        userRoleWishRoom.setRoom(RoomMapper.toRoomEntity(dto.getRoomDTO()));
        userRoleWishRoom.setUserInfoEntity(UserInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));

        return UserRoleWishRoomMapper.toUserRoleWishRoomDTO(userRoleWishRoomRepository.save(userRoleWishRoom));
    }

    /**
     * Метод для получения всех пользоватлей и комнат
     *
     * @return Список всех пользоватлей и комнат
     */
    @Override
    public List<UserRoleWishRoomDTO> readAll() {
        return UserRoleWishRoomMapper.toUserRoleWishRoomDTOList(userRoleWishRoomRepository.findAll());
    }

    /**
     * Метод для обновления пользоватлей в комнате
     *
     * @param id Идентификатор объекта
     * @param dto Объект который требуется обновить
     * @return Обновленный объект
     */
    @Override
    public UserRoleWishRoomDTO update(int id, UserRoleWishRoomDTO dto) {
        UserRoleWishRoomEntity userRoleWishRoom = userRoleWishRoomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("userRoleWishRoom not found with id: " + id));

        userRoleWishRoom.setRole(RoleMapper.toRoleEntity(dto.getRoleDTO()));
        userRoleWishRoom.setWish(WishMapper.toWishEntity(dto.getWishDTO()));
        userRoleWishRoom.setRoom(RoomMapper.toRoomEntity(dto.getRoomDTO()));
        userRoleWishRoom.setUserInfoEntity(UserInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));

        return UserRoleWishRoomMapper.toUserRoleWishRoomDTO(userRoleWishRoomRepository.save(userRoleWishRoom));
    }

    /**
     * Метод для удаления пользователя и комнаты
     *
     * @param id Идентификатор объекта для удаления
     */
    @Override
    public void delete(int id) {
        userRoleWishRoomRepository.deleteById(id);
    }

    /**
     * Метод для удаления пользователя из комнаты
     *
     * @param idRoom Идентификатор комнаты
     * @param idUserInfo Идентификар пользователя
     */
    @Override
    @Transactional
    public void deleteUserFromRoom(int idRoom, int idUserInfo) {
        UserRoleWishRoomEntity userRoleWishRoom =
                userRoleWishRoomRepository.findByIdUserInfoAndIdRoom(idUserInfo, idRoom);
        delete(userRoleWishRoom.getIdUserRoleWishRoom());
    }

}
