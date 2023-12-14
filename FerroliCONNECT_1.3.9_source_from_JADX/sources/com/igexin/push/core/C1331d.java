package com.igexin.push.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import anetwork.channel.util.RequestConstant;
import com.igexin.assist.sdk.AssistPushManager;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.config.C1234k;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.core.p047a.C1259h;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.p053d.C1362c;
import com.igexin.push.p054e.p057c.C1386o;
import com.igexin.push.p088g.p090b.C1574g;
import com.igexin.sdk.PushConsts;

/* renamed from: com.igexin.push.core.d */
public class C1331d extends Handler {

    /* renamed from: a */
    private static String f2075a = "com.igexin.push.core.d";

    public C1331d(Looper looper) {
        super(looper);
    }

    /* renamed from: a */
    private void m2007a() {
        C1362c.m2166a().mo14810b();
        if (C1343f.f2175l) {
            return;
        }
        if (C1343f.f2112D > C1234k.f1861v || C1343f.f2112D == 0) {
            int random = (int) ((Math.random() * 100.0d) + 150.0d);
            C1179b.m1354a(f2075a + "|userPresent, reConnectDelayTime = " + C1234k.f1861v + ", resetDelay = " + random);
            C1343f.f2112D = (long) random;
            C1574g.m3187i().mo15213j();
        }
    }

    /* renamed from: a */
    private void m2008a(Intent intent) {
        Intent intent2;
        String stringExtra = intent.getStringExtra(PushConsts.CMD_ACTION);
        if (stringExtra.equals(PushConsts.ACTION_SERVICE_INITIALIZE)) {
            C1257f.m1711a().mo14473a(intent);
        } else if (stringExtra.equals(PushConsts.ACTION_SERVICE_ONRESUME)) {
            C1179b.m1354a(f2075a + "|handle onresume ~~~");
            C1257f.m1711a().mo14487c();
        } else if (stringExtra.equals(PushConsts.ACTION_SERVICE_INITIALIZE_SLAVE)) {
            C1257f.m1711a().mo14485b(intent);
            AssistPushManager.getInstance().turnOnPush(C1343f.f2169f);
        } else if (stringExtra.equals(PushConsts.ACTION_BROADCAST_PUSHMANAGER)) {
            C1257f.m1711a().mo14474a(intent.getBundleExtra("bundle"));
        } else if (stringExtra.equals(PushConsts.ACTION_BROADCAST_USER_PRESENT)) {
            m2007a();
        } else if (stringExtra.equals(PushConsts.ACTION_BROADCAST_NOTIFICATION_CLICK) && (intent2 = (Intent) intent.getParcelableExtra("broadcast_intent")) != null) {
            C1343f.f2169f.sendBroadcast(intent2);
        }
    }

    public void handleMessage(Message message) {
        C1257f a;
        Intent intent;
        try {
            if (message.obj != null) {
                if (message.what == C1275b.f1899c) {
                    Intent intent2 = (Intent) message.obj;
                    if (intent2.hasExtra(PushConsts.CMD_ACTION)) {
                        m2008a(intent2);
                        return;
                    }
                    return;
                }
                if (message.what == C1275b.f1900d) {
                    a = C1257f.m1711a();
                    intent = (Intent) message.obj;
                } else if (message.what == C1275b.f1901e) {
                    a = C1257f.m1711a();
                    intent = (Intent) message.obj;
                } else if (message.what == C1275b.f1903g) {
                    Bundle bundle = (Bundle) message.obj;
                    C1355r.m2114a().mo14770b(bundle.getString("taskid"), bundle.getString("messageid"));
                    return;
                } else if (message.what == C1275b.f1904h) {
                    Bundle bundle2 = (Bundle) message.obj;
                    String string = bundle2.getString("taskid");
                    String string2 = bundle2.getString("messageid");
                    String string3 = bundle2.getString("actionid");
                    C1179b.m1354a(f2075a + "|hand execute_action taskid = " + string + ", actionid = " + string3);
                    C1355r.m2114a().mo14772b(string, string2, string3);
                    return;
                } else if (message.what == C1275b.f1907k) {
                    C1362c.m2166a().mo14808a((Intent) message.obj);
                    return;
                } else if (message.what != C1275b.f1902f) {
                    if (message.what == C1275b.f1908l) {
                        C1312h.m1937a().mo14684d((String) message.obj);
                        if (C1343f.f2175l) {
                            C1257f.m1711a().mo14492f();
                            return;
                        }
                        return;
                    } else if (message.what == C1275b.f1909m && C1234k.f1837S) {
                        if (!RequestConstant.FALSE.equals(C1343f.f2188y)) {
                            C1386o oVar = new C1386o();
                            oVar.mo14843c();
                            oVar.f2330e = message.obj;
                            oVar.f2331f = message.getData().getByteArray("payload");
                            new C1259h().mo14465a((Object) oVar);
                            return;
                        }
                        return;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
                a.mo14488c(intent);
            } else if (message.what == C1275b.f1906j) {
                C1355r.m2114a().mo14776e();
            }
        } catch (Throwable th) {
            C1179b.m1354a(f2075a + "|" + th.toString());
        }
    }
}
