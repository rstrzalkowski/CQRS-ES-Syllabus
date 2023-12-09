package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.realisation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.SyllabusException;


@ResponseStatus(HttpStatus.CONFLICT)
public class RealisationAlreadyExistsException extends SyllabusException {
}
