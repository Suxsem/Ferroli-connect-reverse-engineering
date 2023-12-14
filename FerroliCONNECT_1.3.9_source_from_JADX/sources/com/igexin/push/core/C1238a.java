package com.igexin.push.core;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import com.igexin.p032b.p033a.p039c.C1179b;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.message.BindAliasCmdMessage;
import com.igexin.sdk.message.FeedbackCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.igexin.sdk.message.SetTagCmdMessage;
import com.igexin.sdk.message.UnBindAliasCmdMessage;

/* renamed from: com.igexin.push.core.a */
public class C1238a {

    /* renamed from: a */
    private static C1238a f1868a;

    private C1238a() {
    }

    /* renamed from: a */
    public static C1238a m1630a() {
        if (f1868a == null) {
            f1868a = new C1238a();
        }
        return f1868a;
    }

    /* renamed from: a */
    private void m1631a(String str, String str2, String str3, byte[] bArr) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 12) {
            intent.addFlags(32);
        }
        intent.setAction("com.igexin.sdk.action." + str3);
        Bundle bundle = new Bundle();
        bundle.putInt(PushConsts.CMD_ACTION, PushConsts.GET_MSG_DATA);
        bundle.putString("taskid", str);
        bundle.putString("messageid", str2);
        bundle.putString("appid", str3);
        bundle.putString("payloadid", str2 + ":" + str);
        bundle.putString("packagename", C1343f.f2168e);
        bundle.putByteArray("payload", bArr);
        intent.putExtras(bundle);
        intent.setPackage(C1343f.f2169f.getPackageName());
        C1343f.f2169f.sendBroadcast(intent);
    }

    @TargetApi(12)
    /* renamed from: d */
    private Intent m1632d() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 12) {
            intent.addFlags(32);
        }
        intent.setAction("com.igexin.sdk.action." + C1343f.f2135a);
        intent.setPackage(C1343f.f2169f.getPackageName());
        return intent;
    }

    /* renamed from: a */
    public Class mo14453a(Context context) {
        return C1356s.m2138a().mo14788d(context);
    }

    /* renamed from: a */
    public void mo14454a(int i) {
        if (C1343f.f2169f != null) {
            Class a = mo14453a(C1343f.f2169f);
            if (a != null) {
                Intent intent = new Intent(C1343f.f2169f, a);
                Bundle bundle = new Bundle();
                bundle.putInt(PushConsts.CMD_ACTION, PushConsts.GET_SDKSERVICEPID);
                bundle.putInt(PushConsts.KEY_SERVICE_PIT, i);
                intent.putExtras(bundle);
                C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
            }
            Intent d = m1632d();
            Bundle bundle2 = new Bundle();
            bundle2.putInt(PushConsts.CMD_ACTION, PushConsts.GET_SDKSERVICEPID);
            bundle2.putInt(PushConsts.KEY_SERVICE_PIT, i);
            d.putExtras(bundle2);
            C1343f.f2169f.sendBroadcast(d);
        }
    }

    /* renamed from: a */
    public void mo14455a(String str, String str2) {
        if (C1343f.f2169f != null) {
            try {
                Class a = mo14453a(C1343f.f2169f);
                if (a != null) {
                    Intent intent = new Intent(C1343f.f2169f, a);
                    Bundle bundle = new Bundle();
                    bundle.putInt(PushConsts.CMD_ACTION, 10010);
                    bundle.putSerializable(PushConsts.KEY_CMD_MSG, new SetTagCmdMessage(str, str2, PushConsts.SET_TAG_RESULT));
                    intent.putExtras(bundle);
                    C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
                    return;
                }
                Intent d = m1632d();
                Bundle bundle2 = new Bundle();
                bundle2.putInt(PushConsts.CMD_ACTION, PushConsts.SET_TAG_RESULT);
                bundle2.putString("sn", str);
                bundle2.putString("code", str2);
                d.putExtras(bundle2);
                C1343f.f2169f.sendBroadcast(d);
            } catch (Throwable th) {
                C1179b.m1354a("Broadcaster|" + th.toString());
            }
        }
    }

    @TargetApi(12)
    /* renamed from: a */
    public void mo14456a(String str, String str2, String str3, String str4) {
        if (C1343f.f2169f != null) {
            C1179b.m1354a("startapp|broadcastPayload");
            byte[] bArr = null;
            if (str4 != null) {
                bArr = str4.getBytes();
            } else {
                PushTaskBean pushTaskBean = C1343f.f2142ad.get(C1257f.m1711a().mo14472a(str, str2));
                if (pushTaskBean != null) {
                    bArr = pushTaskBean.getMsgExtra();
                }
            }
            if (bArr != null) {
                C1179b.m1354a("startapp|broadcast|payload = " + new String(bArr));
                Class a = mo14453a(C1343f.f2169f);
                if (!(a == null || C1343f.f2135a == null || !C1343f.f2135a.equals(str3))) {
                    Intent intent = new Intent(C1343f.f2169f, a);
                    Bundle bundle = new Bundle();
                    bundle.putInt(PushConsts.CMD_ACTION, PushConsts.GET_MSG_DATA);
                    bundle.putSerializable(PushConsts.KEY_MESSAGE_DATA, new GTTransmitMessage(str, str2, str2 + ":" + str, bArr));
                    intent.putExtras(bundle);
                    C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
                }
                m1631a(str, str2, str3, bArr);
                return;
            }
            C1179b.m1354a("startapp|broadcast|payload is empty!");
        }
    }

    /* renamed from: a */
    public void mo14457a(String str, String str2, String str3, String str4, long j) {
        String str5 = str;
        if (C1343f.f2169f != null) {
            Class a = mo14453a(C1343f.f2169f);
            if (!(a == null || C1343f.f2135a == null || !C1343f.f2135a.equals(str))) {
                Intent intent = new Intent(C1343f.f2169f, a);
                Bundle bundle = new Bundle();
                bundle.putInt(PushConsts.CMD_ACTION, 10010);
                bundle.putSerializable(PushConsts.KEY_CMD_MSG, new FeedbackCmdMessage(str2, str3, str4, j, PushConsts.THIRDPART_FEEDBACK));
                intent.putExtras(bundle);
                C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
            }
            Intent d = m1632d();
            Bundle bundle2 = new Bundle();
            bundle2.putInt(PushConsts.CMD_ACTION, PushConsts.THIRDPART_FEEDBACK);
            bundle2.putString("appid", str);
            String str6 = str2;
            bundle2.putString("taskid", str2);
            bundle2.putString("actionid", str3);
            bundle2.putString("result", str4);
            bundle2.putLong("timestamp", j);
            d.putExtras(bundle2);
            C1343f.f2169f.sendBroadcast(d);
        }
    }

    /* renamed from: b */
    public void mo14458b() {
        if (C1343f.f2169f != null) {
            Class a = mo14453a(C1343f.f2169f);
            if (a != null) {
                Intent intent = new Intent(C1343f.f2169f, a);
                Bundle bundle = new Bundle();
                bundle.putInt(PushConsts.CMD_ACTION, PushConsts.GET_SDKONLINESTATE);
                bundle.putBoolean(PushConsts.KEY_ONLINE_STATE, C1343f.f2175l);
                intent.putExtras(bundle);
                C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
            }
            Intent d = m1632d();
            Bundle bundle2 = new Bundle();
            bundle2.putInt(PushConsts.CMD_ACTION, PushConsts.GET_SDKONLINESTATE);
            bundle2.putBoolean(PushConsts.KEY_ONLINE_STATE, C1343f.f2175l);
            d.putExtras(bundle2);
            C1343f.f2169f.sendBroadcast(d);
        }
    }

    /* renamed from: b */
    public void mo14459b(String str, String str2) {
        if (C1343f.f2169f != null) {
            Class a = mo14453a(C1343f.f2169f);
            if (a != null) {
                Intent intent = new Intent(C1343f.f2169f, a);
                Bundle bundle = new Bundle();
                bundle.putInt(PushConsts.CMD_ACTION, 10010);
                bundle.putSerializable(PushConsts.KEY_CMD_MSG, new BindAliasCmdMessage(str, str2, 10010));
                intent.putExtras(bundle);
                C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
                return;
            }
            Intent d = m1632d();
            Bundle bundle2 = new Bundle();
            bundle2.putInt(PushConsts.CMD_ACTION, 10010);
            bundle2.putString("sn", str);
            bundle2.putString("code", str2);
            d.putExtras(bundle2);
            C1343f.f2169f.sendBroadcast(d);
        }
    }

    /* renamed from: b */
    public void mo14460b(String str, String str2, String str3, String str4) {
        Class a;
        if (C1343f.f2169f != null && (a = mo14453a(C1343f.f2169f)) != null && C1343f.f2135a != null) {
            Intent intent = new Intent(C1343f.f2169f, a);
            Bundle bundle = new Bundle();
            bundle.putInt(PushConsts.CMD_ACTION, 10011);
            bundle.putSerializable(PushConsts.KEY_NOTIFICATION_ARRIVED, new GTNotificationMessage(str, str2, str3, str4));
            intent.putExtras(bundle);
            C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
        }
    }

    /* renamed from: c */
    public void mo14461c() {
        if (C1343f.f2169f != null) {
            Log.d("PushService", "clientid is " + C1343f.f2182s);
            C1179b.m1354a("broadcastClientId|" + C1343f.f2182s);
            Class a = mo14453a(C1343f.f2169f);
            if (a != null) {
                Intent intent = new Intent(C1343f.f2169f, a);
                Bundle bundle = new Bundle();
                bundle.putInt(PushConsts.CMD_ACTION, PushConsts.GET_CLIENTID);
                bundle.putString(PushConsts.KEY_CLIENT_ID, C1343f.f2182s);
                intent.putExtras(bundle);
                C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
            }
            Intent d = m1632d();
            Bundle bundle2 = new Bundle();
            bundle2.putInt(PushConsts.CMD_ACTION, PushConsts.GET_CLIENTID);
            bundle2.putString(PushConsts.KEY_CLIENT_ID, C1343f.f2182s);
            d.putExtras(bundle2);
            C1343f.f2169f.sendBroadcast(d);
        }
    }

    /* renamed from: c */
    public void mo14462c(String str, String str2) {
        if (C1343f.f2169f != null) {
            Class a = mo14453a(C1343f.f2169f);
            if (a != null) {
                Intent intent = new Intent(C1343f.f2169f, a);
                Bundle bundle = new Bundle();
                bundle.putInt(PushConsts.CMD_ACTION, 10010);
                bundle.putSerializable(PushConsts.KEY_CMD_MSG, new UnBindAliasCmdMessage(str, str2, 10011));
                intent.putExtras(bundle);
                C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
                return;
            }
            Intent d = m1632d();
            Bundle bundle2 = new Bundle();
            bundle2.putInt(PushConsts.CMD_ACTION, 10011);
            bundle2.putString("sn", str);
            bundle2.putString("code", str2);
            d.putExtras(bundle2);
            C1343f.f2169f.sendBroadcast(d);
        }
    }

    /* renamed from: c */
    public void mo14463c(String str, String str2, String str3, String str4) {
        Class a;
        if (C1343f.f2169f != null && (a = mo14453a(C1343f.f2169f)) != null && C1343f.f2135a != null) {
            Intent intent = new Intent(C1343f.f2169f, a);
            Bundle bundle = new Bundle();
            bundle.putInt(PushConsts.CMD_ACTION, PushConsts.ACTION_NOTIFICATION_CLICKED);
            bundle.putSerializable(PushConsts.KEY_NOTIFICATION_CLICKED, new GTNotificationMessage(str, str2, str3, str4));
            intent.putExtras(bundle);
            C1356s.m2138a().mo14785b(C1343f.f2169f, intent);
        }
    }
}
