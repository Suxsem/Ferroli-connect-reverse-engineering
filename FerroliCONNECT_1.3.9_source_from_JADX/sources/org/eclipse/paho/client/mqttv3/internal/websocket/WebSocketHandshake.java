package org.eclipse.paho.client.mqttv3.internal.websocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WebSocketHandshake {
    private static final String ACCEPT_SALT = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private static final String EMPTY = "";
    private static final String HTTP_HEADER_CONNECTION = "connection";
    private static final String HTTP_HEADER_CONNECTION_VALUE = "upgrade";
    private static final String HTTP_HEADER_SEC_WEBSOCKET_ACCEPT = "sec-websocket-accept";
    private static final String HTTP_HEADER_SEC_WEBSOCKET_PROTOCOL = "sec-websocket-protocol";
    private static final String HTTP_HEADER_UPGRADE = "upgrade";
    private static final String HTTP_HEADER_UPGRADE_WEBSOCKET = "websocket";
    private static final String LINE_SEPARATOR = "\r\n";
    private static final String SHA1_PROTOCOL = "SHA1";
    String host;
    InputStream input;
    OutputStream output;
    int port;
    String uri;

    public WebSocketHandshake(InputStream inputStream, OutputStream outputStream, String str, String str2, int i) {
        this.input = inputStream;
        this.output = outputStream;
        this.uri = str;
        this.host = str2;
        this.port = i;
    }

    public void execute() throws IOException {
        byte[] bArr = new byte[16];
        System.arraycopy(UUID.randomUUID().toString().getBytes(), 0, bArr, 0, 16);
        String encodeBytes = Base64.encodeBytes(bArr);
        sendHandshakeRequest(encodeBytes);
        receiveHandshakeResponse(encodeBytes);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x00d7 A[Catch:{ URISyntaxException -> 0x00f6 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void sendHandshakeRequest(java.lang.String r7) throws java.io.IOException {
        /*
            r6 = this;
            java.lang.String r0 = "\r\n"
            java.lang.String r1 = "/mqtt"
            java.net.URI r2 = new java.net.URI     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r3 = r6.uri     // Catch:{ URISyntaxException -> 0x00f6 }
            r2.<init>(r3)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r3 = r2.getRawPath()     // Catch:{ URISyntaxException -> 0x00f6 }
            if (r3 == 0) goto L_0x0048
            java.lang.String r3 = r2.getRawPath()     // Catch:{ URISyntaxException -> 0x00f6 }
            boolean r3 = r3.isEmpty()     // Catch:{ URISyntaxException -> 0x00f6 }
            if (r3 != 0) goto L_0x0048
            java.lang.String r1 = r2.getRawPath()     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r3 = r2.getRawQuery()     // Catch:{ URISyntaxException -> 0x00f6 }
            if (r3 == 0) goto L_0x0048
            java.lang.String r3 = r2.getRawQuery()     // Catch:{ URISyntaxException -> 0x00f6 }
            boolean r3 = r3.isEmpty()     // Catch:{ URISyntaxException -> 0x00f6 }
            if (r3 != 0) goto L_0x0048
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r1 = java.lang.String.valueOf(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            r3.<init>(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r1 = "?"
            r3.append(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r1 = r2.getRawQuery()     // Catch:{ URISyntaxException -> 0x00f6 }
            r3.append(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r1 = r3.toString()     // Catch:{ URISyntaxException -> 0x00f6 }
        L_0x0048:
            java.io.PrintWriter r3 = new java.io.PrintWriter     // Catch:{ URISyntaxException -> 0x00f6 }
            java.io.OutputStream r4 = r6.output     // Catch:{ URISyntaxException -> 0x00f6 }
            r3.<init>(r4)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r5 = "GET "
            r4.<init>(r5)     // Catch:{ URISyntaxException -> 0x00f6 }
            r4.append(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r1 = " HTTP/1.1"
            r4.append(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            r4.append(r0)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r1 = r4.toString()     // Catch:{ URISyntaxException -> 0x00f6 }
            r3.print(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            int r1 = r6.port     // Catch:{ URISyntaxException -> 0x00f6 }
            r4 = 80
            java.lang.String r5 = "Host: "
            if (r1 == r4) goto L_0x0095
            int r1 = r6.port     // Catch:{ URISyntaxException -> 0x00f6 }
            r4 = 443(0x1bb, float:6.21E-43)
            if (r1 == r4) goto L_0x0095
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.<init>(r5)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r4 = r6.host     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.append(r4)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r4 = ":"
            r1.append(r4)     // Catch:{ URISyntaxException -> 0x00f6 }
            int r4 = r6.port     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.append(r4)     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.append(r0)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r1 = r1.toString()     // Catch:{ URISyntaxException -> 0x00f6 }
            r3.print(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            goto L_0x00a9
        L_0x0095:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.<init>(r5)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r4 = r6.host     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.append(r4)     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.append(r0)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r1 = r1.toString()     // Catch:{ URISyntaxException -> 0x00f6 }
            r3.print(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
        L_0x00a9:
            java.lang.String r1 = "Upgrade: websocket\r\n"
            r3.print(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r1 = "Connection: Upgrade\r\n"
            r3.print(r1)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r4 = "Sec-WebSocket-Key: "
            r1.<init>(r4)     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.append(r7)     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.append(r0)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r7 = r1.toString()     // Catch:{ URISyntaxException -> 0x00f6 }
            r3.print(r7)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r7 = "Sec-WebSocket-Protocol: mqtt\r\n"
            r3.print(r7)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r7 = "Sec-WebSocket-Version: 13\r\n"
            r3.print(r7)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r7 = r2.getUserInfo()     // Catch:{ URISyntaxException -> 0x00f6 }
            if (r7 == 0) goto L_0x00ef
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r2 = "Authorization: Basic "
            r1.<init>(r2)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r7 = org.eclipse.paho.client.mqttv3.internal.websocket.Base64.encode(r7)     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.append(r7)     // Catch:{ URISyntaxException -> 0x00f6 }
            r1.append(r0)     // Catch:{ URISyntaxException -> 0x00f6 }
            java.lang.String r7 = r1.toString()     // Catch:{ URISyntaxException -> 0x00f6 }
            r3.print(r7)     // Catch:{ URISyntaxException -> 0x00f6 }
        L_0x00ef:
            r3.print(r0)     // Catch:{ URISyntaxException -> 0x00f6 }
            r3.flush()     // Catch:{ URISyntaxException -> 0x00f6 }
            return
        L_0x00f6:
            r7 = move-exception
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r7 = r7.getMessage()
            r0.<init>(r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.internal.websocket.WebSocketHandshake.sendHandshakeRequest(java.lang.String):void");
    }

    private void receiveHandshakeResponse(String str) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.input));
        ArrayList arrayList = new ArrayList();
        String readLine = bufferedReader.readLine();
        if (readLine != null) {
            while (!readLine.equals("")) {
                arrayList.add(readLine);
                readLine = bufferedReader.readLine();
            }
            Map headers = getHeaders(arrayList);
            String str2 = (String) headers.get(HTTP_HEADER_CONNECTION);
            if (str2 == null || str2.equalsIgnoreCase("upgrade")) {
                throw new IOException("WebSocket Response header: Incorrect connection header");
            }
            String str3 = (String) headers.get("upgrade");
            if (str3 == null || !str3.toLowerCase().contains(HTTP_HEADER_UPGRADE_WEBSOCKET)) {
                throw new IOException("WebSocket Response header: Incorrect upgrade.");
            } else if (((String) headers.get(HTTP_HEADER_SEC_WEBSOCKET_PROTOCOL)) == null) {
                throw new IOException("WebSocket Response header: empty sec-websocket-protocol");
            } else if (headers.containsKey(HTTP_HEADER_SEC_WEBSOCKET_ACCEPT)) {
                try {
                    verifyWebSocketKey(str, (String) headers.get(HTTP_HEADER_SEC_WEBSOCKET_ACCEPT));
                } catch (NoSuchAlgorithmException e) {
                    throw new IOException(e.getMessage());
                } catch (HandshakeFailedException unused) {
                    throw new IOException("WebSocket Response header: Incorrect Sec-WebSocket-Key");
                }
            } else {
                throw new IOException("WebSocket Response header: Missing Sec-WebSocket-Accept");
            }
        } else {
            throw new IOException("WebSocket Response header: Invalid response from Server, It may not support WebSockets.");
        }
    }

    private Map getHeaders(ArrayList arrayList) {
        HashMap hashMap = new HashMap();
        for (int i = 1; i < arrayList.size(); i++) {
            String[] split = ((String) arrayList.get(i)).split(":");
            hashMap.put(split[0].toLowerCase(), split[1]);
        }
        return hashMap;
    }

    private void verifyWebSocketKey(String str, String str2) throws NoSuchAlgorithmException, HandshakeFailedException {
        if (!Base64.encodeBytes(sha1(String.valueOf(str) + ACCEPT_SALT)).trim().equals(str2.trim())) {
            throw new HandshakeFailedException();
        }
    }

    private byte[] sha1(String str) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(SHA1_PROTOCOL).digest(str.getBytes());
    }
}
