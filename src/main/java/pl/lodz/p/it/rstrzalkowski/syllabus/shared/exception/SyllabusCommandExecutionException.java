package pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception;

import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor.ErrorObject;

public class SyllabusCommandExecutionException extends CommandExecutionException {
    public SyllabusCommandExecutionException() {
        super(null, null, new ErrorObject(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    public SyllabusCommandExecutionException(HttpStatusCode status) {
        super(null, null, new ErrorObject(status.value()));
    }
}
