package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.RoleDTO;
import org.example.secretsanta.model.entity.RoleEntity;
import org.example.secretsanta.model.enums.Role;
import org.example.secretsanta.repository.ResultRepository;
import org.example.secretsanta.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    RoleEntity role;
    RoleDTO roleDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        role = new RoleEntity();
        role.setIdRole(1);
        role.setRole(Role.PARTICIPANT);

        roleDTO = new RoleDTO();
        roleDTO.setIdRole(3);
        roleDTO.setRole(Role.PARTICIPANT);


    }

    @Test
    void readAll() {
        List<RoleEntity> roles = Arrays.asList(
                new RoleEntity(1, Role.PARTICIPANT),
                new RoleEntity(2, Role.ORGANIZER)
        );

        when(roleRepository.findAll()).thenReturn(roles);

        List<RoleDTO> roleDTOs = roleService.readAll();

        assertNotNull(roleDTOs);
        assertEquals(roles.size(), roleDTOs.size());
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getRoleById() {
        int id = 2;
        RoleEntity role = new RoleEntity();
        role.setIdRole(id);
        role.setRole(Role.ORGANIZER);

        when(roleRepository.findById(id)).thenReturn(Optional.of(role));

        RoleDTO roleDTO = roleService.getRoleById(id);

        assertNotNull(roleDTO);
        assertEquals(role.getRole(), roleDTO.getRole());
    }
}