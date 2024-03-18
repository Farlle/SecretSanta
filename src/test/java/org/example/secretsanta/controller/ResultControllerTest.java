package org.example.secretsanta.controller;

import org.example.secretsanta.dto.ResultDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.dto.WishDTO;
import org.example.secretsanta.service.impl.ResultServiceImpl;
import org.example.secretsanta.service.impl.RoomServiceImpl;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.example.secretsanta.service.impl.WishServiceImpl;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ResultController.class)
class ResultControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ResultServiceImpl resultServiceImpl;
    @MockBean
    private UserInfoServiceImpl userInfoServiceImpl;
    @MockBean
    private RoomServiceImpl roomServiceImpl;
    @MockBean
    private CustomUserDetailsService userDetailsService;
    @MockBean
    private WishServiceImpl wishServiceImpl;

    @Test
    void testShowResultDraw() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        ResultDTO resultDTO = new ResultDTO();
        WishDTO wishDTO = new WishDTO();

        when(userDetailsService.findUserByName(anyString())).thenReturn(userInfoDTO);
        when(resultServiceImpl.showDrawInRoom(anyInt())).thenReturn(Collections.singletonList(resultDTO));
        when(userInfoServiceImpl.getUserInfoById(anyInt())).thenReturn(userInfoDTO);
        when(wishServiceImpl.getUserWishInRoom(anyInt(), anyInt())).thenReturn(wishDTO);

        mockMvc.perform(get("/result/show/{idRoom}", 1)
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("result-show-in-room"))
                .andExpect(model().attributeExists("resultWrappers"));

        verify(userDetailsService).findUserByName(anyString());
        verify(resultServiceImpl).showDrawInRoom(anyInt());
        verify(userInfoServiceImpl, times(2)).getUserInfoById(anyInt());
        verify(wishServiceImpl).getUserWishInRoom(anyInt(), anyInt());
    }

    @Test
    void testDrawingLotsOrganizer() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();

        when(userDetailsService.findUserByName(anyString())).thenReturn(userInfoDTO);
        when(roomServiceImpl.getRoomById(anyInt())).thenReturn(roomDTO);
        when(roomServiceImpl.getRoomOrganizer(any(RoomDTO.class))).thenReturn(userInfoDTO);

        mockMvc.perform(get("/result/drawing/{idRoom}", 1)
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/result/show/1"));

        verify(userDetailsService).findUserByName(anyString());
        verify(roomServiceImpl, times(2)).getRoomById(anyInt());
        verify(roomServiceImpl).getRoomOrganizer(any(RoomDTO.class));
        verify(resultServiceImpl).performDraw(any(RoomDTO.class));
    }

    @Test
    void testDrawingLotsNonOrganizer() throws Exception {
        UserInfoDTO currentUser = new UserInfoDTO();
        UserInfoDTO roomOrganizer = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        currentUser.setIdUserInfo(1);
        currentUser.setIdUserInfo(2);

        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);
        when(roomServiceImpl.getRoomById(anyInt())).thenReturn(roomDTO);
        when(roomServiceImpl.getRoomOrganizer(any(RoomDTO.class))).thenReturn(roomOrganizer);


        mockMvc.perform(get("/result/drawing/{idRoom}", 1)
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room/show/1"))
                .andExpect(flash().attribute("errorMessage", "Вы не можете проводить жеребьевку"));

        verify(userDetailsService).findUserByName(anyString());
        verify(roomServiceImpl, times(1)).getRoomById(anyInt());
        verify(roomServiceImpl).getRoomOrganizer(any(RoomDTO.class));
        verify(resultServiceImpl, never()).performDraw(any(RoomDTO.class));
    }


}