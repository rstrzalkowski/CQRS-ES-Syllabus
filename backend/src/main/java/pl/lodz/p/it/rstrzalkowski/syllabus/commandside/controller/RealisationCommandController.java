package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.ArchiveRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.CreateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.realisation.UpdateRealisationCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@RestController
@RequiredArgsConstructor
@RequestMapping("/realisations")
@WriteApplicationBean
public class RealisationCommandController {

    private final CommandGateway commandGateway;

    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "DIRECTOR", "ADMIN"})

    @PostMapping
    public void createRealisation(@Valid @RequestBody CreateRealisationCommand command) {
        commandGateway.sendAndWait(command);
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "DIRECTOR", "ADMIN"})

    @PutMapping
    public void updateRealisation(@Valid @RequestBody UpdateRealisationCommand command) {
        commandGateway.sendAndWait(command);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"TEACHER", "DIRECTOR", "ADMIN"})

    @DeleteMapping
    public void archiveById(@Valid @RequestBody ArchiveRealisationCommand command) {
        commandGateway.sendAndWait(command);
    }
}
