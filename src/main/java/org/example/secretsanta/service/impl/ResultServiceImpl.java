package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.exception.DrawingAlreadyPerformedException;
import org.example.secretsanta.exception.NotEnoughUsersException;
import org.example.secretsanta.mapper.ResultMapper;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.model.entity.ResultEntity;
import org.example.secretsanta.repository.ResultRepository;
import org.example.secretsanta.service.serviceinterface.ResultService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final UserInfoServiceImpl userInfoServiceImpl;
    private final UserInfoTelegramChatsServiceImpl userInfoTelegramChatsServiceImpl;
    private final TelegramServiceImpl telegramServiceImpl;
    private final String MESSAGE_DRAW = "Была проведена жеребьевка, смотри результат по сслыке ";
    private final String HOST = " http://localhost:8080/result/";

    private String RECIPIENT;
    private String WISH;

    private final RoomServiceImpl roomServiceImpl;

    public ResultServiceImpl(ResultRepository resultRepository, UserInfoServiceImpl userInfoServiceImpl, UserInfoTelegramChatsServiceImpl userInfoTelegramChatsServiceImpl, TelegramServiceImpl telegramServiceImpl, RoomServiceImpl roomServiceImpl) {
        this.resultRepository = resultRepository;
        this.userInfoServiceImpl = userInfoServiceImpl;
        this.userInfoTelegramChatsServiceImpl = userInfoTelegramChatsServiceImpl;
        this.telegramServiceImpl = telegramServiceImpl;
        this.roomServiceImpl = roomServiceImpl;
    }

    @Override
    public ResultDTO create(ResultDTO dto) {
        ResultEntity result = new ResultEntity();
        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());
        result.setRoom(RoomMapper.toRoomEntity(dto.getRoomDTO()));

        return ResultMapper.toResultDTO(resultRepository.save(result));
    }

    @Override
    public List<ResultDTO> readAll() {
        return ResultMapper.toResultDTOList(resultRepository.findAll());
    }

    @Override
    public ResultDTO update(int id, ResultDTO dto) {
        ResultEntity result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + id));

        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());
        result.setRoom(RoomMapper.toRoomEntity(dto.getRoomDTO()));

        return ResultMapper.toResultDTO(resultRepository.save(result));
    }

    @Override
    public void delete(int id) {
        resultRepository.deleteById(id);
    }

    @Override
    public void performDraw(RoomDTO room) {

        List<UserInfoDTO> users = userInfoServiceImpl.getUsersInfoById(roomServiceImpl
                .getUserInfoIdInRoom(room.getIdRoom()));
        List<UserInfoTelegramChatsDTO> userInfoTelegramChatsDTO = userInfoTelegramChatsServiceImpl
                .getAllIdChatsUsersWhoNeedNotify(room.getIdRoom());

        List<ResultDTO> existingResults = ResultMapper.toResultDTOList(
                resultRepository.findByRoomIdRoom(room.getIdRoom()));

        if (!existingResults.isEmpty()) {
            throw new DrawingAlreadyPerformedException("Drawing has already been performed in this room");
        }

        if (users.size() < 2) {
            throw new NotEnoughUsersException("Not enough users for drawing");
        }

        Collections.shuffle(users);

        for (int i = 0; i < users.size(); i++) {
            UserInfoDTO santa = users.get(i);
            UserInfoDTO ward = users.get((i + 1) % users.size());
            ResultDTO result = new ResultDTO();
            result.setIdSanta(santa.getIdUserInfo());
            result.setIdWard(ward.getIdUserInfo());
            result.setRoomDTO(room);
            resultRepository.save(ResultMapper.toResultEntity(result));
        }
        for (UserInfoTelegramChatsDTO dto : userInfoTelegramChatsDTO) {
            telegramServiceImpl.sendMessage(dto.getIdChat(), generatedMessageDraw(room.getIdRoom()));
        }

    }

    @Override
    public List<ResultDTO> showDrawInRoom(int idRoom) {
        List<ResultDTO> results = ResultMapper.toResultDTOList(resultRepository.findAll());
        return results
                .stream()
                .filter(result -> result.getRoomDTO().getIdRoom() == idRoom)
                .collect(Collectors.toList());
    }

    public String generatedMessageDraw(int idRoom) {
        return MESSAGE_DRAW + HOST + "show/" + idRoom;
    }

}
