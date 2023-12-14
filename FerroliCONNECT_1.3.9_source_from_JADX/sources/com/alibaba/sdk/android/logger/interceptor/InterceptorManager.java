package com.alibaba.sdk.android.logger.interceptor;

import com.alibaba.sdk.android.logger.LogLevel;
import java.util.ArrayList;

public class InterceptorManager {

    /* renamed from: a */
    private ArrayList<ILogInterceptor> f949a = new ArrayList<>();

    /* renamed from: b */
    private ArrayList<C0742c> f950b = new ArrayList<>();

    /* renamed from: c */
    private C0739a f951c;

    /* renamed from: d */
    private C0741b f952d;

    public InterceptorManager(C0739a aVar, C0741b bVar) {
        this.f951c = aVar;
        this.f952d = bVar;
    }

    /* renamed from: a */
    public InterceptorManager mo9735a() {
        InterceptorManager interceptorManager = new InterceptorManager(this.f951c, this.f952d);
        interceptorManager.f949a.addAll(this.f949a);
        interceptorManager.f950b.addAll(this.f950b);
        return interceptorManager;
    }

    /* renamed from: a */
    public void mo9736a(LogLevel logLevel, String str, Object[] objArr) {
        toNextLogInterceptor(-1, logLevel, str, objArr);
    }

    /* renamed from: a */
    public void mo9737a(ILogInterceptor iLogInterceptor) {
        this.f949a.add(iLogInterceptor);
    }

    /* renamed from: a */
    public void mo9738a(C0742c cVar) {
        this.f950b.add(cVar);
    }

    public void toNextLogInterceptor(int i, LogLevel logLevel, String str, Object[] objArr) {
        int i2 = i + 1;
        if (i2 >= this.f949a.size()) {
            this.f951c.handle(this, i2, logLevel, str, objArr);
        } else {
            this.f949a.get(i2).handle(this, i2, logLevel, str, objArr);
        }
    }

    public void toNextLoggerInterceptor(int i, LogLevel logLevel, String str, String str2) {
        int i2 = i + 1;
        if (i2 >= this.f950b.size()) {
            this.f952d.mo9724a(this, i2, logLevel, str, str2);
        } else {
            this.f950b.get(i2).mo9724a(this, i2, logLevel, str, str2);
        }
    }
}
