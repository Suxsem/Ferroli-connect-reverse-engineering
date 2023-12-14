package com.alibaba.sdk.android.push.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.alibaba.sdk.android.push.register.ThirdPushManager;
import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.utl.ALog;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.common.MsgDO;
import org.android.agoo.control.AgooFactory;

public class ReporterFactory {
    /* access modifiers changed from: private */
    public static final String TAG = ("MPS:" + ReporterFactory.class.getCanonicalName());
    private static volatile IMsgReporter mMsgReporter;
    private static volatile ITokenReporter mTokenReporter;
    private static volatile IPushParser pushParser;
    private static volatile IPushReporter pushReporter;

    public interface IMsgReporter {
        void sendMsgToDecryptor(Context context, String str, byte[] bArr, String str2);
    }

    public interface IPushParser {
        Intent parseMsg(Context context, String str, String str2);
    }

    public interface IPushReporter {
        void reportPushClick(Context context, MsgDO msgDO);
    }

    @Deprecated
    public interface ITokenReporter {
        @Deprecated
        void reportToken(Context context, String str, String str2, String str3);
    }

    public interface ITokenReporterV2 extends ITokenReporter {
        void reportToken(Context context, String str, String str2, String str3, String str4);
    }

    private ReporterFactory() {
    }

    public static ITokenReporter getTokenRepoter() {
        if (mTokenReporter == null) {
            synchronized (ReporterFactory.class) {
                if (mTokenReporter == null) {
                    mTokenReporter = new ITokenReporterV2() {
                        public void reportToken(Context context, String str, String str2, String str3, String str4) {
                            if (!TextUtils.isEmpty(str2) && context != null) {
                                try {
                                    String access$000 = ReporterFactory.TAG;
                                    ALog.m3728i(access$000, "report " + str2 + " ThirdToken: " + str4 + ", version: " + str3, new Object[0]);
                                    AgooFactory.getInstance(context).getNotifyManager().reportThirdPushToken(str, str4, str2, str3, true);
                                } catch (Throwable th) {
                                    ALog.m3727e(ReporterFactory.TAG, "agoo factory could not report third push token", new Object[0]);
                                    th.printStackTrace();
                                }
                            }
                        }

                        public void reportToken(Context context, String str, String str2, String str3) {
                            reportToken(context, "3.7.6", str, str2, str3);
                        }
                    };
                }
            }
        }
        return mTokenReporter;
    }

    public static String addPrefix(String str, String str2) {
        for (int i = 0; i < ThirdPushManager.ThirdPushReportKeyword.values().length; i++) {
            if (str.equals(ThirdPushManager.ThirdPushReportKeyword.values()[i].thirdTokenKeyword)) {
                return ThirdPushManager.ThirdPushReportKeyword.values()[i].thirdSdkVersionPrefix + str2;
            }
        }
        return str + str2;
    }

    public static void setTokenReporter(ITokenReporter iTokenReporter) {
        synchronized (ReporterFactory.class) {
            mTokenReporter = iTokenReporter;
        }
    }

    public static void setMsgReporter(IMsgReporter iMsgReporter) {
        synchronized (ReporterFactory.class) {
            mMsgReporter = iMsgReporter;
        }
    }

    public static IMsgReporter getMsgReporter() {
        if (mMsgReporter == null) {
            synchronized (ReporterFactory.class) {
                if (mMsgReporter == null) {
                    mMsgReporter = new IMsgReporter() {
                        public void sendMsgToDecryptor(Context context, String str, byte[] bArr, String str2) {
                            try {
                                AgooFactory.getInstance(context).msgRecevie(bArr, str, (TaoBaseService.ExtraInfo) null);
                            } catch (Throwable th) {
                                ALog.m3727e(ReporterFactory.TAG, "agoo factory could not report msg", new Object[0]);
                                th.printStackTrace();
                            }
                        }
                    };
                }
            }
        }
        return mMsgReporter;
    }

    public static void setPushParser(IPushParser iPushParser) {
        synchronized (ReporterFactory.class) {
            pushParser = iPushParser;
        }
    }

    public static IPushParser getPushParser() {
        if (pushParser == null) {
            synchronized (ReporterFactory.class) {
                if (pushParser == null) {
                    pushParser = new IPushParser() {
                        public Intent parseMsg(Context context, String str, String str2) {
                            try {
                                Bundle msgReceiverPreHandler = AgooFactory.getInstance(context).msgReceiverPreHandler(str.getBytes("UTF-8"), str2, (TaoBaseService.ExtraInfo) null, false);
                                String string = msgReceiverPreHandler.getString(AgooConstants.MESSAGE_BODY);
                                ALog.m3728i(ReporterFactory.TAG, "begin parse EncryptedMsg", new Object[0]);
                                String parseEncryptedMsg = AgooFactory.parseEncryptedMsg(string);
                                if (!TextUtils.isEmpty(parseEncryptedMsg)) {
                                    msgReceiverPreHandler.putString(AgooConstants.MESSAGE_BODY, parseEncryptedMsg);
                                } else {
                                    ALog.m3727e(ReporterFactory.TAG, "parse EncryptedMsg fail, empty", new Object[0]);
                                }
                                Intent intent = new Intent();
                                intent.putExtras(msgReceiverPreHandler);
                                AgooFactory.getInstance(context).saveMsg(str.getBytes("UTF-8"), "2");
                                if (TextUtils.isEmpty(parseEncryptedMsg)) {
                                    return null;
                                }
                                return intent;
                            } catch (Throwable th) {
                                ALog.m3726e(ReporterFactory.TAG, "agoo factory parse EncryptedMsg fail", th, new Object[0]);
                                return null;
                            }
                        }
                    };
                }
            }
        }
        return pushParser;
    }

    public static void setPushReporter(IPushReporter iPushReporter) {
        synchronized (ReporterFactory.class) {
            pushReporter = iPushReporter;
        }
    }

    public static IPushReporter getPushReporter() {
        if (pushReporter == null) {
            synchronized (ReporterFactory.class) {
                if (pushReporter == null) {
                    pushReporter = new IPushReporter() {
                        public void reportPushClick(Context context, MsgDO msgDO) {
                            AgooFactory.getInstance(context).getNotifyManager().report(msgDO, (TaoBaseService.ExtraInfo) null);
                        }
                    };
                }
            }
        }
        return pushReporter;
    }
}
