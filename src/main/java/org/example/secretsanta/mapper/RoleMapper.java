package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.RoleDTO;
import org.example.secretsanta.model.entity.RoleEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RoleMapper {

    public static RoleDTO toRoleDTO(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setIdRole(roleEntity.getIdRole());
        roleDTO.setRole(roleEntity.getRole());
        return roleDTO;
    }

    public static RoleEntity toRoleEntity(RoleDTO roleDTO) {
        if (roleDTO == null) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(roleDTO.getIdRole());
        roleEntity.setRole(roleDTO.getRole());
        return roleEntity;
    }

    public static List<RoleDTO> toRoleDTOList(List<RoleEntity> roleEntitiesList) {
        if (roleEntitiesList == null) {
            return null;
        }

        return roleEntitiesList.stream()
                .map(RoleMapper::toRoleDTO)
                .collect(Collectors.toList());

    }

}
