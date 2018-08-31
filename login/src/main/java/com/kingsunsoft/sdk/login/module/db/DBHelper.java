package com.kingsunsoft.sdk.login.module.db;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kingsunsoft.sdk.login.module.User;

/**
 * Created by AllynYonge on 11/10/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "user.db";

    public DBHelper(Application application) {
        super(application, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(User.CREATE_USER);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + User.TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public synchronized void close() {
        super.close();
    }

}
