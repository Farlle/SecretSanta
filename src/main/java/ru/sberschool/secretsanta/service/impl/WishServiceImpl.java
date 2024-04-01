package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.dto.WishDTO;
import ru.sberschool.secretsanta.mapper.WishMapper;
import ru.sberschool.secretsanta.model.entity.WishEntity;
import ru.sberschool.secretsanta.repository.WishRepository;
import ru.sberschool.secretsanta.service.WishService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;
    private final WishMapper wishMapper;

    public WishServiceImpl(WishRepository wishRepository, WishMapper wishMapper) {
        this.wishRepository = wishRepository;
        this.wishMapper = wishMapper;
    }

    /**
     * Метод для создания желания
     *
     * @param dto Объект который необходимо создать
     * @return Созданный объект
     */
    @Override
    public WishDTO create(WishDTO dto) {
        WishEntity wish = new WishEntity();
        wish.setWish(dto.getWish());
        return wishMapper.toWishDTO(wishRepository.save(wish));
    }

    /**
     * Метод для получения всех желаний
     *
     * @return Список всех желаний
     */
    @Override
    public List<WishDTO> readAll() {
        return wishMapper.toWishDTOList(wishRepository.findAll());
    }

    /**
     * Метод для обновления желания пользователя
     *
     * @param id Идентификатор желания
     * @param dto Объект который надо обновить
     * @return Обновленный объект
     */
    @Override
    public WishDTO update(int id, WishDTO dto) {
        WishEntity wish = wishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wish not found with id: " + id));
        wish.setWish(dto.getWish());
        return wishMapper.toWishDTO(wishRepository.save(wish));
    }

    /**
     * Метод для получения желания пользователя в комнате
     *
     * @param idRoom Идентификатор комнаты
     * @param idUserInfo Идентификатор Пользователя
     * @return Желание пользователя в комнате
     */
    @Override
    public WishDTO getUserWishInRoom(int idRoom, int idUserInfo) {
        return wishMapper.toWishDTO(wishRepository.findWishesByRoomAndUser(idRoom, idUserInfo));
    }

}
