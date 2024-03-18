package org.example.secretsanta.model.entity;

import org.example.secretsanta.model.enums.Role;
import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private int idRole;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public RoleEntity(int idRole, Role role) {
        this.idRole = idRole;
        this.role = role;
    }

    public RoleEntity() {
    }

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
        if (!(o instanceof RoleEntity)) return false;
        RoleEntity that = (RoleEntity) o;
        return idRole == that.idRole && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRole, role);
    }
}
