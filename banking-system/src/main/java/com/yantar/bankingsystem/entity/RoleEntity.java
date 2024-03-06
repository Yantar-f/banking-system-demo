package com.yantar.bankingsystem.entity;

import com.yantar.bankingsystem.config.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    @Column(name = "designation")
    @Enumerated(EnumType.STRING)
    private Role designation;

    protected RoleEntity() {}

    public RoleEntity(Role designation) {
        this.designation = designation;
    }

    public Role getDesignation() {
        return designation;
    }

    public void setDesignation(Role designation) {
        this.designation = designation;
    }
}
