package org.example.secretsanta.service;

import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.mapper.WishMapper;
import org.example.secretsanta.model.entity.WishEntity;
import org.example.secretsanta.repository.WishRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class WishService {

    private final WishRepository wishRepository;

    public WishService(WishRepository wishRepository) {
        this.wishRepository = wishRepository;
    }

    public WishDTO create(WishDTO dto) {
        WishEntity wish = new WishEntity();
        wish.setWish(dto.getWish());
        return WishMapper.toWishDTO(wishRepository.save(wish));
    }

    public List<WishDTO> readAll() {
        return WishMapper.toWishDTOList(wishRepository.findAll());
    }

    public WishDTO update(int id, WishDTO dto) {
        WishEntity wish = wishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wish not found with id: " + id));
        wish.setWish(dto.getWish());
        return WishMapper.toWishDTO(wishRepository.save(wish));
    }

    public WishDTO getUserWishInRoom(int idRoom, int idUserInfo) {
        return WishMapper.toWishDTO(wishRepository.findWishesByRoomAndUser(idRoom, idUserInfo));
    }

}
