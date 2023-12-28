package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ResponseBadRequestException;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusCommandExecutionException;

public class RoleNotCurrentlyAssignedCommandExecutionException extends ResponseBadRequestException {
}
