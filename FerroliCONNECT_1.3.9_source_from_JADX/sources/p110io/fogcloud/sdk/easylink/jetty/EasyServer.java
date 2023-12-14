package p110io.fogcloud.sdk.easylink.jetty;

import javax.servlet.Servlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkCallBack;

/* renamed from: io.fogcloud.sdk.easylink.jetty.EasyServer */
public class EasyServer {
    private int mPort;
    public Server mServer;

    public EasyServer(int i) {
        this.mPort = i;
    }

    public synchronized void start(EasyLinkCallBack easyLinkCallBack) {
        if (this.mServer == null || !this.mServer.isStarted()) {
            if (this.mServer == null) {
                ServletContextHandler servletContextHandler = new ServletContextHandler(1);
                servletContextHandler.addServlet(new ServletHolder((Servlet) new EasyServlet(easyLinkCallBack)), "/auth-setup");
                HandlerList handlerList = new HandlerList();
                handlerList.addHandler(servletContextHandler);
                this.mServer = new Server(this.mPort);
                this.mServer.setHandler(handlerList);
            }
            try {
                this.mServer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
        return;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001b, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void stop() {
        /*
            r1 = this;
            monitor-enter(r1)
            org.eclipse.jetty.server.Server r0 = r1.mServer     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x001a
            org.eclipse.jetty.server.Server r0 = r1.mServer     // Catch:{ all -> 0x001c }
            boolean r0 = r0.isStopped()     // Catch:{ all -> 0x001c }
            if (r0 == 0) goto L_0x000e
            goto L_0x001a
        L_0x000e:
            org.eclipse.jetty.server.Server r0 = r1.mServer     // Catch:{ Exception -> 0x0014 }
            r0.stop()     // Catch:{ Exception -> 0x0014 }
            goto L_0x0018
        L_0x0014:
            r0 = move-exception
            r0.printStackTrace()     // Catch:{ all -> 0x001c }
        L_0x0018:
            monitor-exit(r1)
            return
        L_0x001a:
            monitor-exit(r1)
            return
        L_0x001c:
            r0 = move-exception
            monitor-exit(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: p110io.fogcloud.sdk.easylink.jetty.EasyServer.stop():void");
    }

    public synchronized boolean isStarted() {
        if (this.mServer == null) {
            return false;
        }
        return this.mServer.isStarted();
    }
}
