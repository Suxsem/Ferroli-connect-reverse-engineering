package anet.channel.detect;

import anet.channel.AwcnConfig;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.AppLifecycle;

/* renamed from: anet.channel.detect.f */
/* compiled from: Taobao */
class C0498f implements AppLifecycle.AppLifecycleListener {

    /* renamed from: a */
    final /* synthetic */ C0496d f200a;

    public void forground() {
    }

    C0498f(C0496d dVar) {
        this.f200a = dVar;
    }

    public void background() {
        ALog.m328i("anet.HorseRaceDetector", "background", (String) null, new Object[0]);
        if (AwcnConfig.isHorseRaceEnable()) {
            ThreadPoolExecutorFactory.submitHRTask(new C0499g(this));
        }
    }
}
