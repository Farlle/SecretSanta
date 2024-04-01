package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.dto.RoleDTO;
import ru.sberschool.secretsanta.mapper.RoleMapper;
import ru.sberschool.secretsanta.model.entity.RoleEntity;
import ru.sberschool.secretsanta.model.enums.Role;
import ru.sberschool.secretsanta.repository.RoleRepository;
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
    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreate() {
        RoleDTO dto = new RoleDTO();
        RoleEntity entity = new RoleEntity();
        when(roleRepository.save(any(RoleEntity.class))).thenReturn(entity);
        when(roleMapper.toRoleDTO(entity)).thenReturn(dto);

        RoleDTO result = roleService.create(dto);
        assertEquals(dto, result);
        verify(roleRepository).save(any(RoleEntity.class));
    }

    @Test
    void testReadAll() {
        List<RoleEntity> entities = Arrays.asList(new RoleEntity(), new RoleEntity());
        List<RoleDTO> dtos = Arrays.asList(new RoleDTO(), new RoleDTO());
        when(roleRepository.findAll()).thenReturn(entities);
        when(roleMapper.toRoleDTOList(entities)).thenReturn(dtos);

        List<RoleDTO> result = roleService.readAll();
        assertEquals(dtos, result);
    }

    @Test
    void testUpdate() {
        int id = 1;
        RoleDTO dto = new RoleDTO();
        dto.setRole(Role.PARTICIPANT);
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(id);
        roleEntity.setRole(dto.getRole());
        when(roleRepository.findById(id)).thenReturn(Optional.of(roleEntity));
        when(roleRepository.save(roleEntity)).thenReturn(roleEntity);

        RoleDTO updatedRoleDTO = roleService.update(id, dto);

        assertEquals(roleMapper.toRoleDTO(roleEntity), updatedRoleDTO);
    }


    @Test
    void testGetRoleById() {
        int id = 1;
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setIdRole(id);
        when(roleRepository.findById(id)).thenReturn(Optional.of(roleEntity));

        RoleDTO roleDTO = roleService.getRoleById(id);

        assertEquals(roleMapper.toRoleDTO(roleEntity), roleDTO);
    }

    @Test
    void testDelete() {
        int id = 1;

        roleService.delete(id);

        verify(roleRepository).deleteById(id);
    }


}
