package org.example.secretsanta.service;

import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserRoleWishRoomDTO;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.entity.UserRoleWishRoomEntity;
import org.example.secretsanta.repository.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserInfoService userInfoService;

    public RoomService(RoomRepository roomRepository, UserInfoService userInfoService) {
        this.roomRepository = roomRepository;
        this.userInfoService = userInfoService;
    }

    public RoomEntity create(RoomDTO dto) {
        RoomEntity room = new RoomEntity();
        room.setName(dto.getName());
        room.setDrawDate(dto.getDrawDate());
        room.setPlace(dto.getPlace());
        room.setIdOrganizer(dto.getIdOrganizer());
        room.setTossDate(dto.getTossDate());

        return roomRepository.save(room);
    }

    public List<RoomEntity> readAll() {
        return roomRepository.findAll();
    }

    public RoomEntity update(int id, RoomDTO dto) {
        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));

        room.setName(dto.getName());
        room.setDrawDate(dto.getDrawDate());
        room.setPlace(dto.getPlace());
        room.setIdOrganizer(dto.getIdOrganizer());
        room.setTossDate(dto.getTossDate());

        return roomRepository.save(room);
    }

    public void delete(int id) {
        roomRepository.deleteById(id);
    }

    public RoomEntity getRoomEntityById(int id) {
        return roomRepository.findById(id).orElseThrow();
    }

    public UserInfoEntity getRoomOrganizer(RoomDTO dto) {
        return userInfoService.getUserInfoEntityById(dto.getIdOrganizer());
    }

    public RoomEntity findRoomByName(String name) {
        List<RoomEntity> allUsers = roomRepository.findAll();
        return allUsers.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<Object[]> getUsersAndRolesByRoomId(int idRoom) {
        return roomRepository.findUserRoleInRoom(idRoom);
    }

    public List<RoomEntity> getRoomsWhereUserJoin(int idUserInfo) {
        return roomRepository.findAllById(roomRepository.findRoomsWhereUserJoin(idUserInfo));
    }

    public  RoomEntity getRoomByName(String name) {
        return roomRepository.findRoomEntitiesByName(name);
    }


}
