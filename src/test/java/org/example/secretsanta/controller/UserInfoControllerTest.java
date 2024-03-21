package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.service.serviceinterface.UserInfoService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Profile("dev")
@Disabled
@WebMvcTest(UserInfoController.class)
class UserInfoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserInfoService userInfoService;
    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    void testShowAddUserPage() throws Exception {
        mockMvc.perform(get("/userInfo/create").with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("add-user-info"))
                .andExpect(model().attributeExists("userInfoDto"));
    }

    @Test
    void testCreateUserInfo() throws Exception {
        UserInfoDTO dto = new UserInfoDTO(1, "test", "password", "telegram");

        when(userInfoService.create(dto)).thenReturn(dto);

        mockMvc.perform(post("/userInfo/create").with(user("username").password("password"))
                        .param("name", dto.getName())
                        .param("password", dto.getPassword())
                        .param("telegram", dto.getTelegram()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/userInfo/show"));
    }

    @Test
    void testUpdateUserInfoPage() throws Exception {
        int id = 1;
        UserInfoDTO dto = new UserInfoDTO(id, "test", "password", "telegram");
        when(userInfoService.getUserInfoById(id)).thenReturn(dto);

        mockMvc.perform(get("/userInfo/update/{id}", id).with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("user-info-update"))
                .andExpect(model().attributeExists("userInfo"));
    }

    @Test
    void testUpdateUserInfoTest() throws Exception {
        int id = 1;
        UserInfoDTO dto = new UserInfoDTO(id, "test", "password", "telegram");
        UserInfoDTO dtoNew = new UserInfoDTO(id, "test2", "password2", "telegram2");


        when(userInfoService.update(id, dto)).thenReturn(dtoNew);

        mockMvc.perform(post("/userInfo/update/{id}", id).with(user("username").password("password"))
                        .param("name", dtoNew.getName())
                        .param("password", dtoNew.getPassword())
                        .param("telegram", dtoNew.getTelegram()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/userInfo/show"));
    }

    @Test
    void getAllUsersInfoTest() throws Exception {
        List<UserInfoDTO> userInfoList = Arrays.asList(
                new UserInfoDTO(1, "test", "1223", "test"),
                new UserInfoDTO(2, "test2", "1223", "test2")
        );
        when(userInfoService.readAll()).thenReturn(userInfoList);

        mockMvc.perform(get("/userInfo/show")
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("user-info-list"))
                .andExpect(model().attributeExists("userInfo"))
                .andExpect(model().attribute("userInfo", userInfoList));
    }

    @Test
    void deleteUserInfoTest() throws Exception {
        int id = 1;

        mockMvc.perform(post("/userInfo/delete/{id}", id).with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/userInfo/show"));
    }

}