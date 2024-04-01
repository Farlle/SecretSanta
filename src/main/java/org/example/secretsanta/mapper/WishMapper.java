package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.model.entity.WishEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class WishMapper {

    public WishDTO toWishDTO(WishEntity wishEntity) {
        WishDTO wishDTO = new WishDTO();

        if (wishEntity == null) {
            throw new IllegalArgumentException("WishEntity cannot be null");
        }

        wishDTO.setIdWish(wishEntity.getIdWish());
        wishDTO.setWish(wishEntity.getWish());
        return wishDTO;
    }

    public WishEntity toWishEntity(WishDTO wishDTO) {
        WishEntity wishEntity = new WishEntity();

        if (wishDTO == null) {
            throw new IllegalArgumentException("WishDTO cannot be null");
        }

        wishEntity.setIdWish(wishDTO.getIdWish());
        wishEntity.setWish(wishDTO.getWish());
        return wishEntity;
    }

    public List<WishDTO> toWishDTOList(List<WishEntity> wishEntitiesList) {
        if (wishEntitiesList == null) {
            throw new IllegalArgumentException("WishEntityList cannot be null");
        }

        return wishEntitiesList.stream()
                .map(this::toWishDTO)
                .collect(Collectors.toList());

    }

}
