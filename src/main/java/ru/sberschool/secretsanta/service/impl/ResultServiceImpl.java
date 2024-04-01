package ru.sberschool.secretsanta.service.impl;

import org.springframework.stereotype.Service;
import ru.sberschool.secretsanta.dto.ResultDTO;
import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.dto.UserInfoTelegramChatsDTO;
import ru.sberschool.secretsanta.exception.DrawingAlreadyPerformedException;
import ru.sberschool.secretsanta.exception.NotEnoughUsersException;
import ru.sberschool.secretsanta.mapper.ResultMapper;
import ru.sberschool.secretsanta.mapper.RoomMapper;
import ru.sberschool.secretsanta.model.entity.ResultEntity;
import ru.sberschool.secretsanta.repository.ResultRepository;
import ru.sberschool.secretsanta.service.*;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для работы с результатамив комнате
 */
@Service
public class ResultServiceImpl implements ResultService {

    private final ResultRepository resultRepository;
    private final UserInfoService userInfoService;
    private final UserInfoTelegramChatsService userInfoTelegramChatsService;
    private final TelegramService telegramService;
    private final RoomService roomService;
    private final ResultMapper resultMapper;
    private final RoomMapper roomMapper;
    private static final String MESSAGE_DRAW = "Была проведена жеребьевка, смотри результат по сслыке ";
    private static final String HOST = " http://localhost:8080/result/";
    private static final String SHOW = "show/";


    public ResultServiceImpl(ResultRepository resultRepository, UserInfoService userInfoService,
                             UserInfoTelegramChatsService userInfoTelegramChatsService,
                             TelegramService telegramService, RoomService roomService, ResultMapper resultMapper, RoomMapper roomMapper) {
        this.resultRepository = resultRepository;
        this.userInfoService = userInfoService;
        this.userInfoTelegramChatsService = userInfoTelegramChatsService;
        this.telegramService = telegramService;
        this.roomService = roomService;
        this.resultMapper = resultMapper;
        this.roomMapper = roomMapper;
    }

    /**
     * Метод для создания результата
     *
     * @param dto Объект который необходимо создать
     * @return Возвращает созданный результат
     */
    @Override
    public ResultDTO create(ResultDTO dto) {
        ResultEntity result = new ResultEntity();
        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());
        result.setRoom(roomMapper.toRoomEntity(dto.getRoomDTO()));

        return resultMapper.toResultDTO(resultRepository.save(result));
    }

    /**
     * Метод для получения всех результатов
     *
     * @return Список всех результатов
     */
    @Override
    public List<ResultDTO> readAll() {
        return resultMapper.toResultDTOList(resultRepository.findAll());
    }

    /**
     * Метод для обновления результата
     *
     * @param id  Идентификатор объекта который необходимо обновить
     * @param dto Объект который необходимо обновить
     * @return Обновленный объект
     */
    @Override
    public ResultDTO update(int id, ResultDTO dto) {
        ResultEntity result = resultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Result not found with id: " + id));

        result.setIdSanta(dto.getIdSanta());
        result.setIdWard(dto.getIdWard());
        result.setRoom(roomMapper.toRoomEntity(dto.getRoomDTO()));

        return resultMapper.toResultDTO(resultRepository.save(result));
    }

    /**
     * Метод для удаления результата
     *
     * @param id Идентификатор объекта для удаления
     */
    @Override
    public void delete(int id) {
        resultRepository.deleteById(id);
    }

    /**
     * Метод для проведения жеребьевки в комнате
     *
     * @param room Комната в которой надо провести жеребьевку
     */
    @Override
    public void performDraw(RoomDTO room) {

        List<UserInfoDTO> users = userInfoService.getUsersInfoById(roomService
                .getUserInfoIdInRoom(room.getIdRoom()));
        List<UserInfoTelegramChatsDTO> userInfoTelegramChatsDTO = userInfoTelegramChatsService
                .getAllUserChatsWhoNeedNotify(room.getIdRoom());

        List<ResultDTO> existingResults = resultMapper.toResultDTOList(
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
            resultRepository.save(resultMapper.toResultEntity(result));
        }
        for (UserInfoTelegramChatsDTO dto : userInfoTelegramChatsDTO) {
            telegramService.sendMessage(dto.getIdChat(), generatedMessageDraw(room.getIdRoom()));
        }

    }

    /**
     * Метод для показа результата в комнате
     *
     * @param idRoom Идентификатор в комнате
     * @return Возвращает список результатов в формате (Санта -> Подопечный)
     */
    @Override
    public List<ResultDTO> showDrawInRoom(int idRoom) {
        List<ResultDTO> results = resultMapper.toResultDTOList(resultRepository.findAll());
        return results
                .stream()
                .filter(result -> result.getRoomDTO().getIdRoom() == idRoom)
                .collect(Collectors.toList());
    }

    /**
     * Метод для генерации уведомления в телеграм о проведении жеребьевки
     *
     * @param idRoom Идентификатор комнаты
     * @return Текст уведомления
     */
    @Override
    public String generatedMessageDraw(int idRoom) {
        return MESSAGE_DRAW + HOST + SHOW + idRoom;
    }

}
