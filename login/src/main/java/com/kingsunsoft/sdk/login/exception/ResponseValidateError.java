package com.kingsunsoft.sdk.login.exception;

/**
 * Created by AllynYonge on 17/07/2017.
 */

public class ResponseValidateError extends KingSunException {

    public ResponseValidateError() {
        super("数据包验证失败！");
    }
}
