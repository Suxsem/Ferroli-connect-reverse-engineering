package com.alibaba.sdk.android.logger;

import com.alibaba.sdk.android.logger.interceptor.C0742c;
import com.alibaba.sdk.android.logger.interceptor.ILogInterceptor;
import com.alibaba.sdk.android.logger.interceptor.InterceptorManager;
import com.alibaba.sdk.android.logger.p016b.C0736f;
import com.alibaba.sdk.android.logger.p016b.C0737g;
import java.util.ArrayList;
import java.util.Iterator;

public class LogBuilder {
    private Object instanceObject;
    private ArrayList<ILogInterceptor> logInterceptors = new ArrayList<>();
    private ArrayList<C0742c> loggerInterceptors = new ArrayList<>();
    private InterceptorManager originInterceptorManager;
    private C0737g tagGenerator;

    public LogBuilder(InterceptorManager interceptorManager, Object obj, C0737g gVar) {
        this.originInterceptorManager = interceptorManager;
        this.instanceObject = obj;
        this.tagGenerator = gVar;
    }

    public LogBuilder addLogInterceptor(ILogInterceptor iLogInterceptor) {
        this.logInterceptors.add(iLogInterceptor);
        return this;
    }

    public LogBuilder addLoggerInterceptor(C0742c cVar) {
        this.loggerInterceptors.add(cVar);
        return this;
    }

    public ILog build() {
        InterceptorManager a = this.originInterceptorManager.mo9735a();
        Iterator<ILogInterceptor> it = this.logInterceptors.iterator();
        while (it.hasNext()) {
            a.mo9737a(it.next());
        }
        Iterator<C0742c> it2 = this.loggerInterceptors.iterator();
        while (it2.hasNext()) {
            a.mo9738a(it2.next());
        }
        return new C0736f(this.tagGenerator.mo9733a(this.instanceObject), a);
    }
}
