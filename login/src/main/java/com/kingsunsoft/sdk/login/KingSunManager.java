package com.kingsunsoft.sdk.login;

import android.app.Application;

import com.blankj.utilcode.util.EncryptUtils;
import com.kingsunsoft.sdk.login.listener.RequestCallBack;
import com.kingsunsoft.sdk.login.module.User;
import com.kingsunsoft.sdk.login.module.db.DBHelper;
import com.kingsunsoft.sdk.login.net.Api;
import com.kingsunsoft.sdk.login.net.converter.TarsConverterFactory;
import com.kingsunsoft.sdk.login.net.request.BinaryReq;
import com.kingsunsoft.sdk.login.net.request.ChangePwdReq;
import com.kingsunsoft.sdk.login.net.request.GetProductListReq;
import com.kingsunsoft.sdk.login.net.request.GetVerifyCodeReq;
import com.kingsunsoft.sdk.login.net.request.LoginReq;
import com.kingsunsoft.sdk.login.net.request.PayOrderReq;
import com.kingsunsoft.sdk.login.net.request.RegisterReq;
import com.kingsunsoft.sdk.login.net.request.ResetPwdReq;
import com.kingsunsoft.sdk.login.net.request.UpdateUserInfoReq;
import com.kingsunsoft.sdk.login.net.utils.ModSubscriber;
import com.kingsunsoft.sdk.modsdk.ChangePasswordResponse;
import com.kingsunsoft.sdk.modsdk.GetProductResponse;
import com.kingsunsoft.sdk.modsdk.GetVerifyCodeResponse;
import com.kingsunsoft.sdk.modsdk.PlaceOrderResponse;
import com.kingsunsoft.sdk.modsdk.RegisterResponse;
import com.kingsunsoft.sdk.modsdk.ResetPasswordResponse;
import com.kingsunsoft.sdk.modsdk.UpdateUserInfoResponse;
import com.kingsunsoft.sdk.utils.Utils;
import com.tencent.smtt.sdk.QbSdk;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by AllynYonge on 09/10/2017.
 */

public class KingSunManager {
    private static Api api;
    private static Application app;
    private static DBHelper dbHelper;
    private static User loginUser;
    private static OkHttpClient okHttpClient;

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }

    private static void setOkHttpClient(OkHttpClient customHttpClient) {
        okHttpClient = customHttpClient;
    }

    public static Api getApi() {
        if (api == null) {
            return new Retrofit.Builder()
                    //https://gateway-app.kingsunedu.com
                    //http://gateway-app-dev.kingsunedu.com:20005
                    .baseUrl(debugUrl())
                    .addConverterFactory(new TarsConverterFactory())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build()
                    .create(Api.class);
        }
        return api;
    }

    private static HttpUrl debugUrl() {
        return new HttpUrl.Builder().scheme("http").host("183.47.42.218").port(20005).build();
    }

    private static HttpUrl relaseUrl() {
        return new HttpUrl.Builder().scheme("https").host("gateway-app.kingsunedu.com").build();
    }

    /**
     * 初始化SDK
     *
     * @param appId 在MOD服务端配置的APPID
     */
    public static void init(Application application, String appId) {
        app = application;
        api = getApi();
        dbHelper = new DBHelper(app);
        AppInfo.init(app);
        AppInfo.appId = appId;
        //初始化Q5浏览器内核提高兼容性
        QbSdk.initX5Environment(application, null);
    }

    public static DBHelper getDbHelper() {
        return dbHelper;
    }

    /**
     * 用户登录接口
     *
     * @param mobilePhone 用户名
     * @param password    用户密码
     * @param callBack    回调函数
     */
    public static void login(String mobilePhone, String password, String validateCode, String appId, String wxLoginCode, final RequestCallBack<User> callBack) {
        login(mobilePhone, password, validateCode, appId, wxLoginCode).subscribe(new ModSubscriber<User>() {
            @Override
            public void _onSuccess(User user) {
                loginUser = user;
                callBack.onSuccess(user);
            }

            @Override
            public void _onError(String msg, int modErrCode, int operateErrCode) {
                callBack.onError(msg, modErrCode, operateErrCode);
            }
        });
    }

    public static Maybe<User> login(String mobilePhone, String password, String validateCode, String appId, String wxLoginCode) {
        LoginReq loginReq = new LoginReq(mobilePhone, EncryptUtils.encryptMD5ToString(password), validateCode, appId, wxLoginCode);
        return loginReq.sendRequest();
    }

    public static User getLoginUser() {
        if (loginUser == null) {
            loginUser = new User().query();
        }
        return loginUser;
    }

    /**
     * 退出登录
     */
    public synchronized static void logout() {
        if (loginUser != null) {
            loginUser.token = "";
            loginUser.refreshToken = "";
            loginUser.save();
            loginUser.account = "";
            loginUser = null;
        }
    }

    public static Single<Boolean> logout2Single() {
        logout();
        return Single.just(true);
    }

    /**
     * 获取验证码
     *
     * @param mobilePhone 用户的手机号码
     * @param type        对应不同业务的验证码 0:注册 1:重置密码
     * @param callBack    获取成功后的回调函数
     */
    public static void getVerifyCode(String mobilePhone, int type,
                                     final RequestCallBack<GetVerifyCodeResponse> callBack) {
        getVerifyCode(mobilePhone, type).subscribe(new ModSubscriber<GetVerifyCodeResponse>() {
            @Override
            public void _onSuccess(GetVerifyCodeResponse getVerifyCodeResponse) {
                callBack.onSuccess(getVerifyCodeResponse);
            }

            @Override
            public void _onError(String msg, int modErrCode, int operateErrCode) {
                callBack.onError(msg, modErrCode, operateErrCode);
            }
        });
    }

    public static Maybe<GetVerifyCodeResponse> getVerifyCode(String mobilePhone, int type) {
        if (Utils.verifyPhoneNumber(mobilePhone)) {
            GetVerifyCodeReq request = new GetVerifyCodeReq(GetVerifyCodeResponse.class, mobilePhone, type);
            return request.sendRequest();
        }
        return Maybe.empty();
    }

    /**
     * 用户注册信息
     *
     * @param mobilePhone 手机号码
     * @param pwd         用户密码
     * @param verifyCode  验证码
     * @param callBack    回调函数
     */
    public static void registerAccount(String mobilePhone, String pwd, String verifyCode,
                                       final RequestCallBack<User> callBack) {
        registerAccount(mobilePhone, pwd, verifyCode).subscribe(new ModSubscriber<User>() {
            @Override
            public void _onSuccess(User user) {
                loginUser = user;
                callBack.onSuccess(user);
            }

            @Override
            public void _onError(String msg, int modErrCode, int operateErrCode) {
                callBack.onError(msg, modErrCode, operateErrCode);
            }
        });
    }

    public static Maybe<User> registerAccount(String mobilePhone, String pwd, String verifyCode) {
        if (Utils.verifyPhoneNumber(mobilePhone) && Utils.verifyPwd(pwd)) {
            RegisterReq registerReq = new RegisterReq(RegisterResponse.class, mobilePhone, EncryptUtils.encryptMD5ToString(pwd), verifyCode);
            return registerReq.sendRequest();
        }
        return Maybe.empty();
    }

    /**
     * 修改用户密码
     *
     * @param oldPwd   老密码
     * @param newPwd   新密码
     * @param callBack 回调函数
     */
    public static void changeAccountPwd(String oldPwd, String newPwd,
                                        final RequestCallBack<ChangePasswordResponse> callBack) {
        changeAccountPwd(oldPwd, newPwd).subscribe(new ModSubscriber<ChangePasswordResponse>() {
            @Override
            public void _onSuccess(ChangePasswordResponse changePasswordResponse) {
                callBack.onSuccess(changePasswordResponse);
            }

            @Override
            public void _onError(String msg, int modErrCode, int operateErrCode) {
                callBack.onError(msg, modErrCode, operateErrCode);
            }
        });
    }

    public static Maybe<ChangePasswordResponse> changeAccountPwd(String oldPwd, String newPwd) {
        if (Utils.verifyPwd(oldPwd) && Utils.verifyPwd(newPwd)) {
            ChangePwdReq changePwdReq = new ChangePwdReq(ChangePasswordResponse.class, EncryptUtils.encryptMD5ToString(oldPwd), EncryptUtils.encryptMD5ToString(newPwd));
            return changePwdReq.sendRequest();
        }
        return Maybe.empty();
    }

    /**
     * 重置用户密码
     *
     * @param mobilePhone 用户手机号码
     * @param newPwd      新密码
     * @param verifyCode  获取到的验证码
     * @param callBack    回调函数
     */
    public static void resetAccountPwd(String mobilePhone, String newPwd, String verifyCode,
                                       final RequestCallBack<ResetPasswordResponse> callBack) {
        resetAccountPwd(mobilePhone, newPwd, verifyCode).subscribe(new ModSubscriber<ResetPasswordResponse>() {
            @Override
            public void _onSuccess(ResetPasswordResponse resetPasswordResponse) {
                callBack.onSuccess(resetPasswordResponse);
            }

            @Override
            public void _onError(String msg, int modErrCode, int operateErrCode) {
                callBack.onError(msg, modErrCode, operateErrCode);
            }
        });
    }

    public static Maybe<ResetPasswordResponse> resetAccountPwd(String mobilePhone, String newPwd, String verifyCode) {
        if (Utils.verifyPhoneNumber(mobilePhone) && Utils.verifyPwd(newPwd)) {
            ResetPwdReq resetPwdReq = new ResetPwdReq(ResetPasswordResponse.class, mobilePhone, EncryptUtils.encryptMD5ToString(newPwd), verifyCode);
            return resetPwdReq.sendRequest();
        }
        return Maybe.empty();
    }

    /**
     * 更新用户信息，只支持用昵称和头像
     *
     * @param name     NickName
     * @param avatar   头像的网络地址
     * @param callBack 回调函数
     */
    public static void updateUserInfo(String name, String avatar, final RequestCallBack<User> callBack) {
        final UpdateUserInfoReq updateUserInfoReq = new UpdateUserInfoReq(UpdateUserInfoResponse.class, name, avatar);
        updateUserInfoReq.sendRequest().doOnSuccess(new Consumer<UpdateUserInfoResponse>() {
            @Override
            public void accept(@NonNull UpdateUserInfoResponse updateUserInfoResponse) throws Exception {
                loginUser.avatarURL = updateUserInfoResponse.user.avatarURL;
                loginUser.name = updateUserInfoResponse.user.name;
                loginUser.save();
            }
        }).subscribe(new ModSubscriber<UpdateUserInfoResponse>() {
            @Override
            public void _onSuccess(UpdateUserInfoResponse updateUserInfoResponse) {
                callBack.onSuccess(loginUser);
            }

            @Override
            public void _onError(String msg, int modErrCode, int operateErrCode) {
                callBack.onError(msg, modErrCode, operateErrCode);
            }
        });
    }

    /**
     * 获取产品信息列表
     */
    public static Maybe<GetProductResponse> getProductList() {
        GetProductListReq productListReq = new GetProductListReq();
        return productListReq.sendRequest();
    }

    public static void getProductList(final RequestCallBack<GetProductResponse> callBack) {
        getProductList().subscribe(new ModSubscriber<GetProductResponse>() {
            @Override
            public void _onSuccess(GetProductResponse getProductResponse) {
                callBack.onSuccess(getProductResponse);
            }

            @Override
            public void _onError(String msg, int modErrCode, int operateErrCode) {
                callBack.onError(msg, modErrCode, operateErrCode);
            }
        });
    }

    public static Maybe<PlaceOrderResponse> payOrder(int type, int productId, String ext) {
        PayOrderReq payOrderReq = new PayOrderReq(type, productId, ext, "");
        return payOrderReq.sendRequest();
    }

    public static void payOrder(int type, int productId, String ext,
                                final RequestCallBack<PlaceOrderResponse> callBack) {
        payOrder(type, productId, ext).subscribe(new ModSubscriber<PlaceOrderResponse>() {
            @Override
            public void _onSuccess(PlaceOrderResponse placeOrderResponse) {
                callBack.onSuccess(placeOrderResponse);
            }

            @Override
            public void _onError(String msg, int modErrCode, int operateErrCode) {
                callBack.onError(msg, modErrCode, operateErrCode);
            }
        });
    }

    /**
     * 接入项目业务请求接口
     *
     * @param requestName 请求业务接口的名称，要和后台协商确定
     * @param data        业务后台所需要的参数，需要把这个参数转化为二进制数据
     * @param callBack    回调函数，自己解析结果
     */
    public static void sendBusinessRequest(String requestName, byte[] data, final RequestCallBack<byte[]> callBack) {
        BinaryReq request = new BinaryReq(requestName, data);
        request.sendRequest().subscribe(new ModSubscriber<byte[]>() {
            @Override
            public void _onSuccess(byte[] bytes) {
                callBack.onSuccess(bytes);
            }

            @Override
            public void _onError(String msg, int modErrCode, int operateErrCode) {
                callBack.onError(msg, modErrCode, operateErrCode);
            }
        });
    }
}
