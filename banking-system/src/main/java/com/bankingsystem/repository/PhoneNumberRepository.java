package com.bankingsystem.repository;

import com.bankingsystem.entity.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, Long> {
    boolean existsByNumber(String number);
}