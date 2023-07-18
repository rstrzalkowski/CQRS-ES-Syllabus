package pl.lodz.p.it.rstrzalkowski.syllabus.controller.command;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.command.CreateSubjectCommand;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
public class SubjectCommandController {
    private final CommandGateway commandGateway;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createSubject(@Valid @RequestBody CreateSubjectCommand command) {
        commandGateway.send(command);
    }
}
