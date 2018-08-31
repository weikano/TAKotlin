package com.kingsunsoft.sdk.login.net.request;

import com.kingsunsoft.sdk.mod.Header;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.request.base.Request;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by AllynYonge on 10/10/2017.
 */

public class BinaryReq extends Request {

    private final String requestName;
    private final byte[] data;

    public BinaryReq(String requestName, byte[] data) {
        this.requestName = requestName;
        this.data = data;
    }

    public Maybe<byte[]> sendRequest(){
        setBody(data);
        Header header = makeHeader();
        header.appRequestName = requestName;
        header.requestName = "";
        setHeader(header);
        return call().map(new Function<Response, byte[]>() {
            @Override
            public byte[] apply(@NonNull Response response) throws Exception {
                return response.body;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.businessReq(this);
    }
}
