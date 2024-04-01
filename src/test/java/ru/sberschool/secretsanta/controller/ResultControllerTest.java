package ru.sberschool.secretsanta.controller;

import ru.sberschool.secretsanta.dto.ResultDTO;
import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.dto.WishDTO;
import ru.sberschool.secretsanta.service.security.CustomUserDetailsService;
import ru.sberschool.secretsanta.service.ResultService;
import ru.sberschool.secretsanta.service.RoomService;
import ru.sberschool.secretsanta.service.UserInfoService;
import ru.sberschool.secretsanta.service.WishService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

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
    private ResultService resultService;
    @MockBean
    private UserInfoService userInfoService;
    @MockBean
    private RoomService roomService;
    @MockBean
    private CustomUserDetailsService userDetailsService;
    @MockBean
    private WishService wishService;

    @Test
    void testShowResultDraw() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        ResultDTO resultDTO = new ResultDTO();
        WishDTO wishDTO = new WishDTO();

        when(userDetailsService.findUserByName(anyString())).thenReturn(userInfoDTO);
        when(resultService.showDrawInRoom(anyInt())).thenReturn(Collections.singletonList(resultDTO));
        when(userInfoService.getUserInfoById(anyInt())).thenReturn(userInfoDTO);
        when(wishService.getUserWishInRoom(anyInt(), anyInt())).thenReturn(wishDTO);

        mockMvc.perform(get("/result/show/{idRoom}", 1)
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("result-show-in-room"))
                .andExpect(model().attributeExists("resultWrappers"));

        verify(userDetailsService).findUserByName(anyString());
        verify(resultService).showDrawInRoom(anyInt());
        verify(userInfoService, times(2)).getUserInfoById(anyInt());
        verify(wishService).getUserWishInRoom(anyInt(), anyInt());
    }

    @Test
    void testDrawingLotsOrganizer() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();

        when(userDetailsService.findUserByName(anyString())).thenReturn(userInfoDTO);
        when(roomService.getRoomById(anyInt())).thenReturn(roomDTO);
        when(roomService.getRoomOrganizer(any(RoomDTO.class))).thenReturn(userInfoDTO);

        mockMvc.perform(get("/result/drawing/{idRoom}", 1)
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/result/show/1"));

        verify(userDetailsService).findUserByName(anyString());
        verify(roomService, times(2)).getRoomById(anyInt());
        verify(roomService).getRoomOrganizer(any(RoomDTO.class));
        verify(resultService).performDraw(any(RoomDTO.class));
    }

    @Test
    void testDrawingLotsNonOrganizer() throws Exception {
        UserInfoDTO currentUser = new UserInfoDTO();
        UserInfoDTO roomOrganizer = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        currentUser.setIdUserInfo(1);
        currentUser.setIdUserInfo(2);

        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);
        when(roomService.getRoomById(anyInt())).thenReturn(roomDTO);
        when(roomService.getRoomOrganizer(any(RoomDTO.class))).thenReturn(roomOrganizer);


        mockMvc.perform(get("/result/drawing/{idRoom}", 1)
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room/show/1"))
                .andExpect(flash().attribute("errorMessage", "Вы не можете проводить жеребьевку"));

        verify(userDetailsService).findUserByName(anyString());
        verify(roomService, times(1)).getRoomById(anyInt());
        verify(roomService).getRoomOrganizer(any(RoomDTO.class));
        verify(resultService, never()).performDraw(any(RoomDTO.class));
    }


}