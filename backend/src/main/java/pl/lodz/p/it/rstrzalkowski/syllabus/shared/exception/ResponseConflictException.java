package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.ErrorObject;

public class ResponseConflictException extends CommandExecutionException {
    public ResponseConflictException() {
        super(null, null, new ErrorObject(HttpStatus.CONFLICT.value()));
    }
}
