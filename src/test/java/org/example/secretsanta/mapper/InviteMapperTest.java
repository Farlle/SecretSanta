package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.enums.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class InviteMapperTest {
    @Mock
    private UserInfoMapper userInfoMapper;

    @InjectMocks
    private InviteMapper inviteMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testToInviteDTOTest() {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        when(userInfoMapper.toUserInfoDTO(Mockito.any())).thenReturn(userInfoDTO);

        InviteEntity inviteEntity = new InviteEntity();
        inviteEntity.setIdInvite(1);
        inviteEntity.setStatus(Status.SENT);
        inviteEntity.setTelegram("Test Telegram");
        inviteEntity.setText("Test Text");

        InviteDTO inviteDTO = inviteMapper.toInviteDTO(inviteEntity);

        assertEquals(inviteEntity.getIdInvite(), inviteDTO.getIdInvite());
        assertEquals(inviteEntity.getStatus(), inviteDTO.getStatus());
        assertEquals(inviteEntity.getTelegram(), inviteDTO.getTelegram());
        assertEquals(userInfoDTO, inviteDTO.getUserInfoDTO());
        assertEquals(inviteEntity.getText(), inviteDTO.getText());
    }

    @Test
    void testToInviteEntity() {
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        Mockito.when(userInfoMapper.toUserInfoEntity(Mockito.any())).thenReturn(userInfoEntity);

        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setIdInvite(1);
        inviteDTO.setStatus(Status.SENT);
        inviteDTO.setTelegram("Test Telegram");
        inviteDTO.setText("Test Text");

        InviteEntity inviteEntity = inviteMapper.toInviteEntity(inviteDTO);

       assertEquals(inviteDTO.getIdInvite(), inviteEntity.getIdInvite());
       assertEquals(inviteDTO.getStatus(), inviteEntity.getStatus());
       assertEquals(inviteDTO.getTelegram(), inviteEntity.getTelegram());
       assertEquals(userInfoEntity, inviteEntity.getUserInfo());
       assertEquals(inviteDTO.getText(), inviteEntity.getText());
    }

    @Test
    void testToInviteDTOList() {
        InviteEntity inviteEntity1 = new InviteEntity();
        inviteEntity1.setIdInvite(1);
        InviteEntity inviteEntity2 = new InviteEntity();
        inviteEntity2.setIdInvite(2);
        List<InviteEntity> inviteEntitiesList = Arrays.asList(inviteEntity1, inviteEntity2);

        List<InviteDTO> inviteDTOList = inviteMapper.toInviteDTOList(inviteEntitiesList);

        assertEquals(inviteEntitiesList.size(), inviteDTOList.size());
        assertEquals(inviteEntitiesList.get(0).getIdInvite(), inviteDTOList.get(0).getIdInvite());
        assertEquals(inviteEntitiesList.get(1).getIdInvite(), inviteDTOList.get(1).getIdInvite());
    }

    @Test
    void testToInviteEntityList() {
        InviteDTO inviteDTO1 = new InviteDTO();
        inviteDTO1.setIdInvite(1);
        InviteDTO inviteDTO2 = new InviteDTO();
        inviteDTO2.setIdInvite(2);
        List<InviteDTO> inviteDTOList = Arrays.asList(inviteDTO1, inviteDTO2);

        List<InviteEntity> inviteEntityList = inviteMapper.toInviteEntityList(inviteDTOList);

        assertEquals(inviteDTOList.size(), inviteEntityList.size());
        assertEquals(inviteDTOList.get(0).getIdInvite(), inviteEntityList.get(0).getIdInvite());
        assertEquals(inviteDTOList.get(1).getIdInvite(), inviteEntityList.get(1).getIdInvite());
    }

    @Test
    void testToInviteDTO_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            inviteMapper.toInviteDTO(null);
        });
    }

    @Test
    void testToInviteEntity_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            inviteMapper.toInviteEntity(null);
        });
    }

    @Test
    void testToInviteDTOList_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            inviteMapper.toInviteDTOList(null);
        });
    }

    @Test
    void testToInviteEntityList_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            inviteMapper.toInviteEntityList(null);
        });
    }

}