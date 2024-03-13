package org.example.secretsanta.service;

import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.mapper.UserInfoTelegramChatsMapper;
import org.example.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import org.example.secretsanta.repository.UserInfoTelegramChatsRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserInfoTelegramChatsService {

    private final UserInfoTelegramChatsRepository userInfoTelegramChatsRepository;

    public UserInfoTelegramChatsService(UserInfoTelegramChatsRepository userInfoTelegramChatsRepository) {
        this.userInfoTelegramChatsRepository = userInfoTelegramChatsRepository;
    }

    public UserInfoTelegramChatsDTO create(UserInfoTelegramChatsDTO dto) {
        UserInfoTelegramChatsEntity userInfoTelegramChats = new UserInfoTelegramChatsEntity();
        userInfoTelegramChats.setUserInfo(dto.getUserInfoEntity());
        userInfoTelegramChats.setIdUserInfoTelegramChat(dto.getIdUserInfoTelegramChat());
        userInfoTelegramChats.setIdChat(dto.getIdChat());
        return UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsRepository.save(userInfoTelegramChats));
    }

    public List<UserInfoTelegramChatsDTO> readAll() {
        return UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(userInfoTelegramChatsRepository.findAll());
    }

    public UserInfoTelegramChatsDTO update(int id,UserInfoTelegramChatsDTO dto) {

        UserInfoTelegramChatsEntity userInfoTelegramChats = userInfoTelegramChatsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("userInfoTelegramChats not found with id: " + id));

        userInfoTelegramChats.setUserInfo(dto.getUserInfoEntity());
        userInfoTelegramChats.setIdUserInfoTelegramChat(dto.getIdUserInfoTelegramChat());
        userInfoTelegramChats.setIdChat(dto.getIdChat());
        return UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsRepository.save(userInfoTelegramChats));
    }

    public void delete(int id) {
        userInfoTelegramChatsRepository.deleteById(id);
    }
    

}
