package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.UserDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.SchoolClassEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.entity.UserEntity;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.user.UserNotFoundException;
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
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.SchoolClassRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class UserQueryHandler {

    private final UserRepository userRepository;
    private final SchoolClassRepository schoolClassRepository;


    @QueryHandler
    public UserDTO handle(GetLoggedInUserQuery query) {
        return new UserDTO(userRepository.findById(query.id())
                .orElseThrow(UserNotFoundException::new));
    }

    @QueryHandler
    public UserDTO handle(GetUserByIdQuery query) {
        return new UserDTO(userRepository.findById(query.id())
                .orElseThrow(UserNotFoundException::new));
    }

    @QueryHandler
    public Page<UserDTO> handle(GetActiveStudentsQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_STUDENT", false, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetActiveTeachersQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_TEACHER", false, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetActiveOfficesQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_OFFICE", false, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetActiveDirectorsQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_DIRECTOR", false, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetActiveAdminsQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_ADMIN", false, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetArchivedStudentsQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_STUDENT", true, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetArchivedTeachersQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_TEACHER", true, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetArchivedOfficesQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_OFFICE", true, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetArchivedDirectorsQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_DIRECTOR", true, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetArchivedAdminsQuery query) {
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_ADMIN", true, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public List<UserDTO> handle(GetNotSupervisingActiveTeachersQuery query) {
        List<SchoolClassEntity> classes = schoolClassRepository.findAllByArchived(false);
        return userRepository.findAllByRolesContainingAndArchived("SYLLABUS_TEACHER", false, query.pageable())
                .map(UserDTO::new)
                .getContent()
                .stream()
                .filter(teacher -> classes.stream()
                        .noneMatch(schoolClass -> Objects.equals(schoolClass.getSupervisingTeacher().getId(), teacher.getId())))
                .toList();
    }

    @QueryHandler
    public Page<UserDTO> handle(GetActiveUsersQuery query) {
        return userRepository.findAllByArchived(false, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetArchivedUsersQuery query) {
        return userRepository.findAllByArchived(true, query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetActiveUnassignedUsersQuery query) {
        Page<UserEntity> allByArchived = userRepository.findAllByArchived(false, query.pageable());

        List<UserDTO> content = allByArchived.getContent().stream()
                .filter(user -> user.getRoles().size() == 0)
                .map(UserDTO::new)
                .toList();

        return new PageImpl<>(content);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetArchivedUnassignedUsersQuery query) {
        Page<UserEntity> allByArchived = userRepository.findAllByArchived(true, query.pageable());

        List<UserDTO> content = allByArchived.getContent().stream()
                .filter(user -> user.getRoles().size() == 0)
                .map(UserDTO::new)
                .toList();

        return new PageImpl<>(content);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetUnassignedStudentsQuery query) {
        return userRepository.findByArchivedAndRolesContainingAndSchoolClassIsNull(false, "SYLLABUS_STUDENT", query.pageable())
                .map(UserDTO::new);
    }

    @QueryHandler
    public Page<UserDTO> handle(GetUserByKeywordQuery query) {
        return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query.keyword(), query.keyword(), query.pageable())
                .map(UserDTO::new);
    }
}
