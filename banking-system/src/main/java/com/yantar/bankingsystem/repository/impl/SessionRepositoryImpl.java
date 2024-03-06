package com.yantar.bankingsystem.repository.impl;

import com.yantar.bankingsystem.config.RefreshTokenConfig;
import com.yantar.bankingsystem.entity.SessionEntity;
import com.yantar.bankingsystem.model.SessionId;
import com.yantar.bankingsystem.repository.SessionRepository;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.InsertOptions;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.springframework.data.cassandra.core.query.Criteria.where;
import static org.springframework.data.cassandra.core.query.Query.query;

@Repository
public class SessionRepositoryImpl implements SessionRepository {
    private final CassandraOperations operations;
    private final InsertOptions insertOptions;

    public SessionRepositoryImpl(CassandraOperations operations, RefreshTokenConfig refreshTokenConfig) {
        this.operations = operations;

        this.insertOptions = InsertOptions.builder()
                .ttl((int) MILLISECONDS.toSeconds(refreshTokenConfig.getExpirationMs()) + 10)
                .build();
    }

    @Override
    public Optional<SessionEntity> findById(SessionId sessionID) {
        return findByID(sessionID.getUserId(), sessionID.getSessionKey());
    }

    @Override
    public List<SessionEntity> findAllByUserId(Long userID) {
        return operations.select(query(where("user_id").is(userID)), SessionEntity.class);
    }

    @Override
    public SessionEntity insert(SessionEntity session) {
        return operations.insert(session, insertOptions).getEntity();
    }

    @Override
    public boolean deleteById(SessionId sessionId) {
        return operations.delete(query(List.of(
                where("user_id").is(sessionId.getUserId()),
                where("session_key").is(sessionId.getSessionKey())
        )), SessionEntity.class);
    }

    @Override
    public boolean deleteAllByUserId(Long userId) {
        return operations.delete(query(where("user_id").is(userId)), SessionEntity.class);
    }

    @Override
    public boolean existsByUserId(Long userID) {
        return operations.exists(query(where("user_id").is(userID)), SessionEntity.class);
    }

    private Optional<SessionEntity> findByID(Long userId, String sessionID) {
        return Optional.ofNullable(operations.selectOne(query(List.of(
                where("user_id").is(userId),
                where("session_key").is(sessionID)
        )).limit(1), SessionEntity.class));
    }
}
