package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.schoolclass;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusCommandExecutionException;

@ResponseStatus(HttpStatus.CONFLICT)
public class SchoolClassAlreadyExistsCommandExecutionException extends SyllabusCommandExecutionException {
}
