package com.kingsunsoft.sdk.login.net;


import com.kingsunsoft.sdk.login.net.request.ChangePwdReq;
import com.kingsunsoft.sdk.login.net.request.GetProductListReq;
import com.kingsunsoft.sdk.login.net.request.GetVerifyCodeReq;
import com.kingsunsoft.sdk.login.net.request.LoginReq;
import com.kingsunsoft.sdk.login.net.request.PayOrderReq;
import com.kingsunsoft.sdk.login.net.request.RegisterReq;
import com.kingsunsoft.sdk.login.net.request.ResetPwdReq;
import com.kingsunsoft.sdk.login.net.request.UpdateUserInfoReq;
import com.kingsunsoft.sdk.mod.Request;
import com.kingsunsoft.sdk.mod.Response;

import io.reactivex.Maybe;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by hu.yang on 2017/5/15.
 */

public interface Api {
    @POST("/")
    Maybe<Response> loginReq(@Body LoginReq req);

    @POST("/")
    Maybe<Response> registerReq(@Body RegisterReq req);

    @POST("/")
    Maybe<Response> getVerifyCodeReq(@Body GetVerifyCodeReq req);

    @POST("/")
    Maybe<Response> changePwdReq(@Body ChangePwdReq req);

    @POST("/")
    Maybe<Response> resetPwdReq(@Body ResetPwdReq req);

    @POST("/")
    Maybe<Response> updateUserInfoReq(@Body UpdateUserInfoReq req);

    @POST("/")
    Maybe<Response> getProductList(@Body GetProductListReq req);

    @POST("/")
    Maybe<Response> payOrderRequest(@Body PayOrderReq req);

    @POST("/")
    Maybe<Response> businessReq(@Body Request req);

}
