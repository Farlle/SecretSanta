package ru.sberschool.secretsanta.service;

import ru.sberschool.secretsanta.dto.UserRoleWishRoomDTO;

import java.util.List;

public interface UserRoleWishRoomService {

    UserRoleWishRoomDTO create(UserRoleWishRoomDTO dto);

    List<UserRoleWishRoomDTO> readAll();

    UserRoleWishRoomDTO update(int id, UserRoleWishRoomDTO dto);

    void delete(int id);

    void deleteUserFromRoom(int idRoom, int idUserInfo);

}
