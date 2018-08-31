package com.kingsunsoft.sdk.login.exception;

/**
 * Created by AllynYonge on 13/10/2017.
 */

public abstract class KingSunException extends RuntimeException {
    private int errorCode = -1;
    public KingSunException(String message) {
        super(message);
    }

    public KingSunException(String errorMessage, int errorCode) {
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public int getErrCode() {
        return errorCode;
    }
}
