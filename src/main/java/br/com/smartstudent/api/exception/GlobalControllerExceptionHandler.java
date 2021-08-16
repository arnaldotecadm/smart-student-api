package br.com.smartstudent.api.exception;

import br.com.smartstudent.api.model.CustomExceptionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseEntity<CustomExceptionModel> handleCustomSimpleException(ValidationException validationException) {
        HttpStatus responseStatus = validationException.getHttpStatus();
        return new ResponseEntity<>(new CustomExceptionModel(validationException.getDescription()), responseStatus);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<CustomExceptionModel> handleAllOtherException(Exception ex) {
        return new ResponseEntity<>(new CustomExceptionModel(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
