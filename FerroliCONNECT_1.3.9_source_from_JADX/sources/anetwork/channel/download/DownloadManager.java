package anetwork.channel.download;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.SparseArray;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import anetwork.channel.Header;
import anetwork.channel.aidl.Connection;
import anetwork.channel.http.NetworkSdkSetting;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import org.eclipse.jetty.http.HttpHeaders;

/* compiled from: Taobao */
public class DownloadManager {
    public static final int ERROR_DOWNLOAD_CANCELLED = -105;
    public static final int ERROR_EXCEPTION_HAPPEN = -104;
    public static final int ERROR_FILE_FOLDER_INVALID = -101;
    public static final int ERROR_FILE_RENAME_FAILED = -106;
    public static final int ERROR_IO_EXCEPTION = -103;
    public static final int ERROR_REQUEST_FAIL = -102;
    public static final int ERROR_URL_INVALID = -100;
    public static final String TAG = "anet.DownloadManager";

    /* renamed from: a */
    SparseArray<C0631b> f682a;

    /* renamed from: b */
    AtomicInteger f683b;

    /* renamed from: c */
    ThreadPoolExecutor f684c;

    /* renamed from: d */
    Context f685d;

    /* compiled from: Taobao */
    public interface DownloadListener {
        void onFail(int i, int i2, String str);

        void onProgress(int i, long j, long j2);

        void onSuccess(int i, String str);
    }

    public static DownloadManager getInstance() {
        return C0630a.f686a;
    }

    private DownloadManager() {
        this.f682a = new SparseArray<>(6);
        this.f683b = new AtomicInteger(0);
        this.f684c = new ThreadPoolExecutor(2, 2, 30, TimeUnit.SECONDS, new LinkedBlockingDeque());
        this.f685d = null;
        this.f685d = NetworkSdkSetting.getContext();
        this.f684c.allowCoreThreadTimeOut(true);
        m402a();
    }

    /* renamed from: anetwork.channel.download.DownloadManager$a */
    /* compiled from: Taobao */
    private static class C0630a {

        /* renamed from: a */
        static DownloadManager f686a = new DownloadManager();

        private C0630a() {
        }
    }

    public int enqueue(String str, String str2, DownloadListener downloadListener) {
        return enqueue(str, (String) null, str2, downloadListener);
    }

    public int enqueue(String str, String str2, String str3, DownloadListener downloadListener) {
        int i = 0;
        if (ALog.isPrintLog(2)) {
            ALog.m328i(TAG, "enqueue", (String) null, "folder", str2, "filename", str3, "url", str);
        }
        if (this.f685d == null) {
            ALog.m327e(TAG, "network sdk not initialized.", (String) null, new Object[0]);
            return -1;
        }
        try {
            URL url = new URL(str);
            if (TextUtils.isEmpty(str2) || m403a(str2)) {
                synchronized (this.f682a) {
                    int size = this.f682a.size();
                    while (true) {
                        if (i >= size) {
                            break;
                        }
                        C0631b valueAt = this.f682a.valueAt(i);
                        if (!url.equals(valueAt.f688b)) {
                            i++;
                        } else if (valueAt.mo9313a(downloadListener)) {
                            int i2 = valueAt.f687a;
                            return i2;
                        }
                    }
                    C0631b bVar = new C0631b(url, str2, str3, downloadListener);
                    this.f682a.put(bVar.f687a, bVar);
                    this.f684c.submit(bVar);
                    int i3 = bVar.f687a;
                    return i3;
                }
            }
            ALog.m327e(TAG, "file folder invalid.", (String) null, new Object[0]);
            if (downloadListener != null) {
                downloadListener.onFail(-1, -101, "file folder path invalid");
            }
            return -1;
        } catch (MalformedURLException e) {
            ALog.m326e(TAG, "url invalid.", (String) null, e, new Object[0]);
            if (downloadListener != null) {
                downloadListener.onFail(-1, -100, "url invalid");
            }
            return -1;
        }
    }

    public void cancel(int i) {
        synchronized (this.f682a) {
            C0631b bVar = this.f682a.get(i);
            if (bVar != null) {
                if (ALog.isPrintLog(2)) {
                    ALog.m328i(TAG, "try cancel task" + i + " url=" + bVar.f688b.toString(), (String) null, new Object[0]);
                }
                this.f682a.remove(i);
                bVar.mo9312a();
            }
        }
    }

    /* renamed from: anetwork.channel.download.DownloadManager$b */
    /* compiled from: Taobao */
    class C0631b implements Runnable {

        /* renamed from: a */
        final int f687a;

        /* renamed from: b */
        final URL f688b;

        /* renamed from: d */
        private final String f690d;

        /* renamed from: e */
        private final CopyOnWriteArrayList<DownloadListener> f691e;

        /* renamed from: f */
        private final AtomicBoolean f692f = new AtomicBoolean(false);

        /* renamed from: g */
        private final AtomicBoolean f693g = new AtomicBoolean(false);

        /* renamed from: h */
        private volatile Connection f694h = null;

        /* renamed from: i */
        private boolean f695i = true;

        C0631b(URL url, String str, String str2, DownloadListener downloadListener) {
            this.f687a = DownloadManager.this.f683b.getAndIncrement();
            this.f688b = url;
            str2 = TextUtils.isEmpty(str2) ? m406a(url) : str2;
            if (TextUtils.isEmpty(str)) {
                this.f690d = DownloadManager.this.m404b(str2);
            } else {
                if (str.endsWith("/")) {
                    this.f690d = str + str2;
                } else {
                    this.f690d = str + '/' + str2;
                }
                if (str.startsWith("/data/user") || str.startsWith("/data/data")) {
                    this.f695i = false;
                }
            }
            this.f691e = new CopyOnWriteArrayList<>();
            this.f691e.add(downloadListener);
        }

        /* renamed from: a */
        public boolean mo9313a(DownloadListener downloadListener) {
            if (this.f693g.get()) {
                return false;
            }
            this.f691e.add(downloadListener);
            return true;
        }

        /* renamed from: a */
        public void mo9312a() {
            this.f692f.set(true);
            m407a(-105, "download canceled.");
            if (this.f694h != null) {
                try {
                    this.f694h.cancel();
                } catch (RemoteException unused) {
                }
            }
        }

        /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
            java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            	at java.base/java.util.Objects.checkIndex(Objects.java:372)
            	at java.base/java.util.ArrayList.get(ArrayList.java:458)
            	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processExcHandler(RegionMaker.java:1043)
            	at jadx.core.dex.visitors.regions.RegionMaker.processTryCatchBlocks(RegionMaker.java:975)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
            */
        /* JADX WARNING: Removed duplicated region for block: B:185:0x023a A[SYNTHETIC, Splitter:B:185:0x023a] */
        /* JADX WARNING: Removed duplicated region for block: B:189:0x023f A[SYNTHETIC, Splitter:B:189:0x023f] */
        /* JADX WARNING: Removed duplicated region for block: B:193:0x0244 A[SYNTHETIC, Splitter:B:193:0x0244] */
        public void run() {
            /*
                r15 = this;
                java.util.concurrent.atomic.AtomicBoolean r0 = r15.f692f
                boolean r0 = r0.get()
                if (r0 == 0) goto L_0x0009
                return
            L_0x0009:
                r0 = 0
                r1 = 0
                anetwork.channel.download.DownloadManager r2 = anetwork.channel.download.DownloadManager.this     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.net.URL r3 = r15.f688b     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                boolean r4 = r15.f695i     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.io.File r2 = r2.m400a((java.lang.String) r3, (boolean) r4)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                boolean r3 = r2.exists()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                anetwork.channel.entity.RequestImpl r4 = new anetwork.channel.entity.RequestImpl     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.net.URL r5 = r15.f688b     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r4.<init>((java.net.URL) r5)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r4.setRetryTime(r0)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r5 = 1
                r4.setFollowRedirects(r5)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                if (r3 == 0) goto L_0x004c
                java.lang.String r5 = "Range"
                java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r6.<init>()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.lang.String r7 = "bytes="
                r6.append(r7)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                long r7 = r2.length()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r6.append(r7)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.lang.String r7 = "-"
                r6.append(r7)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.lang.String r6 = r6.toString()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r4.addHeader(r5, r6)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
            L_0x004c:
                anetwork.channel.degrade.DegradableNetwork r5 = new anetwork.channel.degrade.DegradableNetwork     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                anetwork.channel.download.DownloadManager r6 = anetwork.channel.download.DownloadManager.this     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                android.content.Context r6 = r6.f685d     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r5.<init>(r6)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                anetwork.channel.aidl.Connection r6 = r5.getConnection(r4, r1)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r15.f694h = r6     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                anetwork.channel.aidl.Connection r6 = r15.f694h     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                int r6 = r6.getStatusCode()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                if (r6 <= 0) goto L_0x01f5
                r7 = 416(0x1a0, float:5.83E-43)
                r8 = 200(0xc8, float:2.8E-43)
                if (r6 == r8) goto L_0x0071
                r9 = 206(0xce, float:2.89E-43)
                if (r6 == r9) goto L_0x0071
                if (r6 == r7) goto L_0x0071
                goto L_0x01f5
            L_0x0071:
                if (r3 == 0) goto L_0x00a1
                if (r6 != r7) goto L_0x009e
                java.util.List r3 = r4.getHeaders()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r15.m410a((java.util.List<anetwork.channel.Header>) r3)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.util.concurrent.atomic.AtomicBoolean r3 = r15.f692f     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                boolean r3 = r3.get()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                if (r3 == 0) goto L_0x0097
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r3 = r0.f682a
                monitor-enter(r3)
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this     // Catch:{ all -> 0x0094 }
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r0 = r0.f682a     // Catch:{ all -> 0x0094 }
                int r1 = r15.f687a     // Catch:{ all -> 0x0094 }
                r0.remove(r1)     // Catch:{ all -> 0x0094 }
                monitor-exit(r3)     // Catch:{ all -> 0x0094 }
                return
            L_0x0094:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x0094 }
                throw r0
            L_0x0097:
                anetwork.channel.aidl.Connection r3 = r5.getConnection(r4, r1)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r15.f694h = r3     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r3 = 0
            L_0x009e:
                if (r6 != r8) goto L_0x00a1
                r3 = 0
            L_0x00a1:
                java.util.concurrent.atomic.AtomicBoolean r4 = r15.f692f     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                boolean r4 = r4.get()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                if (r4 == 0) goto L_0x00bc
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r4 = r0.f682a
                monitor-enter(r4)
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this     // Catch:{ all -> 0x00b9 }
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r0 = r0.f682a     // Catch:{ all -> 0x00b9 }
                int r1 = r15.f687a     // Catch:{ all -> 0x00b9 }
                r0.remove(r1)     // Catch:{ all -> 0x00b9 }
                monitor-exit(r4)     // Catch:{ all -> 0x00b9 }
                return
            L_0x00b9:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x00b9 }
                throw r0
            L_0x00bc:
                r4 = 0
                if (r3 != 0) goto L_0x00cc
                java.io.BufferedOutputStream r3 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r7.<init>(r2)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r3.<init>(r7)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r7 = r1
                goto L_0x00ea
            L_0x00cc:
                java.io.RandomAccessFile r3 = new java.io.RandomAccessFile     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.lang.String r4 = "rw"
                r3.<init>(r2, r4)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                long r4 = r3.length()     // Catch:{ Exception -> 0x01f0, all -> 0x01eb }
                r3.seek(r4)     // Catch:{ Exception -> 0x01f0, all -> 0x01eb }
                java.io.BufferedOutputStream r7 = new java.io.BufferedOutputStream     // Catch:{ Exception -> 0x01f0, all -> 0x01eb }
                java.nio.channels.FileChannel r8 = r3.getChannel()     // Catch:{ Exception -> 0x01f0, all -> 0x01eb }
                java.io.OutputStream r8 = java.nio.channels.Channels.newOutputStream(r8)     // Catch:{ Exception -> 0x01f0, all -> 0x01eb }
                r7.<init>(r8)     // Catch:{ Exception -> 0x01f0, all -> 0x01eb }
                r14 = r7
                r7 = r3
                r3 = r14
            L_0x00ea:
                anetwork.channel.aidl.Connection r8 = r15.f694h     // Catch:{ Exception -> 0x01e8, all -> 0x01e4 }
                java.util.Map r8 = r8.getConnHeadFields()     // Catch:{ Exception -> 0x01e8, all -> 0x01e4 }
                long r8 = r15.m405a(r6, r8, r4)     // Catch:{ Exception -> 0x01e8, all -> 0x01e4 }
                anetwork.channel.aidl.Connection r6 = r15.f694h     // Catch:{ Exception -> 0x01e8, all -> 0x01e4 }
                anetwork.channel.aidl.ParcelableInputStream r6 = r6.getInputStream()     // Catch:{ Exception -> 0x01e8, all -> 0x01e4 }
                if (r6 != 0) goto L_0x0123
                r2 = -103(0xffffffffffffff99, float:NaN)
                java.lang.String r4 = "input stream is null."
                r15.m407a((int) r2, (java.lang.String) r4)     // Catch:{ Exception -> 0x01e2 }
                r3.close()     // Catch:{ Exception -> 0x0106 }
            L_0x0106:
                if (r7 == 0) goto L_0x010b
                r7.close()     // Catch:{ Exception -> 0x010b }
            L_0x010b:
                if (r6 == 0) goto L_0x0110
                r6.close()     // Catch:{ Exception -> 0x0110 }
            L_0x0110:
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r0 = r0.f682a
                monitor-enter(r0)
                anetwork.channel.download.DownloadManager r1 = anetwork.channel.download.DownloadManager.this     // Catch:{ all -> 0x0120 }
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r1 = r1.f682a     // Catch:{ all -> 0x0120 }
                int r2 = r15.f687a     // Catch:{ all -> 0x0120 }
                r1.remove(r2)     // Catch:{ all -> 0x0120 }
                monitor-exit(r0)     // Catch:{ all -> 0x0120 }
                return
            L_0x0120:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0120 }
                throw r1
            L_0x0123:
                r10 = 2048(0x800, float:2.87E-42)
                byte[] r10 = new byte[r10]     // Catch:{ Exception -> 0x01e2 }
                r11 = 0
            L_0x0128:
                int r12 = r6.read(r10)     // Catch:{ Exception -> 0x01e2 }
                r13 = -1
                if (r12 == r13) goto L_0x0166
                java.util.concurrent.atomic.AtomicBoolean r13 = r15.f692f     // Catch:{ Exception -> 0x01e2 }
                boolean r13 = r13.get()     // Catch:{ Exception -> 0x01e2 }
                if (r13 == 0) goto L_0x015c
                anetwork.channel.aidl.Connection r2 = r15.f694h     // Catch:{ Exception -> 0x01e2 }
                r2.cancel()     // Catch:{ Exception -> 0x01e2 }
                r3.close()     // Catch:{ Exception -> 0x013f }
            L_0x013f:
                if (r7 == 0) goto L_0x0144
                r7.close()     // Catch:{ Exception -> 0x0144 }
            L_0x0144:
                if (r6 == 0) goto L_0x0149
                r6.close()     // Catch:{ Exception -> 0x0149 }
            L_0x0149:
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r0 = r0.f682a
                monitor-enter(r0)
                anetwork.channel.download.DownloadManager r1 = anetwork.channel.download.DownloadManager.this     // Catch:{ all -> 0x0159 }
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r1 = r1.f682a     // Catch:{ all -> 0x0159 }
                int r2 = r15.f687a     // Catch:{ all -> 0x0159 }
                r1.remove(r2)     // Catch:{ all -> 0x0159 }
                monitor-exit(r0)     // Catch:{ all -> 0x0159 }
                return
            L_0x0159:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0159 }
                throw r1
            L_0x015c:
                int r11 = r11 + r12
                r3.write(r10, r0, r12)     // Catch:{ Exception -> 0x01e2 }
                long r12 = (long) r11     // Catch:{ Exception -> 0x01e2 }
                long r12 = r12 + r4
                r15.m408a((long) r12, (long) r8)     // Catch:{ Exception -> 0x01e2 }
                goto L_0x0128
            L_0x0166:
                r3.flush()     // Catch:{ Exception -> 0x01e2 }
                java.util.concurrent.atomic.AtomicBoolean r4 = r15.f692f     // Catch:{ Exception -> 0x01e2 }
                boolean r4 = r4.get()     // Catch:{ Exception -> 0x01e2 }
                if (r4 == 0) goto L_0x0191
                r3.close()     // Catch:{ Exception -> 0x0174 }
            L_0x0174:
                if (r7 == 0) goto L_0x0179
                r7.close()     // Catch:{ Exception -> 0x0179 }
            L_0x0179:
                if (r6 == 0) goto L_0x017e
                r6.close()     // Catch:{ Exception -> 0x017e }
            L_0x017e:
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r4 = r0.f682a
                monitor-enter(r4)
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this     // Catch:{ all -> 0x018e }
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r0 = r0.f682a     // Catch:{ all -> 0x018e }
                int r1 = r15.f687a     // Catch:{ all -> 0x018e }
                r0.remove(r1)     // Catch:{ all -> 0x018e }
                monitor-exit(r4)     // Catch:{ all -> 0x018e }
                return
            L_0x018e:
                r0 = move-exception
                monitor-exit(r4)     // Catch:{ all -> 0x018e }
                throw r0
            L_0x0191:
                java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x01e2 }
                java.lang.String r5 = r15.f690d     // Catch:{ Exception -> 0x01e2 }
                r4.<init>(r5)     // Catch:{ Exception -> 0x01e2 }
                boolean r2 = r2.renameTo(r4)     // Catch:{ Exception -> 0x01e2 }
                if (r2 == 0) goto L_0x01a4
                java.lang.String r2 = r15.f690d     // Catch:{ Exception -> 0x01e2 }
                r15.m409a((java.lang.String) r2)     // Catch:{ Exception -> 0x01e2 }
                goto L_0x01c1
            L_0x01a4:
                r2 = -106(0xffffffffffffff96, float:NaN)
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x01e2 }
                r4.<init>()     // Catch:{ Exception -> 0x01e2 }
                java.lang.String r5 = "file rename to "
                r4.append(r5)     // Catch:{ Exception -> 0x01e2 }
                java.lang.String r5 = r15.f690d     // Catch:{ Exception -> 0x01e2 }
                r4.append(r5)     // Catch:{ Exception -> 0x01e2 }
                java.lang.String r5 = " failed"
                r4.append(r5)     // Catch:{ Exception -> 0x01e2 }
                java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x01e2 }
                r15.m407a((int) r2, (java.lang.String) r4)     // Catch:{ Exception -> 0x01e2 }
            L_0x01c1:
                r3.close()     // Catch:{ Exception -> 0x01c4 }
            L_0x01c4:
                if (r7 == 0) goto L_0x01c9
                r7.close()     // Catch:{ Exception -> 0x01c9 }
            L_0x01c9:
                if (r6 == 0) goto L_0x01ce
                r6.close()     // Catch:{ Exception -> 0x01ce }
            L_0x01ce:
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r0 = r0.f682a
                monitor-enter(r0)
                anetwork.channel.download.DownloadManager r1 = anetwork.channel.download.DownloadManager.this     // Catch:{ all -> 0x01df }
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r1 = r1.f682a     // Catch:{ all -> 0x01df }
                int r2 = r15.f687a     // Catch:{ all -> 0x01df }
                r1.remove(r2)     // Catch:{ all -> 0x01df }
                monitor-exit(r0)     // Catch:{ all -> 0x01df }
                goto L_0x0256
            L_0x01df:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x01df }
                throw r1
            L_0x01e2:
                r2 = move-exception
                goto L_0x0226
            L_0x01e4:
                r0 = move-exception
                r6 = r1
                goto L_0x025b
            L_0x01e8:
                r2 = move-exception
                r6 = r1
                goto L_0x0226
            L_0x01eb:
                r0 = move-exception
                r6 = r1
                r7 = r3
                goto L_0x025c
            L_0x01f0:
                r2 = move-exception
                r6 = r1
                r7 = r3
                r3 = r6
                goto L_0x0226
            L_0x01f5:
                r2 = -102(0xffffffffffffff9a, float:NaN)
                java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r3.<init>()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.lang.String r4 = "ResponseCode:"
                r3.append(r4)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r3.append(r6)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                r15.m407a((int) r2, (java.lang.String) r3)     // Catch:{ Exception -> 0x0222, all -> 0x021e }
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r0 = r0.f682a
                monitor-enter(r0)
                anetwork.channel.download.DownloadManager r1 = anetwork.channel.download.DownloadManager.this     // Catch:{ all -> 0x021b }
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r1 = r1.f682a     // Catch:{ all -> 0x021b }
                int r2 = r15.f687a     // Catch:{ all -> 0x021b }
                r1.remove(r2)     // Catch:{ all -> 0x021b }
                monitor-exit(r0)     // Catch:{ all -> 0x021b }
                return
            L_0x021b:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x021b }
                throw r1
            L_0x021e:
                r0 = move-exception
                r6 = r1
                r7 = r6
                goto L_0x025c
            L_0x0222:
                r2 = move-exception
                r3 = r1
                r6 = r3
                r7 = r6
            L_0x0226:
                java.lang.String r4 = "anet.DownloadManager"
                java.lang.String r5 = "file download failed!"
                java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x025a }
                anet.channel.util.ALog.m326e(r4, r5, r1, r2, r0)     // Catch:{ all -> 0x025a }
                r0 = -104(0xffffffffffffff98, float:NaN)
                java.lang.String r1 = r2.toString()     // Catch:{ all -> 0x025a }
                r15.m407a((int) r0, (java.lang.String) r1)     // Catch:{ all -> 0x025a }
                if (r3 == 0) goto L_0x023d
                r3.close()     // Catch:{ Exception -> 0x023d }
            L_0x023d:
                if (r7 == 0) goto L_0x0242
                r7.close()     // Catch:{ Exception -> 0x0242 }
            L_0x0242:
                if (r6 == 0) goto L_0x0247
                r6.close()     // Catch:{ Exception -> 0x0247 }
            L_0x0247:
                anetwork.channel.download.DownloadManager r0 = anetwork.channel.download.DownloadManager.this
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r0 = r0.f682a
                monitor-enter(r0)
                anetwork.channel.download.DownloadManager r1 = anetwork.channel.download.DownloadManager.this     // Catch:{ all -> 0x0257 }
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r1 = r1.f682a     // Catch:{ all -> 0x0257 }
                int r2 = r15.f687a     // Catch:{ all -> 0x0257 }
                r1.remove(r2)     // Catch:{ all -> 0x0257 }
                monitor-exit(r0)     // Catch:{ all -> 0x0257 }
            L_0x0256:
                return
            L_0x0257:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0257 }
                throw r1
            L_0x025a:
                r0 = move-exception
            L_0x025b:
                r1 = r3
            L_0x025c:
                if (r1 == 0) goto L_0x0261
                r1.close()     // Catch:{ Exception -> 0x0261 }
            L_0x0261:
                if (r7 == 0) goto L_0x0266
                r7.close()     // Catch:{ Exception -> 0x0266 }
            L_0x0266:
                if (r6 == 0) goto L_0x026b
                r6.close()     // Catch:{ Exception -> 0x026b }
            L_0x026b:
                anetwork.channel.download.DownloadManager r1 = anetwork.channel.download.DownloadManager.this
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r1 = r1.f682a
                monitor-enter(r1)
                anetwork.channel.download.DownloadManager r2 = anetwork.channel.download.DownloadManager.this     // Catch:{ all -> 0x027b }
                android.util.SparseArray<anetwork.channel.download.DownloadManager$b> r2 = r2.f682a     // Catch:{ all -> 0x027b }
                int r3 = r15.f687a     // Catch:{ all -> 0x027b }
                r2.remove(r3)     // Catch:{ all -> 0x027b }
                monitor-exit(r1)     // Catch:{ all -> 0x027b }
                throw r0
            L_0x027b:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x027b }
                goto L_0x027f
            L_0x027e:
                throw r0
            L_0x027f:
                goto L_0x027e
            */
            throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.download.DownloadManager.C0631b.run():void");
        }

        /* renamed from: a */
        private void m409a(String str) {
            if (this.f693g.compareAndSet(false, true)) {
                Iterator<DownloadListener> it = this.f691e.iterator();
                while (it.hasNext()) {
                    it.next().onSuccess(this.f687a, str);
                }
            }
        }

        /* renamed from: a */
        private void m407a(int i, String str) {
            if (this.f693g.compareAndSet(false, true)) {
                Iterator<DownloadListener> it = this.f691e.iterator();
                while (it.hasNext()) {
                    it.next().onFail(this.f687a, i, str);
                }
            }
        }

        /* renamed from: a */
        private void m408a(long j, long j2) {
            if (!this.f693g.get()) {
                Iterator<DownloadListener> it = this.f691e.iterator();
                while (it.hasNext()) {
                    it.next().onProgress(this.f687a, j, j2);
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:8:0x001d, code lost:
            r0 = r7.lastIndexOf(47);
         */
        /* renamed from: a */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private long m405a(int r7, java.util.Map<java.lang.String, java.util.List<java.lang.String>> r8, long r9) {
            /*
                r6 = this;
                r0 = 200(0xc8, float:2.8E-43)
                java.lang.String r1 = "Content-Length"
                r2 = 0
                if (r7 != r0) goto L_0x0011
                java.lang.String r7 = anet.channel.util.HttpHelper.getSingleHeaderFieldByKey(r8, r1)     // Catch:{ Exception -> 0x0042 }
                long r2 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x0042 }
                goto L_0x0042
            L_0x0011:
                r0 = 206(0xce, float:2.89E-43)
                if (r7 != r0) goto L_0x0042
                java.lang.String r7 = "Content-Range"
                java.lang.String r7 = anet.channel.util.HttpHelper.getSingleHeaderFieldByKey(r8, r7)     // Catch:{ Exception -> 0x0042 }
                if (r7 == 0) goto L_0x0031
                r0 = 47
                int r0 = r7.lastIndexOf(r0)     // Catch:{ Exception -> 0x0042 }
                r4 = -1
                if (r0 == r4) goto L_0x0031
                int r0 = r0 + 1
                java.lang.String r7 = r7.substring(r0)     // Catch:{ Exception -> 0x0042 }
                long r4 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x0042 }
                goto L_0x0032
            L_0x0031:
                r4 = r2
            L_0x0032:
                int r7 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1))
                if (r7 != 0) goto L_0x0041
                java.lang.String r7 = anet.channel.util.HttpHelper.getSingleHeaderFieldByKey(r8, r1)     // Catch:{ Exception -> 0x0041 }
                long r7 = java.lang.Long.parseLong(r7)     // Catch:{ Exception -> 0x0041 }
                long r2 = r7 + r9
                goto L_0x0042
            L_0x0041:
                r2 = r4
            L_0x0042:
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.download.DownloadManager.C0631b.m405a(int, java.util.Map, long):long");
        }

        /* renamed from: a */
        private void m410a(List<Header> list) {
            if (list != null) {
                ListIterator<Header> listIterator = list.listIterator();
                while (listIterator.hasNext()) {
                    if (HttpHeaders.RANGE.equalsIgnoreCase(listIterator.next().getName())) {
                        listIterator.remove();
                        return;
                    }
                }
            }
        }

        /* renamed from: a */
        private String m406a(URL url) {
            String path = url.getPath();
            int lastIndexOf = path.lastIndexOf(47);
            String substring = lastIndexOf != -1 ? path.substring(lastIndexOf + 1, path.length()) : null;
            if (!TextUtils.isEmpty(substring)) {
                return substring;
            }
            String md5ToHex = StringUtils.md5ToHex(url.toString());
            return md5ToHex == null ? url.getFile() : md5ToHex;
        }
    }

    /* renamed from: a */
    private void m402a() {
        Context context = this.f685d;
        if (context != null) {
            File file = new File(context.getExternalFilesDir((String) null), "downloads");
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    /* renamed from: a */
    private boolean m403a(String str) {
        if (this.f685d != null) {
            try {
                File file = new File(str);
                if (!file.exists()) {
                    return file.mkdir();
                }
                return true;
            } catch (Exception unused) {
                ALog.m327e(TAG, "create folder failed", (String) null, "folder", str);
            }
        }
        return false;
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public String m404b(String str) {
        StringBuilder sb = new StringBuilder(32);
        sb.append(this.f685d.getExternalFilesDir((String) null));
        sb.append("/");
        sb.append("downloads");
        sb.append("/");
        sb.append(str);
        return sb.toString();
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public File m400a(String str, boolean z) {
        String md5ToHex = StringUtils.md5ToHex(str);
        if (md5ToHex != null) {
            str = md5ToHex;
        }
        if (z) {
            return new File(this.f685d.getExternalCacheDir(), str);
        }
        return new File(this.f685d.getCacheDir(), str);
    }
}
