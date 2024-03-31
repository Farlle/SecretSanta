package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.model.entity.WishEntity;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WishMapperTest {

    @Test
    void testToWishDTO() {
        WishEntity wishEntity = new WishEntity();
        wishEntity.setIdWish(1);
        wishEntity.setWish("Test Wish");

        WishDTO wishDTO = WishMapper.toWishDTO(wishEntity);

        assertEquals(wishEntity.getIdWish(), wishDTO.getIdWish());
        assertEquals(wishEntity.getWish(), wishDTO.getWish());
    }

    @Test
    void testToWishEntity() {
        WishDTO wishDTO = new WishDTO();
        wishDTO.setIdWish(1);
        wishDTO.setWish("Test Wish");

        WishEntity wishEntity = WishMapper.toWishEntity(wishDTO);

        assertEquals(wishDTO.getIdWish(), wishEntity.getIdWish());
        assertEquals(wishDTO.getWish(), wishEntity.getWish());
    }

    @Test
    void testToWishDTOList() {
        WishEntity wishEntity1 = new WishEntity();
        wishEntity1.setIdWish(1);
        WishEntity wishEntity2 = new WishEntity();
        wishEntity2.setIdWish(2);
        List<WishEntity> wishEntitiesList = Arrays.asList(wishEntity1, wishEntity2);

        List<WishDTO> wishDTOList = WishMapper.toWishDTOList(wishEntitiesList);

        assertEquals(wishEntitiesList.size(), wishDTOList.size());
        assertEquals(wishEntitiesList.get(0).getIdWish(), wishDTOList.get(0).getIdWish());
        assertEquals(wishEntitiesList.get(1).getIdWish(), wishDTOList.get(1).getIdWish());
    }

    @Test
    void testToWishDTO_Null() {
        WishDTO wishDTO = WishMapper.toWishDTO(null);

        assertNotNull(wishDTO);
    }

    @Test
    void testToWishEntity_Null() {
        WishEntity wishEntity = WishMapper.toWishEntity(null);

        assertNotNull(wishEntity);
    }

    @Test
    void testToWishDTOList_Null() {
        List<WishDTO> wishDTOList = WishMapper.toWishDTOList(null);

        assertNotNull(wishDTOList);
    }

}