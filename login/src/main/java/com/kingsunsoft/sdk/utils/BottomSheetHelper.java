package com.kingsunsoft.sdk.utils;

import android.app.Activity;
import android.content.DialogInterface;

import com.cocosw.bottomsheet.BottomSheet;
import com.kingsunsoft.sdk.R;

public class BottomSheetHelper {

    public interface OnClickListener {
        void onClick(int which);
    }

    public static BottomSheet showBottomSheet(Activity context, String title, String[] lists, final OnClickListener callback) {
        BottomSheet.Builder builder = new BottomSheet.Builder(context);
        for (int i = 0; i < lists.length; i++) {
            builder.sheet(i, lists[i]);
        }
        return builder
                .title(title)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callback != null) {
                            callback.onClick(i);
                        }
                    }
                })
                .show();
    }

    public static BottomSheet showBottomSheet(Activity context, String title, final OnClickListener callback, int sheets) {
        return new BottomSheet.Builder(context)
                .title(title)
                .sheet(sheets)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callback != null) {
                            callback.onClick(i);
                        }
                    }
                })
                .show();
    }

    public static BottomSheet showPayBottomSheet(Activity context, String title, int[] icons, final OnClickListener callback){
        BottomSheet.Builder builder = new BottomSheet.Builder(context);
        int[] res = new int[]{R.string.wechat,R.string.aliPay};
        for(int i = 0; i<icons.length;i++){
            builder.sheet(i,icons[i],res[i]);
        }
        return builder
                .title(title)
                .listener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (callback != null) {
                            callback.onClick(i);
                        }
                    }
                })
                .show();
    }
}
