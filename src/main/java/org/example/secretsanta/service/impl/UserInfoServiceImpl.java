package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.exception.UserAlreadyExistsException;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.UserInfoRepository;
import org.example.secretsanta.service.serviceinterface.UserInfoService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserInfoDTO getUserInfoById(int id) {
        return UserInfoMapper.toUserInfoDTO(userInfoRepository.findById(id).orElseThrow());
    }

    @Override
    public UserInfoDTO update(int id, UserInfoDTO dto) {
        UserInfoEntity userInfoEntity = userInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));

        userInfoEntity.setName(dto.getName());
        userInfoEntity.setTelegram(dto.getTelegram());
        userInfoEntity.setPassword(passwordEncoder.encode(dto.getPassword()));

        return UserInfoMapper.toUserInfoDTO(userInfoRepository.save(userInfoEntity));
    }

    @Override
    public UserInfoDTO create(UserInfoDTO dto) {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setName(dto.getName());
        userInfo.setPassword(dto.getPassword());
        userInfo.setTelegram(dto.getTelegram());
        return UserInfoMapper.toUserInfoDTO(userInfoRepository.save(userInfo));
    }

    @Override
    public List<UserInfoDTO> readAll() {
        return UserInfoMapper.toUserInfoDTOList(userInfoRepository.findAll());
    }

    @Override
    public void delete(int id) {
        userInfoRepository.deleteById(id);
    }

    @Override
    public void registerNewUserInfoAccount(UserInfoDTO dto) throws Exception {
        if (userInfoRepository.findAll().stream().anyMatch(userInfo -> userInfo.getName().equals(dto.getName()))) {
            throw new UserAlreadyExistsException("User already exists with this name:" + dto.getName());
        }
        dto.setTelegram(dto.getTelegram().replaceAll("@", ""));
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        create(dto);
    }

    @Override
    public List<UserInfoDTO> getUsersInfoById(List<Integer> idUsers) {
        return UserInfoMapper.toUserInfoDTOList(userInfoRepository.findAllById(idUsers));
    }

    @Override
    public UserInfoDTO getUsersInfoByTelegram(String telegram) {
        return UserInfoMapper.toUserInfoDTO(userInfoRepository.findByTelegram(telegram));
    }

    @Override
    public String getTelegramUser(int idUser) {
        UserInfoEntity userInfoEntity = userInfoRepository.findById(idUser).orElseThrow();
        return  userInfoEntity.getTelegram();
    }

}
