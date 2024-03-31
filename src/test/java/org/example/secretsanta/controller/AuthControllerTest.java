package org.example.secretsanta.controller;

import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService userInfoService;
    @MockBean
    private CustomUserDetailsService userDetailsService;

    @Test
    void testLoginTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register"))
                .andExpect(model().attributeExists("userInfo"));
    }

    @Test
    void testRegistrationUserInfo() throws Exception {
        UserInfoDTO dto = new UserInfoDTO();

        mockMvc.perform(post("/register")
                        .flashAttr("userInfo", dto))
                .andExpect(status().isOk())
                .andExpect(view().name("register-telegram"));

        verify(userInfoService).registerNewUserInfoAccount(dto);
    }
}