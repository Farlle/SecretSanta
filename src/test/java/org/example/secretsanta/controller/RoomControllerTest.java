package org.example.secretsanta.controller;

import org.example.secretsanta.dto.*;
import org.example.secretsanta.model.enums.Role;
import org.example.secretsanta.service.*;
import org.example.secretsanta.service.security.CustomUserDetailsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;
    @MockBean
    private CustomUserDetailsService userDetailsService;
    @MockBean
    private WishService wishService;
    @MockBean
    private RoleService roleService;
    @MockBean
    private UserRoleWishRoomService userRoleWishRoomService;
    @MockBean
    private InviteService inviteService;

    @Test
    void testShowAddRoomPage() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO(1, "username", "password",
                "telegram");

        when(userDetailsService.findUserByName("username")).thenReturn(userInfoDTO);

        mockMvc.perform(get("/room/create").with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("roomDto"))
                .andExpect(view().name("room-add-page"));

        verify(userDetailsService, times(1)).findUserByName("username");
    }


    @Test
    void testCreateRoomWithInvalidDate() throws Exception {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("test");
        roomDTO.setPlace("test");
        roomDTO.setIdOrganizer(1);
        roomDTO.setDrawDate(new Date(86400000L));
        roomDTO.setTossDate(new Date(66400000L));

        mockMvc.perform(post("/room/create")
                        .param("name", roomDTO.getName())
                        .param("place", roomDTO.getPlace())
                        .param("idOrganizer", String.valueOf(roomDTO.getIdOrganizer()))
                        .param("drawDate", String.valueOf(roomDTO.getDrawDate()))
                        .param("tossDate", String.valueOf(roomDTO.getTossDate()))
                        .with(user("username").password("password")))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/room/create"))
                .andExpect(flash().attribute("error", "Draw Date must be before Toss Date."));

        verify(roomService, never()).create(any(RoomDTO.class));
    }

    @Test
    void testCreateRoomWithValidDate() throws Exception {
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("test");
        roomDTO.setPlace("test");
        roomDTO.setIdOrganizer(1);
        roomDTO.setDrawDate(new Date(76300000L));
        roomDTO.setTossDate(new Date(286400000L));

        RoomDTO createdRoom;
        createdRoom = roomDTO;
        createdRoom.setIdRoom(1);

        when(roomService.create(any(RoomDTO.class))).thenReturn(createdRoom);
        mockMvc.perform(post("/room/create")
                        .param("id", String.valueOf(createdRoom.getIdRoom()))
                        .param("name", createdRoom.getName())
                        .param("place", createdRoom.getPlace())
                        .param("idOrganizer", String.valueOf(createdRoom.getIdOrganizer()))
                        .param("drawDate", String.valueOf(createdRoom.getDrawDate()))
                        .param("tossDate", String.valueOf(createdRoom.getTossDate()))
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room/" + createdRoom.getIdRoom() + "/join"));

        verify(roomService, times(1)).create(any(RoomDTO.class));

    }

    @Test
    void testGetRoomById() throws Exception {
        int idRoom = 1;
        RoomDTO room = new RoomDTO();
        UserInfoDTO loginUser = new UserInfoDTO();
        UserInfoDTO organizer = new UserInfoDTO();

        when(roomService.getRoomById(idRoom)).thenReturn(room);
        when(userDetailsService.findUserByName(anyString())).thenReturn(loginUser);
        when(roomService.getRoomOrganizer(room)).thenReturn(organizer);

        mockMvc.perform(get("/room/show/" + idRoom)
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("room-show"))
                .andExpect(model().attributeExists("loginUser"))
                .andExpect(model().attributeExists("roomAndOrganizerWrapper"));

        verify(roomService, times(1)).getRoomById(idRoom);
        verify(userDetailsService, times(1)).findUserByName(anyString());
        verify(roomService, times(1)).getRoomOrganizer(room);
    }

    @Test
    void testJoinRoomFormNoInvite() throws Exception {
        int idRoom = 1;
        UserInfoDTO currentUser = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setIdOrganizer(3);
        currentUser.setIdUserInfo(2);

        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);
        when(inviteService.checkInvite(currentUser.getTelegram(), idRoom)).thenReturn(false);
        when(roomService.getRoomOrganizer(roomDTO)).thenReturn(new UserInfoDTO());
        when(roomService.getRoomsWhereUserJoin(currentUser.getIdUserInfo())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/room/{id}/join", idRoom)
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("join-room-page"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "У вас нет приглашения!!!"));
    }

    @Test
    void testJoinRoomFormNoInviteButOrganizer() throws Exception {
        int idRoom = 1;
        UserInfoDTO currentUser = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();


        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);
        when(inviteService.checkInvite(currentUser.getTelegram(), idRoom)).thenReturn(false);
        when(roomService.getRoomOrganizer(roomDTO)).thenReturn(new UserInfoDTO());
        when(roomService.getRoomsWhereUserJoin(currentUser.getIdUserInfo())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/room/{id}/join", idRoom)
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("join-room-page"))
                .andExpect(model().attributeDoesNotExist("errorMessage"))
                .andExpect(model().attributeExists("roomDto"))
                .andExpect(model().attributeExists("idRoom"))
                .andExpect(model().attributeExists("wishDto"));
    }

    @Test
    void testJoinRoomFormInvite() throws Exception {
        int idRoom = 1;
        UserInfoDTO currentUser = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();

        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);
        when(inviteService.checkInvite(currentUser.getTelegram(), idRoom)).thenReturn(true);
        when(roomService.getRoomOrganizer(roomDTO)).thenReturn(new UserInfoDTO());
        when(roomService.getRoomsWhereUserJoin(currentUser.getIdUserInfo())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/room/{id}/join", idRoom)
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("join-room-page"))
                .andExpect(model().attributeDoesNotExist("errorMessage"))
                .andExpect(model().attributeExists("roomDto"))
                .andExpect(model().attributeExists("idRoom"))
                .andExpect(model().attributeExists("wishDto"));

        verify(userDetailsService).findUserByName(anyString());
        verify(roomService, times(2)).getRoomById(idRoom);
        verify(inviteService).checkInvite(currentUser.getTelegram(), idRoom);
        verify(roomService).getRoomsWhereUserJoin(currentUser.getIdUserInfo());
        verify(roomService, times(2)).getRoomById(idRoom);
    }

    @Test
    void testJoinInRoomPost() throws Exception {
        int idRoom = 1;
        WishDTO wishDto = new WishDTO();
        UserInfoDTO currentUser = new UserInfoDTO(1, "username", "pasw", "telegram");
        RoomDTO roomDTO = new RoomDTO();
        WishDTO savedWish = new WishDTO();
        UserRoleWishRoomDTO userRoleWishRoomDTO = new UserRoleWishRoomDTO();
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRole(Role.ORGANIZER);

        when(wishService.create(wishDto)).thenReturn(savedWish);
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);
        when(userDetailsService.findUserByName(anyString())).thenReturn(currentUser);
        when(roomService.getRoomOrganizer(roomDTO)).thenReturn(currentUser);
        when(roleService.getRoleById(Role.ORGANIZER.getId())).thenReturn(roleDTO);
        when(userRoleWishRoomService.create(refEq(userRoleWishRoomDTO))).thenReturn(userRoleWishRoomDTO);
        when(roomService.readAll()).thenReturn(Collections.singletonList(roomDTO));

        mockMvc.perform(post("/room/{id}/join", idRoom)
                        .with(user("username").password("password"))
                        .flashAttr("wishDto", wishDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room/show/" + idRoom));

        verify(wishService, times(1)).create(wishDto);
        verify(roomService, times(1)).getRoomById(idRoom);
        verify(userDetailsService, times(1)).findUserByName(anyString());
        verify(roomService, times(2)).getRoomOrganizer(roomDTO);
        verify(roleService, times(1)).getRoleById(Role.ORGANIZER.getId());
        verify(userRoleWishRoomService, times(1)).create(any(UserRoleWishRoomDTO.class));
        verify(roomService, times(1)).readAll();
    }

    @Test
    void testGetUsersAndRoles() throws Exception {
        int idRoom = 1;
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        UserInfoDTO organizer = new UserInfoDTO();
        List<Object[]> usersAndRoles = new ArrayList<>();
        roomDTO.setIdRoom(idRoom);

        when(userDetailsService.findUserByName(anyString())).thenReturn(userInfoDTO);
        when(roomService.getRoomById(anyInt())).thenReturn(roomDTO);
        when(roomService.getRoomOrganizer(any(RoomDTO.class))).thenReturn(organizer);
        when(roomService.getUsersAndRolesByRoomId(anyInt())).thenReturn(usersAndRoles);

        mockMvc.perform(get("/room/{idRoom}/users-and-roles", idRoom)
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("user-in-room"));

        verify(userDetailsService).findUserByName(anyString());
        verify(roomService).getRoomById(anyInt());
        verify(roomService).getRoomOrganizer(any(RoomDTO.class));
        verify(roomService).getUsersAndRolesByRoomId(anyInt());

    }

    @Test
    void testGetRoomWhereJoin() throws Exception {
        int idUser = 1;
        String username = "username";
        int page = 0;
        int size = 5;
        Page<RoomDTO> roomDTOPage = new PageImpl<>(Collections.emptyList());
        UserInfoDTO userInfoDTO = new UserInfoDTO(idUser, "username", "password", "telegram");

        when(userDetailsService.findUserByName(username)).thenReturn(userInfoDTO);
        when(roomService.getRoomsWhereUserJoin(idUser, PageRequest.of(page, size))).thenReturn(roomDTOPage);

        mockMvc.perform(get("/room/show/participant")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .with(user("username").password("password")))
                .andExpect(status().isOk())
                .andExpect(view().name("room-list"));
        verify(userDetailsService).findUserByName(username);
        verify(roomService).getRoomsWhereUserJoin(idUser, PageRequest.of(page, size));
    }

    @Test
    void testDeleteUserFromRoom() throws Exception {
        int idRoom = 1;
        int idUser = 1;
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        UserInfoDTO loginUser = new UserInfoDTO();
        roomDTO.setIdRoom(idRoom);
        userInfoDTO.setIdUserInfo(idUser);

        when(userDetailsService.findUserByName(anyString())).thenReturn(userInfoDTO, loginUser);
        when(roomService.getRoomByName(anyString())).thenReturn(roomDTO);
        when(roomService.getRoomOrganizer(any(RoomDTO.class))).thenReturn(loginUser);

        mockMvc.perform(post("/room/{nameRoom}/users/{UserInfoName}", "testRoom", "testUser")
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room/1/users-and-roles"));

        verify(userDetailsService, times(2)).findUserByName(anyString());
        verify(roomService).getRoomByName(anyString());
        verify(roomService).getRoomOrganizer(any(RoomDTO.class));
        verify(userRoleWishRoomService).deleteUserFromRoom(idRoom, idUser);
    }

    @Test
    void testDeleteUserFromRoomNotOrganizer() throws Exception {
        int idRoom = 1;
        int idUser = 2;
        int idOrganizer = 1;
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        UserInfoDTO organizer = new UserInfoDTO();
        RoomDTO roomDTO = new RoomDTO();
        UserInfoDTO loginUser = new UserInfoDTO();
        roomDTO.setIdRoom(idRoom);
        roomDTO.setIdOrganizer(idOrganizer);
        userInfoDTO.setIdUserInfo(idUser);
        organizer.setIdUserInfo(idOrganizer);

        when(userDetailsService.findUserByName(anyString())).thenReturn(userInfoDTO, loginUser);
        when(roomService.getRoomByName(anyString())).thenReturn(roomDTO);
        when(roomService.getRoomOrganizer(any(RoomDTO.class))).thenReturn(organizer);

        mockMvc.perform(post("/room/{nameRoom}/users/{UserInfoName}", "testRoom", "testUser")
                        .with(user("username").password("password")))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room/1/users-and-roles"))
                .andExpect(flash().attribute("errorMessage", "Не можешь удалять"));

        verify(userDetailsService, times(2)).findUserByName(anyString());
        verify(roomService).getRoomByName(anyString());
        verify(roomService).getRoomOrganizer(any(RoomDTO.class));
        verify(userRoleWishRoomService, times(0)).deleteUserFromRoom(idRoom, idUser);
    }
}
