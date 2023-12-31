package org.eclipse.jetty.server;

import com.alibaba.sdk.android.tbrest.rest.RestConstants;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.eclipse.jetty.p119io.ByteArrayBuffer;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class LocalConnector extends AbstractConnector {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) LocalConnector.class);
    private final BlockingQueue<Request> _requests = new LinkedBlockingQueue();

    public void close() throws IOException {
    }

    public Object getConnection() {
        return this;
    }

    public int getLocalPort() {
        return -1;
    }

    public void open() throws IOException {
    }

    public LocalConnector() {
        setMaxIdleTime(RestConstants.G_MAX_READ_CONNECTION_STREAM_TIME_OUT);
    }

    public String getResponses(String str) throws Exception {
        return getResponses(str, false);
    }

    public String getResponses(String str, boolean z) throws Exception {
        ByteArrayBuffer responses = getResponses(new ByteArrayBuffer(str, StringUtil.__ISO_8859_1), z);
        if (responses == null) {
            return null;
        }
        return responses.toString(StringUtil.__ISO_8859_1);
    }

    public ByteArrayBuffer getResponses(ByteArrayBuffer byteArrayBuffer, boolean z) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Request request = new Request(byteArrayBuffer, z, countDownLatch);
        this._requests.add(request);
        countDownLatch.await((long) getMaxIdleTime(), TimeUnit.MILLISECONDS);
        return request.getResponsesBuffer();
    }

    /* access modifiers changed from: protected */
    public void accept(int i) throws IOException, InterruptedException {
        getThreadPool().dispatch(this._requests.take());
    }

    public void executeRequest(String str) throws IOException {
        this._requests.add(new Request(new ByteArrayBuffer(str, "UTF-8"), true, (CountDownLatch) null));
    }

    private class Request implements Runnable {
        private final boolean _keepOpen;
        private final CountDownLatch _latch;
        private final ByteArrayBuffer _requestsBuffer;
        private volatile ByteArrayBuffer _responsesBuffer;

        private Request(ByteArrayBuffer byteArrayBuffer, boolean z, CountDownLatch countDownLatch) {
            this._requestsBuffer = byteArrayBuffer;
            this._keepOpen = z;
            this._latch = countDownLatch;
        }

        /* JADX WARNING: Removed duplicated region for block: B:7:0x0038 A[Catch:{ IOException -> 0x0068, Exception -> 0x0056 }, LOOP:1: B:7:0x0038->B:9:0x0042, LOOP_START] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r5 = this;
                org.eclipse.jetty.server.LocalConnector$Request$1 r0 = new org.eclipse.jetty.server.LocalConnector$Request$1     // Catch:{ all -> 0x0090 }
                org.eclipse.jetty.io.ByteArrayBuffer r1 = r5._requestsBuffer     // Catch:{ all -> 0x0090 }
                byte[] r1 = r1.asArray()     // Catch:{ all -> 0x0090 }
                r2 = 1024(0x400, float:1.435E-42)
                r0.<init>(r1, r2)     // Catch:{ all -> 0x0090 }
                r1 = 1
                r0.setGrowOutput(r1)     // Catch:{ all -> 0x0090 }
                org.eclipse.jetty.server.BlockingHttpConnection r1 = new org.eclipse.jetty.server.BlockingHttpConnection     // Catch:{ all -> 0x0090 }
                org.eclipse.jetty.server.LocalConnector r2 = org.eclipse.jetty.server.LocalConnector.this     // Catch:{ all -> 0x0090 }
                org.eclipse.jetty.server.LocalConnector r3 = org.eclipse.jetty.server.LocalConnector.this     // Catch:{ all -> 0x0090 }
                org.eclipse.jetty.server.Server r3 = r3.getServer()     // Catch:{ all -> 0x0090 }
                r1.<init>(r2, r0, r3)     // Catch:{ all -> 0x0090 }
                r0.setConnection(r1)     // Catch:{ all -> 0x0090 }
                org.eclipse.jetty.server.LocalConnector r2 = org.eclipse.jetty.server.LocalConnector.this     // Catch:{ all -> 0x0090 }
                r2.connectionOpened(r1)     // Catch:{ all -> 0x0090 }
                boolean r2 = r5._keepOpen     // Catch:{ all -> 0x0090 }
            L_0x0028:
                org.eclipse.jetty.io.ByteArrayBuffer r3 = r0.getIn()     // Catch:{ IOException -> 0x0068, Exception -> 0x0056 }
                int r3 = r3.length()     // Catch:{ IOException -> 0x0068, Exception -> 0x0056 }
                if (r3 <= 0) goto L_0x0046
                boolean r3 = r0.isOpen()     // Catch:{ IOException -> 0x0068, Exception -> 0x0056 }
                if (r3 == 0) goto L_0x0046
            L_0x0038:
                org.eclipse.jetty.io.Connection r3 = r0.getConnection()     // Catch:{ IOException -> 0x0068, Exception -> 0x0056 }
                org.eclipse.jetty.io.Connection r4 = r3.handle()     // Catch:{ IOException -> 0x0068, Exception -> 0x0056 }
                if (r4 == r3) goto L_0x0028
                r0.setConnection(r4)     // Catch:{ IOException -> 0x0068, Exception -> 0x0056 }
                goto L_0x0038
            L_0x0046:
                if (r2 != 0) goto L_0x004d
                org.eclipse.jetty.server.LocalConnector r2 = org.eclipse.jetty.server.LocalConnector.this     // Catch:{ all -> 0x0090 }
                r2.connectionClosed(r1)     // Catch:{ all -> 0x0090 }
            L_0x004d:
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r0.getOut()     // Catch:{ all -> 0x0090 }
            L_0x0051:
                r5._responsesBuffer = r0     // Catch:{ all -> 0x0090 }
                goto L_0x007a
            L_0x0054:
                r3 = move-exception
                goto L_0x0082
            L_0x0056:
                r3 = move-exception
                org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.server.LocalConnector.LOG     // Catch:{ all -> 0x0054 }
                r4.warn(r3)     // Catch:{ all -> 0x0054 }
                org.eclipse.jetty.server.LocalConnector r2 = org.eclipse.jetty.server.LocalConnector.this     // Catch:{ all -> 0x0090 }
                r2.connectionClosed(r1)     // Catch:{ all -> 0x0090 }
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r0.getOut()     // Catch:{ all -> 0x0090 }
                goto L_0x0051
            L_0x0068:
                r3 = move-exception
                org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.server.LocalConnector.LOG     // Catch:{ all -> 0x0054 }
                r4.debug(r3)     // Catch:{ all -> 0x0054 }
                org.eclipse.jetty.server.LocalConnector r2 = org.eclipse.jetty.server.LocalConnector.this     // Catch:{ all -> 0x0090 }
                r2.connectionClosed(r1)     // Catch:{ all -> 0x0090 }
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r0.getOut()     // Catch:{ all -> 0x0090 }
                goto L_0x0051
            L_0x007a:
                java.util.concurrent.CountDownLatch r0 = r5._latch
                if (r0 == 0) goto L_0x0081
                r0.countDown()
            L_0x0081:
                return
            L_0x0082:
                if (r2 != 0) goto L_0x0089
                org.eclipse.jetty.server.LocalConnector r2 = org.eclipse.jetty.server.LocalConnector.this     // Catch:{ all -> 0x0090 }
                r2.connectionClosed(r1)     // Catch:{ all -> 0x0090 }
            L_0x0089:
                org.eclipse.jetty.io.ByteArrayBuffer r0 = r0.getOut()     // Catch:{ all -> 0x0090 }
                r5._responsesBuffer = r0     // Catch:{ all -> 0x0090 }
                throw r3     // Catch:{ all -> 0x0090 }
            L_0x0090:
                r0 = move-exception
                java.util.concurrent.CountDownLatch r1 = r5._latch
                if (r1 == 0) goto L_0x0098
                r1.countDown()
            L_0x0098:
                goto L_0x009a
            L_0x0099:
                throw r0
            L_0x009a:
                goto L_0x0099
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.LocalConnector.Request.run():void");
        }

        public ByteArrayBuffer getResponsesBuffer() {
            return this._responsesBuffer;
        }
    }
}
