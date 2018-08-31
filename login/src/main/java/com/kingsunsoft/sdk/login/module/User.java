package com.kingsunsoft.sdk.login.module;

import android.content.ContentValues;
import android.database.Cursor;

import com.kingsunsoft.sdk.login.KingSunManager;
import com.kingsunsoft.sdk.login.module.db.Dao;

/**
 * Created by AllynYonge on 10/10/2017.
 */

public class User extends Dao<User>{
    public final static String TABLE_NAME = "userEntity";
    public final static String CREATE_USER = "create table if not exists "
            + TABLE_NAME
            + "(userId integer primary key autoincrement, "
            + "account text, "
            + "avatarURL text, "
            + "refreshToken text, "
            + "token text, "
            + "serverTime integer, "
            + "name text)";

    public long userId = 0L;
    public String account = "";
    public String name = "";
    public String avatarURL = "";
    public String token = "";
    public String refreshToken = "";
    public long serverTime = 0l;

    @Override
    public User query() {
        Cursor query = KingSunManager.getDbHelper().getReadableDatabase().query(TABLE_NAME, new String[]{"userId", "account", "avatarURL",
                        "name","refreshToken","token","serverTime"},
                "token != ?", new String[]{""}, null, null, null);
        if (query.moveToNext()){
            this.userId = query.getLong(query.getColumnIndex("userId"));
            this.account = query.getString(query.getColumnIndex("account"));
            this.avatarURL = query.getString(query.getColumnIndex("avatarURL"));
            this.name = query.getString(query.getColumnIndex("name"));
            this.refreshToken = query.getString(query.getColumnIndex("refreshToken"));
            this.token = query.getString(query.getColumnIndex("token"));
            this.serverTime = query.getLong(query.getColumnIndex("serverTime"));
            return this;
        }
        return this;
    }

    @Override
    public boolean save() {
        Cursor query = KingSunManager.getDbHelper().getReadableDatabase().query(TABLE_NAME, new String[]{"userId"},
                "userId = ?", new String[]{userId+""}, null, null, null);
        ContentValues cValue = new ContentValues();
        cValue.put("userId", userId);
        cValue.put("account", account);
        cValue.put("avatarURL", avatarURL);
        cValue.put("name", name);
        cValue.put("refreshToken", refreshToken);
        cValue.put("token", token);
        cValue.put("serverTime", serverTime);
        if (query.moveToNext()){
            query.close();
            return KingSunManager.getDbHelper().getWritableDatabase().update(TABLE_NAME, cValue, "userId = ?", new String[]{userId+""}) >= 0;
        } else {
            return KingSunManager.getDbHelper().getWritableDatabase().insert(TABLE_NAME, null, cValue) >= 0;
        }
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAll() {

    }
}
