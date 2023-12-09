package pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.queryside.exception.SyllabusException;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class PostNotFoundException extends SyllabusException {
}
