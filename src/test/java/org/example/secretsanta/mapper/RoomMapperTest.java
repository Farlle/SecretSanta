package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.model.entity.RoomEntity;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomMapperTest {

    @Test
    void testToRoomDTO() {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setIdRoom(1);
        roomEntity.setIdOrganizer(2);
        roomEntity.setName("Test Room");
        roomEntity.setPlace("Test Place");
        roomEntity.setDrawDate(new Date(864000));
        roomEntity.setTossDate(new Date(764000));

        RoomDTO roomDTO = RoomMapper.toRoomDTO(roomEntity);

        assertEquals(roomEntity.getIdRoom(), roomDTO.getIdRoom());
        assertEquals(roomEntity.getIdOrganizer(), roomDTO.getIdOrganizer());
        assertEquals(roomEntity.getName(), roomDTO.getName());
        assertEquals(roomEntity.getPlace(), roomDTO.getPlace());
        assertEquals(roomEntity.getDrawDate(), roomDTO.getDrawDate());
        assertEquals(roomEntity.getTossDate(), roomDTO.getTossDate());
    }

    @Test
    void testToRoomDTOList() {
        RoomEntity roomEntity1 = new RoomEntity();
        roomEntity1.setIdRoom(1);
        RoomEntity roomEntity2 = new RoomEntity();
        roomEntity2.setIdRoom(2);
        List<RoomEntity> roomEntities = Arrays.asList(roomEntity1, roomEntity2);

        List<RoomDTO> roomDTOList = RoomMapper.toRoomDTOList(roomEntities);

        assertEquals(roomEntities.size(), roomDTOList.size());
        assertEquals(roomEntities.get(0).getIdRoom(), roomDTOList.get(0).getIdRoom());
        assertEquals(roomEntities.get(1).getIdRoom(), roomDTOList.get(1).getIdRoom());
    }

    @Test
    void testToRoomEntity() {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setIdRoom(1);
        roomDTO.setIdOrganizer(2);
        roomDTO.setName("Test Room");
        roomDTO.setPlace("Test Place");
        roomDTO.setDrawDate(new Date(864000));
        roomDTO.setTossDate(new Date(764000));

        RoomEntity roomEntity = RoomMapper.toRoomEntity(roomDTO);

        assertEquals(roomDTO.getIdRoom(), roomEntity.getIdRoom());
        assertEquals(roomDTO.getIdOrganizer(), roomEntity.getIdOrganizer());
        assertEquals(roomDTO.getName(), roomEntity.getName());
        assertEquals(roomDTO.getPlace(), roomEntity.getPlace());
        assertEquals(roomDTO.getDrawDate(), roomEntity.getDrawDate());
        assertEquals(roomDTO.getTossDate(), roomEntity.getTossDate());
    }

    @Test
    void testToRoomDTO_Null() {
        RoomDTO roomDTO = RoomMapper.toRoomDTO(null);

        assertNotNull(roomDTO);
    }

    @Test
    void testToRoomDTOList_Null() {
        List<RoomDTO> roomDTOList = RoomMapper.toRoomDTOList(null);

        assertNotNull(roomDTOList);
    }

    @Test
    void testToRoomEntity_Null() {
        RoomEntity roomEntity = RoomMapper.toRoomEntity(null);

        assertNotNull(roomEntity);
    }

}