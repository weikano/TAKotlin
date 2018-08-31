package com.kingsunsoft.sdk.login.net.request;

import com.qq.tars.protocol.tars.TarsOutputStream;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.modsdk.ResetPasswordRequest;
import com.kingsunsoft.sdk.modsdk.ResetPasswordResponse;
import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.request.base.ModRequest;

import io.reactivex.Maybe;

/**
 * Created by AllynYonge on 13/10/2017.
 */

public class ResetPwdReq extends ModRequest<ResetPasswordResponse>{

    public ResetPwdReq(Class responseType, Object... params) {
        super(responseType, params);
    }

    @Override
    protected String getModRequestName() {
        return ResetPasswordRequest.class.getSimpleName();
    }

    @Override
    protected byte[] makeReqBinary(Object[] params) {
        TarsOutputStream outputStream = new TarsOutputStream();
        ResetPasswordRequest request = new ResetPasswordRequest();
        request.setMobilePhone((String) params[0]);
        request.setPassword((String) params[1]);
        request.setVerifyCode((String) params[2]);
        request.writeTo(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected boolean validateRsp(ResetPasswordResponse rsp) {
        return true;
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.resetPwdReq(this);
    }
}
