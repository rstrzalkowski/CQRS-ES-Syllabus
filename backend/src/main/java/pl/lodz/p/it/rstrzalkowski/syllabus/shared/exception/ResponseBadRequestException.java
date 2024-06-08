package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.springframework.http.HttpStatus;

public class ResponseBadRequestException extends SyllabusCommandExecutionException {
    public ResponseBadRequestException() {
        super(HttpStatus.BAD_REQUEST);
    }
}
