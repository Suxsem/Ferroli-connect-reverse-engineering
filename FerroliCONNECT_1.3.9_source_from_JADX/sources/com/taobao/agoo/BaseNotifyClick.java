package com.taobao.agoo;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.alibaba.sdk.android.push.impl.HuaweiMsgParseImpl;
import com.alibaba.sdk.android.push.impl.MeizuMsgParseImpl;
import com.alibaba.sdk.android.push.impl.OppoMsgParseImpl;
import com.alibaba.sdk.android.push.impl.VivoMsgParseImpl;
import com.alibaba.sdk.android.push.impl.XiaoMiMsgParseImpl;
import com.alibaba.sdk.android.push.register.ReporterFactory;
import com.alibaba.sdk.android.push.utils.ThreadUtil;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.AppMonitorAdapter;
import com.taobao.agoo.BaseNotifyClickActivity;
import java.util.Iterator;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.MsgDO;
import org.android.agoo.message.MessageService;

public abstract class BaseNotifyClick {
    private static final String TAG = "MPS.BaseNotifyClick";
    /* access modifiers changed from: private */
    public Context context;
    /* access modifiers changed from: private */
    public String msgSource;

    public abstract void onMessage(Intent intent);

    public void onNotPushData(Intent intent) {
    }

    public void onParseFailed(Intent intent) {
    }

    public void onCreate(Context context2, Intent intent) {
        ALog.m3728i(TAG, "onCreate", new Object[0]);
        this.context = context2;
        buildMessage(intent);
    }

    public void onNewIntent(Intent intent) {
        ALog.m3728i(TAG, "onNewIntent", new Object[0]);
        buildMessage(intent);
    }

    private void buildMessage(final Intent intent) {
        ThreadUtil.getExecutor().execute(new Runnable() {
            public void run() {
                String str;
                Intent intent = null;
                try {
                    if (intent != null) {
                        try {
                            str = BaseNotifyClick.this.parseMsgByThirdPush(intent);
                        } catch (Throwable unused) {
                            str = null;
                        }
                        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(BaseNotifyClick.this.msgSource)) {
                            BaseNotifyClick.this.onNotPushData(intent);
                            ALog.m3731w(BaseNotifyClick.TAG, "parseMsgFromNotifyListener null!!", "source", BaseNotifyClick.this.msgSource);
                        } else {
                            try {
                                intent = ReporterFactory.getPushParser().parseMsg(BaseNotifyClick.this.context, str, BaseNotifyClick.this.msgSource);
                            } catch (Throwable unused2) {
                            }
                            if (intent == null) {
                                BaseNotifyClick.this.onParseFailed(intent);
                            } else {
                                BaseNotifyClick.this.reportClickNotifyMsg(intent);
                            }
                        }
                    }
                    if (intent != null) {
                        try {
                            BaseNotifyClick.this.onMessage(intent);
                            return;
                        } catch (Throwable th) {
                            ALog.m3726e(BaseNotifyClick.TAG, "onMessage", th, new Object[0]);
                            return;
                        }
                    } else {
                        return;
                    }
                } catch (Throwable th2) {
                    ALog.m3726e(BaseNotifyClick.TAG, "onMessage", th2, new Object[0]);
                    return;
                }
                throw th;
            }
        });
    }

    /* access modifiers changed from: private */
    public String parseMsgByThirdPush(Intent intent) {
        String str;
        if (BaseNotifyClickActivity.notifyListeners != null && BaseNotifyClickActivity.notifyListeners.size() > 0) {
            Iterator<BaseNotifyClickActivity.INotifyListener> it = BaseNotifyClickActivity.notifyListeners.iterator();
            str = null;
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                BaseNotifyClickActivity.INotifyListener next = it.next();
                String parseMsgFromIntent = next.parseMsgFromIntent(intent);
                if (!TextUtils.isEmpty(parseMsgFromIntent)) {
                    this.msgSource = next.getMsgSource();
                    str = parseMsgFromIntent;
                    break;
                }
                str = parseMsgFromIntent;
            }
        } else {
            ALog.m3731w(TAG, "no impl, try use default impl to parse intent!", new Object[0]);
            BaseNotifyClickActivity.INotifyListener huaweiMsgParseImpl = new HuaweiMsgParseImpl();
            String parseMsgFromIntent2 = huaweiMsgParseImpl.parseMsgFromIntent(intent);
            if (TextUtils.isEmpty(parseMsgFromIntent2)) {
                huaweiMsgParseImpl = new XiaoMiMsgParseImpl();
                parseMsgFromIntent2 = huaweiMsgParseImpl.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(parseMsgFromIntent2)) {
                huaweiMsgParseImpl = new OppoMsgParseImpl();
                parseMsgFromIntent2 = huaweiMsgParseImpl.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(parseMsgFromIntent2)) {
                huaweiMsgParseImpl = new VivoMsgParseImpl();
                ((VivoMsgParseImpl) huaweiMsgParseImpl).setContext(this.context);
                parseMsgFromIntent2 = huaweiMsgParseImpl.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(parseMsgFromIntent2)) {
                huaweiMsgParseImpl = new MeizuMsgParseImpl();
                parseMsgFromIntent2 = huaweiMsgParseImpl.parseMsgFromIntent(intent);
            }
            if (TextUtils.isEmpty(str)) {
                AppMonitorAdapter.commitCount("accs", "error", "parse 3push error", 0.0d);
            } else {
                this.msgSource = huaweiMsgParseImpl.getMsgSource();
                AppMonitorAdapter.commitCount("accs", "error", "parse 3push default " + this.msgSource, 0.0d);
            }
        }
        ALog.m3728i(TAG, "parseMsgByThirdPush", "result", str, "msgSource", this.msgSource);
        return str;
    }

    /* access modifiers changed from: private */
    public void reportClickNotifyMsg(Intent intent) {
        try {
            String stringExtra = intent.getStringExtra(AgooConstants.MESSAGE_ID);
            String stringExtra2 = intent.getStringExtra(AgooConstants.MESSAGE_SOURCE);
            String stringExtra3 = intent.getStringExtra(AgooConstants.MESSAGE_REPORT);
            String stringExtra4 = intent.getStringExtra("extData");
            MsgDO msgDO = new MsgDO();
            msgDO.msgIds = stringExtra;
            msgDO.extData = stringExtra4;
            msgDO.messageSource = stringExtra2;
            msgDO.reportStr = stringExtra3;
            msgDO.msgStatus = MessageService.MSG_ACCS_NOTIFY_CLICK;
            ALog.m3728i(TAG, "reportClickNotifyMsg messageId:" + stringExtra + " source:" + stringExtra2 + " reportStr:" + stringExtra3 + " status:" + msgDO.msgStatus, new Object[0]);
            ReporterFactory.getPushReporter().reportPushClick(this.context, msgDO);
        } catch (Exception e) {
            ALog.m3727e(TAG, "reportClickNotifyMsg exception: " + e, new Object[0]);
        }
    }
}
