package pl.lodz.p.it.rstrzalkowski.syllabus;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration
public class TestContainersConfig {

    @Bean
    public GenericContainer<?> axonServerContainer() {
        GenericContainer<?> container = new GenericContainer<>("axoniq/axonserver:2024.0.2")
                .withEnv("AXONIQ_AXONSERVER_DEVMODE_ENABLED", "true")
                .withExposedPorts(8024, 8124, 8224);
        container.start();
        System.setProperty("axon.axonserver.servers", container.getHost() + ":" + container.getMappedPort(8124));
        return container;
    }

    @Bean
    public PostgreSQLContainer<?> postgresLookupContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("lookup_db")
                .withUsername("lookup_user")
                .withPassword("lookup_password")
                .withExposedPorts(5432);
        container.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
        return container;
    }

    @Bean
    public PostgreSQLContainer<?> postgresReadContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15")
                .withDatabaseName("read_db")
                .withUsername("read_user")
                .withPassword("read_password")
                .withExposedPorts(5432);
        container.start();
        // Additional configuration if needed
        return container;
    }

    @Bean
    public GenericContainer<?> keycloakContainer() {
        GenericContainer<?> container = new GenericContainer<>("keycloak/keycloak:23.0.1")
                .withEnv("DB_VENDOR", "postgres")
                .withEnv("DB_ADDR", "postgres-keycloak")
                .withEnv("DB_DATABASE", "keycloak_db")
                .withEnv("DB_USER", "keycloak_user")
                .withEnv("DB_PASSWORD", "keycloak_password")
                .withEnv("KEYCLOAK_ADMIN", "admin")
                .withEnv("KEYCLOAK_ADMIN_PASSWORD", "admin")
                .withExposedPorts(8080)
                .withCommand("start-dev --import-realm");
        container.start();
        return container;
    }
}