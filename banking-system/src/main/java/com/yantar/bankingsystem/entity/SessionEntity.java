package com.yantar.bankingsystem.entity;

import com.yantar.bankingsystem.config.Role;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.data.cassandra.core.cql.Ordering.DESCENDING;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.LIST;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.TEXT;
import static org.springframework.data.cassandra.core.mapping.CassandraType.Name.TIMESTAMP;

@Table("sessions")
public class SessionEntity implements Serializable {
    @PrimaryKeyColumn(name = "user_id", type = PARTITIONED)
    private Long userId;

    @PrimaryKeyColumn(name = "session_key", type = CLUSTERED, ordinal = 0)
    private String sessionKey;

    @PrimaryKeyColumn(name = "created_at", type = CLUSTERED, ordering = DESCENDING, ordinal = 1)
    @CassandraType(type = TIMESTAMP)
    private Instant refreshedAt;

    @Column("createdAt")
    @CassandraType(type = TIMESTAMP)
    private Instant createdAt;

    @Column
    @CassandraType(typeArguments = TEXT, type = LIST)
    private Set<String> roles;

    @Column("access_token")
    private String accessToken;

    @Column("refresh_token")
    private String refreshToken;

    @Column("token_id")
    private String tokenId;

    protected SessionEntity() {}

    public SessionEntity(Long userId,
                         String sessionKey,
                         Instant createdAt,
                         Set<Role> roles,
                         String accessToken,
                         String refreshToken,
                         String tokenId) {

        this.userId = userId;
        this.sessionKey = sessionKey;
        this.refreshedAt = createdAt;
        this.createdAt = createdAt;
        this.roles = roles.stream().map(Role::name).collect(Collectors.toSet());
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenId = tokenId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public Set<Role> getRoles() {
        return roles.stream().map(Role::valueOf).collect(Collectors.toSet());
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles.stream().map(Role::name).collect(Collectors.toSet());
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public Instant getRefreshedAt() {
        return refreshedAt;
    }

    public void setRefreshedAt(Instant refreshedAt) {
        this.refreshedAt = refreshedAt;
    }

    @Override
    public int hashCode() {
        return  userId.hashCode() *
                sessionKey.hashCode() *
                createdAt.hashCode() *
                refreshedAt.hashCode() *
                roles.hashCode() *
                accessToken.hashCode() *
                refreshToken.hashCode() *
                tokenId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (! (obj instanceof SessionEntity sessionEntity)) return false;

        return  userId.equals(sessionEntity.userId) &&
                sessionKey.equals(sessionEntity.sessionKey) &&
                createdAt.equals(sessionEntity.createdAt) &&
                refreshedAt.equals(sessionEntity.refreshedAt) &&
                accessToken.equals(sessionEntity.accessToken) &&
                refreshToken.equals(sessionEntity.refreshToken) &&
                tokenId.equals(sessionEntity.tokenId) &&
                roles.size() == sessionEntity.roles.size() &&
                roles.containsAll(sessionEntity.roles) &&
                sessionEntity.roles.containsAll(roles);
    }
}
