package anetwork.channel.unified;

import android.support.p000v4.app.NotificationCompat;
import android.support.p000v4.media.session.PlaybackStateCompat;
import anet.channel.RequestCb;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.flow.FlowStat;
import anet.channel.flow.NetworkAnalysis;
import anet.channel.monitor.C0533b;
import anet.channel.request.Request;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.RequestStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.thread.ThreadPoolExecutorFactory;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.HttpHelper;
import anet.channel.util.HttpUrl;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.C0625a;
import anetwork.channel.cache.Cache;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.cookie.CookieManager;
import anetwork.channel.unified.C0646e;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: anetwork.channel.unified.i */
/* compiled from: Taobao */
class C0651i implements RequestCb {

    /* renamed from: a */
    final /* synthetic */ Request f784a;

    /* renamed from: b */
    final /* synthetic */ RequestStatistic f785b;

    /* renamed from: c */
    final /* synthetic */ C0646e f786c;

    C0651i(C0646e eVar, Request request, RequestStatistic requestStatistic) {
        this.f786c = eVar;
        this.f784a = request;
        this.f785b = requestStatistic;
    }

    public void onResponseCode(int i, Map<String, List<String>> map) {
        String singleHeaderFieldByKey;
        if (!this.f786c.f761h.get()) {
            if (ALog.isPrintLog(2)) {
                ALog.m328i(C0646e.TAG, "onResponseCode", this.f784a.getSeq(), "code", Integer.valueOf(i));
                ALog.m328i(C0646e.TAG, "onResponseCode", this.f784a.getSeq(), "headers", map);
            }
            if (HttpHelper.checkRedirect(this.f784a, i) && (singleHeaderFieldByKey = HttpHelper.getSingleHeaderFieldByKey(map, "Location")) != null) {
                HttpUrl parse = HttpUrl.parse(singleHeaderFieldByKey);
                if (parse == null) {
                    ALog.m327e(C0646e.TAG, "redirect url is invalid!", this.f784a.getSeq(), "redirect url", singleHeaderFieldByKey);
                } else if (this.f786c.f761h.compareAndSet(false, true)) {
                    parse.lockScheme();
                    this.f786c.f754a.f787a.mo9331a(parse);
                    this.f786c.f754a.f790d = new AtomicBoolean();
                    this.f786c.f754a.f791e = new C0646e(this.f786c.f754a, (Cache) null, (Cache.Entry) null);
                    this.f785b.recordRedirect(i, parse.simpleUrlString());
                    this.f785b.locationUrl = singleHeaderFieldByKey;
                    ThreadPoolExecutorFactory.submitPriorityTask(this.f786c.f754a.f791e, ThreadPoolExecutorFactory.Priority.HIGH);
                    return;
                } else {
                    return;
                }
            }
            try {
                this.f786c.f754a.mo9369a();
                CookieManager.setCookie(this.f786c.f754a.f787a.mo9337g(), map);
                this.f786c.f762i = HttpHelper.parseContentLength(map);
                String g = this.f786c.f754a.f787a.mo9337g();
                if (this.f786c.f756c == null || i != 304) {
                    if (this.f786c.f755b != null) {
                        if ("no-store".equals(HttpHelper.getSingleHeaderFieldByKey(map, "Cache-Control"))) {
                            this.f786c.f755b.remove(g);
                        } else {
                            C0646e eVar = this.f786c;
                            Cache.Entry a = C0625a.m386a(map);
                            eVar.f756c = a;
                            if (a != null) {
                                HttpHelper.removeHeaderFiledByKey(map, "Cache-Control");
                                map.put("Cache-Control", Arrays.asList(new String[]{"no-store"}));
                                this.f786c.f757d = new ByteArrayOutputStream(this.f786c.f762i != 0 ? this.f786c.f762i : 5120);
                            }
                        }
                    }
                    map.put(HttpConstant.X_PROTOCOL, Arrays.asList(new String[]{this.f785b.protocolType}));
                    if ("open".equalsIgnoreCase(HttpHelper.getSingleHeaderFieldByKey(map, HttpConstant.STREAMING_PARSER)) || !NetworkConfigCenter.isResponseBufferEnable() || this.f786c.f762i > 131072) {
                        this.f786c.f754a.f788b.onResponseCode(i, map);
                        this.f786c.f764k = true;
                        return;
                    }
                    this.f786c.f766m = new C0646e.C0647a(i, map);
                    return;
                }
                this.f786c.f756c.responseHeaders.putAll(map);
                Cache.Entry a2 = C0625a.m386a(map);
                if (a2 != null && a2.ttl > this.f786c.f756c.ttl) {
                    this.f786c.f756c.ttl = a2.ttl;
                }
                this.f786c.f754a.f788b.onResponseCode(200, this.f786c.f756c.responseHeaders);
                this.f786c.f754a.f788b.onDataReceiveSize(1, this.f786c.f756c.data.length, ByteArray.wrap(this.f786c.f756c.data));
                long currentTimeMillis = System.currentTimeMillis();
                this.f786c.f755b.put(g, this.f786c.f756c);
                ALog.m328i(C0646e.TAG, "update cache", this.f786c.f754a.f789c, "cost", Long.valueOf(System.currentTimeMillis() - currentTimeMillis), "key", g);
            } catch (Exception e) {
                ALog.m329w(C0646e.TAG, "[onResponseCode] error.", this.f786c.f754a.f789c, e, new Object[0]);
            }
        }
    }

    public void onDataReceive(ByteArray byteArray, boolean z) {
        if (!this.f786c.f761h.get()) {
            if (this.f786c.f763j == 0) {
                ALog.m328i(C0646e.TAG, "[onDataReceive] receive first data chunk!", this.f786c.f754a.f789c, new Object[0]);
            }
            if (z) {
                ALog.m328i(C0646e.TAG, "[onDataReceive] receive last data chunk!", this.f786c.f754a.f789c, new Object[0]);
            }
            this.f786c.f763j++;
            try {
                if (this.f786c.f766m != null) {
                    this.f786c.f766m.f769c.add(byteArray);
                    if (this.f785b.recDataSize > PlaybackStateCompat.ACTION_PREPARE_FROM_URI || z) {
                        this.f786c.f763j = this.f786c.f766m.mo9365a(this.f786c.f754a.f788b, this.f786c.f762i);
                        this.f786c.f764k = true;
                        this.f786c.f765l = this.f786c.f763j > 1;
                        this.f786c.f766m = null;
                    }
                } else {
                    this.f786c.f754a.f788b.onDataReceiveSize(this.f786c.f763j, this.f786c.f762i, byteArray);
                    this.f786c.f765l = true;
                }
                if (this.f786c.f757d != null) {
                    this.f786c.f757d.write(byteArray.getBuffer(), 0, byteArray.getDataLength());
                    if (z) {
                        String g = this.f786c.f754a.f787a.mo9337g();
                        this.f786c.f756c.data = this.f786c.f757d.toByteArray();
                        long currentTimeMillis = System.currentTimeMillis();
                        this.f786c.f755b.put(g, this.f786c.f756c);
                        ALog.m328i(C0646e.TAG, "write cache", this.f786c.f754a.f789c, "cost", Long.valueOf(System.currentTimeMillis() - currentTimeMillis), "size", Integer.valueOf(this.f786c.f756c.data.length), "key", g);
                    }
                }
            } catch (Exception e) {
                ALog.m329w(C0646e.TAG, "[onDataReceive] error.", this.f786c.f754a.f789c, e, new Object[0]);
            }
        }
    }

    public void onFinish(int i, String str, RequestStatistic requestStatistic) {
        DefaultFinishEvent defaultFinishEvent;
        String str2;
        if (!this.f786c.f761h.getAndSet(true)) {
            int i2 = 3;
            if (ALog.isPrintLog(2)) {
                ALog.m328i(C0646e.TAG, "[onFinish]", this.f786c.f754a.f789c, "code", Integer.valueOf(i), NotificationCompat.CATEGORY_MESSAGE, str);
            }
            if (i < 0) {
                try {
                    if (this.f786c.f754a.f787a.mo9334d()) {
                        if (this.f786c.f764k || this.f786c.f765l) {
                            requestStatistic.msg += ":回调后触发重试";
                            if (this.f786c.f765l) {
                                requestStatistic.roaming = 2;
                            } else if (this.f786c.f764k) {
                                requestStatistic.roaming = 1;
                            }
                            ALog.m327e(C0646e.TAG, "Cannot retry request after onHeader/onDataReceived callback!", this.f786c.f754a.f789c, new Object[0]);
                        } else {
                            ALog.m327e(C0646e.TAG, "clear response buffer and retry", this.f786c.f754a.f789c, new Object[0]);
                            if (this.f786c.f766m != null) {
                                if (!this.f786c.f766m.f769c.isEmpty()) {
                                    i2 = 4;
                                }
                                requestStatistic.roaming = i2;
                                this.f786c.f766m.mo9366a();
                                this.f786c.f766m = null;
                            }
                            if (this.f786c.f754a.f787a.f730a == 0) {
                                requestStatistic.firstProtocol = requestStatistic.protocolType;
                                requestStatistic.firstErrorCode = requestStatistic.tnetErrorCode != 0 ? requestStatistic.tnetErrorCode : i;
                            }
                            this.f786c.f754a.f787a.mo9341k();
                            this.f786c.f754a.f790d = new AtomicBoolean();
                            this.f786c.f754a.f791e = new C0646e(this.f786c.f754a, this.f786c.f755b, this.f786c.f756c);
                            if (requestStatistic.tnetErrorCode != 0) {
                                str2 = i + "|" + requestStatistic.protocolType + "|" + requestStatistic.tnetErrorCode;
                                requestStatistic.tnetErrorCode = 0;
                            } else {
                                str2 = String.valueOf(i);
                            }
                            requestStatistic.appendErrorTrace(str2);
                            long currentTimeMillis = System.currentTimeMillis();
                            requestStatistic.retryCostTime += currentTimeMillis - requestStatistic.start;
                            requestStatistic.start = currentTimeMillis;
                            ThreadPoolExecutorFactory.submitPriorityTask(this.f786c.f754a.f791e, ThreadPoolExecutorFactory.Priority.HIGH);
                            return;
                        }
                    }
                } catch (Exception unused) {
                    return;
                }
            }
            if (this.f786c.f766m != null) {
                this.f786c.f766m.mo9365a(this.f786c.f754a.f788b, this.f786c.f762i);
            }
            this.f786c.f754a.mo9369a();
            requestStatistic.isDone.set(true);
            if (!(!this.f786c.f754a.f787a.mo9340j() || requestStatistic.contentLength == 0 || requestStatistic.contentLength == requestStatistic.rspBodyDeflateSize)) {
                requestStatistic.ret = 0;
                requestStatistic.statusCode = ErrorConstant.ERROR_DATA_LENGTH_NOT_MATCH;
                str = ErrorConstant.getErrMsg(ErrorConstant.ERROR_DATA_LENGTH_NOT_MATCH);
                requestStatistic.msg = str;
                ALog.m327e(C0646e.TAG, "received data length not match with content-length", this.f786c.f754a.f789c, "content-length", Integer.valueOf(this.f786c.f762i), "recDataLength", Long.valueOf(requestStatistic.rspBodyDeflateSize));
                ExceptionStatistic exceptionStatistic = new ExceptionStatistic(ErrorConstant.ERROR_DATA_LENGTH_NOT_MATCH, str, "rt");
                exceptionStatistic.url = this.f786c.f754a.f787a.mo9337g();
                AppMonitor.getInstance().commitStat(exceptionStatistic);
                i = ErrorConstant.ERROR_DATA_LENGTH_NOT_MATCH;
            }
            if (i != 304 || this.f786c.f756c == null) {
                defaultFinishEvent = new DefaultFinishEvent(i, str, this.f784a);
            } else {
                requestStatistic.protocolType = "cache";
                defaultFinishEvent = new DefaultFinishEvent(200, str, this.f784a);
            }
            this.f786c.f754a.f788b.onFinish(defaultFinishEvent);
            if (i >= 0) {
                C0533b.m138a().mo8842a(requestStatistic.sendStart, requestStatistic.rspEnd, requestStatistic.rspHeadDeflateSize + requestStatistic.rspBodyDeflateSize);
            } else {
                requestStatistic.netType = NetworkStatusHelper.getNetworkSubType();
            }
            NetworkAnalysis.getInstance().commitFlow(new FlowStat(this.f786c.f758e, requestStatistic));
        }
    }
}
