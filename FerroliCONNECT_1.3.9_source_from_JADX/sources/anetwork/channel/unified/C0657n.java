package anetwork.channel.unified;

import anet.channel.appmonitor.AppMonitor;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anetwork.channel.aidl.DefaultFinishEvent;

/* renamed from: anetwork.channel.unified.n */
/* compiled from: Taobao */
class C0657n implements Runnable {

    /* renamed from: a */
    final /* synthetic */ C0653k f800a;

    C0657n(C0653k kVar) {
        this.f800a = kVar;
    }

    public void run() {
        if (this.f800a.f793a.f790d.compareAndSet(false, true)) {
            RequestStatistic requestStatistic = this.f800a.f793a.f787a.f731b;
            if (requestStatistic.isDone.compareAndSet(false, true)) {
                requestStatistic.statusCode = ErrorConstant.ERROR_REQUEST_TIME_OUT;
                requestStatistic.msg = ErrorConstant.getErrMsg(ErrorConstant.ERROR_REQUEST_TIME_OUT);
                requestStatistic.rspEnd = System.currentTimeMillis();
                ALog.m327e("anet.UnifiedRequestTask", "task time out", this.f800a.f793a.f789c, "rs", requestStatistic);
                AppMonitor.getInstance().commitStat(new ExceptionStatistic(ErrorConstant.ERROR_REQUEST_TIME_OUT, (String) null, requestStatistic, (Throwable) null));
            }
            this.f800a.f793a.mo9370b();
            this.f800a.f793a.f788b.onFinish(new DefaultFinishEvent((int) ErrorConstant.ERROR_REQUEST_TIME_OUT, (String) null, this.f800a.f793a.f787a.mo9328a()));
        }
    }
}
