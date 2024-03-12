package org.example.secretsanta.service;

import org.example.secretsanta.dto.RoleDTO;
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

    public RoleEntity create(RoleDTO dto) {
        RoleEntity role = new RoleEntity();
        role.setRole(dto.getRole());

        return roleRepository.save(role);
    }

    public List<RoleEntity> readAll() {
        return roleRepository.findAll();
    }

    public RoleEntity update(int id, RoleDTO dto) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        role.setRole(dto.getRole());

        return roleRepository.save(role);
    }

    public  void delete(int id){
        roleRepository.deleteById(id);
    }

    public RoleEntity getRoleById(int id){
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        return role;
    }


}
