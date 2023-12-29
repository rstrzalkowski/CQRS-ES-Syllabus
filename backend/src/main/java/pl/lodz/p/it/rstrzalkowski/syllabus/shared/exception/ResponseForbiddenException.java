package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.ErrorObject;

public class ResponseForbiddenException extends CommandExecutionException {
    public ResponseForbiddenException() {
        super(null, null, new ErrorObject(HttpStatus.FORBIDDEN.value()));
    }
}