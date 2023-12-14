package com.alibaba.sdk.android.push;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.alibaba.sdk.android.push.popup.OnPushParseFailedListener;
import com.alibaba.sdk.android.push.popup.PopupNotifyClick;
import com.alibaba.sdk.android.push.popup.PopupNotifyClickListener;
import java.util.Map;

public abstract class AndroidPopupActivity extends Activity implements OnPushParseFailedListener {
    static final String TAG = "AndroidPopupActivity";
    private PopupNotifyClick popupNotifyClick = new PopupNotifyClick(new MyPopupNotifyClickListener());

    public void onNotPushData(Intent intent) {
    }

    public void onParseFailed(Intent intent) {
    }

    /* access modifiers changed from: protected */
    public abstract void onSysNoticeOpened(String str, String str2, Map<String, String> map);

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.popupNotifyClick.onCreate(this, getIntent());
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.popupNotifyClick.onNewIntent(intent);
    }

    private class MyPopupNotifyClickListener implements PopupNotifyClickListener, OnPushParseFailedListener {
        private MyPopupNotifyClickListener() {
        }

        public void onSysNoticeOpened(String str, String str2, Map<String, String> map) {
            AndroidPopupActivity.this.onSysNoticeOpened(str, str2, map);
        }

        public void onNotPushData(Intent intent) {
            AndroidPopupActivity.this.onNotPushData(intent);
        }

        public void onParseFailed(Intent intent) {
            AndroidPopupActivity.this.onParseFailed(intent);
        }
    }
}
