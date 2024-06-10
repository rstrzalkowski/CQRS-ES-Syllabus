package pl.lodz.p.it.rstrzalkowski.syllabus;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.users.User;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.AssignRoleCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.CheckStudentExistenceCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.CheckTeacherExistenceCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.RegisterCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UnassignRoleCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UpdateDescriptionCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UpdateProfileImageCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.mock.MockKeycloakService;
import pl.lodz.p.it.rstrzalkowski.syllabus.mock.MockUserUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RoleAssignedToUserEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RoleUnassignedFromUserEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserDescriptionUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserProfileImageUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.UserIsNotStudentCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.UserIsNotTeacherCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.KeycloakService;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import static org.axonframework.test.matchers.Matchers.exactSequenceOf;
import static org.axonframework.test.matchers.Matchers.matches;
import static org.axonframework.test.matchers.Matchers.payloadsMatching;

public class UserAggregateTest {

    private AggregateTestFixture<User> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(User.class);
        KeycloakService keycloakService = new MockKeycloakService(null);
        UserUniqueValuesService userUniqueValuesService = new MockUserUniqueValuesService(null);
        fixture.registerInjectableResource(keycloakService);
        fixture.registerInjectableResource(userUniqueValuesService);
    }

    @Test
    void testRegisterCommand() {
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String personalId = "12345";
        String password = "password";

        fixture.givenNoPriorActivity()
                .when(new RegisterCommand(firstName, lastName, email, personalId, password))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof UserCreatedEvent uce) {
                                return Objects.equals(uce.getEmail(), email) &&
                                        Objects.equals(uce.getFirstName(), firstName) &&
                                        Objects.equals(uce.getLastName(), lastName) &&
                                        Objects.equals(uce.getPersonalId(), personalId);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testAssignRoleCommand() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String personalId = "12345";
        String role = "SYLLABUS_STUDENT";

        fixture.given(new UserCreatedEvent(userId, email, firstName, lastName, personalId, "https://cdn-icons-png.flaticon.com/512/149/149071.png", "", Timestamp.from(Instant.now())))
                .when(new AssignRoleCommand(userId.toString(), role))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof RoleAssignedToUserEvent raue) {
                                return Objects.equals(raue.getUserId(), userId) &&
                                        Objects.equals(raue.getRole(), role);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testUnassignRoleCommand() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String personalId = "12345";
        String role = "SYLLABUS_DIRECTOR";

        fixture.given(
                        new UserCreatedEvent(userId, email, firstName, lastName, personalId, "https://cdn-icons-png.flaticon.com/512/149/149071.png", "", Timestamp.from(Instant.now())),
                        new RoleAssignedToUserEvent(userId, role)
                )
                .when(new UnassignRoleCommand(userId.toString(), role))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof RoleUnassignedFromUserEvent ruue) {
                                return Objects.equals(ruue.getUserId(), userId) &&
                                        Objects.equals(ruue.getRole(), role);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testUpdateDescriptionCommand() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String personalId = "12345";
        String description = "New description";

        fixture.given(new UserCreatedEvent(userId, email, firstName, lastName, personalId, "https://cdn-icons-png.flaticon.com/512/149/149071.png", "", Timestamp.from(Instant.now())))
                .when(new UpdateDescriptionCommand(userId, description))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof UserDescriptionUpdatedEvent udue) {
                                return Objects.equals(udue.getUserId(), userId) &&
                                        Objects.equals(udue.getDescription(), description);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testUpdateProfileImageCommand() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String personalId = "12345";
        String imageUrl = "https://new-image-url.com/image.png";

        fixture.given(new UserCreatedEvent(userId, email, firstName, lastName, personalId, "https://cdn-icons-png.flaticon.com/512/149/149071.png", "", Timestamp.from(Instant.now())))
                .when(new UpdateProfileImageCommand(userId, imageUrl))
                .expectSuccessfulHandlerExecution()
                .expectEventsMatching(payloadsMatching(exactSequenceOf(
                        matches(event -> {
                            if (event instanceof UserProfileImageUpdatedEvent upiue) {
                                return Objects.equals(upiue.getId(), userId) &&
                                        Objects.equals(upiue.getImageUrl(), imageUrl);
                            }
                            return false;
                        })
                )));
    }

    @Test
    void testCheckStudentExistenceCommand() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String personalId = "12345";
        String role = "SYLLABUS_STUDENT";

        fixture.given(new UserCreatedEvent(userId, email, firstName, lastName, personalId, "https://cdn-icons-png.flaticon.com/512/149/149071.png", "", Timestamp.from(Instant.now())))
                .when(new CheckStudentExistenceCommand(userId))
                .expectException(UserIsNotStudentCommandExecutionException.class);

        fixture.given(new UserCreatedEvent(userId, email, firstName, lastName, personalId, "https://cdn-icons-png.flaticon.com/512/149/149071.png", "", Timestamp.from(Instant.now())),
                        new RoleAssignedToUserEvent(userId, role))
                .when(new CheckStudentExistenceCommand(userId))
                .expectSuccessfulHandlerExecution();
    }

    @Test
    void testCheckTeacherExistenceCommand() {
        UUID userId = UUID.randomUUID();
        String email = "test@example.com";
        String firstName = "John";
        String lastName = "Doe";
        String personalId = "12345";
        String role = "SYLLABUS_TEACHER";

        fixture.given(new UserCreatedEvent(userId, email, firstName, lastName, personalId, "https://cdn-icons-png.flaticon.com/512/149/149071.png", "", Timestamp.from(Instant.now())))
                .when(new CheckTeacherExistenceCommand(userId))
                .expectException(UserIsNotTeacherCommandExecutionException.class);

        fixture.given(new UserCreatedEvent(userId, email, firstName, lastName, personalId, "https://cdn-icons-png.flaticon.com/512/149/149071.png", "", Timestamp.from(Instant.now())),
                        new RoleAssignedToUserEvent(userId, role))
                .when(new CheckTeacherExistenceCommand(userId))
                .expectSuccessfulHandlerExecution();
    }
}