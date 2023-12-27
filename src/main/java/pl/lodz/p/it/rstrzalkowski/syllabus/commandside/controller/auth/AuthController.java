package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.AssignRoleCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.LoginCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.UnassignRoleCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusCommandExecutionException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.KeycloakService;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.JwtResponse;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@RestController
@RequiredArgsConstructor
@WriteApplicationBean
public class AuthController {

    private final KeycloakService keycloakService;

    @PostMapping("/authorize")
    public JwtResponse login(@Valid @RequestBody LoginCommand command) {
        try {
            return keycloakService.login(
                command.getUsername(),
                command.getPassword(),
                "external");
        } catch (WebClientResponseException wcre) {
            throw new SyllabusCommandExecutionException(wcre.getStatusCode());
        }
    }

    @PostMapping("/assign-role")
    @Secured("ADMIN")
    public void assignRole(@Valid @RequestBody AssignRoleCommand command) {
        try {
            keycloakService.assignRole(command.getUserId(), command.getRole());
        } catch (WebClientResponseException wcre) {
            throw new SyllabusCommandExecutionException(wcre.getStatusCode());
        }
    }

    @PostMapping("/unassign-role")
    @Secured("ADMIN")
    public void assignRole(@Valid @RequestBody UnassignRoleCommand command) {
        try {
            keycloakService.unassignRole(command.getUserId(), command.getRole());
        } catch (WebClientResponseException wcre) {
            throw new SyllabusCommandExecutionException(wcre.getStatusCode());
        }
    }
}
