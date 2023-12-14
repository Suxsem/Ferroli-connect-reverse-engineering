package anet.channel.fulltrace;

import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;

/* renamed from: anet.channel.fulltrace.a */
/* compiled from: Taobao */
public class C0521a {

    /* renamed from: a */
    private static volatile IFullTraceAnalysis f255a = new C0522a((IFullTraceAnalysis) null);
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static boolean f256b = false;

    /* renamed from: a */
    public static IFullTraceAnalysis m128a() {
        return f255a;
    }

    /* renamed from: a */
    public static void m129a(IFullTraceAnalysis iFullTraceAnalysis) {
        f255a = new C0522a(iFullTraceAnalysis);
    }

    /* renamed from: anet.channel.fulltrace.a$a */
    /* compiled from: Taobao */
    private static class C0522a implements IFullTraceAnalysis {

        /* renamed from: a */
        private IFullTraceAnalysis f257a;

        C0522a(IFullTraceAnalysis iFullTraceAnalysis) {
            this.f257a = iFullTraceAnalysis;
            boolean unused = C0521a.f256b = true;
        }

        public String createRequest() {
            IFullTraceAnalysis iFullTraceAnalysis;
            if (!C0521a.f256b || (iFullTraceAnalysis = this.f257a) == null) {
                return null;
            }
            try {
                return iFullTraceAnalysis.createRequest();
            } catch (Throwable th) {
                boolean unused = C0521a.f256b = false;
                ALog.m326e("anet.AnalysisFactory", "createRequest fail.", (String) null, th, new Object[0]);
                return null;
            }
        }

        public void commitRequest(String str, RequestStatistic requestStatistic) {
            IFullTraceAnalysis iFullTraceAnalysis;
            if (C0521a.f256b && (iFullTraceAnalysis = this.f257a) != null) {
                try {
                    iFullTraceAnalysis.commitRequest(str, requestStatistic);
                } catch (Throwable th) {
                    boolean unused = C0521a.f256b = false;
                    ALog.m326e("anet.AnalysisFactory", "fulltrace commit fail.", (String) null, th, new Object[0]);
                }
            }
        }

        public C0523b getSceneInfo() {
            IFullTraceAnalysis iFullTraceAnalysis;
            if (!C0521a.f256b || (iFullTraceAnalysis = this.f257a) == null) {
                return null;
            }
            try {
                return iFullTraceAnalysis.getSceneInfo();
            } catch (Throwable th) {
                boolean unused = C0521a.f256b = false;
                ALog.m326e("anet.AnalysisFactory", "getSceneInfo fail", (String) null, th, new Object[0]);
                return null;
            }
        }
    }
}
