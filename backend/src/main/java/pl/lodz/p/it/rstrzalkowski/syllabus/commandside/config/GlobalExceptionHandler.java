package pl.lodz.p.it.rstrzalkowski.syllabus.commandside.config;

import lombok.extern.java.Log;
import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.exception.ErrorObject;
import pl.lodz.p.it.rstrzalkowski.syllabus.shared.util.WriteApplicationBean;

@ControllerAdvice
@Log
@WriteApplicationBean
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({CommandExecutionException.class})
    protected ResponseEntity<Object> handleCommandExecutionException(final CommandExecutionException cex,
                                                                     final WebRequest request) {
        logger.error("Exception occurred: " + cex);
        ErrorObject errorObject = null;
        try {
            Object details = cex.getDetails().get();
            errorObject = (ErrorObject) details;
        } catch (Exception e) {
            errorObject = new ErrorObject(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }

        return handleExceptionInternal(
                cex,
                errorObject,
                new HttpHeaders(),
                HttpStatus.valueOf(errorObject.getStatusCode()),
                request
        );
    }
}
