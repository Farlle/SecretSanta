package org.example.secretsanta.service;

import org.example.secretsanta.dto.RoleDTO;
import org.example.secretsanta.mapper.RoleMapper;
import org.example.secretsanta.model.entity.RoleEntity;
import org.example.secretsanta.repository.RoleRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleDTO create(RoleDTO dto) {
        RoleEntity role = new RoleEntity();
        role.setRole(dto.getRole());

        return RoleMapper.toRoleDTO(roleRepository.save(role));
    }

    public List<RoleDTO> readAll() {
        return RoleMapper.toRoleDTOList(roleRepository.findAll());
    }

    public RoleDTO update(int id, RoleDTO dto) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        role.setRole(dto.getRole());

        return RoleMapper.toRoleDTO(roleRepository.save(role));
    }

    public void delete(int id) {
        roleRepository.deleteById(id);
    }

    public RoleDTO getRoleById(int id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        return RoleMapper.toRoleDTO(role);
    }


}
