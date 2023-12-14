package anet.channel.detect;

import android.support.p000v4.app.NotificationCompat;
import anet.channel.RequestCb;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.HorseRaceStat;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import java.util.List;
import java.util.Map;

/* renamed from: anet.channel.detect.i */
/* compiled from: Taobao */
class C0501i implements RequestCb {

    /* renamed from: a */
    final /* synthetic */ C0500h f208a;

    public void onDataReceive(ByteArray byteArray, boolean z) {
    }

    C0501i(C0500h hVar) {
        this.f208a = hVar;
    }

    public void onResponseCode(int i, Map<String, List<String>> map) {
        this.f208a.f202a.reqErrorCode = i;
    }

    public void onFinish(int i, String str, RequestStatistic requestStatistic) {
        int i2 = 0;
        ALog.m328i("anet.HorseRaceDetector", "LongLinkTask request finish", this.f208a.f204c, "statusCode", Integer.valueOf(i), NotificationCompat.CATEGORY_MESSAGE, str);
        if (this.f208a.f202a.reqErrorCode == 0) {
            this.f208a.f202a.reqErrorCode = i;
        } else {
            HorseRaceStat horseRaceStat = this.f208a.f202a;
            if (this.f208a.f202a.reqErrorCode == 200) {
                i2 = 1;
            }
            horseRaceStat.reqRet = i2;
        }
        this.f208a.f202a.reqTime = (System.currentTimeMillis() - this.f208a.f203b) + this.f208a.f202a.connTime;
        synchronized (this.f208a.f202a) {
            this.f208a.f202a.notify();
        }
    }
}
