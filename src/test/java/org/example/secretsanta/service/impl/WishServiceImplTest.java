package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.mapper.WishMapper;
import org.example.secretsanta.model.entity.WishEntity;
import org.example.secretsanta.repository.WishRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WishServiceImplTest {

    @Mock
    private WishRepository wishRepository;
    @InjectMocks
    private WishServiceImpl wishService;
    @Mock
    private WishMapper wishMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        WishDTO dto = new WishDTO();
        dto.setWish("Test Wish");

        WishEntity entity = new WishEntity();
        entity.setIdWish(1);
        entity.setWish("Test Wish");

        when(wishRepository.save(any(WishEntity.class))).thenReturn(entity);

        WishDTO result = wishService.create(dto);

        verify(wishRepository, times(1)).save(any(WishEntity.class));
        assertEquals(wishMapper.toWishDTO(entity), result);
    }

    @Test
    void testReadAll() {
        List<WishEntity> wishEntities = Arrays.asList(
                new WishEntity(),
                new WishEntity()
        );
        when(wishRepository.findAll()).thenReturn(wishEntities);

        List<WishDTO> wishDTOs = Arrays.asList(
                new WishDTO(),
                new WishDTO()
        );
        when(wishMapper.toWishDTOList(wishEntities)).thenReturn(wishDTOs);

        List<WishDTO> result = wishService.readAll();
        assertEquals(wishDTOs, result);
    }

    @Test
    void testUpdate() {
        int id = 1;
        WishDTO dto = new WishDTO();
        dto.setWish("Updated Wish");

        WishEntity entity = new WishEntity();
        entity.setIdWish(id);
        entity.setWish("Updated Wish");

        when(wishRepository.findById(id)).thenReturn(Optional.of(entity));
        when(wishRepository.save(any(WishEntity.class))).thenReturn(entity);

        WishDTO result = wishService.update(id, dto);

        verify(wishRepository, times(1)).findById(id);
        verify(wishRepository, times(1)).save(any(WishEntity.class));
        assertEquals(wishMapper.toWishDTO(entity), result);
    }

    @Test
    void testGetUserWishInRoom() {
        int idRoom = 1;
        int idUserInfo = 2;
        WishEntity entity = new WishEntity();
        entity.setIdWish(1);
        entity.setWish("Test Wish");

        when(wishRepository.findWishesByRoomAndUser(idRoom, idUserInfo)).thenReturn(entity);

        WishDTO result = wishService.getUserWishInRoom(idRoom, idUserInfo);

        verify(wishRepository, times(1)).findWishesByRoomAndUser(idRoom, idUserInfo);
        assertEquals(wishMapper.toWishDTO(entity), result);
    }
}