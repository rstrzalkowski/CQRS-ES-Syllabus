package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.springframework.http.HttpStatus;

public class ResponseConflictException extends SyllabusCommandExecutionException {
    public ResponseConflictException() {
        super(HttpStatus.CONFLICT);
    }
}
