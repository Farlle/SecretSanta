package ru.sberschool.secretsanta.mapper;

import ru.sberschool.secretsanta.dto.InviteDTO;
import ru.sberschool.secretsanta.model.entity.InviteEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InviteMapper {

    private final UserInfoMapper userInfoMapper;

    public InviteMapper(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    public InviteDTO toInviteDTO(InviteEntity inviteEntity) {
        InviteDTO inviteDTO = new InviteDTO();

        if (inviteEntity == null) {
            throw new IllegalArgumentException("InviteEntity cannot be null");
        }

        inviteDTO.setIdInvite(inviteEntity.getIdInvite());
        inviteDTO.setStatus(inviteEntity.getStatus());
        inviteDTO.setTelegram(inviteEntity.getTelegram());
        inviteDTO.setUserInfoDTO(userInfoMapper.toUserInfoDTO(inviteEntity.getUserInfo()));
        inviteDTO.setText(inviteEntity.getText());

        return inviteDTO;
    }

    public InviteEntity toInviteEntity(InviteDTO inviteDTO) {
        InviteEntity inviteEntity = new InviteEntity();

        if (inviteDTO == null) {
            throw new IllegalArgumentException("InviteDTO cannot be null");
        }

        inviteEntity.setIdInvite(inviteDTO.getIdInvite());
        inviteEntity.setStatus(inviteDTO.getStatus());
        inviteEntity.setTelegram(inviteDTO.getTelegram());
        inviteEntity.setUserInfo(userInfoMapper.toUserInfoEntity(inviteDTO.getUserInfoDTO()));
        inviteEntity.setText(inviteDTO.getText());

        return inviteEntity;
    }

    public List<InviteDTO> toInviteDTOList(List<InviteEntity> inviteEntitiesList) {
        if (inviteEntitiesList == null) {
            throw new IllegalArgumentException("InviteEntityList cannot be null");
        }

        return inviteEntitiesList.stream()
                .map(this::toInviteDTO)
                .collect(Collectors.toList());

    }

    public List<InviteEntity> toInviteEntityList(List<InviteDTO> inveteDTOList) {
        if (inveteDTOList == null) {
            throw new IllegalArgumentException("InviteDTOList cannot be null");
        }

        return inveteDTOList.stream()
                .map(this::toInviteEntity)
                .collect(Collectors.toList());

    }

}
