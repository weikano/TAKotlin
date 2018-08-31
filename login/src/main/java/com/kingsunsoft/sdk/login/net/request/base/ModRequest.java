package com.kingsunsoft.sdk.login.net.request.base;

import android.util.Log;

import com.google.gson.Gson;
import com.kingsunsoft.sdk.login.exception.ResponseValidateError;
import com.kingsunsoft.sdk.mod.Header;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.login.net.utils.TarsUtils;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by AllynYonge on 10/10/2017.
 */

public abstract class ModRequest<T> extends Request {

    private final Object[] params;
    private final Class responseType;

    public ModRequest(Class responseType, Object... params) {
        this.params = params;
        this.responseType = responseType;
    }

    public Maybe<T> sendRequest(){
        createHeaderAndBody();
        return call().map(new Function<Response, T>() {
            @Override
            public T apply(@NonNull Response response) throws Exception {
                T result = (T) TarsUtils.getBodyRsp(responseType, response.body);
                Log.i("ModRequest:body", new Gson().toJson(result));
                Log.i("ModRequest", "-------------------------");

                if (validateRsp(result)){
                    return result;
                }
                throw new ResponseValidateError();
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    public void createHeaderAndBody(){
        setHeader(makeHeader());
        setBody(makeReqBinary(params));
    }

    @Override
    public Header makeHeader() {
        Header header = super.makeHeader();
        header.requestName = getModRequestName();
        header.appRequestName = "";
        return header;
    }

    protected abstract String getModRequestName();
    protected abstract byte[] makeReqBinary(Object[] params);
    protected abstract boolean validateRsp(T rsp);
}
