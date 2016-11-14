/**
 * 
 */
package com.privasia.scss.common.enums;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Janaka
 *
 */
public enum ErrorCode {
	
	GLOBAL(2),

    AUTHENTICATION(10), JWT_TOKEN_EXPIRED(11);
    
    private int errorCode;

    private ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @JsonValue
    public int getErrorCode() {
        return errorCode;
    }

}
