package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.exception.UserAlreadyExistsException;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.UserInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInfoServiceImplTest {

    @Mock
    private UserInfoRepository userInfoRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserInfoServiceImpl userInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getUserInfoByIdTest() {
        int id = 1;
        UserInfoEntity userInfoEntity = new UserInfoEntity();

        when(userInfoRepository.findById(id)).thenReturn(Optional.of(userInfoEntity));

        UserInfoDTO result = userInfoService.getUserInfoById(id);

        verify(userInfoRepository, times(1)).findById(id);
        assertEquals(UserInfoMapper.toUserInfoDTO(userInfoEntity), result);
    }

    @Test
    void updateTest() {
        int id = 1;
        UserInfoDTO dto = new UserInfoDTO();

        UserInfoEntity userInfoEntity = new UserInfoEntity();

        when(userInfoRepository.findById(id)).thenReturn(Optional.of(userInfoEntity));
        when(userInfoRepository.save(any(UserInfoEntity.class))).thenReturn(userInfoEntity);
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");

        UserInfoDTO result = userInfoService.update(id, dto);

        verify(userInfoRepository, times(1)).findById(id);
        verify(userInfoRepository, times(1)).save(any(UserInfoEntity.class));
        verify(passwordEncoder, times(1)).encode(dto.getPassword());
        assertEquals(UserInfoMapper.toUserInfoDTO(userInfoEntity), result);
    }

    @Test
    void createTest() {
        UserInfoDTO dto = new UserInfoDTO();

        UserInfoEntity userInfoEntity = new UserInfoEntity();

        when(userInfoRepository.save(any(UserInfoEntity.class))).thenReturn(userInfoEntity);

        UserInfoDTO result = userInfoService.create(dto);

        verify(userInfoRepository, times(1)).save(any(UserInfoEntity.class));
        assertEquals(UserInfoMapper.toUserInfoDTO(userInfoEntity), result);
    }

    @Test
    void readAllTest() {
        List<UserInfoEntity> userInfoEntities = Arrays.asList(new UserInfoEntity(), new UserInfoEntity());
        when(userInfoRepository.findAll()).thenReturn(userInfoEntities);

        List<UserInfoDTO> result = userInfoService.readAll();

        verify(userInfoRepository, times(1)).findAll();
        assertEquals(UserInfoMapper.toUserInfoDTOList(userInfoEntities), result);
    }

    @Test
    void deleteTest() {
        int id = 1;

        userInfoService.delete(id);

        verify(userInfoRepository, times(1)).deleteById(id);
    }

    @Test
    void registerNewUserInfoAccountTest() {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setName("New User");
        dto.setTelegram("123");
        dto.setPassword("123");

        when(userInfoRepository.findAll()).thenReturn(Collections.emptyList());
        when(userInfoRepository.save(any(UserInfoEntity.class))).thenReturn(new UserInfoEntity());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("123");

        assertDoesNotThrow(() -> userInfoService.registerNewUserInfoAccount(dto));

        verify(userInfoRepository, times(1)).findAll();
        verify(userInfoRepository, times(1)).save(any(UserInfoEntity.class));
        verify(passwordEncoder, times(1)).encode(dto.getPassword());
    }

    @Test
    void testRegisterNewUserInfoAccount_UserAlreadyExistsTest() {
        UserInfoDTO dto = new UserInfoDTO();
        dto.setName("New User");
        dto.setTelegram("123");
        dto.setPassword("123");

        when(userInfoRepository.findAll()).thenReturn(Arrays.asList(UserInfoMapper.toUserInfoEntity(dto)));
        when(userInfoRepository.save(any(UserInfoEntity.class))).thenReturn(new UserInfoEntity());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("123");

        assertThrows(UserAlreadyExistsException.class, () -> userInfoService.registerNewUserInfoAccount(dto));

        verify(userInfoRepository, times(1)).findAll();
        verify(userInfoRepository, never()).save(any(UserInfoEntity.class));
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    void getUsersInfoByIdTest() {
        List<Integer> usersIds = Arrays.asList(1, 2);
        List<UserInfoEntity> userInfoEntities = Arrays.asList(new UserInfoEntity(), new UserInfoEntity());
        when(userInfoRepository.findAllById(usersIds)).thenReturn(userInfoEntities);

        List<UserInfoDTO> result = userInfoService.getUsersInfoById(usersIds);

        verify(userInfoRepository, times(1)).findAllById(usersIds);
        assertEquals(UserInfoMapper.toUserInfoDTOList(userInfoEntities), result);
    }

    @Test
    void getUsersInfoByTelegramTest() {
        String telegram = "test_telegram";
        UserInfoEntity userInfoEntity = new UserInfoEntity();

        when(userInfoRepository.findByTelegram(telegram)).thenReturn(userInfoEntity);

        UserInfoDTO result = userInfoService.getUsersInfoByTelegram(telegram);

        verify(userInfoRepository, times(1)).findByTelegram(telegram);
        assertEquals(UserInfoMapper.toUserInfoDTO(userInfoEntity), result);
    }
}