package org.eclipse.jetty.http;

import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.ByteArrayBuffer;

public class HttpSchemes {
    public static final String HTTP = "http";
    public static final String HTTPS = "https";
    public static final Buffer HTTPS_BUFFER = new ByteArrayBuffer("https");
    public static final Buffer HTTP_BUFFER = new ByteArrayBuffer("http");
}
