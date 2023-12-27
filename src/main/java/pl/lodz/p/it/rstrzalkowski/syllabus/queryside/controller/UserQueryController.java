package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.UserDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.UserQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetActiveTeachersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetLoggedInUserQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetNotSupervisingActiveTeachersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@ReadApplicationBean
public class UserQueryController {

    private final UserQueryHandler userQueryHandler;

    @GetMapping("/me")
    @Secured({"STUDENT", "TEACHER", "PARENT", "OFFICE", "DIRECTOR", "ADMIN"})
    public UserDTO getLoggedInUser() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userQueryHandler.handle(new GetLoggedInUserQuery(userInfo.getId()));
    }

    @GetMapping("/teachers")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllActiveTeachers(Pageable pageable) {
        return userQueryHandler.handle(new GetActiveTeachersQuery(pageable));
    }

    @GetMapping("/teachers/free")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public List<UserDTO> getAllNotSupervisingActiveTeachers(Pageable pageable) {
        return userQueryHandler.handle(new GetNotSupervisingActiveTeachersQuery(pageable));
    }
}
