package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.InviteMapper;
import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.enums.Status;
import org.example.secretsanta.repository.InviteRepository;
import org.example.secretsanta.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class InviteServiceImplTest {

    @Mock
    private InviteRepository inviteRepository;
    @Mock
    private TelegramServiceImpl telegramService;
    @Mock
    private RoomServiceImpl roomService;
    @Mock
    private UserInfoTelegramChatsServiceImpl userInfoTelegramChatsService;

    @InjectMocks
    private InviteServiceImpl inviteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void create() {
        InviteDTO dto = new InviteDTO();

        when(inviteRepository.save(any(InviteEntity.class))).thenReturn(InviteMapper.toInviteEntity(dto));
        InviteDTO result = inviteService.create(dto);

        verify(inviteRepository, times(1)).save(any(InviteEntity.class));
        assertEquals(dto, result);
    }

    @Test
    void readAll() {
        List<InviteEntity> inviteEntities = Arrays.asList(new InviteEntity(), new InviteEntity());
        when(inviteRepository.findAll()).thenReturn(inviteEntities);

        List<InviteDTO> result = inviteService.readAll();

        verify(inviteRepository, times(1)).findAll();
        assertEquals(InviteMapper.toInviteDTOList(inviteEntities), result);
    }

    @Test
    void update() {
        int id = 1;
        InviteDTO dto = new InviteDTO();

        InviteEntity inviteEntity = new InviteEntity();

        when(inviteRepository.findById(id)).thenReturn(Optional.of(inviteEntity));
        when(inviteRepository.save(any(InviteEntity.class))).thenReturn(inviteEntity);

        InviteDTO result = inviteService.update(id, dto);

        verify(inviteRepository, times(1)).findById(id);
        verify(inviteRepository, times(1)).save(any(InviteEntity.class));
        assertEquals(InviteMapper.toInviteDTO(inviteEntity), result);
    }

    @Test
    void delete() {
        int id = 1;
        inviteService.delete(id);
        verify(inviteRepository, times(1)).deleteById(id);
    }

    @Test
    void getAllUsersInvite() {
        String telegram = "testTelegram";
        List<InviteEntity> inviteEntities = Arrays.asList(new InviteEntity(), new InviteEntity());
        when(inviteRepository.getAllUsersInvite(telegram)).thenReturn(inviteEntities);

        List<InviteDTO> inviteDTOs = inviteService.getAllUsersInvite(telegram);

        verify(inviteRepository, times(1)).getAllUsersInvite(telegram);
        assertEquals(inviteEntities.size(), inviteDTOs.size());
    }

    @Test
    public void testSendInvite() {
        int idRoom = 1;
        RoomDTO roomDTO = new RoomDTO(1, "room", 1, new Date(864000L), new Date(764000L), "asd");
        String telegram = "test_user";
        String generatedText = "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/1/join";
        InviteDTO inviteDTO = new InviteDTO(1, new UserInfoDTO(), "qwe", Status.SENT, generatedText);

        when(userInfoTelegramChatsService.getIdChatByTelegramName(telegram)).thenReturn(123L);
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);
        when(inviteService.create(inviteDTO)).thenReturn(inviteDTO);
        when(inviteRepository.save(any(InviteEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        inviteService.sendInvite(idRoom, inviteDTO);

        assertEquals(Status.SENT, inviteDTO.getStatus());
        assertEquals(generatedText, inviteDTO.getText());
    }

    @Test
    public void testCheckInvite_InviteExists() {
        InviteDTO inviteDTO = new InviteDTO(1, new UserInfoDTO(), "qwe", Status.SENT,
                "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/1/join");
        InviteDTO inviteDTO1 = new InviteDTO(1, new UserInfoDTO(), "qwe", Status.SENT,
                "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/2/join");
        List<InviteDTO> invites = Arrays.asList(inviteDTO, inviteDTO1);

        when(inviteService.getAllUsersInvite("qwe")).thenReturn(invites);

        assertFalse(inviteService.checkInvite("telegram", 2));
    }

    @Test
    public void testCheckInvite_InviteDoesNotExist() {
        InviteDTO inviteDTO = new InviteDTO(1, new UserInfoDTO(), "qwe", Status.SENT,
                "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/1/join");
        InviteDTO inviteDTO1 = new InviteDTO(1, new UserInfoDTO(), "qwe", Status.SENT,
                "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/2/join");
        List<InviteDTO> invites = Arrays.asList(inviteDTO, inviteDTO1);

        when(inviteService.getAllUsersInvite("qwe")).thenReturn(invites);

        assertFalse(inviteService.checkInvite("telegram", 789));
    }

    @Test
    public void testUserAcceptInvite() {
        String telegram = "qwe";
        int idRoom = 1;
        RoomDTO roomDTO = new RoomDTO(1, "room", 1,
                new Date(864000L), new Date(764000L), "asd");
        String generatedTextInvite = "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/1/join";
        InviteEntity inviteEntity1 = new InviteEntity();
        inviteEntity1.setIdInvite(1);
        inviteEntity1.setStatus(Status.SENT);
        InviteEntity inviteEntity2 = new InviteEntity();
        inviteEntity2.setIdInvite(2);
        inviteEntity2.setStatus(Status.SENT);
        List<InviteEntity> inviteEntities = Arrays.asList(inviteEntity1, inviteEntity2);

        when(inviteRepository.getAllInviteUsersInRoom(telegram, generatedTextInvite)).thenReturn(inviteEntities);
        when(inviteRepository.findById(1)).thenReturn(Optional.of(inviteEntity1));
        when(inviteRepository.findById(2)).thenReturn(Optional.of(inviteEntity2));
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);

        inviteService.UserAcceptInvite(telegram, idRoom);

        verify(inviteRepository, times(2)).save(any(InviteEntity.class));
        assertEquals(Status.ACCEPTED, inviteEntity1.getStatus());
        assertEquals(Status.ACCEPTED, inviteEntity2.getStatus());
    }

}