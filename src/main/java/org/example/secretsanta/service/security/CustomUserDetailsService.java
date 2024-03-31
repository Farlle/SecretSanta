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

/**
 * Сервис для работы с пользовательскими данными
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInfoRepository userInfoRepository;

    public CustomUserDetailsService(UserInfoRepository userRepository) {
        this.userInfoRepository = userRepository;
    }

    /**
     * Метод поиска пользователя по имени
     *
     * @param name Имя пользователя
     * @return Полльзователь
     * @throws UsernameNotFoundException Если пользователь не найден
     */
    public UserInfoDTO findUserByName(String name) {
        List<UserInfoEntity> allUsers = userInfoRepository.findAll();
        return UserInfoMapper.toUserInfoDTO(allUsers.stream()
                .filter(user -> user.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("UserInfo not found with name:" + name)));
    }


    /**
     * Метод для загрузки данных по имени пользователя
     *
     * @param name Имя пользователя
     * @return Информацию о пользоватле
     */
    @Override
    public UserDetails loadUserByUsername(String name) {
        UserInfoDTO userInfo = findUserByName(name);
        return new User(userInfo.getName(), userInfo.getPassword(), new ArrayList<>());
    }
}
