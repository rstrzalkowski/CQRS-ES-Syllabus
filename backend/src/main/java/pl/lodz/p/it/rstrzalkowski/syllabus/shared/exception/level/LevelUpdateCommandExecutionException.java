package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.level;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusCommandExecutionException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LevelUpdateCommandExecutionException extends SyllabusCommandExecutionException {
}