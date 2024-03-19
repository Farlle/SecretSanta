package org.example.secretsanta.controller;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.service.impl.InviteServiceImpl;
import org.example.secretsanta.service.impl.RoomServiceImpl;
import org.example.secretsanta.service.impl.UserInfoServiceImpl;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.example.secretsanta.wrapper.InviteTelegramWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InviteController.class)
class InviteControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InviteServiceImpl inviteServiceImpl;
    @MockBean
    private UserInfoServiceImpl userInfoServiceImpl;
    @MockBean
    private CustomUserDetailsService userDetailsService;
    @MockBean
    private RoomServiceImpl roomServiceImpl;

    @Test
    void testInviteTelegramFormOrganizer() throws Exception {
        UserInfoDTO currentUser = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();

        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);
        when(roomServiceImpl.getRoomById(anyInt())).thenReturn(roomDTO);
        when(roomServiceImpl.getRoomOrganizer(any(RoomDTO.class))).thenReturn(currentUser);


        mockMvc.perform(get("/invite/send/{idRoom}", 1)
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("invite-page"))
                .andExpect(model().attributeExists("inviteTelegramWrapper"));

        verify(userDetailsService, times(2)).findUserByName(anyString());
        verify(roomServiceImpl, times(2)).getRoomById(anyInt());
        verify(roomServiceImpl).getRoomOrganizer(any(RoomDTO.class));
    }

    @Test
    void testInviteTelegramFormNonOrganizer() throws Exception {
        UserInfoDTO currentUser = new UserInfoDTO();
        UserInfoDTO loginUser = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        currentUser.setIdUserInfo(1);
        loginUser.setIdUserInfo(2);

        when(userDetailsService.findUserByName(anyString())).thenReturn(loginUser);
        when(roomServiceImpl.getRoomById(anyInt())).thenReturn(roomDTO);
        when(roomServiceImpl.getRoomOrganizer(any(RoomDTO.class))).thenReturn(currentUser);


        mockMvc.perform(get("/invite/send/{idRoom}", 1)
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room/show/1"))
                .andExpect(flash().attribute("error", "Вы не можете приглашать участников"));


        verify(userDetailsService, times(1)).findUserByName(anyString());
        verify(roomServiceImpl, times(1)).getRoomById(anyInt());
        verify(roomServiceImpl).getRoomOrganizer(any(RoomDTO.class));
    }


    @Test
    void testInviteTelegramUser() throws Exception {
        UserInfoDTO participantUser = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setIdRoom(1);
        when(userInfoServiceImpl.getUsersInfoByTelegram(anyString())).thenReturn(participantUser);


        InviteTelegramWrapper inviteTelegramWrapper = new InviteTelegramWrapper();
        inviteTelegramWrapper.setParticipantTelegram("@participant");
        inviteTelegramWrapper.setRoom(roomDTO);

        mockMvc.perform(post("/invite/send")
                        .flashAttr("inviteTelegramWrapper", inviteTelegramWrapper)
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room/show/1"));

        verify(userInfoServiceImpl).getUsersInfoByTelegram(anyString());
        verify(inviteServiceImpl).sendInvite(anyInt(), any(InviteDTO.class));
    }

    @Test
    void testInviteTelegramUserNonTelegram() throws Exception {
        UserInfoDTO participantUser = null;
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setIdRoom(1);
        when(userInfoServiceImpl.getUsersInfoByTelegram(anyString())).thenReturn(participantUser);

        InviteTelegramWrapper inviteTelegramWrapper = new InviteTelegramWrapper();
        inviteTelegramWrapper.setParticipantTelegram("@participant");
        inviteTelegramWrapper.setRoom(roomDTO);

        mockMvc.perform(post("/invite/send")
                        .flashAttr("inviteTelegramWrapper", inviteTelegramWrapper)
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection()) // Ожидаем перенаправление
                .andExpect(redirectedUrl("/room/show/1")) // Ожидаем URL перенаправления
                .andExpect(flash().attribute("error", "Этот пользователь не регестрировал телеграм!")); // Ожидаем атрибут в flash-атрибутах


        verify(userInfoServiceImpl).getUsersInfoByTelegram(anyString());
        verify(inviteServiceImpl, never()).sendInvite(anyInt(), any(InviteDTO.class));
    }

}