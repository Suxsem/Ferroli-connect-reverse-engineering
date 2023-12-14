package com.igexin.sdk;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import com.igexin.sdk.p091a.C1598a;

public class PushActivity extends Activity {
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (C1598a.m3293a().mo15290b() != null) {
            C1598a.m3293a().mo15290b().onActivityConfigurationChanged(this, configuration);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        if (C1598a.m3293a().mo15290b() != null) {
            return C1598a.m3293a().mo15290b().onActivityCreateOptionsMenu(this, menu);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        if (C1598a.m3293a().mo15290b() != null) {
            C1598a.m3293a().mo15290b().onActivityDestroy(this);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (C1598a.m3293a().mo15290b() == null || !C1598a.m3293a().mo15290b().onActivityKeyDown(this, i, keyEvent)) {
            return super.onKeyDown(i, keyEvent);
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (C1598a.m3293a().mo15290b() != null) {
            C1598a.m3293a().mo15290b().onActivityNewIntent(this, intent);
        }
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        if (C1598a.m3293a().mo15290b() != null) {
            C1598a.m3293a().mo15290b().onActivityPause(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onRestart() {
        super.onRestart();
        if (C1598a.m3293a().mo15290b() != null) {
            C1598a.m3293a().mo15290b().onActivityRestart(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        if (C1598a.m3293a().mo15290b() != null) {
            C1598a.m3293a().mo15290b().onActivityResume(this);
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
        super.onStart();
        if (C1598a.m3293a().mo15290b() != null) {
            C1598a.m3293a().mo15290b().onActivityStart(this, getIntent());
        }
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        if (C1598a.m3293a().mo15290b() != null) {
            C1598a.m3293a().mo15290b().onActivityStop(this);
        }
    }
}
