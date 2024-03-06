package com.yantar.bankingsystem.repository;

import com.yantar.bankingsystem.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    boolean existsByAddress(String address);
    int countByUser_Id(Long userId);
    void deleteByAddressAndUser_Id(String address, Long userId);
}
