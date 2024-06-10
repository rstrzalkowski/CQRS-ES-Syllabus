package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.users;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.boundedcontext.AbstractAggregate;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.AssignRoleCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.CheckStudentExistenceCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.CheckTeacherExistenceCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.RegisterCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UnassignRoleCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UpdateDescriptionCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UpdateProfileImageCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.persistence.user.UserUniqueValuesService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RoleAssignedToUserEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.RoleUnassignedFromUserEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserCreatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserDescriptionUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.event.UserProfileImageUpdatedEvent;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.RoleAlreadyAssignedCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.RoleNotCurrentlyAssignedCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.UserIsNotStudentCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user.UserIsNotTeacherCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.KeycloakService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.CreateUserDto;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Aggregate(snapshotTriggerDefinition = "syllabusSnapshotTriggerDefinition")
@NoArgsConstructor
@AllArgsConstructor
@WriteApplicationBean
public class User extends AbstractAggregate {

    private String email;

    private String firstName;

    private String lastName;

    private String personalId;

    private String description;

    private List<String> roles = new ArrayList<>();


    @CommandHandler
    public User(RegisterCommand cmd, KeycloakService keycloakService, UserUniqueValuesService userUniqueValuesService) {
        userUniqueValuesService.lockPersonalIdAndEmail(cmd.getPersonalId(), cmd.getEmail());

        String userId;
        try {
            userId = keycloakService.createUser(new CreateUserDto(
                    cmd.getEmail(),
                    cmd.getFirstName(),
                    cmd.getLastName(),
                    cmd.getPassword())
            );
        } catch (SyllabusCommandExecutionException scee) {
            userUniqueValuesService.releaseValues(cmd.getPersonalId());
            throw scee;
        }

        userUniqueValuesService.updateAggregateId(cmd.getEmail(), UUID.fromString(userId));

        AggregateLifecycle.apply(new UserCreatedEvent(
                UUID.fromString(userId),
                cmd.getEmail(),
                cmd.getFirstName(),
                cmd.getLastName(),
                cmd.getPersonalId(),
                "https://cdn-icons-png.flaticon.com/512/149/149071.png",
                "",
                Timestamp.from(Instant.now()))
        );
    }

    @CommandHandler
    public void on(AssignRoleCommand cmd, KeycloakService keycloakService) {
        if (roles.contains(cmd.getRole())) {
            throw new RoleAlreadyAssignedCommandExecutionException();
        }

        keycloakService.assignRole(getId(), cmd.getRole());

        AggregateLifecycle.apply(new RoleAssignedToUserEvent(
                getId(),
                cmd.getRole())
        );
    }

    @CommandHandler
    public void on(UnassignRoleCommand cmd, KeycloakService keycloakService) {
        if (!roles.contains(cmd.getRole())) {
            throw new RoleNotCurrentlyAssignedCommandExecutionException();
        }

        keycloakService.unassignRole(getId(), cmd.getRole());

        AggregateLifecycle.apply(new RoleUnassignedFromUserEvent(
                getId(),
                cmd.getRole())
        );
    }

    @CommandHandler
    public void on(UpdateDescriptionCommand cmd) {
        AggregateLifecycle.apply(new UserDescriptionUpdatedEvent(
                getId(),
                cmd.getDescription())
        );
    }

    @CommandHandler
    public void on(UpdateProfileImageCommand cmd) {
        AggregateLifecycle.apply(new UserProfileImageUpdatedEvent(
                getId(),
                cmd.getImageUrl())
        );
    }

    @CommandHandler
    public void on(CheckStudentExistenceCommand cmd) {
        if (!roles.contains("SYLLABUS_STUDENT")) {
            throw new UserIsNotStudentCommandExecutionException();
        }
    }

    @CommandHandler
    public void on(CheckTeacherExistenceCommand cmd) {
        if (!roles.contains("SYLLABUS_TEACHER")) {
            throw new UserIsNotTeacherCommandExecutionException();
        }
    }

    @EventSourcingHandler
    public void on(UserCreatedEvent event) {
        setId(event.getId());
        this.email = event.getEmail();
        this.firstName = event.getFirstName();
        this.lastName = event.getLastName();
        this.personalId = event.getPersonalId();
    }

    @EventSourcingHandler
    public void on(RoleAssignedToUserEvent event) {
        this.roles.add(event.getRole());
    }

    @EventSourcingHandler
    public void on(RoleUnassignedFromUserEvent event) {
        this.roles.remove(event.getRole());
    }

    @EventSourcingHandler
    public void on(UserDescriptionUpdatedEvent event) {
        this.description = event.getDescription();
    }
}
