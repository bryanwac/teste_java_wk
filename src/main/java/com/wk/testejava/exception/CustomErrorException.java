package com.wk.testejava.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.Date;


@SuppressWarnings({"unchecked", "rawtypes"})
@ControllerAdvice
public class CustomErrorException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ErrorDetails> handleNotFoundException(ApiException ex, WebRequest request) {
        ErrorDetails exceptionResponse = new ErrorDetails();
        exceptionResponse.setTimestamp(new Date());
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());

        exceptionResponse.setMessages(Collections.singletonList(ex.getMessage()));
        exceptionResponse.setMessage(ex.getMessage());
        exceptionResponse.setDetails(request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        ErrorDetails exceptionResponse = new ErrorDetails();
        exceptionResponse.setTimestamp(new Date());
        exceptionResponse.setStatus(HttpStatus.CONFLICT.value());

        exceptionResponse.setMessages(Collections.singletonList(exception.getMessage()));
        exceptionResponse.setMessage("Este item não pode ser excluído, pois existem outros itens associados a ele.");
        exceptionResponse.setDetails(exception.getMostSpecificCause().getMessage());


        return new ResponseEntity(exceptionResponse, HttpStatus.CONFLICT);
    }


}
