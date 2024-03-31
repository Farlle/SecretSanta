package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.UserInfoDTO;
import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.model.enums.Status;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InviteMapperTest {

    @Test
    void testToInviteDTOTest() {
        InviteEntity inviteEntity = new InviteEntity();
        inviteEntity.setIdInvite(1);
        inviteEntity.setStatus(Status.SENT);
        inviteEntity.setTelegram("telegram");
        inviteEntity.setUserInfo(new UserInfoEntity());
        inviteEntity.setText("text");

        InviteDTO inviteDTO = InviteMapper.toInviteDTO(inviteEntity);

        assertEquals(inviteEntity.getIdInvite(), inviteDTO.getIdInvite());
        assertEquals(inviteEntity.getStatus(), inviteDTO.getStatus());
        assertEquals(inviteEntity.getTelegram(), inviteDTO.getTelegram());
        assertEquals(inviteEntity.getUserInfo(), UserInfoMapper.toUserInfoEntity(inviteDTO.getUserInfoDTO()));
        assertEquals(inviteEntity.getText(), inviteDTO.getText());
    }

    @Test
    void testToInviteEntity() {
        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setIdInvite(1);
        inviteDTO.setStatus(Status.SENT);
        inviteDTO.setTelegram("telegram");
        inviteDTO.setUserInfoDTO(new UserInfoDTO());
        inviteDTO.setText("text");

        InviteEntity inviteEntity = InviteMapper.toInviteEntity(inviteDTO);

        assertEquals(inviteDTO.getIdInvite(), inviteEntity.getIdInvite());
        assertEquals(inviteDTO.getStatus(), inviteEntity.getStatus());
        assertEquals(inviteDTO.getTelegram(), inviteEntity.getTelegram());
        assertEquals(inviteDTO.getUserInfoDTO(), UserInfoMapper.toUserInfoDTO(inviteEntity.getUserInfo()));
        assertEquals(inviteDTO.getText(), inviteEntity.getText());
    }

    @Test
    void testToInviteDTOList() {
        InviteEntity inviteEntity1 = new InviteEntity();
        inviteEntity1.setIdInvite(1);
        InviteEntity inviteEntity2 = new InviteEntity();
        inviteEntity2.setIdInvite(2);
        List<InviteEntity> inviteEntitiesList = Arrays.asList(inviteEntity1, inviteEntity2);

        List<InviteDTO> inviteDTOList = InviteMapper.toInviteDTOList(inviteEntitiesList);

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

        List<InviteEntity> inviteEntityList = InviteMapper.toInviteEntityList(inviteDTOList);

        assertEquals(inviteDTOList.size(), inviteEntityList.size());
        assertEquals(inviteDTOList.get(0).getIdInvite(), inviteEntityList.get(0).getIdInvite());
        assertEquals(inviteDTOList.get(1).getIdInvite(), inviteEntityList.get(1).getIdInvite());
    }

    @Test
    void testToInviteDTO_Null() {
        InviteDTO inviteDTO = InviteMapper.toInviteDTO(null);

        assertNotNull(inviteDTO);
    }

    @Test
    void testToInviteEntity_Null() {
        InviteEntity inviteEntity = InviteMapper.toInviteEntity(null);

        assertNotNull(inviteEntity);
    }

    @Test
    void testToInviteDTOList_Null() {
        List<InviteDTO> inviteDTOList = InviteMapper.toInviteDTOList(null);

        assertNotNull(inviteDTOList);
    }

    @Test
    void testToInviteEntityList_Null() {
        List<InviteEntity> inviteEntityList = InviteMapper.toInviteEntityList(null);

        assertNotNull(inviteEntityList);
    }

}