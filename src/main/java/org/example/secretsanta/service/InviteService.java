package org.example.secretsanta.service;

import org.example.secretsanta.dto.InviteDTO;
import org.example.secretsanta.model.entity.InviteEntity;
import org.example.secretsanta.model.entity.UserInfoEntity;
import org.example.secretsanta.repository.InviteRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class InviteService {

    private final InviteRepository inviteRepository;

    public InviteService(InviteRepository inviteRepository) {
        this.inviteRepository = inviteRepository;
    }

    public InviteEntity create(InviteDTO dto) {
        InviteEntity invite = new InviteEntity();
        invite.setTelegram(dto.getTelegram());
        invite.setStatus(dto.getStatus());
        invite.setUserInfo(dto.getUserInfoEntity());

        return inviteRepository.save(invite);
    }

    public List<InviteEntity> readAll() {
        return inviteRepository.findAll();
    }

    public InviteEntity update(int id, InviteDTO dto) {
        InviteEntity inviteEntity = inviteRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Invite not found with id: " + id));

        inviteEntity.setUserInfo(dto.getUserInfoEntity());
        inviteEntity.setStatus(dto.getStatus());
        inviteEntity.setTelegram(dto.getTelegram());

        return inviteRepository.save(inviteEntity);
    }

    public  void delete(int id){
        inviteRepository.deleteById(id);
    }

}
