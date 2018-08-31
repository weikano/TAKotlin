package com.kingsunsoft.sdk.login.net.converter;

import com.qq.tars.protocol.tars.TarsInputStream;
import com.kingsunsoft.sdk.login.net.encryption.EncryptionManager;
import com.kingsunsoft.sdk.mod.Header;
import com.kingsunsoft.sdk.mod.Response;
import com.kingsunsoft.sdk.mod.Result;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Created by hu.yang on 2017/5/5.
 */

public class TarsResponseBodyConverter implements Converter<ResponseBody, Response> {
    private Type type;
    public TarsResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public Response convert(ResponseBody value) throws IOException {
        byte[] bytes = EncryptionManager.getEncyption().decryption(value.bytes());
        TarsInputStream tarsInputStream = new TarsInputStream(bytes);
        tarsInputStream.setServerEncoding("UTF-8");
        Response response = new Response();
        response.header = new Header();
        response.result = new Result();
        response.readFrom(tarsInputStream);
        return response;
    }

    /*public void replaceHeader(Header header){
        UserManager userManager = UserManager.getInstance();
        if (userManager.getLoginUser().isPresent()){
            UserEntity loginUser = userManager.getLoginUser().get();
            userManager.setServerTime(header.getSvrTimestamp());
            loginUser.account = header.account;
            loginUser.userId = header.userId;
            loginUser.token = header.getToken();
            loginUser.refreshToken = header.getRefreshToken();
            loginUser.save();
        }
    }*/
}
