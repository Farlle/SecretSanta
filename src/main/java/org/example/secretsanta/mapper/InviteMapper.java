package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.model.entity.InviteEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InviteMapper {

    public static InviteDTO toInviteDTO(InviteEntity inviteEntity) {
        if (inviteEntity == null) {
            return null;
        }

        InviteDTO inviteDTO = new InviteDTO();
        inviteDTO.setIdInvite(inviteEntity.getIdInvite());
        inviteDTO.setStatus(inviteEntity.getStatus());
        inviteDTO.setTelegram(inviteEntity.getTelegram());
        inviteDTO.setUserInfoEntity(inviteEntity.getUserInfo());

        return inviteDTO;
    }

    public static InviteEntity toInviteEntity(InviteDTO inviteDTO) {
        if (inviteDTO == null) {
            return null;
        }

        InviteEntity inviteEntity = new InviteEntity();
        inviteEntity.setIdInvite(inviteDTO.getIdInvite());
        inviteEntity.setStatus(inviteDTO.getStatus());
        inviteEntity.setTelegram(inviteDTO.getTelegram());
        inviteEntity.setUserInfo(inviteDTO.getUserInfoEntity());

        return inviteEntity;
    }

    public static List<InviteDTO> toInviteDTOList(List<InviteEntity> inviteEntitiesList) {
        if (inviteEntitiesList == null) {
            return null;
        }

        return inviteEntitiesList.stream()
                .map(InviteMapper::toInviteDTO)
                .collect(Collectors.toList());

    }

}
