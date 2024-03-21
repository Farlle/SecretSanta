package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.dto.RoomDTO;
import org.example.secretsanta.mapper.InviteMapper;
import org.example.secretsanta.mapper.UserInfoMapper;
import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.enums.Status;
import org.example.secretsanta.repository.InviteRepository;
import org.example.secretsanta.service.serviceinterface.InviteService;
import org.example.secretsanta.utils.UrlUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class InviteServiceImpl implements InviteService {

    private final InviteRepository inviteRepository;
    private final RoomServiceImpl roomServiceImpl;
    private final TelegramServiceImpl telegramService;
    private final UserInfoTelegramChatsServiceImpl userInfoTelegramChatsServiceImpl;
    private final String HOST = "http://localhost:8080/room/";

    public InviteServiceImpl(InviteRepository inviteRepository, RoomServiceImpl roomServiceImpl,
                             TelegramServiceImpl telegramService,
                             UserInfoTelegramChatsServiceImpl userInfoTelegramChatsServiceImpl) {
        this.inviteRepository = inviteRepository;
        this.roomServiceImpl = roomServiceImpl;
        this.telegramService = telegramService;
        this.userInfoTelegramChatsServiceImpl = userInfoTelegramChatsServiceImpl;
    }

    @Override
    public InviteDTO create(InviteDTO dto) {
        InviteEntity invite = new InviteEntity();
        invite.setTelegram(dto.getTelegram());
        invite.setStatus(dto.getStatus());
        invite.setUserInfo(UserInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));
        invite.setText(dto.getText());

        return InviteMapper.toInviteDTO(inviteRepository.save(invite));
    }

    @Override
    public List<InviteDTO> readAll() {
        return InviteMapper.toInviteDTOList(inviteRepository.findAll());
    }

    @Override
    public InviteDTO update(int id, InviteDTO dto) {
        InviteEntity inviteEntity = inviteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invite not found with id: " + id));

        inviteEntity.setUserInfo(UserInfoMapper.toUserInfoEntity(dto.getUserInfoDTO()));
        inviteEntity.setStatus(dto.getStatus());
        inviteEntity.setTelegram(dto.getTelegram());
        inviteEntity.setText(dto.getText());

        return InviteMapper.toInviteDTO(inviteRepository.save(inviteEntity));
    }

    @Override
    public void delete(int id) {
        inviteRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void sendInvite(int idRoom, InviteDTO inviteDTO) {

        inviteDTO.setStatus(Status.SENT);
        inviteDTO.setText(generatedTextInvite(idRoom));
        telegramService.sendMessage(userInfoTelegramChatsServiceImpl
                .getIdChatByTelegramName(inviteDTO.getTelegram()), inviteDTO.getText());
        create(inviteDTO);
    }

    @Override
    public boolean checkInvite(String telegram, int idRoom) {
        List<InviteDTO> allUsersInvite = getAllUsersInvite(telegram);
        for (InviteDTO inviteDTO : allUsersInvite) {
            if (idRoom == Integer.parseInt(UrlUtils.extractRoomNumberFromUrl(inviteDTO.getText()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<InviteDTO> getAllUsersInvite(String telegram) {
        return InviteMapper.toInviteDTOList(inviteRepository.getAllUsersInvite(telegram));
    }

    @Override
    public void UserAcceptInvite(String telegram, int idRoom) {
        List<InviteDTO> inviteDTOs = InviteMapper.toInviteDTOList(
                inviteRepository.getAllInviteUsersInRoom(telegram, generatedTextInvite(idRoom)));
        for (InviteDTO inviteDTO : inviteDTOs) {
            inviteDTO.setStatus(Status.ACCEPTED);
            update(inviteDTO.getIdInvite(), inviteDTO);
        }
    }

    @Override
    public String generatedTextInvite(int idRoom) {
        RoomDTO roomDTO = roomServiceImpl.getRoomById(idRoom);
        return "Тебя пригласили в комнату " + roomDTO.getName()
                + " присоединяйся по ссылке " + HOST + roomDTO.getIdRoom() + "/join";

    }

}
