package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.ErrorObject;

public class ResponseBadRequestException extends CommandExecutionException {
    public ResponseBadRequestException() {
        super(null, null, new ErrorObject(HttpStatus.BAD_REQUEST.value()));
    }
}