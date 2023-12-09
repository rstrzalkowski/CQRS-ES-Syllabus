package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.activity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.SyllabusException;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class NotAffiliatedWithActivityException extends SyllabusException {
}
