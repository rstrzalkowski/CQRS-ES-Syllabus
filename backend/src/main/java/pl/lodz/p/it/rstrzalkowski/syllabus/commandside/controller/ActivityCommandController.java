package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.ArchiveActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.CreateActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.activity.UpdateActivityCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
@WriteApplicationBean
public class ActivityCommandController {

    private final CommandGateway commandGateway;

    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "DIRECTOR", "ADMIN"})
    @PostMapping
    public UUID createActivity(@Valid @RequestBody CreateActivityCommand command) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        command.setTeacherId(userInfo.getId());
        command.setActivityId(UUID.randomUUID());
        commandGateway.sendAndWait(command);
        return command.getActivityId();
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "DIRECTOR", "ADMIN"})
    @PutMapping
    public void updateActivity(@Valid @RequestBody UpdateActivityCommand command) {
        commandGateway.sendAndWait(command);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"TEACHER", "DIRECTOR", "ADMIN"})
    @DeleteMapping
    public void archiveById(@Valid @RequestBody ArchiveActivityCommand command) {
        commandGateway.sendAndWait(command);
    }
}
