package org.android.spdy;

import android.os.Handler;
import android.os.HandlerThread;
import java.util.concurrent.atomic.AtomicBoolean;
import org.android.spdy.ProtectedPointer;

public final class SpdySession {
    private static volatile int count;
    private SpdyAgent agent;
    private String authority;
    private AtomicBoolean closed = new AtomicBoolean();
    private String domain;
    private Handler handler;
    Intenalcb intenalcb;
    private Object lock = new Object();
    private int mode;
    private ProtectedPointer pptr4sessionNativePtr;
    private int pubkey_seqnum = 0;
    volatile int refcount = 1;
    SessionCb sessionCallBack = null;
    private boolean sessionClearedFromSessionMgr = false;
    /* access modifiers changed from: private */
    public volatile long sessionNativePtr;
    private NetSparseArray<SpdyStreamContext> spdyStream = null;
    private int streamcount = 1;
    private HandlerThread thread;
    private Object userData = null;

    /* access modifiers changed from: private */
    public native int NotifyNotInvokeAnyMoreN(long j);

    private native int sendCustomControlFrameN(long j, int i, int i2, int i3, int i4, byte[] bArr);

    private native int sendHeadersN(long j, int i, String[] strArr, boolean z);

    private native int setOptionN(long j, int i, int i2);

    private native int streamCloseN(long j, int i, int i2);

    private native int streamSendDataN(long j, int i, byte[] bArr, int i2, int i3, boolean z);

    private native int submitBioPingN(long j);

    private native int submitPingN(long j);

    private native int submitRequestN(long j, String str, byte b, String[] strArr, byte[] bArr, boolean z, int i, int i2, int i3);

    SpdySession(long j, SpdyAgent spdyAgent, String str, String str2, SessionCb sessionCb, int i, int i2, Object obj) {
        this.sessionNativePtr = j;
        this.pptr4sessionNativePtr = new ProtectedPointer(this);
        this.pptr4sessionNativePtr.setHow2close(new ProtectedPointer.ProtectedPointerOnClose() {
            public void close(Object obj) {
                SpdySession spdySession = (SpdySession) obj;
                int unused = spdySession.NotifyNotInvokeAnyMoreN(spdySession.sessionNativePtr);
                spdySession.setSessionNativePtr(0);
            }
        });
        this.agent = spdyAgent;
        this.authority = str;
        this.intenalcb = new SpdySessionCallBack();
        this.domain = str2;
        this.spdyStream = new NetSparseArray<>(5);
        this.sessionCallBack = sessionCb;
        this.pubkey_seqnum = i2;
        this.mode = i;
        this.userData = obj;
        this.closed.set(false);
    }

    public int getRefCount() {
        return this.refcount;
    }

    /* access modifiers changed from: package-private */
    public void increRefCount() {
        this.refcount++;
    }

    private String getAuthority() {
        return this.authority;
    }

    public String getDomain() {
        return this.domain;
    }

    public Object getUserData() {
        return this.userData;
    }

    /* access modifiers changed from: package-private */
    public int putSpdyStreamCtx(SpdyStreamContext spdyStreamContext) {
        int i;
        synchronized (this.lock) {
            i = this.streamcount;
            this.streamcount = i + 1;
            this.spdyStream.put(i, spdyStreamContext);
        }
        return i;
    }

    /* access modifiers changed from: package-private */
    public SpdyStreamContext getSpdyStream(int i) {
        SpdyStreamContext spdyStreamContext;
        if (i <= 0) {
            return null;
        }
        synchronized (this.lock) {
            spdyStreamContext = this.spdyStream.get(i);
        }
        return spdyStreamContext;
    }

    /* access modifiers changed from: package-private */
    public void removeSpdyStream(int i) {
        if (i > 0) {
            synchronized (this.lock) {
                this.spdyStream.remove(i);
            }
        }
    }

    public SpdyStreamContext[] getAllStreamCb() {
        SpdyStreamContext[] spdyStreamContextArr;
        synchronized (this.lock) {
            int size = this.spdyStream.size();
            if (size > 0) {
                spdyStreamContextArr = new SpdyStreamContext[size];
                this.spdyStream.toArray(spdyStreamContextArr);
            } else {
                spdyStreamContextArr = null;
            }
        }
        return spdyStreamContextArr;
    }

    public void clearAllStreamCb() {
        spduLog.Logd("tnet-jni", "[SpdySession.clearAllStreamCb] - ");
        synchronized (this.lock) {
            this.spdyStream.clear();
        }
    }

    /* access modifiers changed from: package-private */
    public SpdyAgent getSpdyAgent() {
        return this.agent;
    }

    /* access modifiers changed from: package-private */
    public Handler getMsgHandler() {
        return this.handler;
    }

    /* access modifiers changed from: package-private */
    public long getSessionNativePtr() {
        return this.sessionNativePtr;
    }

    /* access modifiers changed from: package-private */
    public void setSessionNativePtr(long j) {
        this.sessionNativePtr = j;
    }

    public int setOption(int i, int i2) throws SpdyErrorException {
        int i3;
        sessionIsOpen();
        if (this.pptr4sessionNativePtr.enter()) {
            i3 = setOptionN(this.sessionNativePtr, i, i2);
            this.pptr4sessionNativePtr.exit();
        } else {
            i3 = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (i3 == 0) {
            return i3;
        }
        throw new SpdyErrorException("setOption error: " + i3, i3);
    }

    public int submitPing() throws SpdyErrorException {
        int i;
        sessionIsOpen();
        if (this.pptr4sessionNativePtr.enter()) {
            i = submitPingN(this.sessionNativePtr);
            this.pptr4sessionNativePtr.exit();
        } else {
            i = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (i == 0) {
            return i;
        }
        throw new SpdyErrorException("submitPing error: " + i, i);
    }

    @Deprecated
    public int submitBioPing() throws SpdyErrorException {
        int i;
        sessionIsOpen();
        if (this.pptr4sessionNativePtr.enter()) {
            i = submitBioPingN(this.sessionNativePtr);
            this.pptr4sessionNativePtr.exit();
        } else {
            i = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (i == 0) {
            return i;
        }
        throw new SpdyErrorException("submitBioPing error: " + i, i);
    }

    public int streamReset(long j, int i) throws SpdyErrorException {
        int i2;
        sessionIsOpen();
        spduLog.Logd("tnet-jni", "[SpdySession.streamReset] - ");
        if (this.pptr4sessionNativePtr.enter()) {
            i2 = streamCloseN(this.sessionNativePtr, (int) j, i);
            this.pptr4sessionNativePtr.exit();
        } else {
            i2 = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (i2 == 0) {
            return i2;
        }
        throw new SpdyErrorException("streamReset error: " + i2, i2);
    }

    public int sendCustomControlFrame(int i, int i2, int i3, int i4, byte[] bArr) throws SpdyErrorException {
        int i5;
        sessionIsOpen();
        if (bArr != null && bArr.length <= 0) {
            bArr = null;
        }
        byte[] bArr2 = bArr;
        spduLog.Logi("tnet-jni", "[sendCustomControlFrame] - type: " + i2);
        if (this.pptr4sessionNativePtr.enter()) {
            i5 = sendCustomControlFrameN(this.sessionNativePtr, i, i2, i3, i4, bArr2);
            this.pptr4sessionNativePtr.exit();
        } else {
            i5 = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        if (i5 == 0) {
            return i5;
        }
        throw new SpdyErrorException("sendCustomControlFrame error: " + i5, i5);
    }

    public int submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Spdycb spdycb) throws SpdyErrorException {
        SpdyStreamContext spdyStreamContext;
        String str;
        int i;
        SpdyDataProvider spdyDataProvider2 = spdyDataProvider;
        Object obj2 = obj;
        if (spdyRequest == null || obj2 == null || spdyRequest.getAuthority() == null) {
            throw new SpdyErrorException("submitRequest error: -1102", (int) TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
        }
        sessionIsOpen();
        byte[] dataproviderToByteArray = SpdyAgent.dataproviderToByteArray(spdyRequest, spdyDataProvider);
        if (dataproviderToByteArray != null && dataproviderToByteArray.length <= 0) {
            dataproviderToByteArray = null;
        }
        byte[] bArr = dataproviderToByteArray;
        boolean z = spdyDataProvider2 != null ? spdyDataProvider2.finished : true;
        SpdyStreamContext spdyStreamContext2 = new SpdyStreamContext(obj2, spdycb);
        int putSpdyStreamCtx = putSpdyStreamCtx(spdyStreamContext2);
        String[] mapToByteArray = SpdyAgent.mapToByteArray(spdyRequest.getHeaders());
        spduLog.Logi("tnet-jni", "index=" + putSpdyStreamCtx + "  " + "starttime=" + System.currentTimeMillis());
        if (this.pptr4sessionNativePtr.enter()) {
            spdyStreamContext = spdyStreamContext2;
            str = "tnet-jni";
            i = submitRequestN(this.sessionNativePtr, spdyRequest.getUrlPath(), (byte) spdyRequest.getPriority(), mapToByteArray, bArr, z, putSpdyStreamCtx, spdyRequest.getRequestTimeoutMs(), spdyRequest.getRequestRdTimeoutMs());
            this.pptr4sessionNativePtr.exit();
        } else {
            spdyStreamContext = spdyStreamContext2;
            str = "tnet-jni";
            i = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
        }
        spduLog.Logi(str, "index=" + putSpdyStreamCtx + "  " + " calltime=" + System.currentTimeMillis());
        if (i >= 0) {
            spdyStreamContext.streamId = i;
            return i;
        }
        removeSpdyStream(putSpdyStreamCtx);
        throw new SpdyErrorException("submitRequest error: " + i, i);
    }

    /* access modifiers changed from: package-private */
    public void sessionIsOpen() {
        if (this.closed.get()) {
            throw new SpdyErrorException("session is already closed: -1104", (int) TnetStatusCode.TNET_JNI_ERR_ASYNC_CLOSE);
        }
    }

    public int cleanUp() {
        spduLog.Logd("tnet-jni", "[SpdySession.cleanUp] - ");
        if (this.closed.getAndSet(true)) {
            return 0;
        }
        this.agent.removeSession(this);
        return closeprivate();
    }

    /* access modifiers changed from: package-private */
    public int closeInternal() {
        if (!this.closed.getAndSet(true)) {
            return closeprivate();
        }
        return 0;
    }

    public int closeSession() {
        int i;
        ProtectedPointer protectedPointer;
        spduLog.Logd("tnet-jni", "[SpdySession.closeSession] - ");
        synchronized (this.lock) {
            i = 0;
            if (!this.sessionClearedFromSessionMgr) {
                spduLog.Logd("tnet-jni", "[SpdySession.closeSession] - " + this.authority);
                this.agent.clearSpdySession(this.authority, this.domain, this.mode);
                this.sessionClearedFromSessionMgr = true;
                if (this.pptr4sessionNativePtr.enter()) {
                    try {
                        i = this.agent.closeSession(this.sessionNativePtr);
                        protectedPointer = this.pptr4sessionNativePtr;
                    } catch (UnsatisfiedLinkError e) {
                        try {
                            e.printStackTrace();
                            protectedPointer = this.pptr4sessionNativePtr;
                        } catch (Throwable th) {
                            this.pptr4sessionNativePtr.exit();
                            throw th;
                        }
                    }
                    protectedPointer.exit();
                } else {
                    i = TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS;
                }
            }
        }
        return i;
    }

    private int closeprivate() {
        synchronized (this.lock) {
            if (!this.sessionClearedFromSessionMgr) {
                this.agent.clearSpdySession(this.authority, this.domain, this.mode);
                this.sessionClearedFromSessionMgr = true;
            }
        }
        synchronized (this.lock) {
            SpdyStreamContext[] allStreamCb = getAllStreamCb();
            if (allStreamCb != null) {
                for (SpdyStreamContext spdyStreamContext : allStreamCb) {
                    spduLog.Logi("tnet-jni", "[SpdySessionCallBack.spdyStreamCloseCallback] unfinished stm=" + spdyStreamContext.streamId);
                    spdyStreamContext.callBack.spdyStreamCloseCallback(this, (long) spdyStreamContext.streamId, TnetStatusCode.EASY_REASON_CONN_NOT_EXISTS, spdyStreamContext.streamContext, (SuperviseData) null);
                }
            }
            this.spdyStream.clear();
        }
        return 0;
    }

    /* access modifiers changed from: package-private */
    public void releasePptr() {
        this.pptr4sessionNativePtr.release();
    }
}
