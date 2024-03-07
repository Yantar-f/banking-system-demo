package com.yantar.bankingsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.lang.NonNull;

import java.util.List;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
    private final String contactPoints;
    private final String keyspaceName;
    private final SchemaAction schemaAction;

    public CassandraConfig(@Value("${spring.cassandra.contact-points}") String contactPoints,
                           @Value("${spring.cassandra.keyspace-name}") String keyspaceName,
                           @Value("${spring.cassandra.schema-action}") SchemaAction schemaAction) {

        this.contactPoints = contactPoints;
        this.keyspaceName = keyspaceName;
        this.schemaAction = schemaAction;
    }

    @NonNull
    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @NonNull
    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @NonNull
    @Override
    public SchemaAction getSchemaAction() {
        return schemaAction;
    }

    @NonNull
    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"com.yantar"};
    }

    @NonNull
    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return List.of(
                CreateKeyspaceSpecification
                        .createKeyspace(keyspaceName)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES)
                        .withSimpleReplication()
        );
    }
}
