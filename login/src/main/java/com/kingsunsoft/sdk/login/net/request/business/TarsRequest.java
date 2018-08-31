package com.kingsunsoft.sdk.login.net.request.business;

import android.util.Log;

import com.google.gson.Gson;
import com.kingsunsoft.sdk.BuildConfig;
import com.kingsunsoft.sdk.login.exception.ResponseValidateError;
import com.kingsunsoft.sdk.mod.Header;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.request.base.Request;
import com.kingsunsoft.sdk.login.net.utils.TarsUtils;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by AllynYonge on 10/10/2017.
 */

public abstract class TarsRequest<T> extends Request {
    private final Object[] params;
    private final Class responseType;

    public TarsRequest(Class responseType, Object... params) {
        this.params = params;
        this.responseType = responseType;
    }

    public Maybe<T> sendRequest(){
        setHeader(makeHeader());
        setBody(makeReqBinary(params));
        return call().map(new Function<Response, T>() {
            @Override
            public T apply(@NonNull Response response) throws Exception {
                T result = (T) TarsUtils.getBodyRsp(responseType, response.body);
                if(BuildConfig.DEBUG) {
                    Log.i("TarsRequest:body", new Gson().toJson(result));
                    Log.i("TarsRequest", "-------------------------");
                }
                if (validateRsp(result)){
                    return result;
                }
                throw new ResponseValidateError();
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Header makeHeader() {
        Header header = super.makeHeader();
        header.appRequestName = getRequestName();
        header.requestName = "";
        return header;
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.businessReq(this);
    }

    protected abstract String getRequestName();
    protected abstract byte[] makeReqBinary(Object[] params);
    protected abstract boolean validateRsp(T rsp);
}
