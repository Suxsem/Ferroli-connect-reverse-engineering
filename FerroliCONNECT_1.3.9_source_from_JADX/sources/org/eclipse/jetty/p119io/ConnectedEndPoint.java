package org.eclipse.jetty.p119io;

/* renamed from: org.eclipse.jetty.io.ConnectedEndPoint */
public interface ConnectedEndPoint extends EndPoint {
    Connection getConnection();

    void setConnection(Connection connection);
}
