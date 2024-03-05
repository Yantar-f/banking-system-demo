package com.bankingsystem.repository;

import com.bankingsystem.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<UserEntity, Long>,
                                        JpaSpecificationExecutor<UserEntity> {

    @EntityGraph(UserEntity.FULL_GRAPH)
    Page<UserEntity> findAll(Specification<UserEntity> spec, Pageable pageable);
}
