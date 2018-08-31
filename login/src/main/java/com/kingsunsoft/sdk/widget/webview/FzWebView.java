package com.kingsunsoft.sdk.widget.webview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.kingsunsoft.sdk.R;
import com.kingsunsoft.sdk.login.AppInfo;
import com.kingsunsoft.sdk.login.KingSunManager;
import com.kingsunsoft.sdk.login.module.User;
import com.kingsunsoft.sdk.login.net.utils.ModSubscriber;
import com.kingsunsoft.sdk.mod.Result;
import com.kingsunsoft.sdk.modsdk.GetProductResponse;
import com.kingsunsoft.sdk.pay.PayResultListener;
import com.kingsunsoft.sdk.pay.PayUtils;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;

import org.json.JSONException;
import org.json.JSONObject;

public class FzWebView extends BridgeWebView {
    private static final String TAG = FzWebView.class.getSimpleName();
    private ProgressBar mProgressBar;
    private OnTitleChangeListener onTitleChangeListener;
    private onProgressListener mOnProgressListener;
    private ReloadNativeListener reloadNativeListener;
    private CallBackFunction payCallBackFuction;
    private PayResultListener payResultListener;

    public FzWebView(Context context) {
        super(context);
        initWebView();
        initProgressBar(null);
    }

    public FzWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.fzWebView, 0, 0);
        initWebView();
        if (ta.getBoolean(R.styleable.fzWebView_isProgress, true))
            initProgressBar(attrs);
    }

    private void initWebView() {
        if (Build.VERSION.SDK_INT > 10 && Build.VERSION.SDK_INT < 17) {
            fixWebView();
        }
        setWebChromeClient(new CustomChromeClient());

        registerJSBridgeHandler();

        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // zoom支持
        getSettings().setBuiltInZoomControls(true);
        getSettings().setDisplayZoomControls(false);

        getSettings().setDomStorageEnabled(true);
        // 设置缓存总容量为 8mb
        getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        // 缓存路径
        String appCachePath = getContext().getCacheDir().getAbsolutePath();
        getSettings().setAppCachePath(appCachePath);
        getSettings().setAllowFileAccess(true);
        getSettings().setAppCacheEnabled(true);
        // 设置缓存策略
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        setDefaultHandler(new KingSunHandler());
        // 在UserAgent中添加标识
        getSettings().setUserAgentString(getSettings().getUserAgentString() + WebViewUtils.getAppName(getContext().getPackageName()));
    }

    private void initProgressBar(AttributeSet attrs) {
        mProgressBar = new ProgressBar(getContext(), attrs, android.R.attr.progressBarStyleHorizontal);
        mProgressBar.setLayoutParams(new ViewGroup.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, dp2px(3)));
        Drawable drawable = getContext().getResources().getDrawable(R.drawable.webview_progress_bar_states);
        mProgressBar.setProgressDrawable(drawable);
        addView(mProgressBar);
    }

    @TargetApi(11)
    private void fixWebView() {
        // We hadn't use addJavascriptInterface, but WebView add "searchBoxJavaBridge_" to mJavaScriptObjects
        // below API 17 by default:
        // mJavaScriptObjects.put(SearchBoxImpl.JS_INTERFACE_NAME, mSearchBox);
        removeJavascriptInterface("searchBoxJavaBridge_");
    }

    protected void registerJSBridgeHandler() {
        registerGetUserFuc();
        registerGetAppInfoFuc();
        registerCallPay();
        getProductInfo();
    }

    private void registerGetAppInfoFuc() {
        registerHandler("getAppInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                AppInfo.init(getContext());
                JSONObject resultJson = new JSONObject();
                try {
                    resultJson.put("ret", true);
                    JSONObject appInfoJson = new JSONObject();
                    appInfoJson.put("appId", AppInfo.appId);
                    appInfoJson.put("sdkName", AppInfo.sdkName);
                    appInfoJson.put("sdkVersion", AppInfo.sdkVersion);
                    appInfoJson.put("brand", AppInfo.brand);
                    appInfoJson.put("model", AppInfo.model);
                    appInfoJson.put("versionName", AppInfo.versionName);
                    appInfoJson.put("versionCode", AppInfo.versionCode);
                    appInfoJson.put("buildNum", AppInfo.buildNum);
                    appInfoJson.put("channel", AppInfo.channel);
                    resultJson.put("data", appInfoJson);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                function.onCallBack(resultJson.toString());
            }
        });
    }

    private void registerGetUserFuc() {
        registerHandler("getUser", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                User userInfo = KingSunManager.getLoginUser();
                if (userInfo.userId != 0l) {
                    function.onCallBack(toJson(0, userInfo));
                } else {
                    function.onCallBack(toJson(-1, "user not exit"));
                }
            }
        });
    }

    private void registerCallPay() {
        registerHandler("callPay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                FzWebView.this.payCallBackFuction = function;
                JSONObject json = null;
                try {
                    json = new JSONObject(data);
                    int productId = (int) json.get("productId");
                    String name = (String) json.get("name");
                    String ext = (String) json.get("ext");
                    if (productId != 0){
                        PayUtils.showPayBottomSheet((Activity) getContext(), name, productId, ext, payResultListener);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                function.onCallBack(json.toString());
            }
        });
    }

    private void getProductInfo(){
        registerHandler("getProductInfo", new BridgeHandler() {
            @Override
            public void handler(String data, final CallBackFunction function) {
                KingSunManager.getProductList().subscribe(new ModSubscriber<GetProductResponse>() {
                    @Override
                    public void _onSuccess(GetProductResponse getProductResponse) {
                        Log.d(TAG, "GetProductInfo:" + new Gson().toJson(getProductResponse.products));
                        function.onCallBack(toJson(0, getProductResponse.products));
                    }

                    @Override
                    public void _onError(String msg, int modErrCode, int operateErrCode) {
                        Log.d(TAG, "GetProductInfo:" + msg);
                        function.onCallBack(toJson(modErrCode, msg));
                    }
                });
            }
        });
    }

    private String toJson(int retCode, Object object){
        Gson gson = new Gson();
        Result result = new Result(retCode, gson.toJson(object));
        return gson.toJson(result);
    }

    private int dp2px(final float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public class CustomChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(com.tencent.smtt.sdk.WebView webView, String s, String s1, com.tencent.smtt.export.external.interfaces.JsResult jsResult) {
            return super.onJsAlert(webView, s, s1, jsResult);
        }

        @Override
        public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int newProgress) {
            super.onProgressChanged(webView, newProgress);
            if (mProgressBar != null) showProgress(newProgress);
        }

        @Override
        public boolean onJsPrompt(com.tencent.smtt.sdk.WebView webView, String s, String s1, String s2, com.tencent.smtt.export.external.interfaces.JsPromptResult jsPromptResult) {
            return super.onJsPrompt(webView, s, s1, s2, jsPromptResult);
        }

        @Override
        public void onReceivedTitle(com.tencent.smtt.sdk.WebView webView, String title) {
            super.onReceivedTitle(webView, title);
            if (onTitleChangeListener != null) {
                onTitleChangeListener.onSetTitle(title);
            }
        }

        private void showProgress(int progress) {
            if (progress >= 100) {
                mProgressBar.setVisibility(View.GONE);
                if (mOnProgressListener != null) mOnProgressListener.loadComplete();
            } else {
                if (mProgressBar.getVisibility() == View.GONE) {
                    mProgressBar.setVisibility(View.VISIBLE);
                }
                mProgressBar.setProgress(progress);
            }
        }

    }

    public CallBackFunction getPayCallBackFunction(){
        return payCallBackFuction;
    }

    public interface OnTitleChangeListener {
        void onSetTitle(String title);
    }

    public interface ReloadNativeListener {
        void onReload();
    }

    public interface onProgressListener {
        void loadComplete();
    }

    public void setOnTitleChangeListener(OnTitleChangeListener l) {
        this.onTitleChangeListener = l;
    }

    public void setOnProgressListener(onProgressListener listener) {
        mOnProgressListener = listener;
    }

    public void setOnReloadNativeListener(ReloadNativeListener listener) {
        this.reloadNativeListener = listener;
    }

    public void setOnPayResultListener(PayResultListener listener) {
        this.payResultListener = listener;
    }
}
