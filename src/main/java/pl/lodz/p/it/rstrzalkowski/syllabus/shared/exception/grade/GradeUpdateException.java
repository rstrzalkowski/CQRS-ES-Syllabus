package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.grade;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GradeUpdateException extends SyllabusException {
}
