package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.grade.CreateGradeCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.grade.UpdateGradeCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
@WriteApplicationBean
public class GradeCommandController {

    private final CommandGateway commandGateway;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createGrade(@Valid @RequestBody CreateGradeCommand command) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        command.setTeacherId(userInfo.getId());
        commandGateway.sendAndWait(command);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public void updateGrade(@Valid @RequestBody UpdateGradeCommand command) {
        commandGateway.sendAndWait(command);
    }
}
