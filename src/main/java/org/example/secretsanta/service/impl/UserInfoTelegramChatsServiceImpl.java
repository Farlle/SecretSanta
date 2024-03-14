package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserInfoTelegramChatsDTO;
import org.example.secretsanta.mapper.UserInfoTelegramChatsMapper;
import org.example.secretsanta.model.entity.UserInfoTelegramChatsEntity;
import org.example.secretsanta.repository.UserInfoTelegramChatsRepository;
import org.example.secretsanta.service.serviceinterface.UserInfoTelegramChatsService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserInfoTelegramChatsServiceImpl implements UserInfoTelegramChatsService {

    private final UserInfoTelegramChatsRepository userInfoTelegramChatsRepository;

    public UserInfoTelegramChatsServiceImpl(UserInfoTelegramChatsRepository userInfoTelegramChatsRepository) {
        this.userInfoTelegramChatsRepository = userInfoTelegramChatsRepository;
    }

    @Override
    public UserInfoTelegramChatsDTO create(UserInfoTelegramChatsDTO dto) {
        UserInfoTelegramChatsEntity userInfoTelegramChats = new UserInfoTelegramChatsEntity();
        userInfoTelegramChats.setUserInfo(dto.getUserInfoEntity());
        userInfoTelegramChats.setIdUserInfoTelegramChat(dto.getIdUserInfoTelegramChat());
        userInfoTelegramChats.setIdChat(dto.getIdChat());
        return UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsRepository.save(userInfoTelegramChats));
    }

    @Override
    public List<UserInfoTelegramChatsDTO> readAll() {
        return UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(userInfoTelegramChatsRepository.findAll());
    }

    @Override
    public UserInfoTelegramChatsDTO update(int id,UserInfoTelegramChatsDTO dto) {

        UserInfoTelegramChatsEntity userInfoTelegramChats = userInfoTelegramChatsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("userInfoTelegramChats not found with id: " + id));

        userInfoTelegramChats.setUserInfo(dto.getUserInfoEntity());
        userInfoTelegramChats.setIdUserInfoTelegramChat(dto.getIdUserInfoTelegramChat());
        userInfoTelegramChats.setIdChat(dto.getIdChat());
        return UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsRepository.save(userInfoTelegramChats));
    }

    @Override
    public void delete(int id) {
        userInfoTelegramChatsRepository.deleteById(id);
    }

    @Override
    public UserInfoTelegramChatsDTO getRegisterUserByIdChats(Long idChat) {
        return UserInfoTelegramChatsMapper
                .toUserInfoTelegramChatsDTO(userInfoTelegramChatsRepository
                        .findFirstUserInfoTelegramChatsEntitiesByIdChat(idChat));
    }

    @Override
    public List<UserInfoTelegramChatsDTO> getAllIdChatsUsersWhoNeedNotify(int idRoom) {
       return UserInfoTelegramChatsMapper.toUserInfoTelegramChatsDTOList(
               userInfoTelegramChatsRepository.findAllUserIdChatsWhoNeedNotify(idRoom));
    }

    @Override
    public Long getIdChatByTelegramName(String telegramName) {
        return userInfoTelegramChatsRepository.getIdChatByTelegramName(telegramName);
    }

}
