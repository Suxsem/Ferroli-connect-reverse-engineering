package org.android.spdy;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.UByte;
import kotlin.text.Typography;

public final class SpdyAgent {
    public static final int ACCS_ONLINE_SERVER = 1;
    public static final int ACCS_TEST_SERVER = 0;
    private static final boolean HAVE_CLOSE = false;
    private static final int KB32 = 32768;
    private static final int KB8 = 8192;
    private static final int MAX_SPDY_SESSION_COUNT = 50;
    private static final int MB5 = 5242880;
    static final int MODE_QUIC = 256;
    static final int SPDY_CUSTOM_CONTROL_FRAME_RECV = 4106;
    static final int SPDY_DATA_CHUNK_RECV = 4097;
    static final int SPDY_DATA_RECV = 4098;
    static final int SPDY_DATA_SEND = 4099;
    static final int SPDY_PING_RECV = 4101;
    static final int SPDY_REQUEST_RECV = 4102;
    static final int SPDY_SESSION_CLOSE = 4103;
    static final int SPDY_SESSION_CREATE = 4096;
    static final int SPDY_SESSION_FAILED_ERROR = 4105;
    static final int SPDY_STREAM_CLOSE = 4100;
    static final int SPDY_STREAM_RESPONSE_RECV = 4104;
    private static final String TNET_SO_VERSION = "tnet-3.1.14";
    private static Object domainHashLock = new Object();
    private static HashMap<String, Integer> domainHashMap = new HashMap<>();
    public static volatile boolean enableDebug = false;
    public static volatile boolean enableTimeGaurd = false;
    private static volatile SpdyAgent gSingleInstance = null;
    private static volatile boolean loadSucc = false;
    private static Object lock = new Object();

    /* renamed from: r */
    private static final Lock f4103r = rwLock.readLock();
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static int totalDomain = 0;

    /* renamed from: w */
    private static final Lock f4104w = rwLock.writeLock();
    private AccsSSLCallback accsSSLCallback;
    private long agentNativePtr;
    private AtomicBoolean closed = new AtomicBoolean();
    private String proxyPassword = null;
    private String proxyUsername = null;
    private HashMap<String, SpdySession> sessionMgr = new HashMap<>(5);
    private LinkedList<SpdySession> sessionQueue = new LinkedList<>();

    private native int closeSessionN(long j);

    private static native int configIpStackModeN(int i);

    private native int configLogFileN(String str, int i, int i2);

    private native int configLogFileN(String str, int i, int i2, int i3);

    private static void crashReporter(int i) {
    }

    private native long createSessionN(long j, SpdySession spdySession, int i, byte[] bArr, char c, byte[] bArr2, char c2, byte[] bArr3, byte[] bArr4, Object obj, int i2, int i3, int i4, byte[] bArr5);

    private native int freeAgent(long j);

    private void getPerformance(SpdySession spdySession, SslPermData sslPermData) {
    }

    private native long getSession(long j, byte[] bArr, char c);

    private native long initAgent(int i, int i2, int i3);

    @Deprecated
    public static void inspect(String str) {
    }

    private native void logFileCloseN();

    private native void logFileFlushN();

    private native int setConTimeout(long j, int i);

    private native int setSessionKind(long j, int i);

    public void close() {
    }

    @Deprecated
    public void switchAccsServer(int i) {
    }

    /* access modifiers changed from: package-private */
    public void clearSpdySession(String str, String str2, int i) {
        if (str != null) {
            f4104w.lock();
            if (str != null) {
                try {
                    HashMap<String, SpdySession> hashMap = this.sessionMgr;
                    hashMap.remove(str + str2 + i);
                } catch (Throwable th) {
                    f4104w.unlock();
                    throw th;
                }
            }
            f4104w.unlock();
        }
    }

    public static SpdyAgent getInstance(Context context, SpdyVersion spdyVersion, SpdySessionKind spdySessionKind) throws UnsatisfiedLinkError, SpdyErrorException {
        if (gSingleInstance == null) {
            synchronized (lock) {
                if (gSingleInstance == null) {
                    gSingleInstance = new SpdyAgent(context, spdyVersion, spdySessionKind, (AccsSSLCallback) null);
                }
            }
        }
        return gSingleInstance;
    }

    public static boolean checkLoadSucc() {
        return loadSucc;
    }

    @Deprecated
    public static SpdyAgent getInstance(Context context, SpdyVersion spdyVersion, SpdySessionKind spdySessionKind, AccsSSLCallback accsSSLCallback2) throws UnsatisfiedLinkError, SpdyErrorException {
        if (gSingleInstance == null) {
            synchronized (lock) {
                if (gSingleInstance == null) {
                    gSingleInstance = new SpdyAgent(context, spdyVersion, spdySessionKind, accsSSLCallback2);
                }
            }
        }
        return gSingleInstance;
    }

    private int getDomainHashIndex(String str) {
        Integer num;
        synchronized (domainHashLock) {
            num = domainHashMap.get(str);
            if (num == null) {
                HashMap<String, Integer> hashMap = domainHashMap;
                int i = totalDomain + 1;
                totalDomain = i;
                hashMap.put(str, Integer.valueOf(i));
                num = Integer.valueOf(totalDomain);
            }
        }
        return num.intValue();
    }

    private SpdyAgent(Context context, SpdyVersion spdyVersion, SpdySessionKind spdySessionKind, AccsSSLCallback accsSSLCallback2) throws UnsatisfiedLinkError {
        try {
            SoInstallMgrSdk.init(context);
            loadSucc = SoInstallMgrSdk.initSo(TNET_SO_VERSION, 1);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            this.agentNativePtr = initAgent(spdyVersion.getInt(), spdySessionKind.getint(), SslVersion.SLIGHT_VERSION_V1.getint());
            this.accsSSLCallback = accsSSLCallback2;
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
        this.closed.set(false);
    }

    private void checkLoadSo() throws SpdyErrorException {
        if (!loadSucc) {
            try {
                synchronized (lock) {
                    if (!loadSucc) {
                        loadSucc = SoInstallMgrSdk.initSo(TNET_SO_VERSION, 1);
                        this.agentNativePtr = initAgent(0, 0, 0);
                    } else {
                        return;
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        } else {
            return;
        }
        if (!loadSucc) {
            throw new SpdyErrorException("TNET_JNI_ERR_LOAD_SO_FAIL", (int) TnetStatusCode.TNET_JNI_ERR_LOAD_SO_FAIL);
        }
    }

    public void setProxyUsernamePassword(String str, String str2) {
        this.proxyUsername = str;
        this.proxyPassword = str2;
    }

    static void securityCheck(int i, int i2) {
        if (i >= 32768) {
            throw new SpdyErrorException("SPDY_JNI_ERR_INVALID_PARAM:total=" + i, (int) TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
        } else if (i2 >= 8192) {
            throw new SpdyErrorException("SPDY_JNI_ERR_INVALID_PARAM:value=" + i2, (int) TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
        }
    }

    static void tableListJudge(int i) {
        if (i >= 5242880) {
            throw new SpdyErrorException("SPDY_JNI_ERR_INVALID_PARAM:total=" + i, (int) TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
        }
    }

    static void InvlidCharJudge(byte[] bArr, byte[] bArr2) {
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & UByte.MAX_VALUE) < 32 || (bArr[i] & UByte.MAX_VALUE) > 126) {
                bArr[i] = 63;
            }
        }
        for (int i2 = 0; i2 < bArr2.length; i2++) {
            if ((bArr2[i2] & UByte.MAX_VALUE) < 32 || (bArr2[i2] & UByte.MAX_VALUE) > 126) {
                bArr2[i2] = 63;
            }
        }
    }

    static void headJudge(Map<String, String> map) {
        if (map != null) {
            int i = 0;
            for (Map.Entry next : map.entrySet()) {
                String str = (String) next.getKey();
                String str2 = (String) next.getValue();
                InvlidCharJudge(str.getBytes(), str2.getBytes());
                i += str.length() + 1 + str2.length();
                securityCheck(i, str2.length());
            }
        }
    }

    static String mapBodyToString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        if (map == null) {
            return null;
        }
        int i = 0;
        for (Map.Entry next : map.entrySet()) {
            String str = (String) next.getKey();
            String str2 = (String) next.getValue();
            sb.append(str);
            sb.append('=');
            sb.append(str2);
            sb.append(Typography.amp);
            i += str.length() + 1 + str2.length();
            tableListJudge(i);
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    static byte[] dataproviderToByteArray(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider) {
        byte[] bArr;
        headJudge(spdyRequest.getHeaders());
        if (spdyDataProvider == null) {
            return null;
        }
        String mapBodyToString = mapBodyToString(spdyDataProvider.postBody);
        if (mapBodyToString != null) {
            bArr = mapBodyToString.getBytes();
        } else {
            bArr = spdyDataProvider.data;
        }
        if (bArr == null || bArr.length < 5242880) {
            return bArr;
        }
        throw new SpdyErrorException("SPDY_JNI_ERR_INVALID_PARAM:total=" + bArr.length, (int) TnetStatusCode.TNET_JNI_ERR_INVLID_PARAM);
    }

    @Deprecated
    public SpdySession createSession(String str, Object obj, SessionCb sessionCb, int i) throws SpdyErrorException {
        return createSession(str, "", obj, sessionCb, (SslCertcb) null, i, 0);
    }

    @Deprecated
    public SpdySession createSession(String str, String str2, Object obj, SessionCb sessionCb, int i) throws SpdyErrorException {
        return createSession(str, str2, obj, sessionCb, (SslCertcb) null, i, 0);
    }

    @Deprecated
    public SpdySession createSession(String str, Object obj, SessionCb sessionCb, SslCertcb sslCertcb, int i) throws SpdyErrorException {
        return createSession(str, "", obj, sessionCb, sslCertcb, i, 0);
    }

    public SpdySession createSession(SessionInfo sessionInfo) throws SpdyErrorException {
        return createSession(sessionInfo.getAuthority(), sessionInfo.getDomain(), sessionInfo.getSessonUserData(), sessionInfo.getSessionCb(), (SslCertcb) null, sessionInfo.getMode(), sessionInfo.getPubKeySeqNum(), sessionInfo.getConnectionTimeoutMs(), sessionInfo.getCertHost());
    }

    @Deprecated
    public SpdySession createSession(String str, String str2, Object obj, SessionCb sessionCb, SslCertcb sslCertcb, int i, int i2) throws SpdyErrorException {
        return createSession(str, str2, obj, sessionCb, sslCertcb, i, i2, -1);
    }

    public SpdySession createSession(String str, String str2, Object obj, SessionCb sessionCb, SslCertcb sslCertcb, int i, int i2, int i3) throws SpdyErrorException {
        return createSession(str, str2, obj, sessionCb, sslCertcb, i, i2, i3, (String) null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:47:0x0189 A[Catch:{ all -> 0x013d }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x018e A[Catch:{ all -> 0x013d }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0193 A[Catch:{ all -> 0x013d }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01bd  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.android.spdy.SpdySession createSession(java.lang.String r25, java.lang.String r26, java.lang.Object r27, org.android.spdy.SessionCb r28, org.android.spdy.SslCertcb r29, int r30, int r31, int r32, java.lang.String r33) throws org.android.spdy.SpdyErrorException {
        /*
            r24 = this;
            r15 = r24
            r0 = r25
            r14 = r26
            r13 = r30
            if (r0 == 0) goto L_0x01fd
            java.lang.String r1 = "/"
            java.lang.String[] r1 = r0.split(r1)
            r12 = 0
            r2 = r1[r12]
            r3 = 58
            int r2 = r2.lastIndexOf(r3)
            r3 = r1[r12]
            java.lang.String r11 = r3.substring(r12, r2)
            r3 = r1[r12]
            r10 = 1
            int r2 = r2 + r10
            java.lang.String r16 = r3.substring(r2)
            java.lang.String r2 = "0.0.0.0"
            byte[] r2 = r2.getBytes()
            int r3 = r1.length
            if (r3 == r10) goto L_0x004b
            r1 = r1[r10]
            java.lang.String r2 = ":"
            java.lang.String[] r1 = r1.split(r2)
            r2 = r1[r12]
            byte[] r2 = r2.getBytes()
            r1 = r1[r10]
            int r1 = java.lang.Integer.parseInt(r1)
            char r1 = (char) r1
            r9 = r0
            r18 = r1
            r17 = r2
            goto L_0x0061
        L_0x004b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r0)
            java.lang.String r3 = "/0.0.0.0:0"
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            r9 = r1
            r17 = r2
            r18 = 0
        L_0x0061:
            r24.agentIsOpen()
            java.util.concurrent.locks.Lock r1 = f4103r
            r1.lock()
            java.util.HashMap<java.lang.String, org.android.spdy.SpdySession> r1 = r15.sessionMgr     // Catch:{ all -> 0x01f5 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x01f5 }
            r2.<init>()     // Catch:{ all -> 0x01f5 }
            r2.append(r9)     // Catch:{ all -> 0x01f5 }
            r2.append(r14)     // Catch:{ all -> 0x01f5 }
            r2.append(r13)     // Catch:{ all -> 0x01f5 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x01f5 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x01f5 }
            org.android.spdy.SpdySession r1 = (org.android.spdy.SpdySession) r1     // Catch:{ all -> 0x01f5 }
            java.util.HashMap<java.lang.String, org.android.spdy.SpdySession> r2 = r15.sessionMgr     // Catch:{ all -> 0x01f5 }
            int r2 = r2.size()     // Catch:{ all -> 0x01f5 }
            r3 = 50
            if (r2 < r3) goto L_0x008f
            r2 = 1
            goto L_0x0090
        L_0x008f:
            r2 = 0
        L_0x0090:
            java.util.concurrent.locks.Lock r3 = f4103r
            r3.unlock()
            if (r2 != 0) goto L_0x01ea
            if (r1 == 0) goto L_0x009d
            r1.increRefCount()
            return r1
        L_0x009d:
            java.util.concurrent.locks.Lock r1 = f4104w
            r1.lock()
            r19 = 0
            java.util.HashMap<java.lang.String, org.android.spdy.SpdySession> r1 = r15.sessionMgr     // Catch:{ Throwable -> 0x00bf }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00bf }
            r2.<init>()     // Catch:{ Throwable -> 0x00bf }
            r2.append(r9)     // Catch:{ Throwable -> 0x00bf }
            r2.append(r14)     // Catch:{ Throwable -> 0x00bf }
            r2.append(r13)     // Catch:{ Throwable -> 0x00bf }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x00bf }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ Throwable -> 0x00bf }
            org.android.spdy.SpdySession r1 = (org.android.spdy.SpdySession) r1     // Catch:{ Throwable -> 0x00bf }
            goto L_0x00c1
        L_0x00bf:
            r1 = r19
        L_0x00c1:
            if (r1 == 0) goto L_0x00cc
            java.util.concurrent.locks.Lock r0 = f4104w
            r0.unlock()
            r1.increRefCount()
            return r1
        L_0x00cc:
            org.android.spdy.SpdySession r8 = new org.android.spdy.SpdySession     // Catch:{ all -> 0x01e2 }
            r2 = 0
            r1 = r8
            r4 = r24
            r5 = r9
            r6 = r26
            r7 = r28
            r28 = r8
            r8 = r30
            r20 = r9
            r9 = r31
            r21 = 1
            r10 = r27
            r1.<init>(r2, r4, r5, r6, r7, r8, r9, r10)     // Catch:{ all -> 0x01e2 }
            if (r33 != 0) goto L_0x00ec
            r22 = r19
            goto L_0x00f2
        L_0x00ec:
            byte[] r1 = r33.getBytes()     // Catch:{ all -> 0x01e2 }
            r22 = r1
        L_0x00f2:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e2 }
            r1.<init>()     // Catch:{ all -> 0x01e2 }
            r1.append(r14)     // Catch:{ all -> 0x01e2 }
            r1.append(r13)     // Catch:{ all -> 0x01e2 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x01e2 }
            int r5 = r15.getDomainHashIndex(r1)     // Catch:{ all -> 0x01e2 }
            java.lang.String r1 = r15.proxyUsername     // Catch:{ all -> 0x01e2 }
            if (r1 == 0) goto L_0x0142
            java.lang.String r1 = r15.proxyPassword     // Catch:{ all -> 0x013d }
            if (r1 == 0) goto L_0x0142
            long r2 = r15.agentNativePtr     // Catch:{ all -> 0x013d }
            byte[] r6 = r11.getBytes()     // Catch:{ all -> 0x013d }
            int r1 = java.lang.Integer.parseInt(r16)     // Catch:{ all -> 0x013d }
            char r7 = (char) r1     // Catch:{ all -> 0x013d }
            java.lang.String r1 = r15.proxyUsername     // Catch:{ all -> 0x013d }
            byte[] r10 = r1.getBytes()     // Catch:{ all -> 0x013d }
            java.lang.String r1 = r15.proxyPassword     // Catch:{ all -> 0x013d }
            byte[] r11 = r1.getBytes()     // Catch:{ all -> 0x013d }
            r1 = r24
            r4 = r28
            r8 = r17
            r9 = r18
            r23 = 0
            r12 = r27
            r13 = r30
            r14 = r31
            r15 = r32
            r16 = r22
            long r1 = r1.createSessionN(r2, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x013d }
            goto L_0x0169
        L_0x013d:
            r0 = move-exception
            r1 = r24
            goto L_0x01e4
        L_0x0142:
            r23 = 0
            r15 = r24
            long r2 = r15.agentNativePtr     // Catch:{ all -> 0x01e2 }
            byte[] r6 = r11.getBytes()     // Catch:{ all -> 0x01e2 }
            int r1 = java.lang.Integer.parseInt(r16)     // Catch:{ all -> 0x01e2 }
            char r7 = (char) r1
            r10 = 0
            r11 = 0
            r1 = r24
            r4 = r28
            r8 = r17
            r9 = r18
            r12 = r27
            r13 = r30
            r14 = r31
            r15 = r32
            r16 = r22
            long r1 = r1.createSessionN(r2, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16)     // Catch:{ all -> 0x013d }
        L_0x0169:
            java.lang.String r3 = "tnet-jni"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x013d }
            r4.<init>()     // Catch:{ all -> 0x013d }
            java.lang.String r5 = " create new session: "
            r4.append(r5)     // Catch:{ all -> 0x013d }
            r4.append(r0)     // Catch:{ all -> 0x013d }
            java.lang.String r0 = r4.toString()     // Catch:{ all -> 0x013d }
            org.android.spdy.spduLog.Logi(r3, r0)     // Catch:{ all -> 0x013d }
            r3 = 1
            long r5 = r1 & r3
            r7 = 0
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 != 0) goto L_0x018e
            long r0 = r1 >> r21
            int r12 = (int) r0     // Catch:{ all -> 0x013d }
            r1 = r7
            goto L_0x018f
        L_0x018e:
            r12 = 0
        L_0x018f:
            int r0 = (r1 > r7 ? 1 : (r1 == r7 ? 0 : -1))
            if (r0 == 0) goto L_0x01bd
            r0 = r28
            r0.setSessionNativePtr(r1)     // Catch:{ all -> 0x013d }
            r1 = r24
            java.util.HashMap<java.lang.String, org.android.spdy.SpdySession> r2 = r1.sessionMgr     // Catch:{ all -> 0x01e0 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e0 }
            r3.<init>()     // Catch:{ all -> 0x01e0 }
            r4 = r20
            r3.append(r4)     // Catch:{ all -> 0x01e0 }
            r4 = r26
            r3.append(r4)     // Catch:{ all -> 0x01e0 }
            r4 = r30
            r3.append(r4)     // Catch:{ all -> 0x01e0 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x01e0 }
            r2.put(r3, r0)     // Catch:{ all -> 0x01e0 }
            java.util.LinkedList<org.android.spdy.SpdySession> r2 = r1.sessionQueue     // Catch:{ all -> 0x01e0 }
            r2.add(r0)     // Catch:{ all -> 0x01e0 }
            goto L_0x01c3
        L_0x01bd:
            r1 = r24
            if (r12 != 0) goto L_0x01c9
            r0 = r19
        L_0x01c3:
            java.util.concurrent.locks.Lock r2 = f4104w
            r2.unlock()
            return r0
        L_0x01c9:
            org.android.spdy.SpdyErrorException r0 = new org.android.spdy.SpdyErrorException     // Catch:{ all -> 0x01e0 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x01e0 }
            r2.<init>()     // Catch:{ all -> 0x01e0 }
            java.lang.String r3 = "create session error: "
            r2.append(r3)     // Catch:{ all -> 0x01e0 }
            r2.append(r12)     // Catch:{ all -> 0x01e0 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x01e0 }
            r0.<init>((java.lang.String) r2, (int) r12)     // Catch:{ all -> 0x01e0 }
            throw r0     // Catch:{ all -> 0x01e0 }
        L_0x01e0:
            r0 = move-exception
            goto L_0x01e4
        L_0x01e2:
            r0 = move-exception
            r1 = r15
        L_0x01e4:
            java.util.concurrent.locks.Lock r2 = f4104w
            r2.unlock()
            throw r0
        L_0x01ea:
            r1 = r15
            org.android.spdy.SpdyErrorException r0 = new org.android.spdy.SpdyErrorException
            r2 = -1105(0xfffffffffffffbaf, float:NaN)
            java.lang.String r3 = "SPDY_SESSION_EXCEED_MAXED: session count exceed max"
            r0.<init>((java.lang.String) r3, (int) r2)
            throw r0
        L_0x01f5:
            r0 = move-exception
            r1 = r15
            java.util.concurrent.locks.Lock r2 = f4103r
            r2.unlock()
            throw r0
        L_0x01fd:
            r1 = r15
            org.android.spdy.SpdyErrorException r0 = new org.android.spdy.SpdyErrorException
            r2 = -1102(0xfffffffffffffbb2, float:NaN)
            java.lang.String r3 = "SPDY_JNI_ERR_INVALID_PARAM"
            r0.<init>((java.lang.String) r3, (int) r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.android.spdy.SpdyAgent.createSession(java.lang.String, java.lang.String, java.lang.Object, org.android.spdy.SessionCb, org.android.spdy.SslCertcb, int, int, int, java.lang.String):org.android.spdy.SpdySession");
    }

    @Deprecated
    public SpdySession submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Object obj2, Spdycb spdycb, SessionCb sessionCb, SslCertcb sslCertcb, int i) throws SpdyErrorException {
        SpdySession createSession = createSession(spdyRequest.getAuthority(), spdyRequest.getDomain(), obj, sessionCb, sslCertcb, i, 0, spdyRequest.getConnectionTimeoutMs());
        SpdyRequest spdyRequest2 = spdyRequest;
        SpdyDataProvider spdyDataProvider2 = spdyDataProvider;
        Object obj3 = obj2;
        Spdycb spdycb2 = spdycb;
        createSession.submitRequest(spdyRequest, spdyDataProvider, obj2, spdycb);
        return createSession;
    }

    @Deprecated
    public SpdySession submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Object obj2, Spdycb spdycb, SessionCb sessionCb, SslCertcb sslCertcb, int i, int i2) throws SpdyErrorException {
        SpdySession createSession = createSession(spdyRequest.getAuthority(), spdyRequest.getDomain(), obj, sessionCb, sslCertcb, i, i2, spdyRequest.getConnectionTimeoutMs());
        SpdyRequest spdyRequest2 = spdyRequest;
        SpdyDataProvider spdyDataProvider2 = spdyDataProvider;
        Object obj3 = obj2;
        Spdycb spdycb2 = spdycb;
        createSession.submitRequest(spdyRequest, spdyDataProvider, obj2, spdycb);
        return createSession;
    }

    public SpdySession submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Object obj2, Spdycb spdycb, SessionCb sessionCb, int i, int i2) throws SpdyErrorException {
        return submitRequest(spdyRequest, spdyDataProvider, obj, obj2, spdycb, sessionCb, (SslCertcb) null, i, i2);
    }

    @Deprecated
    public SpdySession submitRequest(SpdyRequest spdyRequest, SpdyDataProvider spdyDataProvider, Object obj, Object obj2, Spdycb spdycb, SessionCb sessionCb, int i) throws SpdyErrorException {
        return submitRequest(spdyRequest, spdyDataProvider, obj, obj2, spdycb, sessionCb, (SslCertcb) null, i);
    }

    private void agentIsOpen() {
        if (!this.closed.get()) {
            checkLoadSo();
            return;
        }
        throw new SpdyErrorException("SPDY_JNI_ERR_ASYNC_CLOSE", (int) TnetStatusCode.TNET_JNI_ERR_ASYNC_CLOSE);
    }

    /* access modifiers changed from: package-private */
    public void removeSession(SpdySession spdySession) {
        f4104w.lock();
        try {
            this.sessionQueue.remove(spdySession);
        } finally {
            f4104w.unlock();
        }
    }

    /* access modifiers changed from: package-private */
    public int closeSession(long j) {
        return closeSessionN(j);
    }

    static String[] mapToByteArray(Map<String, String> map) {
        if (map == null || map.size() <= 0) {
            return null;
        }
        String[] strArr = new String[(map.size() * 2)];
        int i = 0;
        for (Map.Entry next : map.entrySet()) {
            strArr[i] = (String) next.getKey();
            strArr[i + 1] = (String) next.getValue();
            i += 2;
        }
        return strArr;
    }

    static Map<String, List<String>> stringArrayToMap(String[] strArr) {
        if (strArr == null) {
            return null;
        }
        HashMap hashMap = new HashMap(5);
        int i = 0;
        while (true) {
            int i2 = i + 2;
            if (i2 <= strArr.length) {
                if (strArr[i] == null) {
                    break;
                }
                int i3 = i + 1;
                if (strArr[i3] == null) {
                    break;
                }
                List list = (List) hashMap.get(strArr[i]);
                if (list == null) {
                    list = new ArrayList(1);
                    hashMap.put(strArr[i], list);
                }
                list.add(strArr[i3]);
                i = i2;
            } else {
                return hashMap;
            }
        }
        return null;
    }

    @Deprecated
    public int setSessionKind(SpdySessionKind spdySessionKind) {
        agentIsOpen();
        try {
            return setSessionKind(this.agentNativePtr, spdySessionKind.getint());
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Deprecated
    public int setConnectTimeOut(int i) {
        agentIsOpen();
        try {
            return setConTimeout(this.agentNativePtr, i);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void setAccsSslCallback(AccsSSLCallback accsSSLCallback2) {
        spduLog.Logi("tnet-jni", "[setAccsSslCallback] - " + accsSSLCallback2.getClass());
        this.accsSSLCallback = accsSSLCallback2;
    }

    private void spdySessionConnectCB(SpdySession spdySession, SuperviseConnectInfo superviseConnectInfo) {
        spduLog.Logi("tnet-jni", "[spdySessionConnectCB] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdySessionConnectCB] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdySessionConnectCB] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdySessionConnectCB(spdySession, superviseConnectInfo);
        }
    }

    private void spdyDataChunkRecvCB(SpdySession spdySession, boolean z, int i, SpdyByteArray spdyByteArray, int i2) {
        spduLog.Logi("tnet-jni", "[spdyDataChunkRecvCB] - ");
        long j = 4294967295L & ((long) i);
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyDataChunkRecvCB] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyDataChunkRecvCB] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyDataChunkRecvCB(spdySession, z, j, spdyByteArray, i2);
        }
    }

    private void spdyDataRecvCallback(SpdySession spdySession, boolean z, int i, int i2, int i3) {
        spduLog.Logi("tnet-jni", "[spdyDataRecvCallback] - ");
        long j = 4294967295L & ((long) i);
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyDataRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyDataRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyDataRecvCallback(spdySession, z, j, i2, i3);
        }
    }

    private void spdyDataSendCallback(SpdySession spdySession, boolean z, int i, int i2, int i3) {
        long j = 4294967295L & ((long) i);
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyDataSendCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyDataSendCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyDataSendCallback(spdySession, z, j, i2, i3);
        }
    }

    private void spdyStreamCloseCallback(SpdySession spdySession, int i, int i2, int i3, SuperviseData superviseData) {
        spduLog.Logi("tnet-jni", "[spdyStreamCloseCallback] - ");
        long j = ((long) i) & 4294967295L;
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyStreamCloseCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyStreamCloseCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyStreamCloseCallback(spdySession, j, i2, i3, superviseData);
        }
    }

    private void spdyPingRecvCallback(SpdySession spdySession, int i, Object obj) {
        spduLog.Logi("tnet-jni", "[spdyPingRecvCallback] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyPingRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyPingRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyPingRecvCallback(spdySession, (long) i, obj);
        }
    }

    private void spdyCustomControlFrameRecvCallback(SpdySession spdySession, Object obj, int i, int i2, int i3, int i4, byte[] bArr) {
        spduLog.Logi("tnet-jni", "[spdyCustomControlFrameRecvCallback] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyCustomControlFrameRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyCustomControlFrameRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyCustomControlFrameRecvCallback(spdySession, obj, i, i2, i3, i4, bArr);
        }
    }

    private void spdyCustomControlFrameFailCallback(SpdySession spdySession, Object obj, int i, int i2) {
        spduLog.Logi("tnet-jni", "[spdyCustomControlFrameFailCallback] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyCustomControlFrameFailCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyCustomControlFrameFailCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyCustomControlFrameFailCallback(spdySession, obj, i, i2);
        }
    }

    private void bioPingRecvCallback(SpdySession spdySession, int i) {
        spduLog.Logi("tnet-jni", "[bioPingRecvCallback] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[bioPingRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[bioPingRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.bioPingRecvCallback(spdySession, i);
        }
    }

    private void spdyRequestRecvCallback(SpdySession spdySession, int i, int i2) {
        long j = ((long) i) & 4294967295L;
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyRequestRecvCallback] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyRequestRecvCallback] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyRequestRecvCallback(spdySession, j, i2);
        }
    }

    private void spdyStreamResponseRecv(SpdySession spdySession, int i, String[] strArr, int i2) {
        spduLog.Logi("tnet-jni", "[spdyStreamResponseRecv] - ");
        Map<String, List<String>> stringArrayToMap = stringArrayToMap(strArr);
        long j = ((long) i) & 4294967295L;
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdyStreamResponseRecv] - session is null");
        } else if (spdySession.intenalcb == null) {
            spduLog.Logi("tnet-jni", "[spdyStreamResponseRecv] - session.intenalcb is null");
        } else {
            spdySession.intenalcb.spdyOnStreamResponse(spdySession, j, stringArrayToMap, i2);
        }
    }

    private void spdySessionCloseCallback(SpdySession spdySession, Object obj, SuperviseConnectInfo superviseConnectInfo, int i) {
        spduLog.Logi("tnet-jni", "[spdySessionCloseCallback] - errorCode = " + i);
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdySessionCloseCallback] - session is null");
        } else {
            try {
                if (spdySession.intenalcb == null) {
                    spduLog.Logi("tnet-jni", "[spdySessionCloseCallback] - session.intenalcb is null");
                } else {
                    spdySession.intenalcb.spdySessionCloseCallback(spdySession, obj, superviseConnectInfo, i);
                }
            } finally {
                spdySession.cleanUp();
            }
        }
        spdySession.releasePptr();
    }

    private void spdySessionFailedError(SpdySession spdySession, int i, Object obj) {
        spduLog.Logi("tnet-jni", "[spdySessionFailedError] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdySessionFailedError] - session is null");
        } else {
            try {
                if (spdySession.intenalcb == null) {
                    spduLog.Logi("tnet-jni", "[spdySessionFailedError] - session.intenalcb is null");
                } else {
                    spdySession.intenalcb.spdySessionFailedError(spdySession, i, obj);
                }
            } finally {
                spdySession.cleanUp();
            }
        }
        spdySession.releasePptr();
    }

    private void spdySessionOnWritable(SpdySession spdySession, Object obj, int i) {
        spduLog.Logi("tnet-jni", "[spdySessionOnWritable] - ");
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[spdySessionOnWritable] - session is null");
            return;
        }
        try {
            if (spdySession.intenalcb == null) {
                spduLog.Logi("tnet-jni", "[spdySessionOnWritable] - session.intenalcb is null");
            } else {
                spdySession.intenalcb.spdySessionOnWritable(spdySession, obj, i);
            }
        } catch (Throwable th) {
            spduLog.Loge("tnet-jni", "[spdySessionOnWritable] - exception:" + th);
        }
    }

    private byte[] getSSLPublicKey(int i, byte[] bArr) {
        AccsSSLCallback accsSSLCallback2 = this.accsSSLCallback;
        if (accsSSLCallback2 != null) {
            return accsSSLCallback2.getSSLPublicKey(i, bArr);
        }
        spduLog.Logd("tnet-jni", "[getSSLPublicKey] - accsSSLCallback is null.");
        return null;
    }

    private int putSSLMeta(SpdySession spdySession, byte[] bArr) {
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[putSSLMeta] - session is null");
            return -1;
        } else if (spdySession.intenalcb != null) {
            return spdySession.intenalcb.putSSLMeta(spdySession, bArr);
        } else {
            spduLog.Logi("tnet-jni", "[putSSLMeta] - session.intenalcb is null");
            return -1;
        }
    }

    private byte[] getSSLMeta(SpdySession spdySession) {
        if (spdySession == null) {
            spduLog.Logi("tnet-jni", "[getSSLMeta] - session is null");
            return null;
        } else if (spdySession.intenalcb != null) {
            return spdySession.intenalcb.getSSLMeta(spdySession);
        } else {
            spduLog.Logi("tnet-jni", "[getSSLMeta] - session.intenalcb is null");
            return null;
        }
    }

    public HashMap<String, SpdySession> getAllSession() {
        return this.sessionMgr;
    }

    public int configLogFile(String str, int i, int i2) {
        if (loadSucc) {
            return configLogFileN(str, i, i2);
        }
        return -1;
    }

    public int configLogFile(String str, int i, int i2, int i3) {
        if (loadSucc) {
            return configLogFileN(str, i, i2, i3);
        }
        return -1;
    }

    public void logFileFlush() {
        if (loadSucc) {
            logFileFlushN();
        }
    }

    public void logFileClose() {
        if (loadSucc) {
            logFileFlushN();
            logFileCloseN();
        }
    }

    public static int configIpStackMode(int i) {
        spduLog.Logi("tnet-jni", "[configIpStackMode] - " + i);
        return configIpStackModeN(i);
    }
}
