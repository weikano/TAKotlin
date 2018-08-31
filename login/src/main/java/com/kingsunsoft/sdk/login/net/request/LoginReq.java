package com.kingsunsoft.sdk.login.net.request;

import android.util.Log;

import com.qq.tars.protocol.tars.TarsOutputStream;
import com.kingsunsoft.sdk.login.exception.ResponseValidateError;
import com.kingsunsoft.sdk.mod.Header;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.modsdk.LoginRequest;
import com.kingsunsoft.sdk.modsdk.LoginResponse;
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
 * Created by hu.yang on 2017/5/8.
 */

public class LoginReq extends ModRequest<User> {

    public LoginReq(Object... params) {
        super(LoginResponse.class, params);
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
    public String getModRequestName() {
        return LoginRequest.class.getSimpleName();
    }

    @Override
    public byte[] makeReqBinary(Object[] params) {
        LoginRequest loginReq = new LoginRequest();
        TarsOutputStream outputStream = new TarsOutputStream();
        loginReq.setUsername((String) params[0]);
        loginReq.setPassword((String) params[1]);
        loginReq.setVerifyCode((String) params[2]);
        loginReq.setWxAppId((String) params[3]);
        loginReq.setWxLoginCode((String) params[4]);
        loginReq.writeTo(outputStream);
        return outputStream.toByteArray();
    }

    @Override
    protected boolean validateRsp(User rsp) {
        return true;
    }

    @Override
    public Maybe<User> sendRequest() {
        createHeaderAndBody();
        return call().map(new Function<Response, LoginResponse>() {
            @Override
            public LoginResponse apply(@NonNull Response response) throws Exception {
                LoginResponse result = (LoginResponse) TarsUtils.getBodyRsp(LoginResponse.class, response.body);
                if (result.userInfo != null && result.userInfo.userId != 0){
                    return result;
                }
                throw new ResponseValidateError();
            }
        }).map(new Function<LoginResponse, User>() {
            @Override
            public User apply(@NonNull LoginResponse response) throws Exception {
                User userEntity = Mapper.mapperUserEntity(response.userInfo, getHeader());
                if (!userEntity.save())
                    Log.e("KingSunSDK", "持久化用户数据失败！");
                return userEntity;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected Maybe<Response> getRequestSingle(Api api) {
        return api.loginReq(this);
    }
}
