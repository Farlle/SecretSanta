package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.mapper.InviteMapper;
import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.enums.Status;
import org.example.secretsanta.repository.InviteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Transactional
@Rollback
class InviteServiceImplTest {

    @Mock
    private InviteRepository inviteRepository;

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

}