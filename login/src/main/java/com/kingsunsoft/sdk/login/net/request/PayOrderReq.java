package com.kingsunsoft.sdk.login.net.request;

import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.request.base.ModRequest;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.modsdk.PlaceOrderRequest;
import com.kingsunsoft.sdk.modsdk.PlaceOrderResponse;
import com.qq.tars.protocol.tars.TarsOutputStream;

import io.reactivex.Maybe;

/**
 * Created by hu.yang on 2017/5/8.
 */

public class PayOrderReq extends ModRequest<PlaceOrderResponse> {

    public PayOrderReq(Object... params) {
        super(PlaceOrderResponse.class, params);
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.payOrderRequest(this);
    }

    @Override
    public String getModRequestName() {
        return PlaceOrderRequest.class.getSimpleName();
    }

    @Override
    public byte[] makeReqBinary(Object[] params) {
        PlaceOrderRequest placeOrderRequest = new PlaceOrderRequest();
        TarsOutputStream outputStream = new TarsOutputStream();
        placeOrderRequest.setPayType((Integer) params[0]);
        placeOrderRequest.setProductId((Integer) params[1]);
        placeOrderRequest.setExt((String) params[2]);
        placeOrderRequest.setIapReceipt((String) params[3]);
        placeOrderRequest.writeTo(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected boolean validateRsp(PlaceOrderResponse rsp) {
        return true;
    }
}
