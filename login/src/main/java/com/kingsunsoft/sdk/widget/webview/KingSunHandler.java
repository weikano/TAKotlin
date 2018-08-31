package com.kingsunsoft.sdk.widget.webview;


import android.util.Log;

/**
 * Created by AllynYonge on 22/07/2017.
 */

public class KingSunHandler implements BridgeHandler {
    private static final String TAG = KingSunHandler.class.getSimpleName();

    @Override
    public void handler(String data, CallBackFunction function) {
        Log.d(TAG, data);
    }
}
