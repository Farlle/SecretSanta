package ru.sberschool.secretsanta.mapper;

import org.springframework.stereotype.Component;
import ru.sberschool.secretsanta.dto.ResultDTO;
import ru.sberschool.secretsanta.model.entity.ResultEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResultMapper {

    private final RoomMapper roomMapper;

    public ResultMapper(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    public ResultDTO toResultDTO(ResultEntity resultEntity) {
        ResultDTO resultDTO = new ResultDTO();

        if (resultEntity == null) {
            throw new IllegalArgumentException("ResultEntity cannot be null");
        }

        resultDTO.setIdResult(resultEntity.getIdResult());
        resultDTO.setIdSanta(resultEntity.getIdSanta());
        resultDTO.setIdWard(resultEntity.getIdWard());
        resultDTO.setRoomDTO(roomMapper.toRoomDTO(resultEntity.getRoom()));

        return resultDTO;
    }

    public ResultEntity toResultEntity(ResultDTO resultDTO) {
        ResultEntity resultEntity = new ResultEntity();

        if (resultDTO == null) {
            throw new IllegalArgumentException("ResultDTO cannot be null");
        }

        resultEntity.setIdResult(resultDTO.getIdResult());
        resultEntity.setIdSanta(resultDTO.getIdSanta());
        resultEntity.setIdWard(resultDTO.getIdWard());
        resultEntity.setRoom(roomMapper.toRoomEntity(resultDTO.getRoomDTO()));

        return resultEntity;
    }


    public List<ResultDTO> toResultDTOList(List<ResultEntity> resultEntitiesList) {
        if (resultEntitiesList == null) {
            throw new IllegalArgumentException("ResultEntityList cannot be null");
        }

        return resultEntitiesList.stream()
                .map(this::toResultDTO)
                .collect(Collectors.toList());

    }

}
