package org.example.secretsanta.dto;

import org.example.secretsanta.model.enums.Role;

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
}
