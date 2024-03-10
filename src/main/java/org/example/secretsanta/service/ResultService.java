package org.example.secretsanta.service;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.model.entity.ResultEntity;
import org.example.secretsanta.model.entity.RoomEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.ResultRepository;
import org.example.secretsanta.repository.RoomRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final RoomRepository roomRepository;
    private final UserInfoService userInfoService;

    public ResultService(ResultRepository resultRepository, RoomRepository roomRepository, UserInfoService userInfoService) {
        this.resultRepository = resultRepository;
        this.roomRepository = roomRepository;
        this.userInfoService = userInfoService;
    }

    public ResultEntity create(ResultDTO dto) {
        ResultEntity result = new ResultEntity();
        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());
        result.setRoom(dto.getRoomEntity());

        return result;
    }

    public List<ResultEntity> readAll() {
        return resultRepository.findAll();
    }

    public ResultEntity update(int id, ResultDTO dto) {
        ResultEntity result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + id));

        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());
        result.setRoom(dto.getRoomEntity());

        return result;
    }

    public void delete(int id) {
        resultRepository.deleteById(id);
    }

    public void performDraw(RoomEntity room) {

        List<UserInfoEntity> users = userInfoService.getUsersInfoById(room.getIdRoom());

        List<ResultEntity> existingResults = resultRepository.findByRoomIdRoom(room.getIdRoom());
        if (!existingResults.isEmpty()) {
            throw new IllegalStateException("Drawing has already been performed in this room");
        }

        if (users.size() < 2) {
            throw new IllegalStateException("Not enough users for drawing");
        }

        Collections.shuffle(users);

        for (int i = 0; i < users.size(); i++) {
            UserInfoEntity santa = users.get(i);
            UserInfoEntity ward = users.get((i + 1) % users.size());
            ResultEntity result = new ResultEntity();
            result.setIdSanta(santa.getId());
            result.setIdWard(ward.getId());
            result.setRoom(room);
            resultRepository.save(result);
        }

    }

    public List<ResultEntity> showDrawInRoom(int idRoom) {
        List<ResultEntity> results = resultRepository.findAll();
        return results
                .stream()
                .filter(result -> result.getRoom().getIdRoom()==idRoom)
                .collect(Collectors.toList());
    }

}
