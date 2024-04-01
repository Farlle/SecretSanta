package ru.sberschool.secretsanta.mapper;

import ru.sberschool.secretsanta.dto.RoleDTO;
import ru.sberschool.secretsanta.model.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public RoleDTO toRoleDTO(RoleEntity roleEntity) {
        RoleDTO roleDTO = new RoleDTO();

        if (roleEntity == null) {
            throw new IllegalArgumentException("RoleEntity cannot be null");
        }

        roleDTO.setIdRole(roleEntity.getIdRole());
        roleDTO.setRole(roleEntity.getRole());
        return roleDTO;
    }

    public RoleEntity toRoleEntity(RoleDTO roleDTO) {
        RoleEntity roleEntity = new RoleEntity();

        if (roleDTO == null) {
            throw new IllegalArgumentException("RoleDTO cannot be null");
        }

        roleEntity.setIdRole(roleDTO.getIdRole());
        roleEntity.setRole(roleDTO.getRole());
        return roleEntity;
    }

    public List<RoleDTO> toRoleDTOList(List<RoleEntity> roleEntitiesList) {
        if (roleEntitiesList == null) {
            throw new IllegalArgumentException("RoleEntity cannot be null");
        }

        return roleEntitiesList.stream()
                .map(this::toRoleDTO)
                .collect(Collectors.toList());

    }

}
