package com.kingsunsoft.sdk.widget.webview;

import android.graphics.Bitmap;
import android.util.Log;

import com.tencent.smtt.sdk.WebViewClient;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import static android.content.ContentValues.TAG;

/**
 * Created by bruce on 10/28/15.
 */
public class BridgeWebViewClient extends WebViewClient {
    private BridgeWebView webView;

    public BridgeWebViewClient(BridgeWebView webView) {
        this.webView = webView;
    }

    @Override
    public boolean shouldOverrideUrlLoading(com.tencent.smtt.sdk.WebView webView, String url) {
        Class ActClass = BridgeWebView.class.cast(webView).getNativeMapper().get(url);
        if (ActClass != null){
            this.webView.loadNativePage(ActClass);
            return true;
        }
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (url.startsWith(BridgeUtil.YY_RETURN_DATA)) { // 如果是返回数据
            BridgeWebView.class.cast(webView).handlerReturnData(url);
            return true;
        } else if (url.startsWith(BridgeUtil.YY_OVERRIDE_SCHEMA)) { //
            BridgeWebView.class.cast(webView).flushMessageQueue();
            return true;
        } else {
            return super.shouldOverrideUrlLoading(webView, url);
        }
    }

    @Override
    public void onPageStarted(com.tencent.smtt.sdk.WebView webView, String s, Bitmap bitmap) {
        super.onPageStarted(webView, s, bitmap);
    }

    @Override
    public void onPageFinished(com.tencent.smtt.sdk.WebView webView, String url) {
        super.onPageFinished(webView, url);

        if (BridgeWebView.toLoadJs != null) {
            BridgeUtil.webViewLoadLocalJs(webView, BridgeWebView.toLoadJs);
        }
        BridgeWebView bridgeWebView = BridgeWebView.class.cast(webView);
        //
        if (bridgeWebView.getStartupMessage() != null) {
            for (Message m : bridgeWebView.getStartupMessage()) {
                bridgeWebView.dispatchMessage(m);
            }
            bridgeWebView.setStartupMessage(null);
        }

        if (this.webView.getPageLoadCallBack() != null) {
            this.webView.getPageLoadCallBack().loadFinish(url);
        }
    }

    @Override
    public void onReceivedError(com.tencent.smtt.sdk.WebView webView, int i, String description, String failingUrl) {
        super.onReceivedError(webView, i, description, failingUrl);
        Log.d(TAG, "onReceivedError[errorCode: " + i + ", description: " + description + ", failingUrl: " + failingUrl + "]");
        try {
            webView.stopLoading();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        try {
            webView.clearView();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        if (this.webView.getPageLoadCallBack() != null) {
            this.webView.getPageLoadCallBack().loadError(failingUrl);
        }
    }


}