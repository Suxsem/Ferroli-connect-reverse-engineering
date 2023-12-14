package org.eclipse.jetty.p119io.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import org.eclipse.jetty.p119io.AbstractBuffer;
import org.eclipse.jetty.p119io.Buffer;

/* renamed from: org.eclipse.jetty.io.nio.RandomAccessFileBuffer */
public class RandomAccessFileBuffer extends AbstractBuffer implements Buffer {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    final int _capacity;
    final FileChannel _channel;
    final RandomAccessFile _file;

    public byte[] array() {
        return null;
    }

    public RandomAccessFileBuffer(File file) throws FileNotFoundException {
        super(2, true);
        this._file = new RandomAccessFile(file, "rw");
        this._channel = this._file.getChannel();
        this._capacity = Integer.MAX_VALUE;
        setGetIndex(0);
        setPutIndex((int) file.length());
    }

    public RandomAccessFileBuffer(File file, int i) throws FileNotFoundException {
        super(2, true);
        this._capacity = i;
        this._file = new RandomAccessFile(file, "rw");
        this._channel = this._file.getChannel();
        setGetIndex(0);
        setPutIndex((int) file.length());
    }

    public RandomAccessFileBuffer(File file, int i, int i2) throws FileNotFoundException {
        super(i2, true);
        this._capacity = i;
        this._file = new RandomAccessFile(file, i2 == 2 ? "rw" : "r");
        this._channel = this._file.getChannel();
        setGetIndex(0);
        setPutIndex((int) file.length());
    }

    public int capacity() {
        return this._capacity;
    }

    public void clear() {
        try {
            synchronized (this._file) {
                super.clear();
                this._file.setLength(0);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public byte peek() {
        byte readByte;
        synchronized (this._file) {
            try {
                if (((long) this._get) != this._file.getFilePointer()) {
                    this._file.seek((long) this._get);
                }
                readByte = this._file.readByte();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                throw th;
            }
        }
        return readByte;
    }

    public byte peek(int i) {
        byte readByte;
        synchronized (this._file) {
            try {
                this._file.seek((long) i);
                readByte = this._file.readByte();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                throw th;
            }
        }
        return readByte;
    }

    public int peek(int i, byte[] bArr, int i2, int i3) {
        int read;
        synchronized (this._file) {
            try {
                this._file.seek((long) i);
                read = this._file.read(bArr, i2, i3);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                throw th;
            }
        }
        return read;
    }

    public void poke(int i, byte b) {
        synchronized (this._file) {
            try {
                this._file.seek((long) i);
                this._file.writeByte(b);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public int poke(int i, byte[] bArr, int i2, int i3) {
        synchronized (this._file) {
            try {
                this._file.seek((long) i);
                this._file.write(bArr, i2, i3);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } catch (Throwable th) {
                throw th;
            }
        }
        return i3;
    }

    public int writeTo(WritableByteChannel writableByteChannel, int i, int i2) throws IOException {
        int transferTo;
        synchronized (this._file) {
            transferTo = (int) this._channel.transferTo((long) i, (long) i2, writableByteChannel);
        }
        return transferTo;
    }
}
