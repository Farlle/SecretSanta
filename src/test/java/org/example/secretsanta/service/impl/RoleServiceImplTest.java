package org.example.secretsanta.service.impl;

import org.example.secretsanta.dto.RoleDTO;
import org.example.secretsanta.mapper.RoleMapper;
import org.example.secretsanta.model.entity.RoleEntity;
import org.example.secretsanta.model.enums.Role;
import org.example.secretsanta.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRole(Role.PARTICIPANT);

        RoleDTO savedRoleDTO = new RoleDTO();
        savedRoleDTO.setRole(Role.PARTICIPANT);

        when(roleRepository.save(any(RoleEntity.class))).thenReturn(roleEntity);

        RoleDTO result = roleService.create(savedRoleDTO);

        assertEquals(savedRoleDTO, result);
    }

    @Test
    public void testReadAll() {
        RoleEntity roleEntity1 = new RoleEntity();
        roleEntity1.setIdRole(1);
        roleEntity1.setRole(Role.PARTICIPANT);
        RoleEntity roleEntity2 = new RoleEntity();
        roleEntity2.setIdRole(2);
        roleEntity2.setRole(Role.ORGANIZER);
        when(roleRepository.findAll()).thenReturn(Arrays.asList(roleEntity1, roleEntity2));

        List<RoleDTO> roleDTOs = roleService.readAll();

        assertEquals(2, roleDTOs.size());
        assertEquals(RoleMapper.toRoleDTO(roleEntity1), roleDTOs.get(0));
        assertEquals(RoleMapper.toRoleDTO(roleEntity2), roleDTOs.get(1));
    }

    @Test
    public void testUpdate() {
        int id = 1;
        RoleDTO dto = new RoleDTO();
        dto.setRole(Role.PARTICIPANT);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(id);
        roleEntity.setRole(dto.getRole());
        when(roleRepository.findById(id)).thenReturn(Optional.of(roleEntity));
        when(roleRepository.save(roleEntity)).thenReturn(roleEntity);

        RoleDTO updatedRoleDTO = roleService.update(id, dto);

        assertEquals(RoleMapper.toRoleDTO(roleEntity), updatedRoleDTO);
    }


    @Test
    void testGetRoleById() {
        int id = 1;
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(id);
        when(roleRepository.findById(id)).thenReturn(Optional.of(roleEntity));

        RoleDTO roleDTO = roleService.getRoleById(id);

        assertEquals(RoleMapper.toRoleDTO(roleEntity), roleDTO);
    }

    @Test
    public void testDelete() {
        int id = 1;

        roleService.delete(id);

        verify(roleRepository).deleteById(id);
    }


}
