package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.springframework.http.HttpStatus;

public class ResponseNotFoundException extends SyllabusCommandExecutionException {
    public ResponseNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
