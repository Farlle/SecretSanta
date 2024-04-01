package ru.sberschool.secretsanta.mapper;

import org.springframework.stereotype.Component;
import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.model.entity.RoomEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {

    public RoomDTO toRoomDTO(RoomEntity roomEntity) {
        RoomDTO roomDTO = new RoomDTO();

        if (roomEntity == null) {
            throw new IllegalArgumentException("RoomEntity cannot be null");
        }

        roomDTO.setIdRoom(roomEntity.getIdRoom());
        roomDTO.setIdOrganizer(roomEntity.getIdOrganizer());
        roomDTO.setName(roomEntity.getName());
        roomDTO.setPlace(roomEntity.getPlace());
        roomDTO.setDrawDate(roomEntity.getDrawDate());
        roomDTO.setTossDate(roomEntity.getTossDate());

        return roomDTO;
    }

    public List<RoomDTO> toRoomDTOList(List<RoomEntity> roomEntities) {
        if (roomEntities == null) {
            throw new IllegalArgumentException("RoomEntityList cannot be null");
        }

        return roomEntities.stream()
                .map(this::toRoomDTO)
                .collect(Collectors.toList());

    }


    public RoomEntity toRoomEntity(RoomDTO roomDTO) {
        RoomEntity roomEntity = new RoomEntity();

        if (roomDTO == null) {
            throw new IllegalArgumentException("RoomEntity cannot be null");
        }
        roomEntity.setIdRoom(roomDTO.getIdRoom());
        roomEntity.setIdOrganizer(roomDTO.getIdOrganizer());
        roomEntity.setName(roomDTO.getName());
        roomEntity.setPlace(roomDTO.getPlace());
        roomEntity.setDrawDate(roomDTO.getDrawDate());
        roomEntity.setTossDate(roomDTO.getTossDate());

        return roomEntity;

    }

}
