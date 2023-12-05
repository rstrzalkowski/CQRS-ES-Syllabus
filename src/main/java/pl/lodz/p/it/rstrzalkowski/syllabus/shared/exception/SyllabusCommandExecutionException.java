package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;

public class SyllabusCommandExecutionException extends CommandExecutionException {
    public SyllabusCommandExecutionException() {
        super(null, null, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
