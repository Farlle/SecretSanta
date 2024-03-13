package org.example.secretsanta.service.serviceinterface;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.dto.RoomDTO;

import java.util.List;

public interface ResultService {

    ResultDTO create(ResultDTO dto);
    List<ResultDTO> readAll();
    ResultDTO update(int id, ResultDTO dto);
    void delete(int id);
    void performDraw(RoomDTO room);
    List<ResultDTO> showDrawInRoom(int idRoom);

}
