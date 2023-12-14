package anetwork.channel.unified;

import android.os.Looper;
import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.monitor.C0533b;
import anet.channel.request.C0541b;
import anet.channel.request.Request;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpUrl;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.cache.CacheManager;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.entity.C0636c;
import anetwork.channel.entity.C0640g;
import anetwork.channel.interceptor.Callback;
import anetwork.channel.interceptor.Interceptor;
import anetwork.channel.interceptor.InterceptorManager;
import anetwork.channel.util.RequestConstant;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* renamed from: anetwork.channel.unified.k */
/* compiled from: Taobao */
class C0653k {

    /* renamed from: a */
    protected C0652j f793a;

    public C0653k(C0640g gVar, C0636c cVar) {
        cVar.mo9321a(gVar.f734e);
        this.f793a = new C0652j(gVar, cVar);
    }

    /* renamed from: anetwork.channel.unified.k$a */
    /* compiled from: Taobao */
    class C0654a implements Interceptor.Chain {

        /* renamed from: b */
        private int f795b = 0;

        /* renamed from: c */
        private Request f796c = null;

        /* renamed from: d */
        private Callback f797d = null;

        C0654a(int i, Request request, Callback callback) {
            this.f795b = i;
            this.f796c = request;
            this.f797d = callback;
        }

        public Request request() {
            return this.f796c;
        }

        public Callback callback() {
            return this.f797d;
        }

        public Future proceed(Request request, Callback callback) {
            if (C0653k.this.f793a.f790d.get()) {
                ALog.m328i("anet.UnifiedRequestTask", "request canneled or timeout in processing interceptor", request.getSeq(), new Object[0]);
                return null;
            } else if (this.f795b < InterceptorManager.getSize()) {
                return InterceptorManager.getInterceptor(this.f795b).intercept(new C0654a(this.f795b + 1, request, callback));
            } else {
                C0653k.this.f793a.f787a.mo9330a(request);
                C0653k.this.f793a.f788b = callback;
                Cache cache = NetworkConfigCenter.isHttpCacheEnable() ? CacheManager.getCache(C0653k.this.f793a.f787a.mo9337g(), C0653k.this.f793a.f787a.mo9338h()) : null;
                C0653k.this.f793a.f791e = cache != null ? new C0642a(C0653k.this.f793a, cache) : new C0646e(C0653k.this.f793a, (Cache) null, (Cache.Entry) null);
                C0653k.this.f793a.f791e.run();
                C0653k.this.m461c();
                return null;
            }
        }
    }

    /* renamed from: a */
    public Future mo9371a() {
        long currentTimeMillis = System.currentTimeMillis();
        this.f793a.f787a.f731b.reqServiceTransmissionEnd = currentTimeMillis;
        this.f793a.f787a.f731b.start = currentTimeMillis;
        this.f793a.f787a.f731b.isReqSync = this.f793a.f787a.mo9333c();
        this.f793a.f787a.f731b.isReqMain = Looper.myLooper() == Looper.getMainLooper();
        try {
            this.f793a.f787a.f731b.netReqStart = Long.valueOf(this.f793a.f787a.mo9329a(RequestConstant.KEY_REQ_START)).longValue();
        } catch (Exception unused) {
        }
        String a = this.f793a.f787a.mo9329a(RequestConstant.KEY_TRACE_ID);
        if (!TextUtils.isEmpty(a)) {
            this.f793a.f787a.f731b.traceId = a;
        }
        String a2 = this.f793a.f787a.mo9329a(RequestConstant.KEY_REQ_PROCESS);
        this.f793a.f787a.f731b.process = a2;
        this.f793a.f787a.f731b.pTraceId = this.f793a.f787a.mo9329a(RequestConstant.KEY_PARENT_TRACE_ID);
        ALog.m327e("anet.UnifiedRequestTask", "[traceId:" + a + "]" + "start", this.f793a.f789c, "bizId", this.f793a.f787a.mo9328a().getBizId(), "processFrom", a2, "url", this.f793a.f787a.mo9337g());
        if (NetworkConfigCenter.isUrlInDegradeList(this.f793a.f787a.mo9336f())) {
            C0643b bVar = new C0643b(this.f793a);
            this.f793a.f791e = bVar;
            bVar.f745a = new C0541b(ThreadPoolExecutorFactory.submitBackupTask(new C0655l(this)), this.f793a.f787a.mo9328a().getSeq());
            m461c();
            return new C0645d(this);
        }
        ThreadPoolExecutorFactory.submitPriorityTask(new C0656m(this), ThreadPoolExecutorFactory.Priority.HIGH);
        return new C0645d(this);
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public void m461c() {
        this.f793a.f792f = ThreadPoolExecutorFactory.submitScheduledTask(new C0657n(this), (long) this.f793a.f787a.mo9332b(), TimeUnit.MILLISECONDS);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: b */
    public void mo9372b() {
        if (this.f793a.f790d.compareAndSet(false, true)) {
            HttpUrl f = this.f793a.f787a.mo9336f();
            ALog.m327e("anet.UnifiedRequestTask", "task cancelled", this.f793a.f789c, "URL", f.simpleUrlString());
            RequestStatistic requestStatistic = this.f793a.f787a.f731b;
            if (requestStatistic.isDone.compareAndSet(false, true)) {
                requestStatistic.ret = 2;
                requestStatistic.statusCode = ErrorConstant.ERROR_REQUEST_CANCEL;
                requestStatistic.msg = ErrorConstant.getErrMsg(ErrorConstant.ERROR_REQUEST_CANCEL);
                requestStatistic.rspEnd = System.currentTimeMillis();
                AppMonitor.getInstance().commitStat(new ExceptionStatistic(ErrorConstant.ERROR_REQUEST_CANCEL, (String) null, requestStatistic, (Throwable) null));
                if (requestStatistic.recDataSize > 102400) {
                    C0533b.m138a().mo8842a(requestStatistic.sendStart, requestStatistic.rspEnd, requestStatistic.recDataSize);
                }
            }
            this.f793a.mo9370b();
            this.f793a.mo9369a();
            this.f793a.f788b.onFinish(new DefaultFinishEvent((int) ErrorConstant.ERROR_REQUEST_CANCEL, (String) null, this.f793a.f787a.mo9328a()));
        }
    }
}
