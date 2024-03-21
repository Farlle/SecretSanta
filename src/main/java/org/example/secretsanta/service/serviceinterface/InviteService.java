package org.example.secretsanta.service.serviceinterface;

import org.example.secretsanta.dto.InviteDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface InviteService {

    InviteDTO create(InviteDTO dto);

    List<InviteDTO> readAll();

    InviteDTO update(int id, InviteDTO dto);

    void delete(int id);

    @Transactional
    void sendInvite(int idRoom, InviteDTO inviteDTO);

    boolean checkInvite(String telegram, int idRoom);

    List<InviteDTO> getAllUsersInvite(String telegram);

    void UserAcceptInvite(String telegram, int idRoom);

    String generatedTextInvite(int idRoom);

}
