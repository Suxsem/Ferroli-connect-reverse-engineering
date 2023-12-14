package anet.channel.p007d;

import anet.channel.util.ALog;
import com.taobao.tlog.adapter.AdapterForTLog;

/* renamed from: anet.channel.d.a */
/* compiled from: Taobao */
public class C0491a implements ALog.ILog {
    /* renamed from: a */
    private int m67a(char c) {
        if (c == 'D') {
            return 1;
        }
        if (c == 'E') {
            return 4;
        }
        if (c == 'I') {
            return 2;
        }
        if (c != 'V') {
            return c != 'W' ? 5 : 3;
        }
        return 0;
    }

    public void setLogLevel(int i) {
    }

    /* renamed from: d */
    public void mo8758d(String str, String str2) {
        AdapterForTLog.logd(str, str2);
    }

    /* renamed from: i */
    public void mo8761i(String str, String str2) {
        AdapterForTLog.logi(str, str2);
    }

    /* renamed from: w */
    public void mo8765w(String str, String str2) {
        AdapterForTLog.logw(str, str2);
    }

    /* renamed from: w */
    public void mo8766w(String str, String str2, Throwable th) {
        AdapterForTLog.logw(str, str2, th);
    }

    /* renamed from: e */
    public void mo8759e(String str, String str2) {
        AdapterForTLog.loge(str, str2);
    }

    /* renamed from: e */
    public void mo8760e(String str, String str2, Throwable th) {
        AdapterForTLog.loge(str, str2, th);
    }

    public boolean isPrintLog(int i) {
        return i >= m67a(AdapterForTLog.getLogLevel().charAt(0));
    }

    public boolean isValid() {
        return AdapterForTLog.isValid();
    }
}
