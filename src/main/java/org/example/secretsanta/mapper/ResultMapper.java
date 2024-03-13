package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.model.entity.ResultEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component

public class ResultMapper {

    public static ResultDTO toResultDTO(ResultEntity resultEntity) {
        if (resultEntity == null) {
            return null;
        }

        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setIdResult(resultEntity.getIdResult());
        resultDTO.setIdSanta(resultEntity.getIdSanta());
        resultDTO.setIdWard(resultEntity.getIdWard());
        resultDTO.setRoomEntity(resultEntity.getRoom());

        return resultDTO;
    }

    public static ResultEntity toResultEntity(ResultDTO resultDTO) {
        if (resultDTO == null) {
            return null;
        }

        ResultEntity resultEntity = new ResultEntity();

        resultEntity.setIdResult(resultDTO.getIdResult());
        resultEntity.setIdSanta(resultDTO.getIdSanta());
        resultEntity.setIdWard(resultDTO.getIdWard());
        resultEntity.setRoom(resultDTO.getRoomEntity());

        return resultEntity;
    }


    public static List<ResultDTO> toResultDTOList(List<ResultEntity> resultEntitiesList) {
        if (resultEntitiesList == null) {
            return null;
        }

        return resultEntitiesList.stream()
                .map(ResultMapper::toResultDTO)
                .collect(Collectors.toList());

    }

}
