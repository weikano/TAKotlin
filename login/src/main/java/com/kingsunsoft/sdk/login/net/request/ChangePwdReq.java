package com.kingsunsoft.sdk.login.net.request;

import com.qq.tars.protocol.tars.TarsOutputStream;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.modsdk.ChangePasswordRequest;
import com.kingsunsoft.sdk.modsdk.ChangePasswordResponse;
import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.request.base.ModRequest;

import io.reactivex.Maybe;

/**
 * Created by AllynYonge on 13/10/2017.
 */

public class ChangePwdReq extends ModRequest<ChangePasswordResponse>{

    public ChangePwdReq(Class responseType, Object... params) {
        super(responseType, params);
    }

    @Override
    protected String getModRequestName() {
        return ChangePasswordRequest.class.getSimpleName();
    }

    @Override
    protected byte[] makeReqBinary(Object[] params) {
        TarsOutputStream outputStream = new TarsOutputStream();
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.setOldPassword((String) params[0]);
        request.setNewPassword((String) params[1]);
        request.writeTo(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected boolean validateRsp(ChangePasswordResponse rsp) {
        return true;
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.changePwdReq(this);
    }
}
