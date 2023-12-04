package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;

public class SyllabusException extends CommandExecutionException {
    public SyllabusException(String message, Throwable cause, Object details) {
        super(message, cause, details);
    }

    public SyllabusException() {
        super(null, null, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
