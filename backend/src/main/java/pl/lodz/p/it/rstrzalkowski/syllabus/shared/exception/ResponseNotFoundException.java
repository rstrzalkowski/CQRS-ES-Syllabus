package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;

public class ResponseNotFoundException extends CommandExecutionException {
    public ResponseNotFoundException() {
        super(null, null, new ErrorObject(HttpStatus.NOT_FOUND.value()));
    }
}
