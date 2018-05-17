package com.katalon.katalonrecorder.helper;

import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    private static final Logger log = LogHelper.getLogger();

    @ExceptionHandler(value = {RuntimeException.class, IllegalArgumentException.class})
    public ResponseEntity<ResponseMessage> handledException(Exception ex, WebRequest request) {
        log.error(ex.getMessage(), ex);
        ResponseMessage responseMessenger = new ResponseMessage(ex.getMessage());
        return new ResponseEntity<>(responseMessenger, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
