package org.example.secretsanta.service.serviceinterface;

import org.example.secretsanta.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    RoleDTO create(RoleDTO dto);
    List<RoleDTO> readAll();
    RoleDTO update(int id, RoleDTO dto);
    void delete(int id);
    RoleDTO getRoleById(int id);

}
