package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.handler;

import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.dto.UserDTO;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.user.UserNotFoundException;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetActiveTeachersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetLoggedInUserQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.query.user.GetNotSupervisingActiveTeachersQuery;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.repository.UserRepository;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.ReadApplicationBean;

import java.util.List;

@Service
@RequiredArgsConstructor
@ReadApplicationBean
public class UserQueryHandler {

    private final UserRepository userRepository;


    @QueryHandler
    public UserDTO handle(GetLoggedInUserQuery query) {
        return new UserDTO(userRepository.findById(query.id())
            .orElseThrow(UserNotFoundException::new));
    }

    @QueryHandler
    public Page<UserDTO> handle(GetActiveTeachersQuery query) {
        return userRepository.findAllByArchived(false, query.pageable())
            .map(UserDTO::new);

    }

    @QueryHandler
    public List<UserDTO> handle(GetNotSupervisingActiveTeachersQuery query) {
        return userRepository.findAllByArchived(false, query.pageable())
            .map(UserDTO::new).getContent();
    }
}
