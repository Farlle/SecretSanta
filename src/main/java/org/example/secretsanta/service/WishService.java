package org.example.secretsanta.service;

import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.model.entity.InviteEntity;
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

    public WishEntity create(WishDTO dto) {
        WishEntity wish = new WishEntity();
        wish.setWish(dto.getWish());
        return wishRepository.save(wish);
    }

    public List<WishEntity> readAll() {
        return wishRepository.findAll();
    }

    public WishEntity update(int id, WishDTO dto) {
        WishEntity wish = wishRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Wish not found with id: " + id));
        wish.setWish(dto.getWish());
        return wishRepository.save(wish);
    }

    public WishEntity getUserWishInRoom(int idRoom, int idUserInfo) {
        return wishRepository.findWishesByRoomAndUser(idRoom,idUserInfo);
    }

}
