package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.mapper.ResultMapper;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.model.entity.ResultEntity;
import org.example.secretsanta.repository.ResultRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResultService {

    private final ResultRepository resultRepository;
    private final UserInfoService userInfoService;
    private final UserInfoTelegramChatsService userInfoTelegramChatsService;
    private final TelegramService telegramService;
    private final String MESSAGE_DRAW = "Была проведена жеребьевка, ваш подопечный: ";
    private String RECIPIENT;
    private String WISH;

    private final RoomService roomService;
    public ResultService(ResultRepository resultRepository, UserInfoService userInfoService, UserInfoTelegramChatsService userInfoTelegramChatsService, TelegramService telegramService, RoomService roomService) {
        this.resultRepository = resultRepository;
        this.userInfoService = userInfoService;
        this.userInfoTelegramChatsService = userInfoTelegramChatsService;
        this.telegramService = telegramService;
        this.roomService = roomService;
    }

    public ResultDTO create(ResultDTO dto) {
        ResultEntity result = new ResultEntity();
        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());
        result.setRoom(dto.getRoomEntity());

        return ResultMapper.toResultDTO(resultRepository.save(result));
    }

    public List<ResultDTO> readAll() {
        return ResultMapper.toResultDTOList(resultRepository.findAll());
    }

    public ResultDTO update(int id, ResultDTO dto) {
        ResultEntity result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + id));

        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());
        result.setRoom(dto.getRoomEntity());

        return ResultMapper.toResultDTO(resultRepository.save(result));
    }

    public void delete(int id) {
        resultRepository.deleteById(id);
    }

    public void performDraw(RoomDTO room) {

        List<UserInfoDTO> users = userInfoService.getUsersInfoById(roomService.getUserIndoIdInRoom(room.getIdRoom()));
        List<UserInfoTelegramChatsDTO> userInfoTelegramChatsDTO = userInfoTelegramChatsService
                .getAllIdChatsUsersWhoNeedNotify(room.getIdRoom());

        List<ResultDTO> existingResults = ResultMapper.toResultDTOList(
                resultRepository.findByRoomIdRoom(room.getIdRoom()));

 /*       if (!existingResults.isEmpty()) {
            throw new IllegalStateException("Drawing has already been performed in this room");
        }*/

        if (users.size() < 2) {
            throw new IllegalStateException("Not enough users for drawing");
        }

        Collections.shuffle(users);

        for (int i = 0; i < users.size(); i++) {
            UserInfoDTO santa = users.get(i);
            UserInfoDTO ward = users.get((i + 1) % users.size());
            ResultDTO result = new ResultDTO();
            result.setIdSanta(santa.getIdUserInfo());
            result.setIdWard(ward.getIdUserInfo());
            result.setRoomEntity(RoomMapper.toRoomEntity(room));
            resultRepository.save(ResultMapper.toResultEntity(result));
        }
        for (UserInfoTelegramChatsDTO dto: userInfoTelegramChatsDTO ) {
            telegramService.sendMessage(dto.getIdChat(), MESSAGE_DRAW);
        }


    }

    public List<ResultDTO> showDrawInRoom(int idRoom) {
        List<ResultDTO> results = ResultMapper.toResultDTOList(resultRepository.findAll());
        return results
                .stream()
                .filter(result -> result.getRoomEntity().getIdRoom() == idRoom)
                .collect(Collectors.toList());
    }

}
