package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.SyllabusException;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class InvalidPasswordException extends SyllabusException {
}
