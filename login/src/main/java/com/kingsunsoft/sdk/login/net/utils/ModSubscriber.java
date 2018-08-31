package com.kingsunsoft.sdk.login.net.utils;

import com.kingsunsoft.sdk.login.exception.ModResultException;
import com.kingsunsoft.sdk.login.exception.ClientException;
import com.qq.tars.rpc.exc.ServerException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.MaybeObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by AllynYonge on 23/10/2017.
 */

public abstract class ModSubscriber<T> implements MaybeObserver<T> {

    @Override
    public void onError(@NonNull Throwable t) {
        int modErrCode =  -1;
        int clientErrCode = 0;

        t.printStackTrace();

        //MOD相关错误处理
        if (t instanceof ModResultException){
            modErrCode = ModResultException.class.cast(t).getErrCode();
        }

        if (t instanceof ClientException){
            clientErrCode = ClientException.class.cast(t).getErrCode();
        }

        //其他错误处理
        String msg = t.getMessage();
        if (t instanceof ServerException) {
            msg = t.getMessage();
        }  else if (t instanceof UnknownHostException) {
            msg = "没有网络...";
        } else if (t instanceof SocketTimeoutException) {
            msg = "请求超时...";
        }else{
            msg = "请求失败，请稍后重试...";
        }

        _onError(msg, modErrCode, clientErrCode);
    }

    @Override
    public void onSuccess(@NonNull T t) {
        _onSuccess(t);
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    @Override
    public void onComplete() {

    }

    public abstract void _onSuccess(T t);

    public abstract void _onError(String msg, int modErrCode, int operateErrCode);

}
