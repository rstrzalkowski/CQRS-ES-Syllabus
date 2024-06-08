package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.springframework.http.HttpStatus;

public class ResponseForbiddenException extends SyllabusCommandExecutionException {
    public ResponseForbiddenException() {
        super(HttpStatus.FORBIDDEN);
    }
}
