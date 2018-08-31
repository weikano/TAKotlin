package com.kingsunsoft.sdk.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.NetworkUtils;
import com.blankj.utilcode.util.Utils;


public class AppInfo {

    // device info
    public static String sdkName;           // android 系统版本名，eg：4.4.1
    public static int sdkVersion;           // android 系统版本号，整形 eg:17
    public static String brand;             // 手机品牌，eg：三星（SAMSUNG）
    public static String model;             // 手机型号，eg：S4, iPhone,3,1, iPhone,3,2

    // client info
    public static String versionName;   // 客户端版本名，eg：2.x.x,<YXY>_[1-9]_[0-9]_[0-9]
    public static int versionCode;      // 客户端versionCode, versionName->int
    public static int buildNum;         // build号 xxxx
    public static String channel;       // 渠道号 TBT,91SZ,APP,
    public static String appId;

    // net info
    // 客户端网络类型，0=UN_DETECT, 1=WIFI, 2=CMWAP, 3=CMNET, 4=UNIWAP, 5=UNINET, 6=WAP3G,
    // 7=NET3G, 8=CTWAP, 9=CTNET, 10=UNKNOWN, 11=UNKNOW_WAP, 12=NO_NETWORK

    public static void init(Context context) {
        Utils.init(context);
        sdkName = Build.VERSION.RELEASE;
        sdkVersion = DeviceUtils.getSDKVersionCode();
        brand = Build.BRAND;
        model = Build.MODEL;
        versionName = AppUtils.getAppVersionName();
        versionCode = AppUtils.getAppVersionCode();
        buildNum = getBuildNum(context);
        channel = getChannel(context);

        Log.d("AppInfo","init global config:" + "\n" +
                "sdkName:" + sdkName + "\n" +
                "sdkVersion:" + sdkVersion + "\n" +
                "brand:" + brand + "\n" +
                "model:" + model + "\n" +
                "versionName:" + versionName + "\n" +
                "versionCode:" + versionCode + "\n" +
                "buildNum:" + buildNum + "\n" +
                "channel:" + channel + "\n"
        );
    }


    private static final String BUILD_NUM = "BUILD_NUM";

    private static int getBuildNum(Context context) {
        int buildNum = 0;
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            //manifest里面的name值
            buildNum = ai.metaData.getInt(BUILD_NUM);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buildNum;
    }

    private static final String CHANNEL = "KING_CHANNEL";

    private static String getChannel(Context context) {
        String channelid = "";
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            //manifest里面的name值
            Object value = ai.metaData.get(CHANNEL);
            if (value != null) {
                channelid = value.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return channelid;
    }

    private static byte getNetType(){
        @SuppressLint("MissingPermission") NetworkUtils.NetworkType type = NetworkUtils.getNetworkType();
        if (type.equals(NetworkUtils.NetworkType.NETWORK_UNKNOWN)){
            return 0;
        } else if (type.equals(NetworkUtils.NetworkType.NETWORK_2G)){
            return 2;
        } else if (type.equals(NetworkUtils.NetworkType.NETWORK_3G)){
            return 3;
        } else if (type.equals(NetworkUtils.NetworkType.NETWORK_4G)){
            return 4;
        } else if (type.equals(NetworkUtils.NetworkType.NETWORK_WIFI)){
            return 9;
        } else {
            return -1;
        }
    }
}
