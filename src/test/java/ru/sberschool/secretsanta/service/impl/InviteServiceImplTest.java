package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.dto.InviteDTO;
import ru.sberschool.secretsanta.dto.RoomDTO;
import ru.sberschool.secretsanta.dto.UserInfoDTO;
import ru.sberschool.secretsanta.mapper.InviteMapper;
import ru.sberschool.secretsanta.mapper.UserInfoMapper;
import ru.sberschool.secretsanta.model.entity.InviteEntity;
import ru.sberschool.secretsanta.model.entity.UserInfoEntity;
import ru.sberschool.secretsanta.model.enums.Status;
import ru.sberschool.secretsanta.repository.InviteRepository;
import ru.sberschool.secretsanta.service.InviteService;
import ru.sberschool.secretsanta.service.RoomService;
import ru.sberschool.secretsanta.service.TelegramService;
import ru.sberschool.secretsanta.service.UserInfoTelegramChatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class InviteServiceImplTest {

    @Mock
    private InviteRepository inviteRepository;
    @Mock
    private TelegramService telegramService;
    @Mock
    private RoomService roomService;
    @Mock
    private UserInfoTelegramChatsService userInfoTelegramChatsService;
    @InjectMocks
    private InviteServiceImpl inviteService;
    @Mock
    private InviteMapper inviteMapper;
    @Mock
    private UserInfoMapper userInfoMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        InviteDTO dto = new InviteDTO();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        dto.setUserInfoDTO(userInfoDTO);
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        when(userInfoMapper.toUserInfoEntity(userInfoDTO)).thenReturn(userInfoEntity);
        InviteEntity savedInviteEntity = new InviteEntity();
        when(inviteRepository.save(any(InviteEntity.class))).thenReturn(savedInviteEntity);
        InviteDTO savedInviteDTO = new InviteDTO();
        when(inviteMapper.toInviteDTO(savedInviteEntity)).thenReturn(savedInviteDTO);

        InviteDTO result = inviteService.create(dto);

        verify(inviteRepository).save(any(InviteEntity.class));
        assertEquals(savedInviteDTO, result);
    }

    @Test
    void testReadAll() {
        List<InviteEntity> inviteEntities = Arrays.asList(new InviteEntity(), new InviteEntity());
        when(inviteRepository.findAll()).thenReturn(inviteEntities);

        List<InviteDTO> result = inviteService.readAll();

        verify(inviteRepository, times(1)).findAll();
        assertEquals(inviteMapper.toInviteDTOList(inviteEntities), result);
    }

    @Test
    void testUpdate() {
        int id = 1;
        InviteDTO dto = new InviteDTO();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        dto.setUserInfoDTO(userInfoDTO);
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        when(userInfoMapper.toUserInfoEntity(userInfoDTO)).thenReturn(userInfoEntity);
        InviteEntity existingInvite = new InviteEntity();
        when(inviteRepository.findById(id)).thenReturn(Optional.of(existingInvite));
        InviteEntity updatedInvite = new InviteEntity();
        when(inviteRepository.save(existingInvite)).thenReturn(updatedInvite);
        InviteDTO updatedInviteDTO = new InviteDTO();
        when(inviteMapper.toInviteDTO(updatedInvite)).thenReturn(updatedInviteDTO);

        InviteDTO result = inviteService.update(id, dto);

        verify(inviteRepository).findById(id);
        verify(inviteRepository).save(existingInvite);
        assertEquals(updatedInviteDTO, result);
    }

    @Test
    void testDelete() {
        int id = 1;
        inviteService.delete(id);
        verify(inviteRepository, times(1)).deleteById(id);
    }

    @Test
    void testGetAllUsersInvite() {
        String telegram = "testUser";
        List<InviteEntity> inviteEntities = Arrays.asList(new InviteEntity(), new InviteEntity());
        when(inviteRepository.getAllUsersInvite(telegram)).thenReturn(inviteEntities);
        List<InviteDTO> inviteDTOs = Arrays.asList(new InviteDTO(), new InviteDTO());
        when(inviteMapper.toInviteDTOList(inviteEntities)).thenReturn(inviteDTOs);

        List<InviteDTO> result = inviteService.getAllUsersInvite(telegram);

        verify(inviteRepository).getAllUsersInvite(telegram);
        assertEquals(inviteDTOs, result);
    }

    @Test
    void testSendInvite() {
        int idRoom = 1;
        String telegramName = "telegram";
        String expectedText = "Тебя пригласили в комнату roomName" +
                " присоединяйся по ссылке http://localhost:8080/room/1/join";
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("roomName");
        roomDTO.setIdRoom(idRoom);
        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setTelegram(telegramName);

        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);
        when(userInfoTelegramChatsService.getIdChatByTelegramName(telegramName)).thenReturn(1L);

        inviteService.sendInvite(idRoom, inviteDTO);

        verify(telegramService).sendMessage(1L, expectedText);

    }

    @Test
    void testCheckInvite_InviteExists() {
        InviteDTO inviteDTO = new InviteDTO(1, new UserInfoDTO(), "qwe", Status.SENT,
                "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/1/join");
        InviteDTO inviteDTO1 = new InviteDTO(2, new UserInfoDTO(), "qwe", Status.SENT,
                "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/2/join");
        List<InviteDTO> invites = Arrays.asList(inviteDTO, inviteDTO1);

        when(inviteService.getAllUsersInvite("qwe")).thenReturn(invites);

        assertTrue(inviteService.checkInvite("telegram", 2));
    }

    @Test
    void testCheckInvite_InviteDoesNotExist() {
        InviteDTO inviteDTO = new InviteDTO(1, new UserInfoDTO(), "qwe", Status.SENT,
                "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/1/join");
        InviteDTO inviteDTO1 = new InviteDTO(1, new UserInfoDTO(), "qwe", Status.SENT,
                "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/2/join");
        List<InviteDTO> invites = Arrays.asList(inviteDTO, inviteDTO1);

        when(inviteService.getAllUsersInvite("qwe")).thenReturn(invites);

        assertFalse(inviteService.checkInvite("telegram", 789));
    }

    @Test
    void testUserAcceptInvite() {
        String telegram = "testTelegram";
        int idRoom = 1;
        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setIdInvite(1);
        inviteDTO.setStatus(Status.SENT);
        InviteEntity inviteEntity = new InviteEntity();
        inviteEntity.setIdInvite(1);
        inviteEntity.setStatus(Status.SENT);
        List<InviteEntity> inviteEntities = Arrays.asList(inviteEntity);

        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setName("roomName");
        roomDTO.setIdRoom(idRoom);

        when(inviteRepository.getAllInviteUsersInRoom(telegram, "Тебя пригласили в комнату" +
                " roomName присоединяйся по ссылке http://localhost:8080/room/1/join")).thenReturn(inviteEntities);
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);
        when(inviteMapper.toInviteDTOList(inviteEntities)).thenReturn(Arrays.asList(inviteDTO));
        when(inviteRepository.findById(1)).thenReturn(Optional.of(inviteEntity));
        inviteService.userAcceptInvite(telegram, idRoom);

        verify(inviteRepository).getAllInviteUsersInRoom(telegram,"Тебя пригласили в комнату" +
                " roomName присоединяйся по ссылке http://localhost:8080/room/1/join");
        verify(inviteMapper).toInviteDTOList(inviteEntities);
        verify(inviteRepository, times(1)).save(any(InviteEntity.class));
        assertEquals(Status.ACCEPTED, inviteEntity.getStatus());
    }
    @Test
    public void testGeneratedTextInvite() {
        int idRoom = 1;
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setIdRoom(idRoom);
        roomDTO.setName("Test Room");
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);

        String textInvite = inviteService.generatedTextInvite(idRoom);

        verify(roomService).getRoomById(idRoom);
        String expectedText = "Тебя пригласили в комнату " + roomDTO.getName()
                + " присоединяйся по ссылке " + "http://localhost:8080/room/" + roomDTO.getIdRoom() + "/join";
        assert(textInvite.equals(expectedText));
    }
}
