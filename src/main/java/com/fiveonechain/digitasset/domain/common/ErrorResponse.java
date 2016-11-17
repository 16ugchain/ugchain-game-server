package com.fiveonechain.digitasset.domain.common;

import com.fiveonechain.digitasset.domain.result.ErrorInfo;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorResponse {
    // HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final ErrorInfo errorCode;

    private final Date timestamp;

    protected ErrorResponse(final String message, final ErrorInfo errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = new Date();
    }

    public static ErrorResponse of(final String message, final ErrorInfo errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public ErrorInfo getErrorCode() {
        return errorCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
