package org.example.secretsanta.service.serviceinterface;

import org.example.secretsanta.dto.InviteDTO;

import java.util.List;

public interface InviteService {

    InviteDTO create(InviteDTO dto);
    List<InviteDTO> readAll();
    InviteDTO update(int id, InviteDTO dto);
    void delete(int id);
}
