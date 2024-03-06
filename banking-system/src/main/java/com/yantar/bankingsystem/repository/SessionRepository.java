package com.yantar.bankingsystem.repository;

import com.yantar.bankingsystem.entity.SessionEntity;
import com.yantar.bankingsystem.model.SessionId;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    Optional<SessionEntity> findByID            (SessionId sessionId);
    List<SessionEntity>     findAllByUserID     (Long userId);
    boolean                 existsByUserID      (Long userId);

    SessionEntity   insert              (SessionEntity session);
    boolean         deleteByID          (SessionId sessionId);
    boolean         deleteAllByUserID   (Long userId);
}
