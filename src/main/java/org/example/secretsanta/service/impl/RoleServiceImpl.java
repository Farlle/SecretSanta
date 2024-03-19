package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.RoleDTO;
import org.example.secretsanta.mapper.RoleMapper;
import org.example.secretsanta.model.entity.RoleEntity;
import org.example.secretsanta.repository.RoleRepository;
import org.example.secretsanta.service.serviceinterface.RoleService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDTO create(RoleDTO dto) {
        RoleEntity role = new RoleEntity();
        role.setRole(dto.getRole());

        return RoleMapper.toRoleDTO(roleRepository.save(role));
    }

    @Override
    public List<RoleDTO> readAll() {
        return RoleMapper.toRoleDTOList(roleRepository.findAll());
    }

    @Override
    public RoleDTO update(int id, RoleDTO dto) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        role.setRole(dto.getRole());

        return RoleMapper.toRoleDTO(roleRepository.save(role));
    }

    @Override
    public void delete(int id) {
        roleRepository.deleteById(id);
    }

    @Override
    public RoleDTO getRoleById(int id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        return RoleMapper.toRoleDTO(role);
    }


}
