package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.controller.tmpAuth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.command.user.LoginCommand;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@RestController
@RequiredArgsConstructor
@WriteApplicationBean
public class AuthTempController {

    @PostMapping("/authorize")
    public JwtResponse login(@Valid @RequestBody LoginCommand command) {
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setToken("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJzdHVkZW50Iiwicm9sZSI6IlNUVURFTlQiLCJpYXQiOjE3MDI3NjIzNTAsImV4cCI6MTcwMjc2NTk1MH0.cCeQvwM_AHkD4TBenxWWIDrDG1_Jin-CyGKnZ4mzJRebWTUfQTc1YEhxENYsTLxByDY-BhQtErfSbQGxeI-OUQ");
        return jwtResponse;
    }
}
