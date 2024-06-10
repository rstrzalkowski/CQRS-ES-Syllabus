package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.AssignRoleCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.RegisterCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UnassignRoleCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UpdateDescriptionCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UpdateProfileImageCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.util.ImageService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.subject.SubjectImageUpdateCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@WriteApplicationBean
public class UserCommandController {

    private final CommandGateway commandGateway;
    private final ImageService imageService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void register(@Valid @RequestBody RegisterCommand command) {
        commandGateway.sendAndWait(command);
    }

    @PostMapping("/assign-role")
    @Secured("ADMIN")
    public void assignRole(@Valid @RequestBody AssignRoleCommand command) {
        commandGateway.sendAndWait(command);
    }

    @PostMapping("/unassign-role")
    @Secured("ADMIN")
    public void assignRole(@Valid @RequestBody UnassignRoleCommand command) {
        commandGateway.sendAndWait(command);
    }

    @PutMapping("/me/description")
    @Secured({"STUDENT", "TEACHER", "DIRECTOR", "ADMIN"})
    public void updateOwnDescription(@Valid @RequestBody UpdateDescriptionCommand command) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        command.setUserId(userInfo.getId());
        commandGateway.sendAndWait(command);
    }

    @PutMapping("/me/image")
    @Secured({"STUDENT", "TEACHER", "DIRECTOR", "ADMIN"})
    public void changeImage(@Valid @RequestParam("image") MultipartFile image) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            String imageUrl = imageService.saveImage(image);
            commandGateway.sendAndWait(new UpdateProfileImageCommand(userInfo.getId(), imageUrl));
        } catch (IOException e) {
            throw new SubjectImageUpdateCommandExecutionException();
        }
    }
}
