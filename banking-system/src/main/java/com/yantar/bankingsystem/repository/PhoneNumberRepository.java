package com.yantar.bankingsystem.repository;

import com.yantar.bankingsystem.entity.PhoneNumberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumberEntity, Long> {
    boolean existsByNumber(String number);
}
