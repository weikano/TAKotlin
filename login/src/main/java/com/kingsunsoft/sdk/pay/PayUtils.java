package com.kingsunsoft.sdk.pay;

import android.app.Activity;

import com.blankj.utilcode.util.AppUtils;
import com.kingsunsoft.sdk.R;
import com.kingsunsoft.sdk.login.KingSunManager;
import com.kingsunsoft.sdk.login.net.utils.ModSubscriber;
import com.kingsunsoft.sdk.modsdk.AliPayInfo;
import com.kingsunsoft.sdk.modsdk.PlaceOrderResponse;
import com.kingsunsoft.sdk.modsdk.WXPayInfo;
import com.kingsunsoft.sdk.utils.BottomSheetHelper;

import java.lang.reflect.Field;

/**
 * Created by AllynYonge on 27/12/2017.
 */

public class PayUtils {

    private static void payAli(Activity activity, AliPayInfo aliPayInfo, AliPayReq2.OnAliPayListener onAliPayListener){
        AliPayReq2 aliPayReq2 = new AliPayReq2.Builder().with(activity).setSignedAliPayOrderInfo(aliPayInfo.payInfo).create();
        aliPayReq2.setOnAliPayListener(onAliPayListener);
        PayAPI.getInstance().sendPayRequest(aliPayReq2);
    }

    private static void payWeiXin(Activity activity, WXPayInfo wxPayInfo){
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(activity) //activity instance
                .setAppId(wxPayInfo.appid) //wechat pay AppID
                .setPartnerId(wxPayInfo.partnerid)//wechat pay partner id
                .setPrepayId(wxPayInfo.prepayid)//pre pay id
                .setNonceStr(wxPayInfo.noncestr)
                .setTimeStamp(wxPayInfo.timestamp)//startTime stamp
                .setSign(wxPayInfo.sign)//sign
                .create();
        PayAPI.getInstance().sendPayRequest(wechatPayReq);
    }

    public static void showPayBottomSheet(final Activity context, String title, final int productId, final String ext, final PayResultListener payResultListener){
        int[] res = new int[]{R.mipmap.pay_wechat,R.mipmap.pay_ali};
        BottomSheetHelper.showPayBottomSheet(context, title, res, new BottomSheetHelper.OnClickListener() {
            @Override
            public void onClick(int which) {
                int payType = 0;
                switch (which){
                    case 0:
                        //请求网络获取签名
                        payType = 1;
                        break;
                    case 1:
                        //请求网络获取签名
                        payType = 2;
                        break;
                }

                KingSunManager.payOrder(payType, productId, ext).subscribe(new ModSubscriber<PlaceOrderResponse>() {
                    @Override
                    public void _onSuccess(PlaceOrderResponse placeOrderResponse) {
                        if (placeOrderResponse.payType == 2){
                            payAli(context, placeOrderResponse.aliPayInfo, new AliPayReq2.OnAliPayListener() {
                                @Override
                                public void onPaySuccess(String resultInfo) {
                                    if (payResultListener != null) payResultListener.paySuccess();
                                }

                                @Override
                                public void onPayFailure(String resultInfo) {
                                    if (payResultListener != null) payResultListener.payFail("aliPayResult:" + resultInfo);
                                }

                                @Override
                                public void onPayConfirming(String resultInfo) {
                                    if (payResultListener != null) payResultListener.payFail("aliPayResult:" + resultInfo);
                                }

                                @Override
                                public void onPayCheck(String status) {

                                }
                            });
                        } else if (placeOrderResponse.payType == 1){
                            payWeiXin(context, placeOrderResponse.wxPayInfo);
                            StringBuilder builder = new StringBuilder();
                            builder.append(AppUtils.getAppPackageName()).append(".").append("wxapi.WXPayEntryActivity");
                            try {
                                Class<?> aClass = Class.forName(builder.toString());
                                Field filed = aClass.getDeclaredField("payResultListener");
                                filed.setAccessible(true);
                                filed.set(null, payResultListener);
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            } catch (NoSuchFieldException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void _onError(String msg, int modErrCode, int operateErrCode) {
                        StringBuilder builder = new StringBuilder();
                        builder.append("msg:" + msg + "\r\n");
                        builder.append("modErrCode:" + modErrCode + "\r\n");
                        builder.append("operateErrCode:" + operateErrCode);
                        if (payResultListener != null) payResultListener.payFail(builder.toString());
                    }
                });
            }
        });
    }

    public static void WXPayResult(){

    }
}
