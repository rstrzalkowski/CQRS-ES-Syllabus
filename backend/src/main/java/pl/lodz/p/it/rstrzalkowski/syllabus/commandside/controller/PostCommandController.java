package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.ArchivePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.CreatePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.post.UpdatePostCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@WriteApplicationBean
public class PostCommandController {

    private final CommandGateway commandGateway;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createPost(@Valid @RequestBody CreatePostCommand command) {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        command.setTeacherId(userInfo.getId());
        commandGateway.sendAndWait(command);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping
    public void updatePost(@Valid @RequestBody UpdatePostCommand command) {
        commandGateway.sendAndWait(command);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping
    public void archiveById(@Valid @RequestBody ArchivePostCommand command) {
        commandGateway.sendAndWait(command);
    }
}
