package org.example.secretsanta.controller;

import org.example.secretsanta.dto.MessageDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.impl.MessageServiceImpl;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(MessageController.class)
class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MessageServiceImpl messageServiceImpl;

    @MockBean
    private UserInfoServiceImpl userInfoServiceImpl;

    @MockBean
    private CustomUserDetailsService userDetailsService;


    @Test
    void testGetDialogs() throws Exception {
        UserInfoDTO currentUser = new UserInfoDTO();
        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);
        when(messageServiceImpl.getDistinctDialog(anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/message/dialogs")
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("dialogs"));

        verify(userDetailsService).findUserByName(anyString());
        verify(messageServiceImpl).getDistinctDialog(anyInt());
    }

    @Test
    void testReceiveMessages() throws Exception {
        UserInfoDTO currentUser = new UserInfoDTO();
        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);
        when(messageServiceImpl.getConversation(anyInt(), anyInt())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/message/conversation/1")
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("conversation"));

        verify(userDetailsService).findUserByName(anyString());
        verify(messageServiceImpl, times(2)).getConversation(anyInt(), anyInt());
    }

    @Test
    void testSendMessage() throws Exception {
        UserInfoDTO currentUser = new UserInfoDTO();
        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);

        mockMvc.perform(post("/message/sendMessage")
                        .param("idRecipient", "1")
                        .param("messageDto", "test message")
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/message/conversation/1"));

        verify(userDetailsService).findUserByName(anyString());
        verify(messageServiceImpl).create(any(MessageDTO.class));
    }
}