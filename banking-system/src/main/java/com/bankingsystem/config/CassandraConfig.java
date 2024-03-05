package com.bankingsystem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.List;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
    private final String contactPoints;
    private final String keyspaceName;
    private final SchemaAction schemaAction;

    public CassandraConfig(
            @Value("${spring.cassandra.contact-points}") String contactPoints,
            @Value("${spring.cassandra.keyspace-name}") String keyspaceName,
            @Value("${spring.cassandra.schema-action}") SchemaAction schemaAction
    ) {
        this.contactPoints = contactPoints;
        this.keyspaceName = keyspaceName;
        this.schemaAction = schemaAction;
    }

    @Override
    protected String getKeyspaceName() {
        return keyspaceName;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return schemaAction;
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        return List.of(
                CreateKeyspaceSpecification
                        .createKeyspace(keyspaceName)
                        .ifNotExists()
                        .with(KeyspaceOption.DURABLE_WRITES, true)
        );
    }
}
