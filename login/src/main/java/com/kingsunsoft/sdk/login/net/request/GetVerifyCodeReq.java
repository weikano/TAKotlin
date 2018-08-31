package com.kingsunsoft.sdk.login.net.request;

import com.qq.tars.protocol.tars.TarsOutputStream;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.modsdk.GetVerifyCodeRequest;
import com.kingsunsoft.sdk.modsdk.GetVerifyCodeResponse;
import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.request.base.ModRequest;

import io.reactivex.Maybe;

/**
 * Created by AllynYonge on 13/10/2017.
 */

public class GetVerifyCodeReq extends ModRequest<GetVerifyCodeResponse> {
    public GetVerifyCodeReq(Class responseType, Object... params) {
        super(responseType, params);
    }

    @Override
    protected String getModRequestName() {
        return GetVerifyCodeRequest.class.getSimpleName();
    }

    @Override
    protected byte[] makeReqBinary(Object[] params) {
        TarsOutputStream outputStream = new TarsOutputStream();
        GetVerifyCodeRequest request = new GetVerifyCodeRequest();
        request.setMobilePhone((String) params[0]);
        request.setType((Integer) params[1]);
        request.writeTo(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected boolean validateRsp(GetVerifyCodeResponse rsp) {
        return true;
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.getVerifyCodeReq(this);
    }
}
