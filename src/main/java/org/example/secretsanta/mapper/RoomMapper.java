package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.model.entity.RoomEntity;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public static RoomDTO toRoomDTO(RoomEntity roomEntity) {
        if (roomEntity == null) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setIdRoom(roomEntity.getIdRoom());
        roomDTO.setIdOrganizer(roomEntity.getIdOrganizer());
        roomDTO.setName(roomEntity.getName());
        roomDTO.setPlace(roomEntity.getPlace());
        roomDTO.setDrawDate(roomEntity.getDrawDate());
        roomDTO.setTossDate(roomEntity.getTossDate());

        return roomDTO;
    }

    public static RoomEntity toRoomEntity(RoomDTO roomDTO) {
        if (roomDTO == null) {
            return null;
        }
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setIdRoom(roomDTO.getIdRoom());
        roomEntity.setIdOrganizer(roomDTO.getIdOrganizer());
        roomEntity.setName(roomDTO.getName());
        roomEntity.setPlace(roomDTO.getPlace());
        roomEntity.setDrawDate(roomDTO.getDrawDate());
        roomEntity.setTossDate(roomDTO.getTossDate());

        return roomEntity;

    }

}
