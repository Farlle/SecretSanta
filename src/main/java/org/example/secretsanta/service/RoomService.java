package org.example.secretsanta.service;

import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.repository.RoomRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.sql.Date;


@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserInfoService userInfoService;

    public RoomService(RoomRepository roomRepository, UserInfoService userInfoService) {
        this.roomRepository = roomRepository;
        this.userInfoService = userInfoService;
    }

    public RoomDTO create(RoomDTO dto) {
        RoomEntity room = new RoomEntity();
        room.setName(dto.getName());
        room.setDrawDate(dto.getDrawDate());
        room.setPlace(dto.getPlace());
        room.setIdOrganizer(dto.getIdOrganizer());
        room.setTossDate(dto.getTossDate());

        return RoomMapper.toRoomDTO(roomRepository.save(room));
    }

    public List<RoomDTO> readAll() {
        return RoomMapper.toRoomDTOList(roomRepository.findAll());
    }

    public RoomDTO update(int id, RoomDTO dto) {
        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));

        room.setName(dto.getName());
        room.setDrawDate(dto.getDrawDate());
        room.setPlace(dto.getPlace());
        room.setIdOrganizer(dto.getIdOrganizer());
        room.setTossDate(dto.getTossDate());

        return RoomMapper.toRoomDTO(roomRepository.save(room));
    }

    public void delete(int id) {
        roomRepository.deleteById(id);
    }

    public RoomDTO getRoomById(int id) {
        return RoomMapper.toRoomDTO(roomRepository.findById(id).orElseThrow());
    }

    public UserInfoDTO getRoomOrganizer(RoomDTO dto) {
        return userInfoService.getUserInfoById(dto.getIdOrganizer());
    }

    public RoomDTO findRoomByName(String name) {
        List<RoomEntity> allUsers = roomRepository.findAll();
        return RoomMapper.toRoomDTO(allUsers.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null));
    }

    public List<Object[]> getUsersAndRolesByRoomId(int idRoom) {
        return roomRepository.findUserRoleInRoom(idRoom);
    }

    public List<RoomDTO> getRoomsWhereUserJoin(int idUserInfo) {
        return RoomMapper.toRoomDTOList(roomRepository.findAllById(roomRepository.findRoomsWhereUserJoin(idUserInfo)));
    }

    public RoomDTO getRoomByName(String name) {
        return RoomMapper.toRoomDTO(roomRepository.findRoomEntitiesByName(name));
    }

    public List<RoomDTO> getRoomByUserName(String name) {
        return RoomMapper.toRoomDTOList(roomRepository.findRoomsByUserName(name));
    }

    public List<Integer> getUserIndoIdInRoom(int idRoom) {
        return roomRepository.findUserInfoIdInRoom(idRoom);
    }

    public List<RoomDTO> getByDrawDateLessThanEqual(Date date) {
        return RoomMapper.toRoomDTOList(roomRepository.findByDrawDateLessThanEqual(date));
    }


}
