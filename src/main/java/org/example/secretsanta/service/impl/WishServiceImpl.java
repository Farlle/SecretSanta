package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.mapper.WishMapper;
import org.example.secretsanta.model.entity.WishEntity;
import org.example.secretsanta.repository.WishRepository;
import org.example.secretsanta.service.WishService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class WishServiceImpl implements WishService {

    private final WishRepository wishRepository;

    public WishServiceImpl(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
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
        return WishMapper.toWishDTO(wishRepository.save(wish));
    }

    /**
     * Метод для получения всех желаний
     *
     * @return Список всех желаний
     */
    @Override
    public List<WishDTO> readAll() {
        return WishMapper.toWishDTOList(wishRepository.findAll());
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
        return WishMapper.toWishDTO(wishRepository.save(wish));
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
        return WishMapper.toWishDTO(wishRepository.findWishesByRoomAndUser(idRoom, idUserInfo));
    }

}
