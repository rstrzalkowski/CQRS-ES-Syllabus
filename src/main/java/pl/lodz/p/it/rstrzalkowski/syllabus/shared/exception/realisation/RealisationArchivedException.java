package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.realisation;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.SyllabusException;

@ResponseStatus(HttpStatus.GONE)
public class RealisationArchivedException extends SyllabusException {
}