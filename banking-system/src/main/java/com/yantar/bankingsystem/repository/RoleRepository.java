package com.yantar.bankingsystem.repository;

import com.yantar.bankingsystem.config.Role;
import com.yantar.bankingsystem.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Role> {
    boolean existsByDesignation(Role designation);
}