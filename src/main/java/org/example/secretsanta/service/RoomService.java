package org.example.secretsanta.service;

import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.repository.RoomRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public RoomEntity create(RoomDTO dto) {
        RoomEntity room = new RoomEntity();
        room.setName(dto.getName());
        room.setDrawDate(dto.getDrawDate());
        room.setPlace(dto.getPlace());
        room.setIdOrganizer(dto.getIdOrganizer());
        room.setTossDate(dto.getTossDate());
        room.setResult(dto.getResultEntity());

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
        room.setResult(dto.getResultEntity());

        return roomRepository.save(room);
    }

    public  void delete(int id){
        roomRepository.deleteById(id);
    }

    public RoomEntity getRoomEntityById(int id) {
        return roomRepository.findById(id).orElseThrow();
    }


}
