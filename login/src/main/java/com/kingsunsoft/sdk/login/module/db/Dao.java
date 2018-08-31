package com.kingsunsoft.sdk.login.module.db;

/**
 * Created by AllynYonge on 11/10/2017.
 */

public abstract class Dao<T> {
    public abstract T query();
    public abstract boolean save();
    public abstract void delete(T t);
    public abstract void deleteAll();
}
