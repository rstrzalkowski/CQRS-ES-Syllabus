package pl.lodz.p.it.rstrzalkowski.syllabus;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@SpringBootTest
@ContextConfiguration(classes = {TestContainersConfig.class})
@DirtiesContext
public class ActivityControllerIntegrationTests {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private EventStore eventStore;

    static GenericContainer<?> axonServer;
    static PostgreSQLContainer<?> postgresLookup;
    static PostgreSQLContainer<?> postgresRead;
    static GenericContainer<?> keycloak;
    private MockMvc mockMvc;

    @BeforeAll
    static void startContainers() {
        // This will start containers and set necessary system properties
        axonServer = new TestContainersConfig().axonServerContainer();
        postgresLookup = new TestContainersConfig().postgresLookupContainer();
        postgresRead = new TestContainersConfig().postgresReadContainer();
        keycloak = new TestContainersConfig().keycloakContainer();
    }

    @AfterAll
    static void stopContainers() {
        axonServer.stop();
        postgresRead.stop();
        postgresLookup.stop();
        keycloak.stop();
    }


    @Test
    void testCreateActivity() throws Exception {
        System.out.println("started?");
    }
}