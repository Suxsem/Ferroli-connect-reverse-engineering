package org.eclipse.jetty.server;

import android.support.p000v4.app.NotificationCompat;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import org.eclipse.jetty.util.thread.ShutdownThread;

public class ShutdownMonitor {
    /* access modifiers changed from: private */
    public boolean DEBUG;
    /* access modifiers changed from: private */
    public boolean exitVm;
    /* access modifiers changed from: private */
    public String key;
    /* access modifiers changed from: private */
    public int port;
    /* access modifiers changed from: private */
    public ServerSocket serverSocket;
    private ShutdownMonitorThread thread;

    static class Holder {
        static ShutdownMonitor instance = new ShutdownMonitor();

        Holder() {
        }
    }

    public static ShutdownMonitor getInstance() {
        return Holder.instance;
    }

    public class ShutdownMonitorThread extends Thread {
        public ShutdownMonitorThread() {
            setDaemon(true);
            setName("ShutdownMonitor");
        }

        public void run() {
            Socket socket;
            Exception e;
            if (ShutdownMonitor.this.serverSocket != null) {
                while (ShutdownMonitor.this.serverSocket != null) {
                    Socket socket2 = null;
                    try {
                        socket = ShutdownMonitor.this.serverSocket.accept();
                        try {
                            LineNumberReader lineNumberReader = new LineNumberReader(new InputStreamReader(socket.getInputStream()));
                            if (!ShutdownMonitor.this.key.equals(lineNumberReader.readLine())) {
                                System.err.println("Ignoring command with incorrect key");
                                ShutdownMonitor.this.close(socket);
                            } else {
                                OutputStream outputStream = socket.getOutputStream();
                                String readLine = lineNumberReader.readLine();
                                ShutdownMonitor.this.debug("command=%s", readLine);
                                if ("stop".equals(readLine)) {
                                    ShutdownMonitor.this.debug("Issuing graceful shutdown..", new Object[0]);
                                    ShutdownThread.getInstance().run();
                                    ShutdownMonitor.this.debug("Informing client that we are stopped.", new Object[0]);
                                    outputStream.write("Stopped\r\n".getBytes("UTF-8"));
                                    outputStream.flush();
                                    ShutdownMonitor.this.debug("Shutting down monitor", new Object[0]);
                                    ShutdownMonitor.this.close(socket);
                                    ShutdownMonitor.this.close(ShutdownMonitor.this.serverSocket);
                                    ServerSocket unused = ShutdownMonitor.this.serverSocket = null;
                                    if (ShutdownMonitor.this.exitVm) {
                                        ShutdownMonitor.this.debug("Killing JVM", new Object[0]);
                                        System.exit(0);
                                    }
                                } else {
                                    if (NotificationCompat.CATEGORY_STATUS.equals(readLine)) {
                                        outputStream.write("OK\r\n".getBytes("UTF-8"));
                                        outputStream.flush();
                                    }
                                    socket2 = socket;
                                }
                                ShutdownMonitor.this.close(socket2);
                            }
                        } catch (Exception e2) {
                            e = e2;
                            try {
                                ShutdownMonitor.this.debug(e);
                                System.err.println(e.toString());
                                ShutdownMonitor.this.close(socket);
                            } catch (Throwable th) {
                                th = th;
                            }
                        }
                    } catch (Exception e3) {
                        Exception exc = e3;
                        socket = null;
                        e = exc;
                        ShutdownMonitor.this.debug(e);
                        System.err.println(e.toString());
                        ShutdownMonitor.this.close(socket);
                    } catch (Throwable th2) {
                        Throwable th3 = th2;
                        socket = null;
                        th = th3;
                        ShutdownMonitor.this.close(socket);
                        throw th;
                    }
                }
            }
        }

        public void start() {
            if (isAlive()) {
                System.err.printf("ShutdownMonitorThread already started", new Object[0]);
                return;
            }
            startListenSocket();
            if (ShutdownMonitor.this.serverSocket != null) {
                if (ShutdownMonitor.this.DEBUG) {
                    System.err.println("Starting ShutdownMonitorThread");
                }
                super.start();
            }
        }

        private void startListenSocket() {
            if (ShutdownMonitor.this.port >= 0) {
                try {
                    ServerSocket unused = ShutdownMonitor.this.serverSocket = new ServerSocket(ShutdownMonitor.this.port, 1, InetAddress.getByName("127.0.0.1"));
                    if (ShutdownMonitor.this.port == 0) {
                        int unused2 = ShutdownMonitor.this.port = ShutdownMonitor.this.serverSocket.getLocalPort();
                        System.out.printf("STOP.PORT=%d%n", new Object[]{Integer.valueOf(ShutdownMonitor.this.port)});
                    }
                    if (ShutdownMonitor.this.key == null) {
                        ShutdownMonitor shutdownMonitor = ShutdownMonitor.this;
                        double random = Math.random() * 9.223372036854776E18d;
                        double hashCode = (double) hashCode();
                        Double.isNaN(hashCode);
                        double d = random + hashCode;
                        double currentTimeMillis = (double) System.currentTimeMillis();
                        Double.isNaN(currentTimeMillis);
                        String unused3 = shutdownMonitor.key = Long.toString((long) (d + currentTimeMillis), 36);
                        System.out.printf("STOP.KEY=%s%n", new Object[]{ShutdownMonitor.this.key});
                    }
                } catch (Exception e) {
                    ShutdownMonitor.this.debug(e);
                    PrintStream printStream = System.err;
                    printStream.println("Error binding monitor port " + ShutdownMonitor.this.port + ": " + e.toString());
                    ServerSocket unused4 = ShutdownMonitor.this.serverSocket = null;
                } finally {
                    ShutdownMonitor shutdownMonitor2 = ShutdownMonitor.this;
                    shutdownMonitor2.debug("STOP.PORT=%d", Integer.valueOf(shutdownMonitor2.port));
                    ShutdownMonitor shutdownMonitor3 = ShutdownMonitor.this;
                    shutdownMonitor3.debug("STOP.KEY=%s", shutdownMonitor3.key);
                    ShutdownMonitor shutdownMonitor4 = ShutdownMonitor.this;
                    shutdownMonitor4.debug("%s", shutdownMonitor4.serverSocket);
                }
            } else if (ShutdownMonitor.this.DEBUG) {
                PrintStream printStream2 = System.err;
                printStream2.println("ShutdownMonitor not in use (port < 0): " + ShutdownMonitor.this.port);
            }
        }
    }

    private ShutdownMonitor() {
        Properties properties = System.getProperties();
        this.DEBUG = properties.containsKey("DEBUG");
        this.port = Integer.parseInt(properties.getProperty("STOP.PORT", "-1"));
        this.key = properties.getProperty("STOP.KEY", (String) null);
        this.exitVm = true;
    }

    /* access modifiers changed from: private */
    public void close(ServerSocket serverSocket2) {
        if (serverSocket2 != null) {
            try {
                serverSocket2.close();
            } catch (IOException unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void close(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public void debug(String str, Object... objArr) {
        if (this.DEBUG) {
            PrintStream printStream = System.err;
            printStream.printf("[ShutdownMonitor] " + str + "%n", objArr);
        }
    }

    /* access modifiers changed from: private */
    public void debug(Throwable th) {
        if (this.DEBUG) {
            th.printStackTrace(System.err);
        }
    }

    public String getKey() {
        return this.key;
    }

    public int getPort() {
        return this.port;
    }

    public ServerSocket getServerSocket() {
        return this.serverSocket;
    }

    public boolean isExitVm() {
        return this.exitVm;
    }

    public void setDebug(boolean z) {
        this.DEBUG = z;
    }

    public void setExitVm(boolean z) {
        synchronized (this) {
            if (this.thread != null) {
                if (this.thread.isAlive()) {
                    throw new IllegalStateException("ShutdownMonitorThread already started");
                }
            }
            this.exitVm = z;
        }
    }

    public void setKey(String str) {
        synchronized (this) {
            if (this.thread != null) {
                if (this.thread.isAlive()) {
                    throw new IllegalStateException("ShutdownMonitorThread already started");
                }
            }
            this.key = str;
        }
    }

    public void setPort(int i) {
        synchronized (this) {
            if (this.thread != null) {
                if (this.thread.isAlive()) {
                    throw new IllegalStateException("ShutdownMonitorThread already started");
                }
            }
            this.port = i;
        }
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:11:0x0023, code lost:
        if (r0 == null) goto L_?;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0025, code lost:
        r0.start();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        return;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:?, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void start() throws java.lang.Exception {
        /*
            r3 = this;
            monitor-enter(r3)
            org.eclipse.jetty.server.ShutdownMonitor$ShutdownMonitorThread r0 = r3.thread     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x0019
            org.eclipse.jetty.server.ShutdownMonitor$ShutdownMonitorThread r0 = r3.thread     // Catch:{ all -> 0x0029 }
            boolean r0 = r0.isAlive()     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x0019
            java.io.PrintStream r0 = java.lang.System.err     // Catch:{ all -> 0x0029 }
            java.lang.String r1 = "ShutdownMonitorThread already started"
            r2 = 0
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ all -> 0x0029 }
            r0.printf(r1, r2)     // Catch:{ all -> 0x0029 }
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            return
        L_0x0019:
            org.eclipse.jetty.server.ShutdownMonitor$ShutdownMonitorThread r0 = new org.eclipse.jetty.server.ShutdownMonitor$ShutdownMonitorThread     // Catch:{ all -> 0x0029 }
            r0.<init>()     // Catch:{ all -> 0x0029 }
            r3.thread = r0     // Catch:{ all -> 0x0029 }
            org.eclipse.jetty.server.ShutdownMonitor$ShutdownMonitorThread r0 = r3.thread     // Catch:{ all -> 0x0029 }
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            if (r0 == 0) goto L_0x0028
            r0.start()
        L_0x0028:
            return
        L_0x0029:
            r0 = move-exception
            monitor-exit(r3)     // Catch:{ all -> 0x0029 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.ShutdownMonitor.start():void");
    }

    /* access modifiers changed from: protected */
    public boolean isAlive() {
        boolean z;
        synchronized (this) {
            z = this.thread != null && this.thread.isAlive();
        }
        return z;
    }

    public String toString() {
        return String.format("%s[port=%d]", new Object[]{getClass().getName(), Integer.valueOf(this.port)});
    }
}
