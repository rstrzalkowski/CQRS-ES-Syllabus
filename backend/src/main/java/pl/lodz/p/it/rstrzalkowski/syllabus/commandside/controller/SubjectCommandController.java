package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.ArchiveSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.CreateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.UpdateSubjectCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.subject.UpdateSubjectImageCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.util.ImageService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.subject.SubjectImageUpdateCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/subjects")
@WriteApplicationBean
public class SubjectCommandController {

    private final CommandGateway commandGateway;
    private final ImageService imageService;

    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"TEACHER", "DIRECTOR", "ADMIN"})

    @PostMapping
    public UUID createSubject(@Valid @RequestBody CreateSubjectCommand command) {
        command.setSubjectId(UUID.randomUUID());
        commandGateway.sendAndWait(command);
        return command.getSubjectId();
    }

    @ResponseStatus(HttpStatus.OK)
    @Secured({"TEACHER", "DIRECTOR", "ADMIN"})

    @PutMapping
    public void updateSubject(@Valid @RequestBody UpdateSubjectCommand command) {
        commandGateway.sendAndWait(command);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/image")
    @Secured({"DIRECTOR", "ADMIN"})
    public void updateSubjectImage(@PathVariable("id") UUID id, @Valid @RequestParam("image") MultipartFile image) {
        try {
            String imageUrl = imageService.saveImage(image);
            commandGateway.sendAndWait(new UpdateSubjectImageCommand(id, imageUrl));
        } catch (IOException e) {
            throw new SubjectImageUpdateCommandExecutionException();
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured({"TEACHER", "DIRECTOR", "ADMIN"})

    @DeleteMapping
    public void archiveById(@Valid @RequestBody ArchiveSubjectCommand command) {
        commandGateway.sendAndWait(command);
    }
}
