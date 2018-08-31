package com.kingsunsoft.sdk.login.net.request;

import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.request.base.ModRequest;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.modsdk.GetProductRequest;
import com.kingsunsoft.sdk.modsdk.GetProductResponse;
import com.qq.tars.protocol.tars.TarsOutputStream;

import io.reactivex.Maybe;

/**
 * Created by hu.yang on 2017/5/8.
 */

public class GetProductListReq extends ModRequest<GetProductResponse> {

    public GetProductListReq(Object... params) {
        super(GetProductResponse.class, params);
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.getProductList(this);
    }

    @Override
    public String getModRequestName() {
        return GetProductRequest.class.getSimpleName();
    }

    @Override
    public byte[] makeReqBinary(Object[] params) {
        GetProductRequest getProductRequest = new GetProductRequest();
        TarsOutputStream outputStream = new TarsOutputStream();
        getProductRequest.writeTo(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected boolean validateRsp(GetProductResponse rsp) {
        return true;
    }
}
