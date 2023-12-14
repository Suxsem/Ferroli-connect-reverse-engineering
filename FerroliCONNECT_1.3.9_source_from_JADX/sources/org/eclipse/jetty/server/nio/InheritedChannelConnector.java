package org.eclipse.jetty.server.nio;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class InheritedChannelConnector extends SelectChannelConnector {
    private static final Logger LOG = Log.getLogger((Class<?>) InheritedChannelConnector.class);

    /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
        LOG.warn("Need at least Java 5 to use socket inherited from xinetd/inetd.", new java.lang.Object[0]);
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:11:0x004c */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void open() throws java.io.IOException {
        /*
            r5 = this;
            monitor-enter(r5)
            r0 = 0
            java.nio.channels.Channel r1 = java.lang.System.inheritedChannel()     // Catch:{ NoSuchMethodError -> 0x004c }
            boolean r2 = r1 instanceof java.nio.channels.ServerSocketChannel     // Catch:{ NoSuchMethodError -> 0x004c }
            if (r2 == 0) goto L_0x000f
            java.nio.channels.ServerSocketChannel r1 = (java.nio.channels.ServerSocketChannel) r1     // Catch:{ NoSuchMethodError -> 0x004c }
            r5._acceptChannel = r1     // Catch:{ NoSuchMethodError -> 0x004c }
            goto L_0x003f
        L_0x000f:
            org.eclipse.jetty.util.log.Logger r2 = LOG     // Catch:{ NoSuchMethodError -> 0x004c }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ NoSuchMethodError -> 0x004c }
            r3.<init>()     // Catch:{ NoSuchMethodError -> 0x004c }
            java.lang.String r4 = "Unable to use System.inheritedChannel() ["
            r3.append(r4)     // Catch:{ NoSuchMethodError -> 0x004c }
            r3.append(r1)     // Catch:{ NoSuchMethodError -> 0x004c }
            java.lang.String r1 = "]. Trying a new ServerSocketChannel at "
            r3.append(r1)     // Catch:{ NoSuchMethodError -> 0x004c }
            java.lang.String r1 = r5.getHost()     // Catch:{ NoSuchMethodError -> 0x004c }
            r3.append(r1)     // Catch:{ NoSuchMethodError -> 0x004c }
            java.lang.String r1 = ":"
            r3.append(r1)     // Catch:{ NoSuchMethodError -> 0x004c }
            int r1 = r5.getPort()     // Catch:{ NoSuchMethodError -> 0x004c }
            r3.append(r1)     // Catch:{ NoSuchMethodError -> 0x004c }
            java.lang.String r1 = r3.toString()     // Catch:{ NoSuchMethodError -> 0x004c }
            java.lang.Object[] r3 = new java.lang.Object[r0]     // Catch:{ NoSuchMethodError -> 0x004c }
            r2.warn((java.lang.String) r1, (java.lang.Object[]) r3)     // Catch:{ NoSuchMethodError -> 0x004c }
        L_0x003f:
            java.nio.channels.ServerSocketChannel r1 = r5._acceptChannel     // Catch:{ NoSuchMethodError -> 0x004c }
            if (r1 == 0) goto L_0x0055
            java.nio.channels.ServerSocketChannel r1 = r5._acceptChannel     // Catch:{ NoSuchMethodError -> 0x004c }
            r2 = 1
            r1.configureBlocking(r2)     // Catch:{ NoSuchMethodError -> 0x004c }
            goto L_0x0055
        L_0x004a:
            r0 = move-exception
            goto L_0x005e
        L_0x004c:
            org.eclipse.jetty.util.log.Logger r1 = LOG     // Catch:{ all -> 0x004a }
            java.lang.String r2 = "Need at least Java 5 to use socket inherited from xinetd/inetd."
            java.lang.Object[] r0 = new java.lang.Object[r0]     // Catch:{ all -> 0x004a }
            r1.warn((java.lang.String) r2, (java.lang.Object[]) r0)     // Catch:{ all -> 0x004a }
        L_0x0055:
            java.nio.channels.ServerSocketChannel r0 = r5._acceptChannel     // Catch:{ all -> 0x004a }
            if (r0 != 0) goto L_0x005c
            super.open()     // Catch:{ all -> 0x004a }
        L_0x005c:
            monitor-exit(r5)     // Catch:{ all -> 0x004a }
            return
        L_0x005e:
            monitor-exit(r5)     // Catch:{ all -> 0x004a }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.nio.InheritedChannelConnector.open():void");
    }
}
