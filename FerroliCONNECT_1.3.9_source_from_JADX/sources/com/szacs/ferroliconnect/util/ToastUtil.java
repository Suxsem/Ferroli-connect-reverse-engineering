package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.widget.Toast;
import p110io.reactivex.Observable;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.functions.Consumer;

public class ToastUtil {
    public static void showShortToast(final Context context, final String str) {
        Observable.just(str).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            public void accept(String str) throws Exception {
                Toast.makeText(context, str, 1).show();
            }
        });
    }

    public static void showCenterToast(Context context, String str) {
        Toast makeText = Toast.makeText(context, str, 1);
        makeText.setGravity(17, 0, 0);
        makeText.show();
    }
}
