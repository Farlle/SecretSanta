package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.mapper.InviteMapper;
import org.example.secretsanta.mapper.RoomMapper;
import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.enums.Status;
import org.example.secretsanta.repository.InviteRepository;
import org.example.secretsanta.repository.RoomRepository;
import org.example.secretsanta.service.serviceinterface.RoomService;
import org.example.secretsanta.service.serviceinterface.TelegramService;
import org.example.secretsanta.utils.UrlUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
@Rollback
class InviteServiceImplTest {

    @Mock
    private InviteRepository inviteRepository;
    @Mock
    private TelegramServiceImpl telegramService;
    @Mock
    private RoomServiceImpl roomService;
    @Mock
    private UserInfoTelegramChatsServiceImpl userInfoTelegramChatsService;
    @Mock
    private UserInfoServiceImpl userInfoService;
    @Mock
    private RoomRepository roomRepository;

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
        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setTelegram(telegram);
        inviteDTO.setIdInvite(1);
        inviteDTO.setStatus(Status.SENT);
        inviteDTO.setText(generatedText);
        inviteDTO.setUserInfoDTO(new UserInfoDTO());


        when(userInfoTelegramChatsService.getIdChatByTelegramName(telegram)).thenReturn(123L);
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);
        when(inviteService.create(inviteDTO)).thenReturn(inviteDTO);

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
    @Disabled
    public void testUserAcceptInvite() {
        // Arrange
        String telegram = "qwe";
        int idRoom = 1;
        RoomDTO roomDTO = new RoomDTO(1, "room", 1,
                new Date(864000L), new Date(764000L), "asd");
        String generatedTextInvite = "Тебя пригласили в комнату room присоединяйся по ссылке http://localhost:8080/room/1/join";
        InviteDTO inviteDTO1 = new InviteDTO();
        inviteDTO1.setIdInvite(1);
        inviteDTO1.setStatus(Status.SENT);
        InviteDTO inviteDTO2 = new InviteDTO();
        inviteDTO2.setIdInvite(2);
        inviteDTO2.setStatus(Status.SENT);
        List<InviteDTO> inviteDTOs = Arrays.asList(inviteDTO1, inviteDTO2);
        InviteDTO inviteDTOUpdate = inviteDTO1;
        inviteDTOUpdate.setStatus(Status.ACCEPTED);




        when(inviteRepository.getAllInviteUsersInRoom(telegram, generatedTextInvite)).thenReturn(InviteMapper
                .toInviteEntityList(inviteDTOs));
        when(inviteRepository.findById(1)).thenReturn(Optional.of(InviteMapper.toInviteEntity(inviteDTO1)));
        when(inviteRepository.findById(2)).thenReturn(Optional.of(InviteMapper.toInviteEntity(inviteDTO2)));
        when(roomService.getRoomById(idRoom)).thenReturn(roomDTO);
        when(inviteService.update(1, inviteDTO1)).thenReturn(inviteDTOUpdate);
        inviteService.UserAcceptInvite(telegram, idRoom);

        for (InviteDTO inviteDTO : inviteDTOs) {
            assertEquals(Status.ACCEPTED, inviteDTO.getStatus());
            verify(inviteService).update(inviteDTO.getIdInvite(), inviteDTO);
        }
    }

}