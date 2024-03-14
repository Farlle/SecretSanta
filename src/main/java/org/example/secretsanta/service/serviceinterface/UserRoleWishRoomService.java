package org.example.secretsanta.service.serviceinterface;

import org.example.secretsanta.dto.UserRoleWishRoomDTO;

import java.util.List;

public interface UserRoleWishRoomService {

    UserRoleWishRoomDTO create(UserRoleWishRoomDTO dto);
    List<UserRoleWishRoomDTO> readAll();
    UserRoleWishRoomDTO update(int id, UserRoleWishRoomDTO dto);
    void delete(int id);
    void deleteUserFromRoom(int idRoom, int idUserInfo);
    
}
