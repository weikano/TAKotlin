package com.kingsunsoft.sdk.login.module;

import com.kingsunsoft.sdk.mod.Header;

/**
 * Created by AllynYonge on 11/10/2017.
 */

public class Mapper {
    public static User mapperUserEntity(com.kingsunsoft.sdk.modsdk.User userInfo, Header header){
        User userEntity = new User();
        userEntity.account = userInfo.account;
        userEntity.avatarURL = userInfo.avatarURL;
        userEntity.name = userInfo.name;
        userEntity.userId = userInfo.userId;
        if (header != null){
            userEntity.token = header.token;
            userEntity.refreshToken = header.refreshToken;
            userEntity.serverTime = header.svrTimestamp;
        }
        return userEntity;
    }
}
