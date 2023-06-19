package com.wk.testejava.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApiException extends RuntimeException {

    public ApiException(String exception) {
        super(exception);
    }

    public ApiException(String exception, Throwable throwable) {
        super(exception, throwable);
    }
}
