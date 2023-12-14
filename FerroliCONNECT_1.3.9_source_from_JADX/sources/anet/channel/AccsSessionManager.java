package anet.channel;

import android.content.Intent;
import android.text.TextUtils;
import anet.channel.entity.ConnType;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.StrategyCenter;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anet.channel.util.StringUtils;
import com.taobao.accs.common.Constants;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: Taobao */
class AccsSessionManager {
    /* access modifiers changed from: private */

    /* renamed from: c */
    public static CopyOnWriteArraySet<ISessionListener> f38c = new CopyOnWriteArraySet<>();

    /* renamed from: a */
    SessionCenter f39a = null;

    /* renamed from: b */
    Set<String> f40b = Collections.EMPTY_SET;

    AccsSessionManager(SessionCenter sessionCenter) {
        this.f39a = sessionCenter;
    }

    public synchronized void checkAndStartSession() {
        Collection<SessionInfo> a = this.f39a.f122g.mo8745a();
        Set<String> set = Collections.EMPTY_SET;
        if (!a.isEmpty()) {
            set = new TreeSet<>();
        }
        for (SessionInfo next : a) {
            if (next.isKeepAlive) {
                set.add(StringUtils.concatString(StrategyCenter.getInstance().getSchemeByHost(next.host, next.isAccs ? "https" : "http"), HttpConstant.SCHEME_SPLIT, next.host));
            }
        }
        for (String next2 : this.f40b) {
            if (!set.contains(next2)) {
                m11a(next2);
            }
        }
        if (m12b()) {
            for (String next3 : set) {
                try {
                    this.f39a.get(next3, ConnType.TypeLevel.SPDY, 0);
                } catch (Exception unused) {
                    ALog.m327e("start session failed", (String) null, Constants.KEY_HOST, next3);
                }
            }
            this.f40b = set;
        }
    }

    public synchronized void forceCloseSession(boolean z) {
        if (ALog.isPrintLog(1)) {
            ALog.m325d("awcn.AccsSessionManager", "forceCloseSession", this.f39a.f118c, "reCreate", Boolean.valueOf(z));
        }
        for (String a : this.f40b) {
            m11a(a);
        }
        if (z) {
            checkAndStartSession();
        }
    }

    /* renamed from: b */
    private boolean m12b() {
        if ((!GlobalAppRuntimeInfo.isAppBackground() || !AwcnConfig.isAccsSessionCreateForbiddenInBg()) && NetworkStatusHelper.isConnected()) {
            return true;
        }
        return false;
    }

    /* renamed from: a */
    private void m11a(String str) {
        if (!TextUtils.isEmpty(str)) {
            ALog.m325d("awcn.AccsSessionManager", "closeSessions", this.f39a.f118c, Constants.KEY_HOST, str);
            this.f39a.mo8667a(str).mo8705b(false);
        }
    }

    public void registerListener(ISessionListener iSessionListener) {
        if (iSessionListener != null) {
            f38c.add(iSessionListener);
        }
    }

    public void unregisterListener(ISessionListener iSessionListener) {
        f38c.remove(iSessionListener);
    }

    public void notifyListener(Intent intent) {
        ThreadPoolExecutorFactory.submitScheduledTask(new C0475a(this, intent));
    }
}
