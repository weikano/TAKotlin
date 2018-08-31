package com.kingsunsoft.sdk.login.listener;

/**
 * Created by AllynYonge on 10/10/2017.
 */

public interface RequestCallBack<T> {
    void onSuccess(T data);
    void onError(String message, int modErrCode, int operateErrCode);
}
