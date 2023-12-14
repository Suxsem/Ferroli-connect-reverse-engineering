package org.eclipse.jetty.p119io;

import anetwork.channel.util.RequestConstant;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/* renamed from: org.eclipse.jetty.io.UncheckedPrintWriter */
public class UncheckedPrintWriter extends PrintWriter {
    private static final Logger LOG = Log.getLogger((Class<?>) UncheckedPrintWriter.class);
    private boolean _autoFlush;
    private IOException _ioException;
    private boolean _isClosed;
    private String _lineSeparator;

    public UncheckedPrintWriter(Writer writer) {
        this(writer, false);
    }

    public UncheckedPrintWriter(Writer writer, boolean z) {
        super(writer, z);
        this._autoFlush = false;
        this._isClosed = false;
        this._autoFlush = z;
        this._lineSeparator = System.getProperty("line.separator");
    }

    public UncheckedPrintWriter(OutputStream outputStream) {
        this(outputStream, false);
    }

    public UncheckedPrintWriter(OutputStream outputStream, boolean z) {
        this((Writer) new BufferedWriter(new OutputStreamWriter(outputStream)), z);
    }

    public boolean checkError() {
        return this._ioException != null || super.checkError();
    }

    private void setError(Throwable th) {
        super.setError();
        if (th instanceof IOException) {
            this._ioException = (IOException) th;
        } else {
            this._ioException = new IOException(String.valueOf(th));
            this._ioException.initCause(th);
        }
        LOG.debug(th);
    }

    /* access modifiers changed from: protected */
    public void setError() {
        setError(new IOException());
    }

    private void isOpen() throws IOException {
        IOException iOException = this._ioException;
        if (iOException != null) {
            throw new RuntimeIOException((Throwable) iOException);
        } else if (this._isClosed) {
            throw new IOException("Stream closed");
        }
    }

    public void flush() {
        try {
            synchronized (this.lock) {
                isOpen();
                this.out.flush();
            }
        } catch (IOException e) {
            setError(e);
        }
    }

    public void close() {
        try {
            synchronized (this.lock) {
                this.out.close();
                this._isClosed = true;
            }
        } catch (IOException e) {
            setError(e);
        }
    }

    public void write(int i) {
        try {
            synchronized (this.lock) {
                isOpen();
                this.out.write(i);
            }
        } catch (InterruptedIOException unused) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            setError(e);
        }
    }

    public void write(char[] cArr, int i, int i2) {
        try {
            synchronized (this.lock) {
                isOpen();
                this.out.write(cArr, i, i2);
            }
        } catch (InterruptedIOException unused) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            setError(e);
        }
    }

    public void write(char[] cArr) {
        write(cArr, 0, cArr.length);
    }

    public void write(String str, int i, int i2) {
        try {
            synchronized (this.lock) {
                isOpen();
                this.out.write(str, i, i2);
            }
        } catch (InterruptedIOException unused) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            setError(e);
        }
    }

    public void write(String str) {
        write(str, 0, str.length());
    }

    private void newLine() {
        try {
            synchronized (this.lock) {
                isOpen();
                this.out.write(this._lineSeparator);
                if (this._autoFlush) {
                    this.out.flush();
                }
            }
        } catch (InterruptedIOException unused) {
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            setError(e);
        }
    }

    public void print(boolean z) {
        write(z ? RequestConstant.TRUE : RequestConstant.FALSE);
    }

    public void print(char c) {
        write((int) c);
    }

    public void print(int i) {
        write(String.valueOf(i));
    }

    public void print(long j) {
        write(String.valueOf(j));
    }

    public void print(float f) {
        write(String.valueOf(f));
    }

    public void print(double d) {
        write(String.valueOf(d));
    }

    public void print(char[] cArr) {
        write(cArr);
    }

    public void print(String str) {
        if (str == null) {
            str = "null";
        }
        write(str);
    }

    public void print(Object obj) {
        write(String.valueOf(obj));
    }

    public void println() {
        newLine();
    }

    public void println(boolean z) {
        synchronized (this.lock) {
            print(z);
            println();
        }
    }

    public void println(char c) {
        synchronized (this.lock) {
            print(c);
            println();
        }
    }

    public void println(int i) {
        synchronized (this.lock) {
            print(i);
            println();
        }
    }

    public void println(long j) {
        synchronized (this.lock) {
            print(j);
            println();
        }
    }

    public void println(float f) {
        synchronized (this.lock) {
            print(f);
            println();
        }
    }

    public void println(double d) {
        synchronized (this.lock) {
            print(d);
            println();
        }
    }

    public void println(char[] cArr) {
        synchronized (this.lock) {
            print(cArr);
            println();
        }
    }

    public void println(String str) {
        synchronized (this.lock) {
            print(str);
            println();
        }
    }

    public void println(Object obj) {
        synchronized (this.lock) {
            print(obj);
            println();
        }
    }
}
