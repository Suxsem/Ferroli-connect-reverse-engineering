package org.eclipse.jetty.p119io;

import java.net.Socket;

/* renamed from: org.eclipse.jetty.io.NetworkTrafficListener */
public interface NetworkTrafficListener {

    /* renamed from: org.eclipse.jetty.io.NetworkTrafficListener$Empty */
    public static class Empty implements NetworkTrafficListener {
        public void closed(Socket socket) {
        }

        public void incoming(Socket socket, Buffer buffer) {
        }

        public void opened(Socket socket) {
        }

        public void outgoing(Socket socket, Buffer buffer) {
        }
    }

    void closed(Socket socket);

    void incoming(Socket socket, Buffer buffer);

    void opened(Socket socket);

    void outgoing(Socket socket, Buffer buffer);
}
