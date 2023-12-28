package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.grade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusCommandExecutionException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GradeNotFoundCommandExecutionException extends SyllabusCommandExecutionException {
}
