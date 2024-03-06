package com.yantar.bankingsystem.repository;

import com.yantar.bankingsystem.entity.SessionEntity;
import com.yantar.bankingsystem.model.SessionId;

import java.util.List;
import java.util.Optional;

public interface SessionRepository {
    Optional<SessionEntity> findById            (SessionId sessionId);
    List<SessionEntity>     findAllByUserId     (Long userId);
    boolean                 existsByUserId      (Long userId);

    SessionEntity   insert              (SessionEntity session);
    boolean         deleteById          (SessionId sessionId);
    boolean         deleteAllByUserId   (Long userId);
}
