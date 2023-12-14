package com.alibaba.sdk.android.logger;

import com.alibaba.sdk.android.logger.interceptor.C0739a;
import com.alibaba.sdk.android.logger.interceptor.C0741b;
import com.alibaba.sdk.android.logger.interceptor.C0742c;
import com.alibaba.sdk.android.logger.interceptor.ILogInterceptor;
import com.alibaba.sdk.android.logger.interceptor.InterceptorManager;
import com.alibaba.sdk.android.logger.p015a.C0729a;
import com.alibaba.sdk.android.logger.p016b.C0730a;
import com.alibaba.sdk.android.logger.p016b.C0731b;
import com.alibaba.sdk.android.logger.p016b.C0732c;
import com.alibaba.sdk.android.logger.p016b.C0735e;
import com.alibaba.sdk.android.logger.p016b.C0736f;
import com.alibaba.sdk.android.logger.p016b.C0737g;
import com.alibaba.sdk.android.logger.p016b.C0738h;

public class BaseSdkLogApi {

    /* renamed from: a */
    private C0737g f927a;

    /* renamed from: b */
    private C0735e f928b;

    /* renamed from: c */
    private C0729a f929c = new C0729a();

    /* renamed from: d */
    private InterceptorManager f930d;

    /* renamed from: e */
    private C0731b f931e = new C0731b();

    public BaseSdkLogApi(String str, boolean z) {
        this.f927a = new C0737g(str);
        this.f928b = new C0735e(this.f931e);
        this.f930d = new InterceptorManager(new C0739a(new C0738h(this.f929c)), new C0741b(this.f928b));
        this.f930d.mo9737a((ILogInterceptor) new C0732c(this.f928b));
        if (z) {
            this.f931e.mo9725a(LogLevel.DEBUG);
            this.f930d.mo9738a((C0742c) new C0730a());
        }
    }

    public void addILogger(ILogger iLogger) {
        this.f928b.mo9731b(iLogger);
    }

    public <T> void addObjectFormat(Class<T> cls, IObjectLogFormat<T> iObjectLogFormat) {
        this.f929c.mo9723a(cls, iObjectLogFormat);
    }

    public void enable(boolean z) {
        this.f931e.mo9726a(z);
    }

    public LogBuilder getLogBuilder(Object obj) {
        return new LogBuilder(this.f930d, obj, this.f927a);
    }

    public ILog getLogger(Object obj) {
        return new C0736f(this.f927a.mo9733a(obj), this.f930d);
    }

    public void removeILogger(ILogger iLogger) {
        this.f928b.mo9732c(iLogger);
    }

    public void setILogger(ILogger iLogger) {
        this.f928b.mo9729a(iLogger);
    }

    public void setLevel(LogLevel logLevel) {
        this.f931e.mo9725a(logLevel);
    }
}
