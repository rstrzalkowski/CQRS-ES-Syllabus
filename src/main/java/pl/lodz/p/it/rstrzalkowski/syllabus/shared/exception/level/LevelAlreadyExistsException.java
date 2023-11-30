package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.level;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusException;

@ResponseStatus(HttpStatus.CONFLICT)
public class LevelAlreadyExistsException extends SyllabusException {
}
