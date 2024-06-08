package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.springframework.http.HttpStatus;

public class ResponseUnauthorizedException extends SyllabusCommandExecutionException {
    public ResponseUnauthorizedException() {
        super(HttpStatus.UNAUTHORIZED);
    }
}
