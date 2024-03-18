package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.repository.RoomRepository;
import org.example.secretsanta.service.serviceinterface.RoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.sql.Date;


@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserInfoServiceImpl userInfoServiceImpl;

    public RoomServiceImpl(RoomRepository roomRepository, UserInfoServiceImpl userInfoServiceImpl) {
        this.roomRepository = roomRepository;
        this.userInfoServiceImpl = userInfoServiceImpl;
    }

    @Override
    public Page<RoomDTO> readAllRoom(Pageable pageable) {
        return roomRepository.findAll(pageable)
                .map(RoomMapper::toRoomDTO);
    }

    @Override
    public RoomDTO create(RoomDTO dto) {
        RoomEntity room = new RoomEntity();
        room.setName(dto.getName());
        room.setDrawDate(dto.getDrawDate());
        room.setPlace(dto.getPlace());
        room.setIdOrganizer(dto.getIdOrganizer());
        room.setTossDate(dto.getTossDate());

        return RoomMapper.toRoomDTO(roomRepository.save(room));
    }

    @Override
    public List<RoomDTO> readAll() {
        return RoomMapper.toRoomDTOList(roomRepository.findAll());
    }

    @Override
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

    @Override
    public void delete(int id) {
        roomRepository.deleteById(id);
    }

    @Override
    public RoomDTO getRoomById(int id) {
        return RoomMapper.toRoomDTO(roomRepository.findById(id).orElseThrow());
    }


    @Override
    public UserInfoDTO getRoomOrganizer(RoomDTO dto) {
        return userInfoServiceImpl.getUserInfoById(dto.getIdOrganizer());
    }

    @Override
    public RoomDTO findRoomByName(String name) {
        List<RoomEntity> allUsers = roomRepository.findAll();
        return RoomMapper.toRoomDTO(allUsers.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null));
    }

    @Override
    public List<Object[]> getUsersAndRolesByRoomId(int idRoom) {
        return roomRepository.findUserRoleInRoom(idRoom);
    }

    @Override
    public List<RoomDTO> getRoomsWhereUserJoin(int idUserInfo) {
        return RoomMapper.toRoomDTOList(roomRepository.findAllById(roomRepository.findRoomsWhereUserJoin(idUserInfo)));
    }

    @Override
    public RoomDTO getRoomByName(String name) {
        return RoomMapper.toRoomDTO(roomRepository.findRoomEntitiesByName(name));
    }

    @Override
    public List<RoomDTO> getRoomByUserName(String name) {
        return RoomMapper.toRoomDTOList(roomRepository.findRoomsByUserName(name));
    }

    @Override
    public List<Integer> getUserInfoIdInRoom(int idRoom) {
        return roomRepository.findUserInfoIdInRoom(idRoom);
    }

    @Override
    public List<RoomDTO> getByDrawDateLessThanEqual(Date date) {
        return RoomMapper.toRoomDTOList(roomRepository.findByDrawDateLessThanEqual(date));
    }

    @Override
    public Page<RoomDTO> getRoomsWhereUserJoin(int idUserInfo, Pageable pageable) {
        return (roomRepository.findRoomsWhereUserJoinPage(idUserInfo, pageable)).map(RoomMapper::toRoomDTO);
    }


}
