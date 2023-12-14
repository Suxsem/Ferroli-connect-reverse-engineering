package com.igexin.assist.action;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.igexin.assist.MessageBean;
import com.igexin.assist.util.AssistUtils;
import com.igexin.push.core.C1275b;
import com.igexin.push.core.C1340e;
import com.igexin.push.core.C1343f;
import com.igexin.push.core.C1356s;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.core.p047a.C1257f;
import com.igexin.push.core.p050c.C1307c;
import com.igexin.push.core.p050c.C1312h;
import com.igexin.push.util.C1593r;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.message.GTTransmitMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

public class MessageManger {

    /* renamed from: a */
    private ExecutorService f1524a;

    private MessageManger() {
        this.f1524a = Executors.newSingleThreadExecutor();
    }

    /* renamed from: a */
    private PushTaskBean m1205a(C1145d dVar) {
        long currentTimeMillis = System.currentTimeMillis();
        PushTaskBean pushTaskBean = new PushTaskBean();
        pushTaskBean.setAppid(dVar.mo14199e());
        pushTaskBean.setMessageId(dVar.mo14198d());
        pushTaskBean.setTaskId(dVar.mo14197c());
        pushTaskBean.setId(String.valueOf(currentTimeMillis));
        pushTaskBean.setAppKey(C1343f.f2165b);
        pushTaskBean.setCurrentActionid(1);
        return pushTaskBean;
    }

    /* renamed from: a */
    private Class m1206a(Context context) {
        try {
            String str = (String) C1593r.m3270c(context, "uis", "");
            if (!TextUtils.isEmpty(str)) {
                return Class.forName(str);
            }
            return null;
        } catch (Throwable unused) {
            return null;
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m1207a(Context context, C1145d dVar) {
        if (!C1343f.f2170g.get()) {
            AssistUtils.startGetuiService(context);
        }
        if (dVar != null) {
            Message obtain = Message.obtain();
            obtain.what = C1275b.f1909m;
            obtain.obj = dVar.mo14202h();
            Bundle bundle = new Bundle();
            bundle.putString("content", dVar.mo14202h());
            if (dVar.mo14196b() != null) {
                bundle.putByteArray("payload", dVar.mo14196b());
            }
            obtain.setData(bundle);
            C1340e.m2032a().mo14702a(obtain);
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m1208a(Context context, String str) {
        try {
            if (!TextUtils.isEmpty(str) && !str.equals(C1343f.f2188y)) {
                Log.e("Assist_MessageManger", "other token = " + str);
                new C1307c(context).mo14657c(str);
                if (C1343f.f2170g.get()) {
                    C1312h.m1937a().mo14684d(str);
                    if (C1343f.f2175l) {
                        C1257f.m1711a().mo14492f();
                        return;
                    }
                    return;
                }
                Message obtain = Message.obtain();
                obtain.what = C1275b.f1908l;
                obtain.obj = str;
                C1340e.m2032a().mo14702a(obtain);
            }
        } catch (Throwable unused) {
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public void m1212a(C1145d dVar, Context context) {
        StringBuilder sb;
        if (dVar != null && context != null) {
            try {
                C1307c cVar = new C1307c(context);
                if (!cVar.mo14653a(dVar.mo14197c())) {
                    cVar.mo14655b(dVar.mo14197c());
                    Class a = m1206a(context);
                    if (a != null) {
                        Intent intent = new Intent(context, a);
                        Bundle bundle = new Bundle();
                        bundle.putInt(PushConsts.CMD_ACTION, PushConsts.GET_MSG_DATA);
                        String c = dVar.mo14197c();
                        String d = dVar.mo14198d();
                        bundle.putSerializable(PushConsts.KEY_MESSAGE_DATA, new GTTransmitMessage(c, d, dVar.mo14198d() + ":" + dVar.mo14197c(), dVar.mo14196b()));
                        intent.putExtras(bundle);
                        C1356s.m2138a().mo14785b(context, intent);
                    } else {
                        Intent intent2 = new Intent();
                        if (Build.VERSION.SDK_INT >= 12) {
                            intent2.addFlags(32);
                        }
                        intent2.setAction("com.igexin.sdk.action." + dVar.mo14199e());
                        Bundle bundle2 = new Bundle();
                        bundle2.putInt(PushConsts.CMD_ACTION, PushConsts.GET_MSG_DATA);
                        bundle2.putString("taskid", dVar.mo14197c());
                        bundle2.putString("messageid", dVar.mo14198d());
                        bundle2.putString("appid", dVar.mo14199e());
                        bundle2.putString("payloadid", dVar.mo14198d() + ":" + dVar.mo14197c());
                        bundle2.putString("packagename", dVar.mo14201g());
                        bundle2.putByteArray("payload", dVar.mo14196b());
                        intent2.putExtras(bundle2);
                        context.sendBroadcast(intent2);
                    }
                    sb = new StringBuilder();
                    sb.append(getBrandCode(context));
                    sb.append("0");
                } else {
                    sb = new StringBuilder();
                    sb.append(getBrandCode(context));
                    sb.append("1");
                }
                feedbackPushMessage(context, dVar, sb.toString());
            } catch (Throwable unused) {
            }
        }
    }

    public static MessageManger getInstance() {
        return C1144c.f1527a;
    }

    public void addMessage(MessageBean messageBean) {
        ExecutorService executorService = this.f1524a;
        if (executorService != null) {
            executorService.execute(new C1143b(this, messageBean));
        }
    }

    public void feedbackPushMessage(Context context, C1145d dVar, String str) {
        try {
            if (C1343f.f2170g.get()) {
                C1257f.m1711a().mo14476a(m1205a(dVar), str);
                return;
            }
            C1307c cVar = new C1307c(context);
            long currentTimeMillis = System.currentTimeMillis();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(AgooConstants.MESSAGE_ID, String.valueOf(currentTimeMillis));
            jSONObject.put("messageid", dVar.mo14198d());
            jSONObject.put("taskid", dVar.mo14197c());
            jSONObject.put("multaid", str);
            jSONObject.put("timestamp", String.valueOf(System.currentTimeMillis()));
            cVar.mo14652a(dVar.mo14197c(), jSONObject);
        } catch (Throwable unused) {
        }
    }

    public String getBrandCode(Context context) {
        return null;
    }
}
