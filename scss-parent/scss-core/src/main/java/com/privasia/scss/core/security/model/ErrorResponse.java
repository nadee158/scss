/**
 * 
 */
package com.privasia.scss.core.security.model;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;

import com.privasia.scss.common.enums.ErrorCode;


/**
 * @author Janaka
 *
 */
public class ErrorResponse {
	
	// HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    // Error code
    private final ErrorCode errorCode;

    private final LocalDate timestamp;

    protected ErrorResponse(final String message, final ErrorCode errorCode, HttpStatus status) {
        this.message = message;
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = LocalDate.now();
    }

    public static ErrorResponse of(final String message, final ErrorCode errorCode, HttpStatus status) {
        return new ErrorResponse(message, errorCode, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

}
