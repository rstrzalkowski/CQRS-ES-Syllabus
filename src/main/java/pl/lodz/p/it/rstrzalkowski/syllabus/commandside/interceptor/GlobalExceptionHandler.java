package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.interceptor;

import lombok.extern.java.Log;
import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Log
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CommandExecutionException.class})
    protected ResponseEntity<Object> handleCommandExecution(final CommandExecutionException cex, final WebRequest request) {
        Object details = cex.getDetails().orElse(new ErrorObject(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        ErrorObject errorObject = (ErrorObject) details;

        return handleExceptionInternal(
                cex,
                errorObject,
                new HttpHeaders(),
                HttpStatus.valueOf(errorObject.getStatusCode()),
                request
        );
    }
}
