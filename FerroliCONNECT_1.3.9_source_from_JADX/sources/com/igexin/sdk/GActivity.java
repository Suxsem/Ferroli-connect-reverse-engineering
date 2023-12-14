package com.igexin.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.C1356s;
import com.igexin.push.core.p047a.C1257f;

public class GActivity extends Activity {
    public static final String TAG = "com.igexin.sdk.GActivity";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, C1257f.m1711a().mo14471a((Context) this));
        if (intent != null) {
            try {
                if (intent.hasExtra(PushConsts.CMD_ACTION) && intent.hasExtra("isSlave")) {
                    intent2.putExtra(PushConsts.CMD_ACTION, intent.getStringExtra(PushConsts.CMD_ACTION));
                    intent2.putExtra("isSlave", intent.getBooleanExtra("isSlave", false));
                    if (intent.hasExtra("op_app")) {
                        intent2.putExtra("op_app", intent.getStringExtra("op_app"));
                    }
                    C1179b.m1354a("GActivity action = " + intent.getStringExtra(PushConsts.CMD_ACTION) + ", isSlave = " + intent.getBooleanExtra("isSlave", false));
                }
            } catch (Exception e) {
                C1179b.m1354a(TAG + "|put extra exception" + e.toString());
            }
        }
        C1356s.m2138a().mo14782a((Context) this, intent2);
        C1179b.m1354a(TAG + "|start PushService from GActivity");
        finish();
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
    }
}
