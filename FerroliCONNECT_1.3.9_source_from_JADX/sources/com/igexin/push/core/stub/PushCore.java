package com.igexin.push.core.stub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import com.igexin.push.core.C1275b;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.p052e.C1341a;
import com.igexin.push.core.p052e.C1342b;
import com.igexin.sdk.IPushCore;
import java.util.HashMap;
import java.util.Map;

public class PushCore implements IPushCore {

    /* renamed from: a */
    private C1340e f2218a;

    /* renamed from: b */
    private Map<Activity, C1341a> f2219b = new HashMap();

    public void onActivityConfigurationChanged(Activity activity, Configuration configuration) {
        C1341a aVar = this.f2219b.get(activity);
        if (aVar != null) {
            aVar.mo14717a(configuration);
        }
    }

    public boolean onActivityCreateOptionsMenu(Activity activity, Menu menu) {
        C1341a aVar = this.f2219b.get(activity);
        return aVar != null && aVar.mo14721a(menu);
    }

    public void onActivityDestroy(Activity activity) {
        C1341a aVar = this.f2219b.get(activity);
        if (aVar != null) {
            aVar.mo14728h();
            this.f2219b.remove(activity);
            C1342b.m2068a().mo14733c(aVar);
        }
    }

    public boolean onActivityKeyDown(Activity activity, int i, KeyEvent keyEvent) {
        C1341a aVar = this.f2219b.get(activity);
        return aVar != null && aVar.mo14720a(i, keyEvent);
    }

    public void onActivityNewIntent(Activity activity, Intent intent) {
        C1341a aVar = this.f2219b.get(activity);
        if (aVar != null) {
            aVar.mo14716a(intent);
        }
    }

    public void onActivityPause(Activity activity) {
        C1341a aVar = this.f2219b.get(activity);
        if (aVar != null) {
            aVar.mo14726f();
        }
    }

    public void onActivityRestart(Activity activity) {
        C1341a aVar = this.f2219b.get(activity);
        if (aVar != null) {
            aVar.mo14724d();
        }
    }

    public void onActivityResume(Activity activity) {
        C1341a aVar = this.f2219b.get(activity);
        if (aVar != null) {
            aVar.mo14725e();
        }
    }

    public void onActivityStart(Activity activity, Intent intent) {
        if (activity != null && intent != null && intent.hasExtra("activityid")) {
            C1341a a = C1342b.m2068a().mo14730a(Long.valueOf(intent.getLongExtra("activityid", 0)));
            if (a != null) {
                a.mo14715a(activity);
                this.f2219b.put(activity, a);
                a.mo14723c();
                return;
            }
            activity.finish();
        }
    }

    public void onActivityStop(Activity activity) {
        C1341a aVar = this.f2219b.get(activity);
        if (aVar != null) {
            aVar.mo14727g();
        }
    }

    public IBinder onServiceBind(Intent intent) {
        return null;
    }

    public void onServiceDestroy() {
    }

    public int onServiceStartCommand(Intent intent, int i, int i2) {
        if (this.f2218a == null) {
            return 1;
        }
        Message obtain = Message.obtain();
        obtain.what = C1275b.f1899c;
        obtain.obj = intent;
        this.f2218a.mo14702a(obtain);
        return 1;
    }

    public boolean start(Context context) {
        this.f2218a = C1340e.m2032a();
        this.f2218a.mo14701a(context);
        return true;
    }
}
