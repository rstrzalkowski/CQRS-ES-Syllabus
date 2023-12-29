package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.ChangePasswordCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.LoginCommand;
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
        return keycloakService.login(
            command.getUsername(),
            command.getPassword(),
            "external");
    }

    @PutMapping("/me/password")
    @Secured({"STUDENT", "TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public void changePassword(@Valid @RequestBody ChangePasswordCommand command) {
        keycloakService.changePassword(command.getOldPassword(), command.getNewPassword());
    }
}
