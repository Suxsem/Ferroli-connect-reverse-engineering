package org.eclipse.jetty.server.session;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class HashedSession extends AbstractSession {
    private static final Logger LOG = Log.getLogger((Class<?>) HashedSession.class);
    private final HashSessionManager _hashSessionManager;
    private transient boolean _idled = false;
    private transient boolean _saveFailed = false;

    protected HashedSession(HashSessionManager hashSessionManager, HttpServletRequest httpServletRequest) {
        super(hashSessionManager, httpServletRequest);
        this._hashSessionManager = hashSessionManager;
    }

    protected HashedSession(HashSessionManager hashSessionManager, long j, long j2, String str) {
        super(hashSessionManager, j, j2, str);
        this._hashSessionManager = hashSessionManager;
    }

    /* access modifiers changed from: protected */
    public void checkValid() {
        if (this._hashSessionManager._idleSavePeriodMs != 0) {
            deIdle();
        }
        super.checkValid();
    }

    public void setMaxInactiveInterval(int i) {
        super.setMaxInactiveInterval(i);
        if (getMaxInactiveInterval() > 0 && (((long) getMaxInactiveInterval()) * 1000) / 10 < this._hashSessionManager._scavengePeriodMs) {
            this._hashSessionManager.setScavengePeriod((i + 9) / 10);
        }
    }

    /* access modifiers changed from: protected */
    public void doInvalidate() throws IllegalStateException {
        super.doInvalidate();
        if (this._hashSessionManager._storeDir != null && getId() != null) {
            new File(this._hashSessionManager._storeDir, getId()).delete();
        }
    }

    /* access modifiers changed from: package-private */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void save(boolean r6) throws java.lang.Exception {
        /*
            r5 = this;
            monitor-enter(r5)
            boolean r0 = r5.isIdled()     // Catch:{ all -> 0x0074 }
            if (r0 != 0) goto L_0x0072
            boolean r0 = r5._saveFailed     // Catch:{ all -> 0x0074 }
            if (r0 != 0) goto L_0x0072
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x0074 }
            boolean r0 = r0.isDebugEnabled()     // Catch:{ all -> 0x0074 }
            if (r0 == 0) goto L_0x002b
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x0074 }
            java.lang.String r1 = "Saving {} {}"
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0074 }
            r3 = 0
            java.lang.String r4 = super.getId()     // Catch:{ all -> 0x0074 }
            r2[r3] = r4     // Catch:{ all -> 0x0074 }
            r3 = 1
            java.lang.Boolean r4 = java.lang.Boolean.valueOf(r6)     // Catch:{ all -> 0x0074 }
            r2[r3] = r4     // Catch:{ all -> 0x0074 }
            r0.debug((java.lang.String) r1, (java.lang.Object[]) r2)     // Catch:{ all -> 0x0074 }
        L_0x002b:
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x0062 }
            org.eclipse.jetty.server.session.HashSessionManager r2 = r5._hashSessionManager     // Catch:{ Exception -> 0x0062 }
            java.io.File r2 = r2._storeDir     // Catch:{ Exception -> 0x0062 }
            java.lang.String r3 = super.getId()     // Catch:{ Exception -> 0x0062 }
            r1.<init>(r2, r3)     // Catch:{ Exception -> 0x0062 }
            boolean r2 = r1.exists()     // Catch:{ Exception -> 0x0060 }
            if (r2 == 0) goto L_0x0042
            r1.delete()     // Catch:{ Exception -> 0x0060 }
        L_0x0042:
            r1.createNewFile()     // Catch:{ Exception -> 0x0060 }
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0060 }
            r2.<init>(r1)     // Catch:{ Exception -> 0x0060 }
            r5.willPassivate()     // Catch:{ Exception -> 0x005d }
            r5.save((java.io.OutputStream) r2)     // Catch:{ Exception -> 0x005d }
            org.eclipse.jetty.util.C2439IO.close((java.io.OutputStream) r2)     // Catch:{ Exception -> 0x005d }
            if (r6 == 0) goto L_0x0059
            r5.didActivate()     // Catch:{ Exception -> 0x005d }
            goto L_0x0072
        L_0x0059:
            r5.clearAttributes()     // Catch:{ Exception -> 0x005d }
            goto L_0x0072
        L_0x005d:
            r6 = move-exception
            r0 = r2
            goto L_0x0064
        L_0x0060:
            r6 = move-exception
            goto L_0x0064
        L_0x0062:
            r6 = move-exception
            r1 = r0
        L_0x0064:
            r5.saveFailed()     // Catch:{ all -> 0x0074 }
            if (r0 == 0) goto L_0x006c
            org.eclipse.jetty.util.C2439IO.close((java.io.OutputStream) r0)     // Catch:{ all -> 0x0074 }
        L_0x006c:
            if (r1 == 0) goto L_0x0071
            r1.delete()     // Catch:{ all -> 0x0074 }
        L_0x0071:
            throw r6     // Catch:{ all -> 0x0074 }
        L_0x0072:
            monitor-exit(r5)
            return
        L_0x0074:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.HashedSession.save(boolean):void");
    }

    public synchronized void save(OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeUTF(getClusterId());
        dataOutputStream.writeUTF(getNodeId());
        dataOutputStream.writeLong(getCreationTime());
        dataOutputStream.writeLong(getAccessed());
        dataOutputStream.writeInt(getRequests());
        dataOutputStream.writeInt(getAttributes());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataOutputStream);
        Enumeration<String> attributeNames = getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String nextElement = attributeNames.nextElement();
            objectOutputStream.writeUTF(nextElement);
            objectOutputStream.writeObject(doGet(nextElement));
        }
        objectOutputStream.close();
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0098  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void deIdle() {
        /*
            r7 = this;
            monitor-enter(r7)
            boolean r0 = r7.isIdled()     // Catch:{ all -> 0x00a0 }
            if (r0 == 0) goto L_0x009e
            long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x00a0 }
            r7.access(r0)     // Catch:{ all -> 0x00a0 }
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x00a0 }
            boolean r0 = r0.isDebugEnabled()     // Catch:{ all -> 0x00a0 }
            r1 = 0
            if (r0 == 0) goto L_0x0033
            org.eclipse.jetty.util.log.Logger r0 = LOG     // Catch:{ all -> 0x00a0 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a0 }
            r2.<init>()     // Catch:{ all -> 0x00a0 }
            java.lang.String r3 = "De-idling "
            r2.append(r3)     // Catch:{ all -> 0x00a0 }
            java.lang.String r3 = super.getId()     // Catch:{ all -> 0x00a0 }
            r2.append(r3)     // Catch:{ all -> 0x00a0 }
            java.lang.String r2 = r2.toString()     // Catch:{ all -> 0x00a0 }
            java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ all -> 0x00a0 }
            r0.debug((java.lang.String) r2, (java.lang.Object[]) r3)     // Catch:{ all -> 0x00a0 }
        L_0x0033:
            r0 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Exception -> 0x007b }
            org.eclipse.jetty.server.session.HashSessionManager r3 = r7._hashSessionManager     // Catch:{ Exception -> 0x007b }
            java.io.File r3 = r3._storeDir     // Catch:{ Exception -> 0x007b }
            java.lang.String r4 = super.getId()     // Catch:{ Exception -> 0x007b }
            r2.<init>(r3, r4)     // Catch:{ Exception -> 0x007b }
            boolean r3 = r2.exists()     // Catch:{ Exception -> 0x007b }
            if (r3 == 0) goto L_0x0071
            boolean r3 = r2.canRead()     // Catch:{ Exception -> 0x007b }
            if (r3 == 0) goto L_0x0071
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x007b }
            r3.<init>(r2)     // Catch:{ Exception -> 0x007b }
            r7._idled = r1     // Catch:{ Exception -> 0x006d }
            org.eclipse.jetty.server.session.HashSessionManager r0 = r7._hashSessionManager     // Catch:{ Exception -> 0x006d }
            r0.restoreSession(r3, r7)     // Catch:{ Exception -> 0x006d }
            org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r3)     // Catch:{ Exception -> 0x006d }
            r7.didActivate()     // Catch:{ Exception -> 0x006d }
            org.eclipse.jetty.server.session.HashSessionManager r0 = r7._hashSessionManager     // Catch:{ Exception -> 0x006d }
            long r0 = r0._savePeriodMs     // Catch:{ Exception -> 0x006d }
            r4 = 0
            int r6 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r6 != 0) goto L_0x009e
            r2.delete()     // Catch:{ Exception -> 0x006d }
            goto L_0x009e
        L_0x006d:
            r0 = move-exception
            r1 = r0
            r0 = r3
            goto L_0x007c
        L_0x0071:
            java.io.FileNotFoundException r1 = new java.io.FileNotFoundException     // Catch:{ Exception -> 0x007b }
            java.lang.String r2 = r2.getName()     // Catch:{ Exception -> 0x007b }
            r1.<init>(r2)     // Catch:{ Exception -> 0x007b }
            throw r1     // Catch:{ Exception -> 0x007b }
        L_0x007b:
            r1 = move-exception
        L_0x007c:
            org.eclipse.jetty.util.log.Logger r2 = LOG     // Catch:{ all -> 0x00a0 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x00a0 }
            r3.<init>()     // Catch:{ all -> 0x00a0 }
            java.lang.String r4 = "Problem de-idling session "
            r3.append(r4)     // Catch:{ all -> 0x00a0 }
            java.lang.String r4 = super.getId()     // Catch:{ all -> 0x00a0 }
            r3.append(r4)     // Catch:{ all -> 0x00a0 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x00a0 }
            r2.warn((java.lang.String) r3, (java.lang.Throwable) r1)     // Catch:{ all -> 0x00a0 }
            if (r0 == 0) goto L_0x009b
            org.eclipse.jetty.util.C2439IO.close((java.io.InputStream) r0)     // Catch:{ all -> 0x00a0 }
        L_0x009b:
            r7.invalidate()     // Catch:{ all -> 0x00a0 }
        L_0x009e:
            monitor-exit(r7)
            return
        L_0x00a0:
            r0 = move-exception
            monitor-exit(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.session.HashedSession.deIdle():void");
    }

    public synchronized void idle() throws Exception {
        save(false);
        this._idled = true;
    }

    public synchronized boolean isIdled() {
        return this._idled;
    }

    public synchronized boolean isSaveFailed() {
        return this._saveFailed;
    }

    public synchronized void saveFailed() {
        this._saveFailed = true;
    }
}
