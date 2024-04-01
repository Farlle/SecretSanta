package ru.sberschool.secretsanta.service.impl;

import ru.sberschool.secretsanta.dto.RoleDTO;
import ru.sberschool.secretsanta.mapper.RoleMapper;
import ru.sberschool.secretsanta.model.entity.RoleEntity;
import ru.sberschool.secretsanta.repository.RoleRepository;
import ru.sberschool.secretsanta.service.RoleService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Сервис для работы с ролями
 */
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    /**
     * Метод для создания роли
     *
     * @param dto Объект который надо создать
     * @return Созданный объект
     */
    @Override
    public RoleDTO create(RoleDTO dto) {
        RoleEntity role = new RoleEntity();
        role.setRole(dto.getRole());

        return roleMapper.toRoleDTO(roleRepository.save(role));
    }

    /**
     * Метод для получения всех ролей
     *
     * @return Список всех ролей
     */
    @Override
    public List<RoleDTO> readAll() {
        return roleMapper.toRoleDTOList(roleRepository.findAll());
    }

    /**
     * Метод для обновления роли
     *
     * @param id Идентификатор роли
     * @param dto Объект для обновления
     * @return Обновленный объект
     */
    @Override
    public RoleDTO update(int id, RoleDTO dto) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));

        role.setRole(dto.getRole());

        return roleMapper.toRoleDTO(roleRepository.save(role));
    }

    /**
     * Метод для удаления роли
     *
     * @param id Идентификатор роли
     */
    @Override
    public void delete(int id) {
        roleRepository.deleteById(id);
    }

    /**
     * Метод получения роли по id
     *
     * @param id Идентификатор роли
     * @return Объект роли
     */
    @Override
    public RoleDTO getRoleById(int id) {
        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
        return roleMapper.toRoleDTO(role);
    }


}
