package com.alibaba.sdk.android.emas;

import android.content.Context;
import android.os.Looper;
import com.alibaba.sdk.android.tbrest.SendService;
import com.alibaba.sdk.android.tbrest.request.BizRequest;
import com.alibaba.sdk.android.tbrest.request.UrlWrapper;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* renamed from: com.alibaba.sdk.android.emas.j */
/* compiled from: SendManager */
public class C0716j {

    /* renamed from: a */
    private static final ThreadPoolExecutor f915a = new ThreadPoolExecutor(3, 3, 10, TimeUnit.SECONDS, new LinkedBlockingQueue(10), new C0718a());

    /* renamed from: a */
    private PreSendHandler f916a;

    /* renamed from: b */
    private final EmasSender f917b;

    /* renamed from: d */
    private boolean f918d = false;

    /* renamed from: f */
    private int f919f = 0;
    private final C0708e mDiskCacheManager;
    private final SendService mSendService;

    public C0716j(EmasSender emasSender, C0708e eVar) {
        this.f917b = emasSender;
        this.mSendService = new SendService();
        this.mDiskCacheManager = eVar;
    }

    public void init(Context context, String str, String str2, String str3, String str4, String str5) {
        this.mSendService.init(context.getApplicationContext(), str, str2, str3, str4, str5);
    }

    public void setHost(String str) {
        this.mSendService.changeHost(str);
    }

    /* renamed from: a */
    public void mo9658a(String str) {
        this.mSendService.appSecret = str;
    }

    public void openHttp(boolean z) {
        this.mSendService.openHttp = Boolean.valueOf(z);
    }

    /* renamed from: a */
    public void mo9657a(PreSendHandler preSendHandler) {
        this.f916a = preSendHandler;
    }

    public void setUserNick(String str) {
        this.mSendService.userNick = str;
    }

    /* renamed from: a */
    public void mo9660a(boolean z) {
        this.f918d = z;
    }

    /* renamed from: a */
    public void mo9656a(int i) {
        if (i >= 10) {
            this.f919f = 9;
        } else {
            this.f919f = i;
        }
    }

    /* renamed from: b */
    public void mo9661b(C0712g gVar) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(gVar);
        mo9659a((List<C0712g>) arrayList);
    }

    /* renamed from: a */
    public void mo9659a(List<C0712g> list) {
        m648c(new C0711f(list));
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m648c(C0711f fVar) {
        f915a.execute(new C0719b(fVar, this.f918d, this.f919f));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: e */
    public void mo9662e() {
        if (this.mDiskCacheManager != null && f915a.getQueue().isEmpty()) {
            new Thread(new Runnable() {
                public void run() {
                    C0711f a;
                    C0716j.m641a(C0716j.this).clear();
                    if (C0716j.mo9655a().getQueue().size() <= C0716j.m641a(C0716j.this) && (a = C0716j.m641a(C0716j.this).get()) != null) {
                        C0716j.this.m648c(a);
                    }
                }
            }).start();
        }
    }

    /* renamed from: a */
    public SendService m649a() {
        return this.mSendService;
    }

    /* renamed from: com.alibaba.sdk.android.emas.j$b */
    /* compiled from: SendManager */
    private class C0719b implements Runnable {

        /* renamed from: a */
        private final C0711f f921a;

        /* renamed from: d */
        private final boolean f923d;

        /* renamed from: f */
        private final int f924f;

        public C0719b(C0711f fVar, boolean z, int i) {
            this.f921a = fVar;
            this.f923d = z;
            this.f924f = i;
        }

        public void run() {
            List<C0712g> list;
            if (this.f921a.mo9643a() == C0707d.DISK_CACHE) {
                LogUtil.m1029d("SendManager send disk log, location:" + this.f921a.getLocation());
            }
            if (C0716j.m641a(C0716j.this) != null) {
                list = C0716j.m641a(C0716j.this).onHandlePreSend(this.f921a.mo9643a(), this.f921a.mo9643a());
            } else {
                list = this.f921a.mo9643a();
            }
            if (list == null || list.isEmpty()) {
                m660d(this.f921a);
                return;
            }
            byte[] bArr = null;
            try {
                bArr = BizRequest.getPackRequest(C0716j.m641a(C0716j.this).context, C0716j.m641a(C0716j.this), m657a(list));
            } catch (Exception e) {
                LogUtil.m1033w("SendManager pack request failed", e);
                if (C0716j.m641a(C0716j.this) != null) {
                    C0716j.m641a(C0716j.this).mo9638b(this.f921a);
                }
            }
            if (bArr == null) {
                LogUtil.m1029d("SendManager pack requst is null.");
                m660d(this.f921a);
            } else if (UrlWrapper.sendRequest(C0716j.m641a(C0716j.this), C0716j.m641a(C0716j.this).host, bArr).isSuccess()) {
                m660d(this.f921a);
            } else if (C0716j.m641a(C0716j.this) != null) {
                LogUtil.m1029d("SendManager request failed. put into cache.");
                C0716j.m641a(C0716j.this).remove(this.f921a);
            } else {
                LogUtil.m1029d("SendManager request failed. do nothing.");
            }
        }

        /* renamed from: d */
        private void m660d(C0711f fVar) {
            if (C0716j.m641a(C0716j.this) != null) {
                if (fVar.mo9643a() == C0707d.DISK_CACHE) {
                    C0716j.m641a(C0716j.this).remove(fVar);
                }
                if (m658a()) {
                    LogUtil.m1029d("SendManager trying send disk cache.");
                    C0711f a = C0716j.m641a(C0716j.this).get();
                    if (a != null) {
                        LogUtil.m1029d("SendManager sending disk cache.");
                        C0716j.this.m648c(a);
                        return;
                    }
                    LogUtil.m1029d("SendManager disk cache is empty.");
                    return;
                }
                LogUtil.m1029d("SendManager finish send. background: " + C0716j.m641a(C0716j.this).isBackground() + ", queue size: " + C0716j.mo9655a().getQueue().size() + ", limit: " + this.f924f);
            }
        }

        /* renamed from: a */
        private boolean m658a() {
            if ((this.f923d || !C0716j.m641a(C0716j.this).isBackground()) && C0716j.mo9655a().getQueue().size() <= this.f924f) {
                return true;
            }
            return false;
        }

        /* renamed from: b */
        private boolean mo9669b() {
            return this.f921a.mo9643a() == C0707d.DISK_CACHE;
        }

        /* renamed from: b */
        public C0711f m661b() {
            return this.f921a;
        }

        /* renamed from: f */
        public void mo9670f() {
            if (C0716j.m641a(C0716j.this) == null) {
                LogUtil.m1029d("SendManager send queue fill, disk cache not open, discard.");
            } else if (!mo9669b()) {
                LogUtil.m1029d("SendManager send queue fill, write into disk cache.");
                C07201 r0 = new Runnable() {
                    public void run() {
                        C0716j.m641a(C0716j.this).remove(C0719b.this.mo9669b());
                    }
                };
                if (Looper.myLooper() == Looper.getMainLooper()) {
                    new Thread(r0).start();
                } else {
                    r0.run();
                }
            } else {
                LogUtil.m1029d("SendManager send queue fill, already in disk cache. do nothing.");
            }
        }

        /* renamed from: a */
        private Map<String, String> m657a(List<C0712g> list) {
            HashMap hashMap = new HashMap();
            for (C0712g next : list) {
                StringBuilder sb = (StringBuilder) hashMap.get(next.f910i);
                if (sb == null) {
                    hashMap.put(next.f910i, new StringBuilder(next.f909h));
                } else {
                    sb.append(1);
                    sb.append(next.f909h);
                }
            }
            HashMap hashMap2 = new HashMap();
            for (Map.Entry entry : hashMap.entrySet()) {
                hashMap2.put(entry.getKey(), ((StringBuilder) entry.getValue()).toString());
            }
            return hashMap2;
        }
    }

    /* renamed from: com.alibaba.sdk.android.emas.j$a */
    /* compiled from: SendManager */
    private static class C0718a implements RejectedExecutionHandler {
        private C0718a() {
        }

        public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
            if (runnable instanceof C0719b) {
                ((C0719b) runnable).mo9670f();
            }
        }
    }
}
