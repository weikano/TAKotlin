package com.kingsunsoft.sdk.login.net.converter;

import com.qq.tars.protocol.tars.TarsOutputStream;
import com.kingsunsoft.sdk.mod.Request;
import com.kingsunsoft.sdk.login.net.encryption.EncryptionManager;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

/**
 * Created by hu.yang on 2017/5/5.
 */

public class TarsRequestBodyConverter<T extends Request> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/octet-stream");
    @Override
    public RequestBody convert(T value) throws IOException {
        TarsOutputStream outputStream = new TarsOutputStream();
        value.writeTo(outputStream);
        return RequestBody.create(MEDIA_TYPE, EncryptionManager.getEncyption().encryption(outputStream.toByteArray()));
    }
}
