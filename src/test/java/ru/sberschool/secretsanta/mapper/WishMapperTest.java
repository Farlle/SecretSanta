package ru.sberschool.secretsanta.mapper;

import ru.sberschool.secretsanta.dto.WishDTO;
import ru.sberschool.secretsanta.model.entity.WishEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WishMapperTest {

    @InjectMocks
    private WishMapper wishMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testToWishDTO() {
        WishEntity wishEntity = new WishEntity();
        wishEntity.setIdWish(1);
        wishEntity.setWish("Test Wish");

        WishDTO wishDTO = wishMapper.toWishDTO(wishEntity);

        assertEquals(wishEntity.getIdWish(), wishDTO.getIdWish());
        assertEquals(wishEntity.getWish(), wishDTO.getWish());
    }

    @Test
    void testToWishEntity() {
        WishDTO wishDTO = new WishDTO();
        wishDTO.setIdWish(1);
        wishDTO.setWish("Test Wish");

        WishEntity wishEntity = wishMapper.toWishEntity(wishDTO);

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

        List<WishDTO> wishDTOList = wishMapper.toWishDTOList(wishEntitiesList);

        assertEquals(wishEntitiesList.size(), wishDTOList.size());
        assertEquals(wishEntitiesList.get(0).getIdWish(), wishDTOList.get(0).getIdWish());
        assertEquals(wishEntitiesList.get(1).getIdWish(), wishDTOList.get(1).getIdWish());
    }

    @Test
    void testToWishDTO_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            wishMapper.toWishDTO(null);
        });
    }

    @Test
    void testToWishEntity_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            wishMapper.toWishEntity(null);
        });
    }

    @Test
    void testToWishDTOList_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            wishMapper.toWishDTOList(null);
        });
    }

}