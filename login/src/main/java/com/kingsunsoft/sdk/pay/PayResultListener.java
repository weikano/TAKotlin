package com.kingsunsoft.sdk.pay;

/**
 * Created by AllynYonge on 27/12/2017.
 */

public interface PayResultListener {

    void paySuccess();

    void payFail(String msg);
}
