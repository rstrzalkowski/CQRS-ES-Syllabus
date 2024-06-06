package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.UserDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler.UserQueryHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetActiveAdminsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetActiveDirectorsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetActiveOfficesQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetActiveStudentsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetActiveTeachersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetActiveUnassignedUsersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetActiveUsersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetArchivedAdminsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetArchivedDirectorsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetArchivedOfficesQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetArchivedStudentsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetArchivedTeachersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetArchivedUnassignedUsersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetArchivedUsersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetLoggedInUserQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetNotSupervisingActiveTeachersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetUnassignedStudentsQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetUserByIdQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetUserByKeywordQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.keycloak.dto.UserInfo;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@ReadApplicationBean
public class UserQueryController {

    private final UserQueryHandler userQueryHandler;

    @GetMapping("/me")
    @Secured({"STUDENT", "TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public UserDTO getLoggedInUser() {
        UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userQueryHandler.handle(new GetLoggedInUserQuery(userInfo.getId()));
    }

    @GetMapping("/{userId}")
    @Secured({"STUDENT", "TEACHER", "OFFICE", "DIRECTOR", "ADMIN"})
    public UserDTO getUserById(@PathVariable String userId) {
        return userQueryHandler.handle(new GetUserByIdQuery(UUID.fromString(userId)));
    }

    @GetMapping
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllActiveUsers(Pageable pageable) {
        return userQueryHandler.handle(new GetActiveUsersQuery(pageable));
    }

    @GetMapping("/teachers")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllActiveTeachers(Pageable pageable) {
        return userQueryHandler.handle(new GetActiveTeachersQuery(pageable));
    }

    @GetMapping("/students")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllActiveStudents(Pageable pageable) {
        return userQueryHandler.handle(new GetActiveStudentsQuery(pageable));
    }

    @GetMapping("/offices")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllActiveOffices(Pageable pageable) {
        return userQueryHandler.handle(new GetActiveOfficesQuery(pageable));
    }

    @GetMapping("/directors")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllActiveDirectors(Pageable pageable) {
        return userQueryHandler.handle(new GetActiveDirectorsQuery(pageable));
    }

    @GetMapping("/admins")
    @Secured("ADMIN")
    public Page<UserDTO> getAllActiveAdmins(Pageable pageable) {
        return userQueryHandler.handle(new GetActiveAdminsQuery(pageable));
    }

    @GetMapping("/unassigned")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllActiveUnassignedUsers() {
        return userQueryHandler.handle(new GetActiveUnassignedUsersQuery(Pageable.unpaged()));
    }

    @GetMapping("/teachers/archived")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllArchivedTeachers(Pageable pageable) {
        return userQueryHandler.handle(new GetArchivedTeachersQuery(pageable));
    }

    @GetMapping("/students/archived")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllArchivedStudents(Pageable pageable) {
        return userQueryHandler.handle(new GetArchivedStudentsQuery(pageable));
    }

    @GetMapping("/offices/archived")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllArchivedOffices(Pageable pageable) {
        return userQueryHandler.handle(new GetArchivedOfficesQuery(pageable));
    }

    @GetMapping("/directors/archived")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllArchivedDirectors(Pageable pageable) {
        return userQueryHandler.handle(new GetArchivedDirectorsQuery(pageable));
    }

    @GetMapping("/admins/archived")
    @Secured("ADMIN")
    public Page<UserDTO> getAllArchivedAdmins(Pageable pageable) {
        return userQueryHandler.handle(new GetArchivedAdminsQuery(pageable));
    }

    @GetMapping("/archived")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllArchivedUsers(Pageable pageable) {
        return userQueryHandler.handle(new GetArchivedUsersQuery(pageable));
    }

    @GetMapping("/unassigned/archived")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getAllArchivedUnassignedUsers() {
        return userQueryHandler.handle(new GetArchivedUnassignedUsersQuery(Pageable.unpaged()));
    }

    @GetMapping("/teachers/free")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public List<UserDTO> getAllNotSupervisingActiveTeachers(Pageable pageable) {
        return userQueryHandler.handle(new GetNotSupervisingActiveTeachersQuery(pageable));
    }

    @GetMapping("/students/unassigned")
    @Secured({"OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> getUnassignedStudents(Pageable pageable) {
        return userQueryHandler.handle(new GetUnassignedStudentsQuery(pageable));
    }

    @GetMapping("/search")
    @Secured({"STUDENT", "TEACHER", "PARENT", "OFFICE", "DIRECTOR", "ADMIN"})
    public Page<UserDTO> searchForUser(@PathParam("keyword") String keyword, Pageable pageable) {
        return userQueryHandler.handle(new GetUserByKeywordQuery(keyword, pageable));
    }
}
