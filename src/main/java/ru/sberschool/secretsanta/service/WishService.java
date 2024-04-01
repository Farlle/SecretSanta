package ru.sberschool.secretsanta.service;

import ru.sberschool.secretsanta.dto.WishDTO;

import java.util.List;

public interface WishService {

    WishDTO create(WishDTO dto);

    List<WishDTO> readAll();

    WishDTO update(int id, WishDTO dto);

    WishDTO getUserWishInRoom(int idRoom, int idUserInfo);

}
