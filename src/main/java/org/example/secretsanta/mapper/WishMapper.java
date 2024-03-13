package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.model.entity.WishEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WishMapper {

    public static WishDTO toWishDTO(WishEntity wishEntity) {
        if (wishEntity == null) {
            return null;
        }

        WishDTO wishDTO = new WishDTO();
        wishDTO.setIdWish(wishEntity.getIdWish());
        wishDTO.setWish(wishEntity.getWish());
        return wishDTO;
    }

    public static WishEntity toWishEntity(WishDTO wishDTO) {
        if (wishDTO == null) {
            return null;
        }

        WishEntity wishEntity = new WishEntity();
        wishEntity.setIdWish(wishDTO.getIdWish());
        wishEntity.setWish(wishDTO.getWish());
        return wishEntity;
    }

    public static List<WishDTO> toWishDTOList(List<WishEntity> wishEntitiesList) {
        if (wishEntitiesList == null) {
            return null;
        }

        return wishEntitiesList.stream()
                .map(WishMapper::toWishDTO)
                .collect(Collectors.toList());

    }

}
