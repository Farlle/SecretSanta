package org.example.secretsanta.service;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfoEntity getUserInfoEntityById(int id){
        return userInfoRepository.findById(id).orElseThrow();
    }

    public UserInfoEntity update(int id, UserInfoDTO dto) {
        UserInfoEntity userInfoEntity = userInfoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found with id: " + id));

        userInfoEntity.setName(dto.getName());
        userInfoEntity.setTelegram(dto.getTelegram());
        userInfoEntity.setPassword(dto.getPassword());

        return userInfoRepository.save(userInfoEntity);
    }

    public UserInfoEntity create(UserInfoDTO dto){
        UserInfoEntity userInfo = new UserInfoEntity();
        userInfo.setName(dto.getName());
        userInfo.setPassword(dto.getPassword());
        userInfo.setTelegram(dto.getTelegram());
        return userInfoRepository.save(userInfo);
    }

    public List<UserInfoEntity> readAll(){
        return userInfoRepository.findAll();
    }

    public  void delete(int id){
        userInfoRepository.deleteById(id);
    }

}
