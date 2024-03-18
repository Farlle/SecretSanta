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
        inviteDTO.setUserInfoDTO(UserInfoMapper.toUserInfoDTO(inviteEntity.getUserInfo()));
        inviteDTO.setText(inviteEntity.getText());

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
        inviteEntity.setUserInfo(UserInfoMapper.toUserInfoEntity(inviteDTO.getUserInfoDTO()));
        inviteEntity.setText(inviteDTO.getText());

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

    public static List<InviteEntity> toInviteEntityList(List<InviteDTO> inveteDTOList) {
        if (inveteDTOList == null) {
            return null;
        }

        return inveteDTOList.stream()
                .map(InviteMapper::toInviteEntity)
                .collect(Collectors.toList());

    }

}
