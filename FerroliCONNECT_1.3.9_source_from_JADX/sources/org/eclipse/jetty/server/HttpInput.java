package org.eclipse.jetty.server;

import java.io.IOException;
import javax.servlet.ServletInputStream;
import kotlin.UByte;
import org.eclipse.jetty.http.HttpParser;
import org.eclipse.jetty.p119io.Buffer;
import org.eclipse.jetty.p119io.EofException;

public class HttpInput extends ServletInputStream {
    protected final AbstractHttpConnection _connection;
    protected final HttpParser _parser;

    public HttpInput(AbstractHttpConnection abstractHttpConnection) {
        this._connection = abstractHttpConnection;
        this._parser = (HttpParser) abstractHttpConnection.getParser();
    }

    public int read() throws IOException {
        byte[] bArr = new byte[1];
        if (read(bArr, 0, 1) < 0) {
            return -1;
        }
        return bArr[0] & UByte.MAX_VALUE;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        Buffer blockForContent = this._parser.blockForContent((long) this._connection.getMaxIdleTime());
        if (blockForContent != null) {
            return blockForContent.get(bArr, i, i2);
        }
        if (!this._connection.isEarlyEOF()) {
            return -1;
        }
        throw new EofException("early EOF");
    }

    public int available() throws IOException {
        return this._parser.available();
    }
}
