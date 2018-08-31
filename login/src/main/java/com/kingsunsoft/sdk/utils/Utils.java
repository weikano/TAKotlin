package com.kingsunsoft.sdk.utils;

import android.app.Application;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by AllynYonge on 13/10/2017.
 */

public class Utils {
    private static Application app;

    public static void init(Application application){
        app = application;
    }

    public static void showToast(String message){
        Toast.makeText(app, message, Toast.LENGTH_SHORT);
    }

    public static void ss(String password){

    }

    public static boolean verifyPwd(String password){
        if (password.matches("^[0-9A-Za-z]{6,20}$"))
            return true;

        if (password.length() < 6 || password.length() > 20){
            ToastUtils.showShort("请输入长度为6-20位之间的密码！");
        } else {
            ToastUtils.showShort("密码只支持数字和字母！");
        }
        return false;
    }

    public static boolean verifyPhoneNumber(String phone){
        if (phone.matches("^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$")){
            return true;
        }
        ToastUtils.showShort("手机号错误");
        return false;
    }

}
