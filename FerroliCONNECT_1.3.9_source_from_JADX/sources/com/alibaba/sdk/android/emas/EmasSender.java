package com.alibaba.sdk.android.emas;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.alibaba.sdk.android.emas.C0714i;
import com.alibaba.sdk.android.tbrest.rest.C0890e;
import com.alibaba.sdk.android.tbrest.utils.LogUtil;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class EmasSender {
    private static final int BUILD_REQ_DATA_DONE = 1;
    private static final String TAG = "EmasSender";
    /* access modifiers changed from: private */
    public static Handler sHandler;
    /* access modifiers changed from: private */
    public boolean mBackground;
    /* access modifiers changed from: private */
    public C0706c mCacheManager;
    private C0708e mDiskCacheManager;
    private final ExecutorService mPackDataExecutor;
    /* access modifiers changed from: private */
    public final C0716j mSendManager;
    /* access modifiers changed from: private */
    public final int mSingleLogLimitCapacity;

    private EmasSender(Builder builder) {
        this.mBackground = false;
        this.mPackDataExecutor = Executors.newSingleThreadExecutor(new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "EmasSenderPackDataThread");
            }
        });
        this.mSingleLogLimitCapacity = builder.singleLogLimitCapacity;
        if (builder.diskCache) {
            this.mDiskCacheManager = new C0708e(builder.context, builder.host, builder.appKey, builder.businessKey);
            this.mDiskCacheManager.mo9636a(builder.diskCacheLimitCount, builder.diskCacheLimitCapacity, builder.diskCacheLimitDay);
        }
        this.mSendManager = new C0716j(this, this.mDiskCacheManager);
        this.mSendManager.init(builder.context, builder.appId, builder.appKey, builder.appVersion, builder.channel, builder.userNick);
        this.mSendManager.setHost(builder.host);
        this.mSendManager.mo9658a(builder.appSecret);
        this.mSendManager.openHttp(builder.openHttp);
        this.mSendManager.mo9660a(builder.sendDiskCacheBackground);
        this.mSendManager.mo9656a(builder.maxSendDiskCacheQueueCount);
        this.mSendManager.mo9657a(builder.preSendHandler);
        this.mSendManager.mo9662e();
        if (builder.cache && builder.cacheLimitCount > 1) {
            this.mCacheManager = new C0706c(this.mSendManager, builder.cacheLimitCount, builder.cacheLimitCapacity);
            C0714i iVar = new C0714i();
            iVar.mo9647a(new C0714i.C0715a() {
                /* renamed from: c */
                public void mo9606c() {
                    boolean unused = EmasSender.this.mBackground = false;
                }

                /* renamed from: d */
                public void mo9607d() {
                    boolean unused = EmasSender.this.mBackground = true;
                    EmasSender.this.mCacheManager.flush();
                }
            });
            builder.context.registerActivityLifecycleCallbacks(iVar);
        }
        sHandler = new C0702a(Looper.getMainLooper(), this);
    }

    public void changeHost(String str) {
        this.mSendManager.setHost(str);
    }

    public void openHttp(boolean z) {
        this.mSendManager.openHttp(z);
    }

    public void setUserNick(String str) {
        this.mSendManager.setUserNick(str);
    }

    public void send(long j, String str, int i, String str2, String str3, String str4, Map<String, String> map) {
        if (TextUtils.isEmpty(this.mSendManager.mo9655a().getAppKey()) || TextUtils.isEmpty(this.mSendManager.mo9655a().getChangeHost())) {
            LogUtil.m1029d("EmasSender send failed. appkey or host is empty.");
        } else {
            this.mPackDataExecutor.submit(new C0703b(j, str, i, str2, str3, str4, map));
        }
    }

    public void flush() {
        C0706c cVar = this.mCacheManager;
        if (cVar != null) {
            cVar.flush();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isBackground() {
        return this.mBackground;
    }

    /* renamed from: com.alibaba.sdk.android.emas.EmasSender$b */
    private class C0703b implements Runnable {

        /* renamed from: a */
        private final Map<String, String> f881a;

        /* renamed from: b */
        private final long f882b;

        /* renamed from: d */
        private final int f883d;

        /* renamed from: d */
        private final String f884d;

        /* renamed from: e */
        private final String f885e;

        /* renamed from: f */
        private final String f886f;

        /* renamed from: g */
        private final String f887g;

        public C0703b(long j, String str, int i, String str2, String str3, String str4, Map<String, String> map) {
            this.f882b = j;
            this.f884d = str;
            this.f883d = i;
            this.f885e = str2;
            this.f886f = str3;
            this.f887g = str4;
            this.f881a = map;
        }

        public void run() {
            String a = C0890e.m1021a(EmasSender.this.mSendManager.mo9655a(), EmasSender.this.mSendManager.mo9655a().getAppKey(), this.f882b, this.f884d, this.f883d, this.f885e, this.f886f, this.f887g, this.f881a);
            if (!TextUtils.isEmpty(a)) {
                int length = a.getBytes(Charset.forName("UTF-8")).length;
                if (length <= EmasSender.this.mSingleLogLimitCapacity) {
                    EmasSender.sHandler.obtainMessage(1, new C0712g(String.valueOf(this.f883d), a, this.f882b)).sendToTarget();
                    return;
                }
                LogUtil.m1029d("EmasSender send failed. build data is exceed limit. current length: " + length);
                return;
            }
            LogUtil.m1029d("EmasSender send failed. build data is null.");
        }
    }

    /* renamed from: com.alibaba.sdk.android.emas.EmasSender$a */
    private static class C0702a extends Handler {

        /* renamed from: a */
        private final WeakReference<EmasSender> f879a;

        public C0702a(Looper looper, EmasSender emasSender) {
            super(looper);
            this.f879a = new WeakReference<>(emasSender);
        }

        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                try {
                    C0712g gVar = (C0712g) message.obj;
                    if (gVar == null) {
                        LogUtil.m1029d("EmasSender EmasHandler singleLog is null");
                        return;
                    }
                    EmasSender emasSender = (EmasSender) this.f879a.get();
                    if (emasSender == null) {
                        LogUtil.m1029d("EmasSender EmasHandler weakRef sender get null");
                    } else if (emasSender.mCacheManager != null) {
                        emasSender.mCacheManager.remove(gVar);
                    } else {
                        emasSender.mSendManager.mo9661b(gVar);
                    }
                } catch (Exception unused) {
                    LogUtil.m1030e("EmasSender EmasHandler error:");
                }
            } else {
                LogUtil.m1030e("EmasSender unknown msg");
            }
        }
    }

    public static final class Builder {
        /* access modifiers changed from: private */
        public String appId;
        /* access modifiers changed from: private */
        public String appKey;
        /* access modifiers changed from: private */
        public String appSecret;
        /* access modifiers changed from: private */
        public String appVersion;
        /* access modifiers changed from: private */
        public String businessKey = "common";
        /* access modifiers changed from: private */
        public boolean cache = true;
        /* access modifiers changed from: private */
        public int cacheLimitCapacity = 2097152;
        /* access modifiers changed from: private */
        public int cacheLimitCount = 20;
        /* access modifiers changed from: private */
        public String channel;
        /* access modifiers changed from: private */
        public Application context;
        /* access modifiers changed from: private */
        public boolean diskCache = true;
        /* access modifiers changed from: private */
        public int diskCacheLimitCapacity = 104857600;
        /* access modifiers changed from: private */
        public int diskCacheLimitCount = 50;
        /* access modifiers changed from: private */
        public int diskCacheLimitDay = 5;
        /* access modifiers changed from: private */
        public String host;
        /* access modifiers changed from: private */
        public int maxSendDiskCacheQueueCount = 0;
        /* access modifiers changed from: private */
        public boolean openHttp;
        /* access modifiers changed from: private */
        public PreSendHandler preSendHandler;
        /* access modifiers changed from: private */
        public boolean sendDiskCacheBackground = false;
        /* access modifiers changed from: private */
        public int singleLogLimitCapacity = 204800;
        /* access modifiers changed from: private */
        public String userNick;

        public Builder context(Application application) {
            this.context = application;
            return this;
        }

        public Builder host(String str) {
            this.host = str;
            return this;
        }

        public Builder appKey(String str) {
            this.appKey = str;
            return this;
        }

        public Builder appId(String str) {
            this.appId = str;
            return this;
        }

        public Builder appSecret(String str) {
            this.appSecret = str;
            return this;
        }

        public Builder appVersion(String str) {
            this.appVersion = str;
            return this;
        }

        public Builder channel(String str) {
            this.channel = str;
            return this;
        }

        public Builder openHttp(boolean z) {
            this.openHttp = z;
            return this;
        }

        public Builder userNick(String str) {
            this.userNick = str;
            return this;
        }

        public Builder cache(boolean z) {
            this.cache = z;
            return this;
        }

        public Builder cacheLimitCount(int i) {
            this.cacheLimitCount = i;
            return this;
        }

        public Builder diskCache(boolean z) {
            this.diskCache = z;
            return this;
        }

        public Builder diskCacheLimitCount(int i) {
            this.diskCacheLimitCount = i;
            return this;
        }

        public Builder diskCacheLimitDay(int i) {
            this.diskCacheLimitDay = i;
            return this;
        }

        public Builder preSendHandler(PreSendHandler preSendHandler2) {
            this.preSendHandler = preSendHandler2;
            return this;
        }

        public Builder businessKey(String str) {
            this.businessKey = str;
            return this;
        }

        public Builder setSendDiskCacheBackground(boolean z) {
            this.sendDiskCacheBackground = z;
            return this;
        }

        public Builder setMaxSendDiskCacheQueueCount(int i) {
            this.maxSendDiskCacheQueueCount = i;
            return this;
        }

        public EmasSender build() {
            return new EmasSender(this);
        }
    }
}
