package org.android.agoo.control;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.client.AdapterGlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.dispatch.IntentDispatch;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AdapterUtilityImpl;
import com.taobao.accs.utl.UtilityImpl;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.C2353a;
import org.android.agoo.common.Config;
import org.android.agoo.common.MsgDO;
import org.android.agoo.message.MessageService;

/* compiled from: Taobao */
public class AgooFactory {
    private static final String DEAL_MESSAGE = "accs.msgRecevie";
    private static final String TAG = "AgooFactory";
    private static AgooFactory instance;
    /* access modifiers changed from: private */
    public static Context mContext;
    /* access modifiers changed from: private */
    public MessageService messageService = null;
    /* access modifiers changed from: private */
    public NotifManager notifyManager = null;

    public static AgooFactory getInstance(Context context) {
        if (instance == null) {
            synchronized (AgooFactory.class) {
                if (instance == null) {
                    instance = new AgooFactory(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private AgooFactory(Context context) {
        init(context, (NotifManager) null, (MessageService) null);
    }

    private void init(Context context, NotifManager notifManager, MessageService messageService2) {
        mContext = context.getApplicationContext();
        this.notifyManager = notifManager;
        if (this.notifyManager == null) {
            this.notifyManager = new NotifManager();
        }
        this.notifyManager.init(mContext);
        this.messageService = messageService2;
        if (this.messageService == null) {
            this.messageService = new MessageService();
        }
        this.messageService.mo25528a(mContext);
    }

    public NotifManager getNotifyManager() {
        return this.notifyManager;
    }

    public MessageService getMessageService() {
        return this.messageService;
    }

    public void saveMsg(byte[] bArr) {
        saveMsg(bArr, "0");
    }

    public void saveMsg(byte[] bArr, String str) {
        if (bArr != null && bArr.length > 0) {
            ThreadPoolExecutorFactory.execute(new C2354a(this, bArr, str));
        }
    }

    public void msgRecevie(byte[] bArr, String str) {
        msgRecevie(bArr, str, (TaoBaseService.ExtraInfo) null);
    }

    public void msgRecevie(byte[] bArr, String str, TaoBaseService.ExtraInfo extraInfo) {
        try {
            if (ALog.isPrintLog(ALog.Level.I)) {
                ALog.m3728i(TAG, "into--[AgooFactory,msgRecevie]:messageSource=" + str, new Object[0]);
            }
            ThreadPoolExecutorFactory.execute(new C2355b(this, bArr, str, extraInfo));
        } catch (Throwable th) {
            ALog.m3727e(TAG, "serviceImpl init task fail:" + th.toString(), new Object[0]);
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:50|51|(1:53)) */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0157, code lost:
        if (com.taobao.accs.utl.ALog.isPrintLog(com.taobao.accs.utl.ALog.Level.I) != false) goto L_0x0159;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:53:0x0159, code lost:
        com.taobao.accs.utl.ALog.m3728i(TAG, "agoo msg has no time", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:50:0x0151 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.os.Bundle msgReceiverPreHandlerOnly(android.content.Context r19, byte[] r20, java.lang.String r21, java.lang.String r22) {
        /*
            r0 = r20
            r1 = r21
            java.lang.String r2 = "ext"
            r3 = 0
            java.lang.String r4 = "AgooFactory"
            r5 = 0
            if (r0 == 0) goto L_0x01a4
            int r6 = r0.length     // Catch:{ Throwable -> 0x01a2 }
            if (r6 > 0) goto L_0x0011
            goto L_0x01a4
        L_0x0011:
            java.lang.String r6 = new java.lang.String     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r7 = "utf-8"
            r6.<init>(r0, r7)     // Catch:{ Throwable -> 0x01a2 }
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x01a2 }
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)     // Catch:{ Throwable -> 0x01a2 }
            if (r0 == 0) goto L_0x0042
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01a2 }
            r0.<init>()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r7 = "msgRecevieOnly,message--->["
            r0.append(r7)     // Catch:{ Throwable -> 0x01a2 }
            r0.append(r6)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r7 = "],utdid="
            r0.append(r7)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r7 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r19)     // Catch:{ Throwable -> 0x01a2 }
            r0.append(r7)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.Object[] r7 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x01a2 }
            com.taobao.accs.utl.ALog.m3728i(r4, r0, r7)     // Catch:{ Throwable -> 0x01a2 }
        L_0x0042:
            boolean r0 = android.text.TextUtils.isEmpty(r6)     // Catch:{ Throwable -> 0x01a2 }
            if (r0 == 0) goto L_0x0063
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01a2 }
            r0.<init>()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r1 = "handleMessage message==null,utdid="
            r0.append(r1)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r1 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r19)     // Catch:{ Throwable -> 0x01a2 }
            r0.append(r1)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.Object[] r1 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x01a2 }
            com.taobao.accs.utl.ALog.m3728i(r4, r0, r1)     // Catch:{ Throwable -> 0x01a2 }
            return r3
        L_0x0063:
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ Throwable -> 0x01a2 }
            r0.<init>(r6)     // Catch:{ Throwable -> 0x01a2 }
            int r7 = r0.length()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01a2 }
            r8.<init>()     // Catch:{ Throwable -> 0x01a2 }
            r10 = r3
            r11 = r10
            r9 = 0
        L_0x0074:
            if (r9 >= r7) goto L_0x01a1
            android.os.Bundle r10 = new android.os.Bundle     // Catch:{ Throwable -> 0x01a2 }
            r10.<init>()     // Catch:{ Throwable -> 0x01a2 }
            org.json.JSONObject r12 = r0.getJSONObject(r9)     // Catch:{ Throwable -> 0x01a2 }
            if (r12 != 0) goto L_0x0088
            r3 = r22
            r20 = r0
            r5 = r6
            goto L_0x0198
        L_0x0088:
            org.android.agoo.common.MsgDO r13 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x01a2 }
            r13.<init>()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r14 = "p"
            java.lang.String r14 = r12.getString(r14)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r15 = "i"
            java.lang.String r15 = r12.getString(r15)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r3 = "b"
            java.lang.String r3 = r12.getString(r3)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r5 = "f"
            r16 = r6
            long r5 = r12.getLong(r5)     // Catch:{ Throwable -> 0x01a2 }
            boolean r17 = r12.isNull(r2)     // Catch:{ Throwable -> 0x01a2 }
            if (r17 != 0) goto L_0x00b1
            java.lang.String r11 = r12.getString(r2)     // Catch:{ Throwable -> 0x01a2 }
        L_0x00b1:
            r8.append(r15)     // Catch:{ Throwable -> 0x01a2 }
            r20 = r0
            int r0 = r7 + -1
            if (r9 >= r0) goto L_0x00bf
            java.lang.String r0 = ","
            r8.append(r0)     // Catch:{ Throwable -> 0x01a2 }
        L_0x00bf:
            r13.msgIds = r15     // Catch:{ Throwable -> 0x01a2 }
            r13.extData = r11     // Catch:{ Throwable -> 0x01a2 }
            r13.removePacks = r14     // Catch:{ Throwable -> 0x01a2 }
            r13.messageSource = r1     // Catch:{ Throwable -> 0x01a2 }
            boolean r0 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x01a2 }
            if (r0 == 0) goto L_0x00d7
            java.lang.String r0 = "11"
            r13.errorCode = r0     // Catch:{ Throwable -> 0x01a2 }
        L_0x00d1:
            r3 = r22
            r5 = r16
            goto L_0x0198
        L_0x00d7:
            boolean r0 = android.text.TextUtils.isEmpty(r14)     // Catch:{ Throwable -> 0x01a2 }
            if (r0 == 0) goto L_0x00e2
            java.lang.String r0 = "12"
            r13.errorCode = r0     // Catch:{ Throwable -> 0x01a2 }
            goto L_0x00d1
        L_0x00e2:
            r17 = -1
            int r0 = (r5 > r17 ? 1 : (r5 == r17 ? 0 : -1))
            if (r0 != 0) goto L_0x00ed
            java.lang.String r0 = "13"
            r13.errorCode = r0     // Catch:{ Throwable -> 0x01a2 }
            goto L_0x00d1
        L_0x00ed:
            r0 = r19
            boolean r17 = checkPackage(r0, r14)     // Catch:{ Throwable -> 0x01a2 }
            if (r17 != 0) goto L_0x010d
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01a2 }
            r3.<init>()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r5 = "msgRecevie checkpackage is del,pack="
            r3.append(r5)     // Catch:{ Throwable -> 0x01a2 }
            r3.append(r14)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x01a2 }
            r5 = 0
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x01a2 }
            com.taobao.accs.utl.ALog.m3725d(r4, r3, r6)     // Catch:{ Throwable -> 0x01a2 }
            goto L_0x00d1
        L_0x010d:
            android.os.Bundle r5 = getFlag(r5, r13)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r6 = "encrypted"
            java.lang.String r6 = r5.getString(r6)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = r19.getPackageName()     // Catch:{ Throwable -> 0x01a2 }
            boolean r0 = r0.equals(r14)     // Catch:{ Throwable -> 0x01a2 }
            if (r0 == 0) goto L_0x013a
            r0 = 4
            java.lang.String r0 = java.lang.Integer.toString(r0)     // Catch:{ Throwable -> 0x01a2 }
            boolean r0 = android.text.TextUtils.equals(r6, r0)     // Catch:{ Throwable -> 0x01a2 }
            if (r0 == 0) goto L_0x012d
            goto L_0x013a
        L_0x012d:
            java.lang.String r0 = "msgRecevie msg encrypted flag not exist, cannot prase!!!"
            r3 = 0
            java.lang.Object[] r5 = new java.lang.Object[r3]     // Catch:{ Throwable -> 0x01a2 }
            com.taobao.accs.utl.ALog.m3727e(r4, r0, r5)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = "24"
            r13.errorCode = r0     // Catch:{ Throwable -> 0x01a2 }
            goto L_0x00d1
        L_0x013a:
            if (r5 == 0) goto L_0x013f
            r10.putAll(r5)     // Catch:{ Throwable -> 0x01a2 }
        L_0x013f:
            java.lang.String r0 = "t"
            java.lang.String r0 = r12.getString(r0)     // Catch:{ Throwable -> 0x0151 }
            boolean r5 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x0151 }
            if (r5 != 0) goto L_0x0161
            java.lang.String r5 = "time"
            r10.putString(r5, r0)     // Catch:{ Throwable -> 0x0151 }
            goto L_0x0161
        L_0x0151:
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x01a2 }
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)     // Catch:{ Throwable -> 0x01a2 }
            if (r0 == 0) goto L_0x0161
            java.lang.String r0 = "agoo msg has no time"
            r5 = 0
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x01a2 }
            com.taobao.accs.utl.ALog.m3728i(r4, r0, r6)     // Catch:{ Throwable -> 0x01a2 }
        L_0x0161:
            java.lang.String r0 = "trace"
            long r5 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x01a2 }
            r10.putLong(r0, r5)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = "id"
            r10.putString(r0, r15)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = "body"
            r10.putString(r0, r3)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = "source"
            r10.putString(r0, r14)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = "fromAppkey"
            r3 = r22
            r10.putString(r0, r3)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = "extData"
            r10.putString(r0, r11)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = "oriData"
            r5 = r16
            r10.putString(r0, r5)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = "type"
            java.lang.String r6 = "common-push"
            r10.putString(r0, r6)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = "message_source"
            r10.putString(r0, r1)     // Catch:{ Throwable -> 0x01a2 }
        L_0x0198:
            int r9 = r9 + 1
            r0 = r20
            r6 = r5
            r3 = 0
            r5 = 0
            goto L_0x0074
        L_0x01a1:
            return r10
        L_0x01a2:
            r0 = move-exception
            goto L_0x01c1
        L_0x01a4:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x01a2 }
            r0.<init>()     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r1 = "handleMessage data==null,utdid="
            r0.append(r1)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r1 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r19)     // Catch:{ Throwable -> 0x01a2 }
            r0.append(r1)     // Catch:{ Throwable -> 0x01a2 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x01a2 }
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x01a2 }
            com.taobao.accs.utl.ALog.m3728i(r4, r0, r2)     // Catch:{ Throwable -> 0x01a2 }
            r1 = 0
            return r1
        L_0x01c1:
            com.taobao.accs.utl.ALog$Level r1 = com.taobao.accs.utl.ALog.Level.E
            boolean r1 = com.taobao.accs.utl.ALog.isPrintLog(r1)
            if (r1 == 0) goto L_0x01e0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "msgRecevie is error,e="
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.taobao.accs.utl.ALog.m3727e(r4, r0, r1)
        L_0x01e0:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.control.AgooFactory.msgReceiverPreHandlerOnly(android.content.Context, byte[], java.lang.String, java.lang.String):android.os.Bundle");
    }

    public static String parseEncryptedMsgWithoutAgoo(String str, String str2, String str3, String str4) {
        try {
            byte[] bytes = str3.getBytes("utf-8");
            byte[] a = C2353a.m3904a(bytes, (str2 + str4).getBytes("utf-8"));
            if (a != null && a.length > 0) {
                return new String(C2353a.m3903a(Base64.decode(str, 8), new SecretKeySpec(C2353a.m3902a(a), "AES"), C2353a.m3902a(str2.getBytes("utf-8"))), "utf-8");
            }
            ALog.m3731w(TAG, "aesDecrypt key is null!", new Object[0]);
            return null;
        } catch (Throwable th) {
            ALog.m3731w(TAG, "parseEncryptedMsg body: ", str);
            ALog.m3731w(TAG, "parseEncryptedMsg appKey: ", str2);
            ALog.m3731w(TAG, "parseEncryptedMsg utdid: ", str4);
            ALog.m3730w(TAG, "parseEncryptedMsg failure: ", th, new Object[0]);
            return null;
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(3:59|60|(1:62)) */
    /* JADX WARNING: Code restructure failed: missing block: B:61:0x0213, code lost:
        if (com.taobao.accs.utl.ALog.isPrintLog(com.taobao.accs.utl.ALog.Level.I) != false) goto L_0x0215;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:0x0215, code lost:
        com.taobao.accs.utl.ALog.m3728i(TAG, "agoo msg has no time", new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing exception handler attribute for start block: B:59:0x020d */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.os.Bundle msgReceiverPreHandler(byte[] r30, java.lang.String r31, com.taobao.accs.base.TaoBaseService.ExtraInfo r32, boolean r33) {
        /*
            r29 = this;
            r8 = r29
            r0 = r30
            r9 = r31
            r10 = r32
            java.lang.String r11 = "ext"
            java.lang.String r1 = "accs.msgRecevie"
            r2 = 66002(0x101d2, float:9.2489E-41)
            r12 = 0
            java.lang.String r13 = "AgooFactory"
            r14 = 0
            if (r0 == 0) goto L_0x02bb
            int r3 = r0.length     // Catch:{ Throwable -> 0x02e9 }
            if (r3 > 0) goto L_0x001a
            goto L_0x02bb
        L_0x001a:
            java.lang.String r15 = new java.lang.String     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r3 = "utf-8"
            r15.<init>(r0, r3)     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.ALog$Level r0 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x02e9 }
            boolean r0 = com.taobao.accs.utl.ALog.isPrintLog(r0)     // Catch:{ Throwable -> 0x02e9 }
            if (r0 == 0) goto L_0x004d
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02e9 }
            r0.<init>()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r3 = "msgRecevie,message--->["
            r0.append(r3)     // Catch:{ Throwable -> 0x02e9 }
            r0.append(r15)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r3 = "],utdid="
            r0.append(r3)     // Catch:{ Throwable -> 0x02e9 }
            android.content.Context r3 = mContext     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r3)     // Catch:{ Throwable -> 0x02e9 }
            r0.append(r3)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.Object[] r3 = new java.lang.Object[r14]     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.ALog.m3728i(r13, r0, r3)     // Catch:{ Throwable -> 0x02e9 }
        L_0x004d:
            boolean r0 = android.text.TextUtils.isEmpty(r15)     // Catch:{ Throwable -> 0x02e9 }
            if (r0 == 0) goto L_0x007f
            com.taobao.accs.utl.UTMini r0 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x02e9 }
            android.content.Context r3 = mContext     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r3)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r4 = "message==null"
            r0.commitEvent(r2, r1, r3, r4)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02e9 }
            r0.<init>()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r1 = "handleMessage message==null,utdid="
            r0.append(r1)     // Catch:{ Throwable -> 0x02e9 }
            android.content.Context r1 = mContext     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r1 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r1)     // Catch:{ Throwable -> 0x02e9 }
            r0.append(r1)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.Object[] r1 = new java.lang.Object[r14]     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.ALog.m3728i(r13, r0, r1)     // Catch:{ Throwable -> 0x02e9 }
            return r12
        L_0x007f:
            org.json.JSONArray r0 = new org.json.JSONArray     // Catch:{ Throwable -> 0x02e9 }
            r0.<init>(r15)     // Catch:{ Throwable -> 0x02e9 }
            int r7 = r0.length()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02e9 }
            r6.<init>()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02e9 }
            r5.<init>()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02e9 }
            r4.<init>()     // Catch:{ Throwable -> 0x02e9 }
            r1 = r12
            r2 = r1
            r3 = 0
        L_0x009a:
            if (r3 >= r7) goto L_0x0296
            android.os.Bundle r1 = new android.os.Bundle     // Catch:{ Throwable -> 0x02e9 }
            r1.<init>()     // Catch:{ Throwable -> 0x02e9 }
            org.json.JSONObject r12 = r0.getJSONObject(r3)     // Catch:{ Throwable -> 0x02e9 }
            if (r12 != 0) goto L_0x00b6
            r30 = r0
            r14 = r3
            r0 = r4
            r17 = r6
            r18 = r7
            r26 = r11
            r12 = r15
            r11 = r1
            r15 = r5
            goto L_0x0284
        L_0x00b6:
            org.android.agoo.common.MsgDO r14 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x02e9 }
            r14.<init>()     // Catch:{ Throwable -> 0x02e9 }
            r30 = r0
            java.lang.String r0 = "p"
            java.lang.String r0 = r12.getString(r0)     // Catch:{ Throwable -> 0x02e9 }
            r16 = r2
            java.lang.String r2 = "i"
            java.lang.String r2 = r12.getString(r2)     // Catch:{ Throwable -> 0x02e9 }
            r22 = r15
            java.lang.String r15 = "b"
            java.lang.String r15 = r12.getString(r15)     // Catch:{ Throwable -> 0x02e9 }
            r23 = r1
            java.lang.String r1 = "f"
            r25 = r4
            r24 = r5
            long r4 = r12.getLong(r1)     // Catch:{ Throwable -> 0x02e9 }
            boolean r1 = r12.isNull(r11)     // Catch:{ Throwable -> 0x02e9 }
            if (r1 != 0) goto L_0x00ea
            java.lang.String r1 = r12.getString(r11)     // Catch:{ Throwable -> 0x02e9 }
            goto L_0x00ec
        L_0x00ea:
            r1 = r16
        L_0x00ec:
            r6.append(r2)     // Catch:{ Throwable -> 0x02e9 }
            r26 = r11
            int r11 = r7 + -1
            r27 = r7
            java.lang.String r7 = ","
            if (r3 >= r11) goto L_0x00fc
            r6.append(r7)     // Catch:{ Throwable -> 0x02e9 }
        L_0x00fc:
            r14.msgIds = r2     // Catch:{ Throwable -> 0x02e9 }
            r14.extData = r1     // Catch:{ Throwable -> 0x02e9 }
            r14.removePacks = r0     // Catch:{ Throwable -> 0x02e9 }
            r14.messageSource = r9     // Catch:{ Throwable -> 0x02e9 }
            boolean r16 = android.text.TextUtils.isEmpty(r15)     // Catch:{ Throwable -> 0x02e9 }
            if (r16 == 0) goto L_0x0124
            java.lang.String r0 = "11"
            r14.errorCode = r0     // Catch:{ Throwable -> 0x02e9 }
            org.android.agoo.control.NotifManager r0 = r8.notifyManager     // Catch:{ Throwable -> 0x02e9 }
            r0.handlerACKMessage(r14, r10)     // Catch:{ Throwable -> 0x02e9 }
        L_0x0113:
            r16 = r1
            r14 = r3
            r17 = r6
            r12 = r22
            r11 = r23
            r15 = r24
            r0 = r25
            r18 = r27
            goto L_0x0282
        L_0x0124:
            boolean r16 = android.text.TextUtils.isEmpty(r0)     // Catch:{ Throwable -> 0x02e9 }
            if (r16 == 0) goto L_0x0134
            java.lang.String r0 = "12"
            r14.errorCode = r0     // Catch:{ Throwable -> 0x02e9 }
            org.android.agoo.control.NotifManager r0 = r8.notifyManager     // Catch:{ Throwable -> 0x02e9 }
            r0.handlerACKMessage(r14, r10)     // Catch:{ Throwable -> 0x02e9 }
            goto L_0x0113
        L_0x0134:
            r16 = -1
            int r18 = (r4 > r16 ? 1 : (r4 == r16 ? 0 : -1))
            if (r18 != 0) goto L_0x0144
            java.lang.String r0 = "13"
            r14.errorCode = r0     // Catch:{ Throwable -> 0x02e9 }
            org.android.agoo.control.NotifManager r0 = r8.notifyManager     // Catch:{ Throwable -> 0x02e9 }
            r0.handlerACKMessage(r14, r10)     // Catch:{ Throwable -> 0x02e9 }
            goto L_0x0113
        L_0x0144:
            r28 = r6
            android.content.Context r6 = mContext     // Catch:{ Throwable -> 0x02e9 }
            boolean r6 = checkPackage(r6, r0)     // Catch:{ Throwable -> 0x02e9 }
            if (r6 != 0) goto L_0x019c
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02e9 }
            r4.<init>()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r5 = "msgRecevie checkpackage is del,pack="
            r4.append(r5)     // Catch:{ Throwable -> 0x02e9 }
            r4.append(r0)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x02e9 }
            r5 = 0
            java.lang.Object[] r6 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.ALog.m3725d(r13, r4, r6)     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.UTMini r16 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x02e9 }
            r17 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r18 = "accs.msgRecevie"
            android.content.Context r4 = mContext     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r19 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r4)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r20 = "deletePack"
            r21 = r0
            r16.commitEvent(r17, r18, r19, r20, r21)     // Catch:{ Throwable -> 0x02e9 }
            r6 = r25
            r6.append(r0)     // Catch:{ Throwable -> 0x02e9 }
            r0 = r24
            r0.append(r2)     // Catch:{ Throwable -> 0x02e9 }
            if (r3 >= r11) goto L_0x018d
            r6.append(r7)     // Catch:{ Throwable -> 0x02e9 }
            r0.append(r7)     // Catch:{ Throwable -> 0x02e9 }
        L_0x018d:
            r15 = r0
            r16 = r1
            r14 = r3
            r0 = r6
        L_0x0192:
            r12 = r22
            r11 = r23
            r18 = r27
            r17 = r28
            goto L_0x0282
        L_0x019c:
            r7 = r24
            r6 = r25
            android.os.Bundle r4 = getFlag(r4, r14)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r5 = "encrypted"
            java.lang.String r5 = r4.getString(r5)     // Catch:{ Throwable -> 0x02e9 }
            android.content.Context r11 = mContext     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r11 = r11.getPackageName()     // Catch:{ Throwable -> 0x02e9 }
            boolean r11 = r11.equals(r0)     // Catch:{ Throwable -> 0x02e9 }
            if (r11 == 0) goto L_0x01f0
            r11 = 4
            java.lang.String r11 = java.lang.Integer.toString(r11)     // Catch:{ Throwable -> 0x02e9 }
            boolean r5 = android.text.TextUtils.equals(r5, r11)     // Catch:{ Throwable -> 0x02e9 }
            if (r5 == 0) goto L_0x01c3
            r5 = 0
            goto L_0x01f1
        L_0x01c3:
            java.lang.String r0 = "msgRecevie msg encrypted flag not exist, cannot prase!!!"
            r2 = 0
            java.lang.Object[] r4 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.ALog.m3727e(r13, r0, r4)     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.UTMini r16 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x02e9 }
            r17 = 66002(0x101d2, float:9.2489E-41)
            java.lang.String r18 = "accs.msgRecevie"
            android.content.Context r0 = mContext     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r19 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r0)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r20 = "encrypted!=4"
            java.lang.String r21 = "15"
            r16.commitEvent(r17, r18, r19, r20, r21)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r0 = "24"
            r14.errorCode = r0     // Catch:{ Throwable -> 0x02e9 }
            org.android.agoo.control.NotifManager r0 = r8.notifyManager     // Catch:{ Throwable -> 0x02e9 }
            r0.handlerACKMessage(r14, r10)     // Catch:{ Throwable -> 0x02e9 }
            r16 = r1
            r14 = r3
            r0 = r6
            r15 = r7
            goto L_0x0192
        L_0x01f0:
            r5 = 1
        L_0x01f1:
            if (r4 == 0) goto L_0x01f9
            r11 = r23
            r11.putAll(r4)     // Catch:{ Throwable -> 0x02e9 }
            goto L_0x01fb
        L_0x01f9:
            r11 = r23
        L_0x01fb:
            java.lang.String r4 = "t"
            java.lang.String r4 = r12.getString(r4)     // Catch:{ Throwable -> 0x020d }
            boolean r12 = android.text.TextUtils.isEmpty(r4)     // Catch:{ Throwable -> 0x020d }
            if (r12 != 0) goto L_0x021d
            java.lang.String r12 = "time"
            r11.putString(r12, r4)     // Catch:{ Throwable -> 0x020d }
            goto L_0x021d
        L_0x020d:
            com.taobao.accs.utl.ALog$Level r4 = com.taobao.accs.utl.ALog.Level.I     // Catch:{ Throwable -> 0x02e9 }
            boolean r4 = com.taobao.accs.utl.ALog.isPrintLog(r4)     // Catch:{ Throwable -> 0x02e9 }
            if (r4 == 0) goto L_0x021d
            java.lang.String r4 = "agoo msg has no time"
            r12 = 0
            java.lang.Object[] r14 = new java.lang.Object[r12]     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.ALog.m3728i(r13, r4, r14)     // Catch:{ Throwable -> 0x02e9 }
        L_0x021d:
            java.lang.String r4 = "trace"
            r25 = r6
            r24 = r7
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x02e9 }
            r11.putLong(r4, r6)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r4 = "id"
            r11.putString(r4, r2)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r2 = "body"
            r11.putString(r2, r15)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r2 = "source"
            r11.putString(r2, r0)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r2 = "fromAppkey"
            android.content.Context r4 = mContext     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r4 = org.android.agoo.common.Config.m3894b(r4)     // Catch:{ Throwable -> 0x02e9 }
            r11.putString(r2, r4)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r2 = "extData"
            r11.putString(r2, r1)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r2 = "oriData"
            r12 = r22
            r11.putString(r2, r12)     // Catch:{ Throwable -> 0x02e9 }
            if (r33 == 0) goto L_0x026b
            android.content.Context r2 = mContext     // Catch:{ Throwable -> 0x02e9 }
            r16 = r1
            r1 = r29
            r14 = r3
            r3 = r0
            r0 = r25
            r4 = r11
            r15 = r24
            r17 = r28
            r6 = r31
            r18 = r27
            r7 = r32
            r1.sendMsgToBussiness(r2, r3, r4, r5, r6, r7)     // Catch:{ Throwable -> 0x02e9 }
            goto L_0x0282
        L_0x026b:
            r16 = r1
            r14 = r3
            r15 = r24
            r0 = r25
            r18 = r27
            r17 = r28
            java.lang.String r1 = "type"
            java.lang.String r2 = "common-push"
            r11.putString(r1, r2)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r1 = "message_source"
            r11.putString(r1, r9)     // Catch:{ Throwable -> 0x02e9 }
        L_0x0282:
            r2 = r16
        L_0x0284:
            int r3 = r14 + 1
            r4 = r0
            r1 = r11
            r5 = r15
            r6 = r17
            r7 = r18
            r11 = r26
            r14 = 0
            r0 = r30
            r15 = r12
            r12 = 0
            goto L_0x009a
        L_0x0296:
            r0 = r4
            r15 = r5
            int r2 = r0.length()     // Catch:{ Throwable -> 0x02e9 }
            if (r2 <= 0) goto L_0x02ba
            org.android.agoo.common.MsgDO r2 = new org.android.agoo.common.MsgDO     // Catch:{ Throwable -> 0x02e9 }
            r2.<init>()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r3 = r15.toString()     // Catch:{ Throwable -> 0x02e9 }
            r2.msgIds = r3     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x02e9 }
            r2.removePacks = r0     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r0 = "10"
            r2.errorCode = r0     // Catch:{ Throwable -> 0x02e9 }
            r2.messageSource = r9     // Catch:{ Throwable -> 0x02e9 }
            org.android.agoo.control.NotifManager r0 = r8.notifyManager     // Catch:{ Throwable -> 0x02e9 }
            r0.handlerACKMessage(r2, r10)     // Catch:{ Throwable -> 0x02e9 }
        L_0x02ba:
            return r1
        L_0x02bb:
            com.taobao.accs.utl.UTMini r0 = com.taobao.accs.utl.UTMini.getInstance()     // Catch:{ Throwable -> 0x02e9 }
            android.content.Context r3 = mContext     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r3 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r3)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r4 = "data==null"
            r0.commitEvent(r2, r1, r3, r4)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x02e9 }
            r0.<init>()     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r1 = "handleMessage data==null,utdid="
            r0.append(r1)     // Catch:{ Throwable -> 0x02e9 }
            android.content.Context r1 = mContext     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r1 = com.taobao.accs.utl.AdapterUtilityImpl.getDeviceId(r1)     // Catch:{ Throwable -> 0x02e9 }
            r0.append(r1)     // Catch:{ Throwable -> 0x02e9 }
            java.lang.String r0 = r0.toString()     // Catch:{ Throwable -> 0x02e9 }
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x02e9 }
            com.taobao.accs.utl.ALog.m3728i(r13, r0, r2)     // Catch:{ Throwable -> 0x02e9 }
            r1 = 0
            return r1
        L_0x02e9:
            r0 = move-exception
            com.taobao.accs.utl.ALog$Level r1 = com.taobao.accs.utl.ALog.Level.E
            boolean r1 = com.taobao.accs.utl.ALog.isPrintLog(r1)
            if (r1 == 0) goto L_0x0309
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "msgRecevie is error,e="
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r1 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]
            com.taobao.accs.utl.ALog.m3727e(r13, r0, r1)
        L_0x0309:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.agoo.control.AgooFactory.msgReceiverPreHandler(byte[], java.lang.String, com.taobao.accs.base.TaoBaseService$ExtraInfo, boolean):android.os.Bundle");
    }

    public static String parseEncryptedMsg(String str) {
        try {
            String b = Config.m3894b(mContext);
            String str2 = AdapterUtilityImpl.mAgooAppSecret;
            if (TextUtils.isEmpty(str2)) {
                ALog.m3727e(TAG, "getAppsign secret null", new Object[0]);
                return null;
            }
            List<String> utdids = UtilityImpl.getUtdids(Constants.SP_FILE_NAME, mContext);
            for (int i = 0; i < utdids.size(); i++) {
                String parseEncryptedMsgWithoutAgoo = parseEncryptedMsgWithoutAgoo(str, b, str2, utdids.get(i));
                if (parseEncryptedMsgWithoutAgoo != null) {
                    UtilityImpl.hitUtdid(Constants.SP_FILE_NAME, mContext, utdids.get(i));
                    return parseEncryptedMsgWithoutAgoo;
                }
            }
            return null;
        } catch (Throwable th) {
            ALog.m3726e(TAG, "parseEncryptedMsg failure: ", th, new Object[0]);
        }
    }

    public void reportCacheMsg() {
        try {
            ThreadPoolExecutorFactory.execute(new C2356c(this));
        } catch (Throwable th) {
            ALog.m3727e(TAG, "reportCacheMsg fail:" + th.toString(), new Object[0]);
        }
    }

    public void updateMsg(byte[] bArr, boolean z) {
        ThreadPoolExecutorFactory.execute(new C2357d(this, bArr, z));
    }

    public void updateNotifyMsg(String str, String str2) {
        ThreadPoolExecutorFactory.execute(new C2358e(this, str, str2));
    }

    public void updateMsgStatus(String str, String str2) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (!TextUtils.isEmpty(str2)) {
                if (ALog.isPrintLog(ALog.Level.I)) {
                    ALog.m3728i(TAG, "updateNotifyMsg begin,messageId=" + str + ",status=" + str2 + ",reportTimes=" + Config.m3899f(mContext), new Object[0]);
                }
                if (TextUtils.equals(str2, MessageService.MSG_ACCS_NOTIFY_CLICK)) {
                    this.messageService.mo25529a(str, "2");
                } else if (TextUtils.equals(str2, MessageService.MSG_ACCS_NOTIFY_DISMISS)) {
                    this.messageService.mo25529a(str, "3");
                }
            }
        } catch (Throwable th) {
            ALog.m3727e(TAG, "updateNotifyMsg e=" + th.toString(), new Object[0]);
        }
    }

    private static final boolean checkPackage(Context context, String str) {
        try {
            if (context.getPackageManager().getApplicationInfo(str, 0) != null) {
                return true;
            }
            return false;
        } catch (Throwable unused) {
        }
    }

    private static Bundle getFlag(long j, MsgDO msgDO) {
        Bundle bundle = new Bundle();
        try {
            char[] charArray = Long.toBinaryString(j).toCharArray();
            if (charArray != null && 8 <= charArray.length) {
                if (8 <= charArray.length) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("");
                    sb.append(Integer.parseInt("" + charArray[1] + charArray[2] + charArray[3] + charArray[4], 2));
                    bundle.putString(AgooConstants.MESSAGE_ENCRYPTED, sb.toString());
                    if (charArray[6] == '1') {
                        bundle.putString(AgooConstants.MESSAGE_REPORT, "1");
                        msgDO.reportStr = "1";
                    }
                    if (charArray[7] == '1') {
                        bundle.putString(AgooConstants.MESSAGE_NOTIFICATION, "1");
                    }
                }
                if (9 <= charArray.length && charArray[8] == '1') {
                    bundle.putString(AgooConstants.MESSAGE_HAS_TEST, "1");
                }
                if (10 <= charArray.length && charArray[9] == '1') {
                    bundle.putString("duplicate", "1");
                }
                if (11 <= charArray.length && charArray[10] == '1') {
                    bundle.putInt(AgooConstants.MESSAGE_POPUP, 1);
                }
            }
        } catch (Throwable unused) {
        }
        return bundle;
    }

    private void sendMsgToBussiness(Context context, String str, Bundle bundle, boolean z, String str2, TaoBaseService.ExtraInfo extraInfo) {
        Intent intent = new Intent();
        intent.setAction("org.agoo.android.intent.action.RECEIVE");
        intent.setPackage(str);
        intent.putExtras(bundle);
        intent.putExtra("type", "common-push");
        intent.putExtra(AgooConstants.MESSAGE_SOURCE, str2);
        intent.addFlags(32);
        try {
            Bundle bundle2 = new Bundle();
            bundle2.putSerializable(AgooConstants.MESSAGE_ACCS_EXTRA, extraInfo);
            intent.putExtra(AgooConstants.MESSAGE_AGOO_BUNDLE, bundle2);
        } catch (Throwable th) {
            ALog.m3726e(TAG, "sendMsgToBussiness", th, new Object[0]);
        }
        if (ALog.isPrintLog(ALog.Level.I)) {
            ALog.m3728i(TAG, "sendMsgToBussiness intent:" + bundle.toString() + ",utdid=" + AdapterUtilityImpl.getDeviceId(context) + ",pack=" + str + ",agooFlag=" + z, new Object[0]);
        }
        if (!z) {
            String agooCustomServiceName = AdapterGlobalClientInfo.getAgooCustomServiceName(context);
            if (TextUtils.isEmpty(agooCustomServiceName)) {
                ALog.m3727e(TAG, "sendMsgToBussiness failed, can not find custom service", new Object[0]);
                return;
            }
            intent.setClassName(str, agooCustomServiceName);
            IntentDispatch.dispatchIntent(context, intent, agooCustomServiceName);
        }
    }

    public void clickMessage(Context context, String str, String str2) {
        ThreadPoolExecutorFactory.execute(new C2359f(this, str, str2));
    }

    public void dismissMessage(Context context, String str, String str2) {
        ThreadPoolExecutorFactory.execute(new C2360g(this, str, str2));
    }
}
