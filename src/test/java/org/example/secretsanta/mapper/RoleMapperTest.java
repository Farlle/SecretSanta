package org.example.secretsanta.mapper;

import org.example.secretsanta.dto.RoleDTO;
import org.example.secretsanta.model.entity.RoleEntity;
import org.example.secretsanta.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoleMapperTest {

    @InjectMocks
    private RoleMapper roleMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testToRoleDTO() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(2);
        roleEntity.setRole(Role.ORGANIZER);

        RoleDTO roleDTO = roleMapper.toRoleDTO(roleEntity);

        assertEquals(roleEntity.getIdRole(), roleDTO.getIdRole());
        assertEquals(roleEntity.getRole(), roleDTO.getRole());
    }

    @Test
    void testToRoleEntity() {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setIdRole(2);
        roleDTO.setRole(Role.ORGANIZER);

        RoleEntity roleEntity = roleMapper.toRoleEntity(roleDTO);

        assertEquals(roleDTO.getIdRole(), roleEntity.getIdRole());
        assertEquals(roleDTO.getRole(), roleEntity.getRole());
    }

    @Test
    void testToRoleDTOList() {
        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setIdRole(1);
        RoleEntity roleEntity2 = new RoleEntity();
        roleEntity2.setIdRole(2);
        List<RoleEntity> roleEntitiesList = Arrays.asList(roleEntity1, roleEntity2);

        List<RoleDTO> roleDTOList = roleMapper.toRoleDTOList(roleEntitiesList);

        assertEquals(roleEntitiesList.size(), roleDTOList.size());
        assertEquals(roleEntitiesList.get(0).getIdRole(), roleDTOList.get(0).getIdRole());
        assertEquals(roleEntitiesList.get(1).getIdRole(), roleDTOList.get(1).getIdRole());
    }

    @Test
    void testToRoleDTO_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            roleMapper.toRoleDTO(null);
        });
    }

    @Test
    void testToRoleEntity_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            roleMapper.toRoleEntity(null);
        });
    }

    @Test
    void testToRoleDTOList_Null() {
        assertThrows(IllegalArgumentException.class, () -> {
            roleMapper.toRoleDTOList(null);
        });
    }
}