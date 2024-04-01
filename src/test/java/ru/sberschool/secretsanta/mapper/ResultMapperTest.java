package ru.sberschool.secretsanta.mapper;

import ru.sberschool.secretsanta.dto.ResultDTO;
import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.model.entity.ResultEntity;
import ru.sberschool.secretsanta.model.entity.RoomEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ResultMapperTest {

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private ResultMapper resultMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testToResultDTO() {
        RoomDTO roomDTO = new RoomDTO();
        when(roomMapper.toRoomDTO(any())).thenReturn(roomDTO);

        ResultEntity resultEntity = new ResultEntity();
        resultEntity.setIdResult(1);
        resultEntity.setIdSanta(2);
        resultEntity.setIdWard(3);

        ResultDTO resultDTO = resultMapper.toResultDTO(resultEntity);

        assertEquals(resultEntity.getIdResult(), resultDTO.getIdResult());
        assertEquals(resultEntity.getIdSanta(), resultDTO.getIdSanta());
        assertEquals(resultEntity.getIdWard(), resultDTO.getIdWard());
        assertEquals(roomDTO, resultDTO.getRoomDTO());
    }

    @Test
    void testToResultEntity() {
        RoomEntity roomEntity = new RoomEntity();
        when(roomMapper.toRoomEntity(any())).thenReturn(roomEntity);

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setIdResult(1);
        resultDTO.setIdSanta(2);
        resultDTO.setIdWard(3);

        ResultEntity resultEntity = resultMapper.toResultEntity(resultDTO);

        assertEquals(resultDTO.getIdResult(), resultEntity.getIdResult());
        assertEquals(resultDTO.getIdSanta(), resultEntity.getIdSanta());
        assertEquals(resultDTO.getIdWard(), resultEntity.getIdWard());
        assertEquals(roomEntity, resultEntity.getRoom());
    }

    @Test
    void testToResultDTOList() {
        ResultEntity resultEntity1 = new ResultEntity();
        resultEntity1.setIdResult(1);
        ResultEntity resultEntity2 = new ResultEntity();
        resultEntity2.setIdResult(2);
        List<ResultEntity> resultEntitiesList = Arrays.asList(resultEntity1, resultEntity2);

        List<ResultDTO> resultDTOList = resultMapper.toResultDTOList(resultEntitiesList);

        assertEquals(resultEntitiesList.size(), resultDTOList.size());
        assertEquals(resultEntitiesList.get(0).getIdResult(), resultDTOList.get(0).getIdResult());
        assertEquals(resultEntitiesList.get(1).getIdResult(), resultDTOList.get(1).getIdResult());
    }

    @Test
    void testToResultDTO_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            resultMapper.toResultDTO(null);
        });
    }

    @Test
    void testToResultEntity_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            resultMapper.toResultEntity(null);
        });
    }

    @Test
    void testToResultDTOList_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            resultMapper.toResultDTOList(null);
        });
    }

}