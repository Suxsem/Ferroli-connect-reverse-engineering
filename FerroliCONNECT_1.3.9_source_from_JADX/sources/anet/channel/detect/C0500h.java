package anet.channel.detect;

import anet.channel.Session;
import anet.channel.entity.C0518b;
import anet.channel.entity.EventCb;
import anet.channel.request.Request;
import anet.channel.session.TnetSpdySession;
import anet.channel.statist.HorseRaceStat;
import anet.channel.strategy.C0583l;
import anet.channel.util.ALog;
import anet.channel.util.HttpUrl;

/* renamed from: anet.channel.detect.h */
/* compiled from: Taobao */
class C0500h implements EventCb {

    /* renamed from: a */
    final /* synthetic */ HorseRaceStat f202a;

    /* renamed from: b */
    final /* synthetic */ long f203b;

    /* renamed from: c */
    final /* synthetic */ String f204c;

    /* renamed from: d */
    final /* synthetic */ C0583l.C0588e f205d;

    /* renamed from: e */
    final /* synthetic */ TnetSpdySession f206e;

    /* renamed from: f */
    final /* synthetic */ C0496d f207f;

    C0500h(C0496d dVar, HorseRaceStat horseRaceStat, long j, String str, C0583l.C0588e eVar, TnetSpdySession tnetSpdySession) {
        this.f207f = dVar;
        this.f202a = horseRaceStat;
        this.f203b = j;
        this.f204c = str;
        this.f205d = eVar;
        this.f206e = tnetSpdySession;
    }

    public void onEvent(Session session, int i, C0518b bVar) {
        if (this.f202a.connTime == 0) {
            this.f202a.connTime = System.currentTimeMillis() - this.f203b;
            if (i == 1) {
                ALog.m328i("anet.HorseRaceDetector", "tnetSpdySession connect success", this.f204c, new Object[0]);
                this.f202a.connRet = 1;
                HttpUrl parse = HttpUrl.parse(session.getHost() + this.f205d.f547c);
                if (parse != null) {
                    this.f206e.request(new Request.Builder().setUrl(parse).setReadTimeout(this.f205d.f546b.f518d).setRedirectEnable(false).setSeq(this.f204c).build(), new C0501i(this));
                    return;
                }
                return;
            }
            this.f202a.connErrorCode = bVar.f247b;
            synchronized (this.f202a) {
                this.f202a.notify();
            }
        }
    }
}
