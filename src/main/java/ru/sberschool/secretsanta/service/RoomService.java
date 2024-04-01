package ru.sberschool.secretsanta.service;

import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface RoomService {

    RoomDTO create(RoomDTO dto);

    List<RoomDTO> readAll();

    RoomDTO update(int id, RoomDTO dto);

    void delete(int id);

    RoomDTO getRoomById(int id);

    UserInfoDTO getRoomOrganizer(RoomDTO dto);

    List<Object[]> getUsersAndRolesByRoomId(int idRoom);

    List<RoomDTO> getRoomsWhereUserJoin(int idUserInfo);

    RoomDTO getRoomByName(String name);

    List<Integer> getUserInfoIdInRoom(int idRoom);

    List<RoomDTO> getByDrawDateLessThanEqual(Date date);

    Page<RoomDTO> readAllRoom(Pageable pageable);

    Page<RoomDTO> getRoomsWhereUserJoin(int idUserInfo, Pageable pageable);

}
