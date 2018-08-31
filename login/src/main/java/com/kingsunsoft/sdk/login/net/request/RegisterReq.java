package com.kingsunsoft.sdk.login.net.request;

import android.util.Log;

import com.qq.tars.protocol.tars.TarsOutputStream;
import com.kingsunsoft.sdk.login.exception.ResponseValidateError;
import com.kingsunsoft.sdk.mod.Header;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.modsdk.RegisterRequest;
import com.kingsunsoft.sdk.modsdk.RegisterResponse;
import com.kingsunsoft.sdk.login.module.Mapper;
import com.kingsunsoft.sdk.login.module.User;
import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.request.base.ModRequest;
import com.kingsunsoft.sdk.login.net.utils.TarsUtils;

import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * Created by AllynYonge on 13/10/2017.
 */

public class RegisterReq extends ModRequest<User>{
    public RegisterReq(Class responseType, Object... params) {
        super(responseType, params);
    }

    @Override
    protected String getModRequestName() {
        return RegisterRequest.class.getSimpleName();
    }

    @Override
    public Header makeHeader() {
        Header header = super.makeHeader();
        header.token = "";
        header.account = "";
        header.refreshToken = "";
        return header;
    }

    @Override
    protected byte[] makeReqBinary(Object[] params) {
        TarsOutputStream outputStream = new TarsOutputStream();
        RegisterRequest request = new RegisterRequest();
        request.setMobilePhone((String) params[0]);
        request.setPassword((String) params[1]);
        request.setVerifyCode((String) params[2]);
        request.writeTo(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected boolean validateRsp(User rsp) {
        return true;
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.registerReq(this);
    }

    @Override
    public Maybe<User> sendRequest() {
        createHeaderAndBody();
        return call().map(new Function<Response, RegisterResponse>() {
            @Override
            public RegisterResponse apply(@NonNull Response response) throws Exception {
                RegisterResponse result = (RegisterResponse) TarsUtils.getBodyRsp(RegisterResponse.class, response.body);
                if (result.userInfo != null && result.userInfo.userId != 0){
                    return result;
                }
                throw new ResponseValidateError();
            }
        }).map(new Function<RegisterResponse, User>() {
            @Override
            public User apply(@NonNull RegisterResponse response) throws Exception {
                User userEntity = Mapper.mapperUserEntity(response.userInfo, getHeader());
                if (!userEntity.save())
                    Log.e("KingSunSDK", "持久化用户数据失败！");
                return userEntity;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }
}
