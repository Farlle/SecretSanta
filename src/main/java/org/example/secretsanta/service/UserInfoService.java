package org.example.secretsanta.service;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.UserInfoRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;

    public UserInfoService(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserInfoDTO getUserInfoById(int id) {
        return UserInfoMapper.toUserInfoDTO(userInfoRepository.findById(id).orElseThrow());
    }

    public UserInfoDTO update(int id, UserInfoDTO dto) {
        UserInfoEntity userInfoEntity = userInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));

        userInfoEntity.setName(dto.getName());
        userInfoEntity.setTelegram(dto.getTelegram());
        userInfoEntity.setPassword(passwordEncoder.encode(dto.getPassword()));

        return UserInfoMapper.toUserInfoDTO(userInfoRepository.save(userInfoEntity));
    }

    public UserInfoDTO create(UserInfoDTO dto) {
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setName(dto.getName());
        userInfo.setPassword(dto.getPassword());
        userInfo.setTelegram(dto.getTelegram());
        return UserInfoMapper.toUserInfoDTO(userInfoRepository.save(userInfo));
    }

    public List<UserInfoDTO> readAll() {
        return UserInfoMapper.toUserInfoDTOList(userInfoRepository.findAll());
    }

    public void delete(int id) {
        userInfoRepository.deleteById(id);
    }

    public void registerNewUserInfoAccount(UserInfoDTO dto) throws Exception {
        if (readAll().stream().anyMatch(userInfo -> userInfo.getName().equals(dto.getName()))) {
            throw new Exception("User already exists with this name:" + dto.getName());
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        create(dto);
    }

    public List<UserInfoDTO> getUsersInfoById(List<Integer> usersIds) {
        return UserInfoMapper.toUserInfoDTOList(userInfoRepository.findAllById(usersIds));
    }


}
