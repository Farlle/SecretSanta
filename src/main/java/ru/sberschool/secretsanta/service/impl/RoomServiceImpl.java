package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.mapper.RoomMapper;
import ru.sberschool.secretsanta.model.entity.RoomEntity;
import ru.sberschool.secretsanta.repository.RoomRepository;
import ru.sberschool.secretsanta.service.RoomService;
import ru.sberschool.secretsanta.service.UserInfoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.sql.Date;


/**
 * Сервис для работы с комнатой
 */
@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UserInfoService userInfoService;
    private final RoomMapper roomMapper;

    public RoomServiceImpl(RoomRepository roomRepository, UserInfoService userInfoService, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.userInfoService = userInfoService;
        this.roomMapper = roomMapper;
    }

    /**
     * Метод для получения всех комнат с пагинацией
     *
     * @param pageable Параметр для пагинации
     * @return Страницу с комнатами
     */
    @Override
    public Page<RoomDTO> readAllRoom(Pageable pageable) {
        return roomRepository.findAll(pageable)
                .map(roomMapper::toRoomDTO);
    }

    /**
     * Метод для создания комнаты
     *
     * @param dto Объект который необходимо создать
     * @return Созданный объект
     */
    @Override
    public RoomDTO create(RoomDTO dto) {
        RoomEntity room = new RoomEntity();
        room.setName(dto.getName());
        room.setDrawDate(dto.getDrawDate());
        room.setPlace(dto.getPlace());
        room.setIdOrganizer(dto.getIdOrganizer());
        room.setTossDate(dto.getTossDate());

        return roomMapper.toRoomDTO(roomRepository.save(room));
    }

    /**
     * Метод для получения всех комнат
     *
     * @return Список всех комнат
     */
    @Override
    public List<RoomDTO> readAll() {
        return roomMapper.toRoomDTOList(roomRepository.findAll());
    }

    /**
     * Метод для обновления комнаты
     *
     * @param id Идентификатор комнаты
     * @param dto Объект для обновления
     * @return Обновленную комнату
     */
    @Override
    public RoomDTO update(int id, RoomDTO dto) {
        RoomEntity room = roomRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + id));

        room.setName(dto.getName());
        room.setDrawDate(dto.getDrawDate());
        room.setPlace(dto.getPlace());
        room.setIdOrganizer(dto.getIdOrganizer());
        room.setTossDate(dto.getTossDate());

        return roomMapper.toRoomDTO(roomRepository.save(room));
    }

    /**
     * Метод для удаления комнаты
     *
     * @param id Идентификатор комнаты
     */
    @Override
    public void delete(int id) {
        roomRepository.deleteById(id);
    }

    /**
     * Метод для получения комнаты по id
     *
     * @param id Идентификатор
     * @return Комната
     */
    @Override
    public RoomDTO getRoomById(int id) {
        return roomMapper.toRoomDTO(roomRepository.findById(id).orElseThrow());
    }


    /**
     * Метод для получения организатора комнаты
     *
     * @param dto Объект комнаты
     * @return Организатора
     */
    @Override
    public UserInfoDTO getRoomOrganizer(RoomDTO dto) {
        return userInfoService.getUserInfoById(dto.getIdOrganizer());
    }

    /**
     * Метод для получения пользователей и их ролей в комнате
     *
     * @param idRoom Идентификатор в комнате
     * @return Список всех пользователей с их ролями
     */
    @Override
    public List<Object[]> getUsersAndRolesByRoomId(int idRoom) {
        return roomRepository.findUserRoleInRoom(idRoom);
    }

    /**
     * Метод для получения всех комнат, в которых участвет пользователь
     *
     * @param idUserInfo Идентификатор пользователя
     * @return Список комнат, в которых участвует пользователь
     */
    @Override
    public List<RoomDTO> getRoomsWhereUserJoin(int idUserInfo) {
        return roomMapper.toRoomDTOList(roomRepository.findAllById(roomRepository.findRoomsWhereUserJoin(idUserInfo)));
    }

    /**
     * Метод для получения комнаты по имени
     *
     * @param name Имя комнаты
     * @return Найденная комната
     */
    @Override
    public RoomDTO getRoomByName(String name) {
        return roomMapper.toRoomDTO(roomRepository.findRoomEntitiesByName(name));
    }

    /**
     * Метод для получения id всех пользоватлей в комнате
     *
     * @param idRoom ИНдентификатор комнаты
     * @return Список иденификаторов всех пользователй
     */
    @Override
    public List<Integer> getUserInfoIdInRoom(int idRoom) {
        return roomRepository.findUserInfoIdInRoom(idRoom);
    }

    /**
     * Метод возвращающий список комнат, в которых подошел срок проведение жеребьевки
     *
     * @param date текущая дата
     * @return Список комнат в которых надо провести жеребьевку
     */
    @Override
    public List<RoomDTO> getByDrawDateLessThanEqual(Date date) {
        return roomMapper.toRoomDTOList(roomRepository.findByDrawDateLessThanEqual(date));
    }

    /**
     * Метод получающий список комнат к которым присоединен пользователь с пагинацией
     *
     * @param idUserInfo Идентификатор пользователя
     * @param pageable Параметры пагинцаии
     * @return Страница с комнатами
     */
    @Override
    public Page<RoomDTO> getRoomsWhereUserJoin(int idUserInfo, Pageable pageable) {
        return (roomRepository.findRoomsWhereUserJoinPage(idUserInfo, pageable)).map(roomMapper::toRoomDTO);
    }


}
