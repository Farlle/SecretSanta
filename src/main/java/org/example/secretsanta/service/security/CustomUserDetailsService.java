package org.example.secretsanta.service.security;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.UserInfoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    public CustomUserDetailsService(UserInfoRepository userRepository) {
        this.userInfoRepository = userRepository;
    }

    public UserInfoDTO findUserByName(String name) {
        List<UserInfoEntity> allUsers = userInfoRepository.findAll();
        return UserInfoMapper.toUserInfoDTO(allUsers.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElse(null));
    }


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        UserInfoDTO userInfo = findUserByName(name);
        if (userInfo == null) {
            throw new UsernameNotFoundException("UserInfo not found with name:" + name);
        }
        return new User(userInfo.getName(), userInfo.getPassword(), new ArrayList<>());
    }
}
