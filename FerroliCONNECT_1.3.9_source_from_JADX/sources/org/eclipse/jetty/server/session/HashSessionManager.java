package org.eclipse.jetty.server.session;

import com.igexin.sdk.GTIntentService;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.log.Logger;

public class HashSessionManager extends AbstractSessionManager {
    private static int __id;
    static final Logger __log = SessionHandler.LOG;
    private boolean _deleteUnrestorableSessions = false;
    long _idleSavePeriodMs = 0;
    private boolean _lazyLoad = false;
    long _savePeriodMs = 0;
    private TimerTask _saveTask;
    long _scavengePeriodMs = GTIntentService.WAIT_TIME;
    protected final ConcurrentMap<String, HashedSession> _sessions = new ConcurrentHashMap();
    private volatile boolean _sessionsLoaded = false;
    File _storeDir;
    private TimerTask _task;
    private Timer _timer;
    private boolean _timerStop = false;

    public void doStart() throws Exception {
        super.doStart();
        this._timerStop = false;
        ContextHandler.Context currentContext = ContextHandler.getCurrentContext();
        if (currentContext != null) {
            this._timer = (Timer) currentContext.getAttribute("org.eclipse.jetty.server.session.timer");
        }
        if (this._timer == null) {
            this._timerStop = true;
            StringBuilder sb = new StringBuilder();
            sb.append("HashSessionScavenger-");
            int i = __id;
            __id = i + 1;
            sb.append(i);
            this._timer = new Timer(sb.toString(), true);
        }
        setScavengePeriod(getScavengePeriod());
        File file = this._storeDir;
        if (file != null) {
            if (!file.exists()) {
                this._storeDir.mkdirs();
            }
            if (!this._lazyLoad) {
                restoreSessions();
            }
        }
        setSavePeriod(getSavePeriod());
    }

    public void doStop() throws Exception {
        synchronized (this) {
            if (this._saveTask != null) {
                this._saveTask.cancel();
            }
            this._saveTask = null;
            if (this._task != null) {
                this._task.cancel();
            }
            this._task = null;
            if (this._timer != null && this._timerStop) {
                this._timer.cancel();
            }
            this._timer = null;
        }
        super.doStop();
        this._sessions.clear();
    }

    public int getScavengePeriod() {
        return (int) (this._scavengePeriodMs / 1000);
    }

    public int getSessions() {
        int sessions = super.getSessions();
        if (__log.isDebugEnabled() && this._sessions.size() != sessions) {
            Logger logger = __log;
            logger.warn("sessions: " + this._sessions.size() + "!=" + sessions, new Object[0]);
        }
        return sessions;
    }

    public int getIdleSavePeriod() {
        long j = this._idleSavePeriodMs;
        if (j <= 0) {
            return 0;
        }
        return (int) (j / 1000);
    }

    public void setIdleSavePeriod(int i) {
        this._idleSavePeriodMs = ((long) i) * 1000;
    }

    public void setMaxInactiveInterval(int i) {
        super.setMaxInactiveInterval(i);
        if (this._dftMaxIdleSecs > 0 && this._scavengePeriodMs > ((long) this._dftMaxIdleSecs) * 1000) {
            setScavengePeriod((this._dftMaxIdleSecs + 9) / 10);
        }
    }

    public void setSavePeriod(int i) {
        long j = ((long) i) * 1000;
        if (j < 0) {
            j = 0;
        }
        this._savePeriodMs = j;
        if (this._timer != null) {
            synchronized (this) {
                if (this._saveTask != null) {
                    this._saveTask.cancel();
                }
                if (this._savePeriodMs > 0 && this._storeDir != null) {
                    this._saveTask = new TimerTask() {
                        public void run() {
                            try {
                                HashSessionManager.this.saveSessions(true);
                            } catch (Exception e) {
                                HashSessionManager.__log.warn(e);
                            }
                        }
                    };
                    this._timer.schedule(this._saveTask, this._savePeriodMs, this._savePeriodMs);
                }
            }
        }
    }

    public int getSavePeriod() {
        long j = this._savePeriodMs;
        if (j <= 0) {
            return 0;
        }
        return (int) (j / 1000);
    }

    public void setScavengePeriod(int i) {
        if (i == 0) {
            i = 60;
        }
        long j = this._scavengePeriodMs;
        long j2 = ((long) i) * 1000;
        if (j2 > 60000) {
            j2 = 60000;
        }
        if (j2 < 1000) {
            j2 = 1000;
        }
        this._scavengePeriodMs = j2;
        if (this._timer == null) {
            return;
        }
        if (j2 != j || this._task == null) {
            synchronized (this) {
                if (this._task != null) {
                    this._task.cancel();
                }
                this._task = new TimerTask() {
                    public void run() {
                        HashSessionManager.this.scavenge();
                    }
                };
                this._timer.schedule(this._task, this._scavengePeriodMs, this._scavengePeriodMs);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void scavenge() {
        if (!isStopping() && !isStopped()) {
            Thread currentThread = Thread.currentThread();
            ClassLoader contextClassLoader = currentThread.getContextClassLoader();
            try {
                if (this._loader != null) {
                    currentThread.setContextClassLoader(this._loader);
                }
                long currentTimeMillis = System.currentTimeMillis();
                for (HashedSession hashedSession : this._sessions.values()) {
                    long maxInactiveInterval = ((long) hashedSession.getMaxInactiveInterval()) * 1000;
                    if (maxInactiveInterval > 0 && hashedSession.getAccessed() + maxInactiveInterval < currentTimeMillis) {
                        hashedSession.timeout();
                    } else if (this._idleSavePeriodMs > 0 && hashedSession.getAccessed() + this._idleSavePeriodMs < currentTimeMillis) {
                        try {
                            hashedSession.idle();
                        } catch (Exception e) {
                            Logger logger = __log;
                            logger.warn("Problem idling session " + hashedSession.getId(), (Throwable) e);
                        }
                    }
                }
                currentThread.setContextClassLoader(contextClassLoader);
            } catch (Exception e2) {
                __log.warn("Problem scavenging sessions", (Throwable) e2);
            } catch (Throwable th) {
                currentThread.setContextClassLoader(contextClassLoader);
                throw th;
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addSession(AbstractSession abstractSession) {
        if (isRunning()) {
            this._sessions.put(abstractSession.getClusterId(), (HashedSession) abstractSession);
        }
    }

    public AbstractSession getSession(String str) {
        if (this._lazyLoad && !this._sessionsLoaded) {
            try {
                restoreSessions();
            } catch (Exception e) {
                __log.warn(e);
            }
        }
        ConcurrentMap<String, HashedSession> concurrentMap = this._sessions;
        if (concurrentMap == null) {
            return null;
        }
        HashedSession hashedSession = concurrentMap.get(str);
        if (hashedSession == null && this._lazyLoad) {
            hashedSession = restoreSession(str);
        }
        if (hashedSession == null) {
            return null;
        }
        if (this._idleSavePeriodMs != 0) {
            hashedSession.deIdle();
        }
        return hashedSession;
    }

    /* access modifiers changed from: protected */
    public void invalidateSessions() throws Exception {
        File file;
        ArrayList arrayList = new ArrayList(this._sessions.values());
        int i = 100;
        while (arrayList.size() > 0) {
            int i2 = i - 1;
            if (i > 0) {
                if (!isStopping() || (file = this._storeDir) == null || !file.exists() || !this._storeDir.canWrite()) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ((HashedSession) it.next()).invalidate();
                    }
                } else {
                    Iterator it2 = arrayList.iterator();
                    while (it2.hasNext()) {
                        HashedSession hashedSession = (HashedSession) it2.next();
                        hashedSession.save(false);
                        removeSession((AbstractSession) hashedSession, false);
                    }
                }
                arrayList = new ArrayList(this._sessions.values());
                i = i2;
            } else {
                return;
            }
        }
    }

    /* access modifiers changed from: protected */
    public AbstractSession newSession(HttpServletRequest httpServletRequest) {
        return new HashedSession(this, httpServletRequest);
    }

    /* access modifiers changed from: protected */
    public AbstractSession newSession(long j, long j2, String str) {
        return new HashedSession(this, j, j2, str);
    }

    /* access modifiers changed from: protected */
    public boolean removeSession(String str) {
        return this._sessions.remove(str) != null;
    }

    public void setStoreDirectory(File file) throws IOException {
        this._storeDir = file.getCanonicalFile();
    }

    public File getStoreDirectory() {
        return this._storeDir;
    }

    public void setLazyLoad(boolean z) {
        this._lazyLoad = z;
    }

    public boolean isLazyLoad() {
        return this._lazyLoad;
    }

    public boolean isDeleteUnrestorableSessions() {
        return this._deleteUnrestorableSessions;
    }

    public void setDeleteUnrestorableSessions(boolean z) {
        this._deleteUnrestorableSessions = z;
    }

    public void restoreSessions() throws Exception {
        this._sessionsLoaded = true;
        File file = this._storeDir;
        if (file != null && file.exists()) {
            int i = 0;
            if (!this._storeDir.canRead()) {
                Logger logger = __log;
                logger.warn("Unable to restore Sessions: Cannot read from Session storage directory " + this._storeDir.getAbsolutePath(), new Object[0]);
                return;
            }
            String[] list = this._storeDir.list();
            while (list != null && i < list.length) {
                restoreSession(list[i]);
                i++;
            }
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0033 A[Catch:{ Exception -> 0x003a, all -> 0x0030 }] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x003e A[Catch:{ Exception -> 0x003a, all -> 0x0030 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0059 A[Catch:{ Exception -> 0x003a, all -> 0x0030 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0073 A[Catch:{ Exception -> 0x003a, all -> 0x0030 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized org.eclipse.jetty.server.session.HashedSession restoreSession(java.lang.String r6) {
        /*
            r5 = this;
            monitor-enter(r5)
            java.io.File r0 = new java.io.File     // Catch:{ all -> 0x008b }
            java.io.File r1 = r5._storeDir     // Catch:{ all -> 0x008b }
            r0.<init>(r1, r6)     // Catch:{ all -> 0x008b }
            r1 = 0
            boolean r2 = r0.exists()     // Catch:{ Exception -> 0x003a, all -> 0x0030 }
            if (r2 == 0) goto L_0x002c
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Exception -> 0x003a, all -> 0x0030 }
            r2.<init>(r0)     // Catch:{ Exception -> 0x003a, all -> 0x0030 }
            org.eclipse.jetty.server.session.HashedSession r3 = r5.restoreSession(r2, r1)     // Catch:{ Exception -> 0x002a, all -> 0x0027 }
            r4 = 0
            r5.addSession(r3, r4)     // Catch:{ Exception -> 0x002a, all -> 0x0027 }
            r3.didActivate()     // Catch:{ Exception -> 0x002a, all -> 0x0027 }
            org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r2)     // Catch:{ all -> 0x008b }
            r0.delete()     // Catch:{ all -> 0x008b }
            monitor-exit(r5)
            return r3
        L_0x0027:
            r6 = move-exception
            r1 = r2
            goto L_0x0031
        L_0x002a:
            r3 = move-exception
            goto L_0x003c
        L_0x002c:
            r0.delete()     // Catch:{ all -> 0x008b }
            goto L_0x0089
        L_0x0030:
            r6 = move-exception
        L_0x0031:
            if (r1 == 0) goto L_0x0036
            org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r1)     // Catch:{ all -> 0x008b }
        L_0x0036:
            r0.delete()     // Catch:{ all -> 0x008b }
            throw r6     // Catch:{ all -> 0x008b }
        L_0x003a:
            r3 = move-exception
            r2 = r1
        L_0x003c:
            if (r2 == 0) goto L_0x0041
            org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r2)     // Catch:{ all -> 0x008b }
        L_0x0041:
            boolean r2 = r5.isDeleteUnrestorableSessions()     // Catch:{ all -> 0x008b }
            if (r2 == 0) goto L_0x0073
            boolean r2 = r0.exists()     // Catch:{ all -> 0x008b }
            if (r2 == 0) goto L_0x0073
            java.io.File r2 = r0.getParentFile()     // Catch:{ all -> 0x008b }
            java.io.File r4 = r5._storeDir     // Catch:{ all -> 0x008b }
            boolean r2 = r2.equals(r4)     // Catch:{ all -> 0x008b }
            if (r2 == 0) goto L_0x0073
            r0.delete()     // Catch:{ all -> 0x008b }
            org.eclipse.jetty.util.log.Logger r0 = __log     // Catch:{ all -> 0x008b }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x008b }
            r2.<init>()     // Catch:{ all -> 0x008b }
            java.lang.String r4 = "Deleting file for unrestorable session "
            r2.append(r4)     // Catch:{ all -> 0x008b }
            r2.append(r6)     // Catch:{ all -> 0x008b }
            java.lang.String r6 = r2.toString()     // Catch:{ all -> 0x008b }
            r0.warn((java.lang.String) r6, (java.lang.Throwable) r3)     // Catch:{ all -> 0x008b }
            goto L_0x0089
        L_0x0073:
            org.eclipse.jetty.util.log.Logger r0 = __log     // Catch:{ all -> 0x008b }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x008b }
            r2.<init>()     // Catch:{ all -> 0x008b }
            java.lang.String r4 = "Problem restoring session "
            r2.append(r4)     // Catch:{ all -> 0x008b }
            r2.append(r6)     // Catch:{ all -> 0x008b }
            java.lang.String r6 = r2.toString()     // Catch:{ all -> 0x008b }
            r0.warn((java.lang.String) r6, (java.lang.Throwable) r3)     // Catch:{ all -> 0x008b }
        L_0x0089:
            monitor-exit(r5)
            return r1
        L_0x008b:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.HashSessionManager.restoreSession(java.lang.String):org.eclipse.jetty.server.session.HashedSession");
    }

    public void saveSessions(boolean z) throws Exception {
        File file = this._storeDir;
        if (file != null && file.exists()) {
            if (!this._storeDir.canWrite()) {
                Logger logger = __log;
                logger.warn("Unable to save Sessions: Session persistence storage directory " + this._storeDir.getAbsolutePath() + " is not writeable", new Object[0]);
                return;
            }
            for (HashedSession save : this._sessions.values()) {
                save.save(true);
            }
        }
    }

    public HashedSession restoreSession(InputStream inputStream, HashedSession hashedSession) throws Exception {
        ClassLoadingObjectInputStream dataInputStream = new DataInputStream(inputStream);
        try {
            String readUTF = dataInputStream.readUTF();
            dataInputStream.readUTF();
            long readLong = dataInputStream.readLong();
            long readLong2 = dataInputStream.readLong();
            int readInt = dataInputStream.readInt();
            if (hashedSession == null) {
                hashedSession = (HashedSession) newSession(readLong, readLong2, readUTF);
            }
            hashedSession.setRequests(readInt);
            int readInt2 = dataInputStream.readInt();
            if (readInt2 > 0) {
                dataInputStream = new ClassLoadingObjectInputStream(dataInputStream);
                for (int i = 0; i < readInt2; i++) {
                    hashedSession.setAttribute(dataInputStream.readUTF(), dataInputStream.readObject());
                }
            }
            C2439IO.close((InputStream) dataInputStream);
            return hashedSession;
        } catch (Throwable th) {
            throw th;
        } finally {
            C2439IO.close((InputStream) dataInputStream);
        }
    }

    protected class ClassLoadingObjectInputStream extends ObjectInputStream {
        public ClassLoadingObjectInputStream(InputStream inputStream) throws IOException {
            super(inputStream);
        }

        public ClassLoadingObjectInputStream() throws IOException {
        }

        public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws IOException, ClassNotFoundException {
            try {
                return Class.forName(objectStreamClass.getName(), false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException unused) {
                return super.resolveClass(objectStreamClass);
            }
        }
    }
}
