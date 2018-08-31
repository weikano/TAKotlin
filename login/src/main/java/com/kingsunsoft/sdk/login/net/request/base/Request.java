package com.kingsunsoft.sdk.login.net.request.base;

import android.util.Log;

import com.google.gson.Gson;
import com.kingsunsoft.sdk.login.AppInfo;
import com.kingsunsoft.sdk.login.KingSunManager;
import com.kingsunsoft.sdk.login.exception.ModResultException;
import com.kingsunsoft.sdk.login.exception.ClientException;
import com.kingsunsoft.sdk.mod.Device;
import com.kingsunsoft.sdk.mod.Header;
import com.kingsunsoft.sdk.mod.ModConst;
import com.kingsunsoft.sdk.mod.Platform;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.login.module.User;
import com.kingsunsoft.sdk.login.net.Api;

import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.MaybeTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import static com.kingsunsoft.sdk.mod.ModConst.ERR_BOOK_RESOURCE_LOST;
import static com.kingsunsoft.sdk.mod.ModConst.ERR_BOOK_UNPUBLISH;
import static com.kingsunsoft.sdk.mod.ModConst.ERR_KEY_NOTFOUND;

/**
 * Created by hu.yang on 2017/5/8.
 */

public abstract class Request extends com.kingsunsoft.sdk.mod.Request {
    private static final String TAG = Request.class.getSimpleName();
    private static AtomicInteger mRequestId = new AtomicInteger(1);
    public final Api api = KingSunManager.getApi();

    protected Maybe<Response> call() {
        return getRequestSingle(api)
                .subscribeOn(Schedulers.io())
                .compose(this.handleResult())
                .doOnSuccess(new Consumer<Response>() {
                    @Override
                    public void accept(@NonNull Response response) throws Exception {
                        replaceHeader(response.header);
                    }
                });
    }

    private <T extends Response> MaybeTransformer<T, T> handleResult() {
        return new MaybeTransformer<T, T>() {
            @Override
            public MaybeSource<T> apply(@NonNull Maybe<T> upstream) {
                return upstream.flatMap(new Function<T, MaybeSource<? extends T>>() {
                    @Override
                    public MaybeSource<? extends T> apply(@NonNull T t) throws Exception {
                        Log.i("Request:Header", new Gson().toJson(getHeader()));
                        Log.i("Request:Result", new Gson().toJson(t.getResult()));
                        Log.i("Request","-----body and header-----");
                        if (t.getResult().retCode == 0) {
                            return Maybe.just(t);
                        } else if (100000 < t.getResult().retCode && t.getResult().retCode < 500000) {
                            if (t.getResult().retCode == ModConst.ERR_TOKEN_EXPIRE
                                    || t.getResult().retCode == ModConst.ERR_FUNCNAME
                                    || t.getResult().retCode == ModConst.ERR_ACCOUNT_ABNORMAL) {
                                KingSunManager.logout();
                            }

                            if (t.getResult().retCode == ERR_BOOK_UNPUBLISH||
                                    t.getResult().retCode == ERR_BOOK_RESOURCE_LOST||
                                    t.getResult().retCode == ERR_KEY_NOTFOUND) {
                                return Maybe.error(new ClientException(t.result.getMsg(), t.result.getRetCode()));
                            }

                            return Maybe.error(new ModResultException(t.getResult().getMsg(), t.getResult().retCode));
                        } else {
                            return Maybe.error(new ClientException(t.result.getMsg(), t.result.getRetCode()));
                        }
                    }
                });
            }
        };
    }

    public Header makeHeader() {
        Header header = new Header();
        User loginUser = KingSunManager.getLoginUser();
        if (loginUser != null) {
            header.setToken(loginUser.token);
            header.setRefreshToken("");
            header.setAccount(loginUser.account);
            header.setUserId(loginUser.userId);
        }
        header.setPlatform(Platform.Android.value());
        header.setAppId(AppInfo.appId);
        header.setDevice(new Device(AppInfo.sdkName, AppInfo.sdkVersion,
                AppInfo.brand, AppInfo.model, AppInfo.versionName,
                AppInfo.versionCode, AppInfo.buildNum, AppInfo.channel));
        header.setRequestId(mRequestId.incrementAndGet());
        return header;
    }

    public void replaceHeader(Header header) {
        setHeader(header);
        User loginUser = KingSunManager.getLoginUser();
        if (loginUser != null) {
            loginUser.serverTime = header.getSvrTimestamp();
            loginUser.account = header.account;
            loginUser.userId = header.userId;
            loginUser.token = header.getToken();
            loginUser.refreshToken = header.getRefreshToken();
        }
    }

    protected abstract Maybe<Response> getRequestSingle(Api api);
}
