package com.kingsunsoft.sdk.login.exception;

/**
 * Created by AllynYonge on 24/10/2017.
 */

public class ClientException extends RuntimeException {
    private int errCode;

    public ClientException(String message, int errCode) {
        super(message);
        this.errCode = errCode;
    }

    public int getErrCode(){
        return errCode;
    }
}
