package com.kingsunsoft.sdk.login.net.request;

import com.qq.tars.protocol.tars.TarsOutputStream;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.modsdk.UpdateUserInfoRequest;
import com.kingsunsoft.sdk.modsdk.UpdateUserInfoResponse;
import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.request.base.ModRequest;

import io.reactivex.Maybe;

/**
 * Created by AllynYonge on 13/10/2017.
 */

public class UpdateUserInfoReq extends ModRequest<UpdateUserInfoResponse>{

    public UpdateUserInfoReq(Class responseType, Object... params) {
        super(responseType, params);
    }

    @Override
    protected String getModRequestName() {
        return UpdateUserInfoRequest.class.getSimpleName();
    }

    @Override
    protected byte[] makeReqBinary(Object[] params) {
        TarsOutputStream outputStream = new TarsOutputStream();
        UpdateUserInfoRequest request = new UpdateUserInfoRequest();
        request.setName((String) params[0]);
        request.setAvatar((String) params[1]);
        request.writeTo(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected boolean validateRsp(UpdateUserInfoResponse rsp) {
        return true;
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.updateUserInfoReq(this);
    }
}
