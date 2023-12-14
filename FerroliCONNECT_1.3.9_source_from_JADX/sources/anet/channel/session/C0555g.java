package anet.channel.session;

import anet.channel.RequestCb;
import anet.channel.bytes.ByteArray;
import anet.channel.entity.C0518b;
import anet.channel.statist.RequestStatistic;
import anet.channel.util.ALog;
import anet.channel.util.HttpHelper;
import java.util.List;
import java.util.Map;

/* renamed from: anet.channel.session.g */
/* compiled from: Taobao */
class C0555g implements RequestCb {

    /* renamed from: a */
    final /* synthetic */ C0554f f400a;

    C0555g(C0554f fVar) {
        this.f400a = fVar;
    }

    public void onResponseCode(int i, Map<String, List<String>> map) {
        ALog.m328i("awcn.HttpSession", "", this.f400a.f396a.getSeq(), "httpStatusCode", Integer.valueOf(i));
        ALog.m328i("awcn.HttpSession", "", this.f400a.f396a.getSeq(), "response headers", map);
        this.f400a.f397b.onResponseCode(i, map);
        this.f400a.f398c.serverRT = HttpHelper.parseServerRT(map);
        this.f400a.f399d.handleResponseCode(this.f400a.f396a, i);
        this.f400a.f399d.handleResponseHeaders(this.f400a.f396a, map);
    }

    public void onDataReceive(ByteArray byteArray, boolean z) {
        this.f400a.f397b.onDataReceive(byteArray, z);
    }

    public void onFinish(int i, String str, RequestStatistic requestStatistic) {
        if (i <= 0 && i != -204) {
            this.f400a.f399d.handleCallbacks(2, new C0518b(2, 0, "Http connect fail"));
        }
        this.f400a.f397b.onFinish(i, str, requestStatistic);
    }
}
