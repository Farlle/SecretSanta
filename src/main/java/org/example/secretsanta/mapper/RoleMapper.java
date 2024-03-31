package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.RoleDTO;
import org.example.secretsanta.model.entity.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

    public static RoleDTO toRoleDTO(RoleEntity roleEntity) {
        RoleDTO roleDTO = new RoleDTO();

        if (roleEntity == null) {
            return roleDTO;
        }

        roleDTO.setIdRole(roleEntity.getIdRole());
        roleDTO.setRole(roleEntity.getRole());
        return roleDTO;
    }

    public static RoleEntity toRoleEntity(RoleDTO roleDTO) {
        RoleEntity roleEntity = new RoleEntity();

        if (roleDTO == null) {
            return roleEntity;
        }

        roleEntity.setIdRole(roleDTO.getIdRole());
        roleEntity.setRole(roleDTO.getRole());
        return roleEntity;
    }

    public static List<RoleDTO> toRoleDTOList(List<RoleEntity> roleEntitiesList) {
        if (roleEntitiesList == null) {
            return Collections.emptyList();
        }

        return roleEntitiesList.stream()
                .map(RoleMapper::toRoleDTO)
                .collect(Collectors.toList());

    }

}
