package com.fiveonechain.digitasset;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fiveonechain.digitasset.domain.result.Meta;
import com.fiveonechain.digitasset.domain.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.ParseException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * Created by yuanshichao on 2016/11/17.
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({
            IOException.class,
            ParseException.class,
            JsonParseException.class,
            JsonMappingException.class})
    @ResponseBody
    public ResponseEntity<?> handleParseException(Exception e) {
        return errorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalAccessError.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class})
    @ResponseBody
    public ResponseEntity<?> handleIllegalAccessException(Exception e) {
        return errorResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<?> handleAnyException(Exception e) {
        LOG.error("handleAnyException", e);
        return errorResponse(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    protected ResponseEntity<Result> errorResponse(Throwable throwable, HttpStatus status) {
        String message = throwable.getMessage();
        return response(new Result(new Meta(status.value(), message)), status);
    }

    protected <T> ResponseEntity<T> response(T body, HttpStatus status) {
        return new ResponseEntity<T>(body, new HttpHeaders(), status);
    }

}
