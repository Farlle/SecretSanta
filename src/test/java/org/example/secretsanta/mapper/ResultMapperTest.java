package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.model.entity.ResultEntity;
import org.example.secretsanta.model.entity.RoomEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResultMapperTest {

    @Test
    void testToResultDTO() {
        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setIdResult(1);
        resultEntity.setIdSanta(2);
        resultEntity.setIdWard(3);
        resultEntity.setRoom(new RoomEntity());

        ResultDTO resultDTO = ResultMapper.toResultDTO(resultEntity);

        assertEquals(resultEntity.getIdResult(), resultDTO.getIdResult());
        assertEquals(resultEntity.getIdSanta(), resultDTO.getIdSanta());
        assertEquals(resultEntity.getIdWard(), resultDTO.getIdWard());
        assertEquals(resultEntity.getRoom(), RoomMapper.toRoomEntity(resultDTO.getRoomDTO()));
    }

    @Test
    void testToResultEntity() {
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setIdResult(1);
        resultDTO.setIdSanta(2);
        resultDTO.setIdWard(3);
        resultDTO.setRoomDTO(new RoomDTO());

        ResultEntity resultEntity = ResultMapper.toResultEntity(resultDTO);

        assertEquals(resultDTO.getIdResult(), resultEntity.getIdResult());
        assertEquals(resultDTO.getIdSanta(), resultEntity.getIdSanta());
        assertEquals(resultDTO.getIdWard(), resultEntity.getIdWard());
        assertEquals(resultDTO.getRoomDTO(), RoomMapper.toRoomDTO(resultEntity.getRoom()));
    }

    @Test
    void testToResultDTOList() {
        ResultEntity resultEntity1 = new ResultEntity();
        resultEntity1.setIdResult(1);
        ResultEntity resultEntity2 = new ResultEntity();
        resultEntity2.setIdResult(2);
        List<ResultEntity> resultEntitiesList = Arrays.asList(resultEntity1, resultEntity2);

        List<ResultDTO> resultDTOList = ResultMapper.toResultDTOList(resultEntitiesList);

        assertEquals(resultEntitiesList.size(), resultDTOList.size());
        assertEquals(resultEntitiesList.get(0).getIdResult(), resultDTOList.get(0).getIdResult());
        assertEquals(resultEntitiesList.get(1).getIdResult(), resultDTOList.get(1).getIdResult());
    }

    @Test
    void testToResultDTO_Null() {
        ResultDTO resultDTO = ResultMapper.toResultDTO(null);

        assertNotNull(resultDTO);
    }

    @Test
    void testToResultEntity_Null() {
        ResultEntity resultEntity = ResultMapper.toResultEntity(null);

        assertNotNull(resultEntity);
    }

    @Test
    void testToResultDTOList_Null() {
        List<ResultDTO> resultDTOList = ResultMapper.toResultDTOList(null);

        assertNotNull(resultDTOList);
    }

}