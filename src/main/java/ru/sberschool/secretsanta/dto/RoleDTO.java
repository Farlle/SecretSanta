package ru.sberschool.secretsanta.dto;

import ru.sberschool.secretsanta.model.enums.Role;

import java.util.Objects;

public class RoleDTO {

    private int idRole;
    private Role role;

    public int getIdRole() {
        return idRole;
    }

    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleDTO)) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return idRole == roleDTO.idRole && role == roleDTO.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, role);
    }
}
