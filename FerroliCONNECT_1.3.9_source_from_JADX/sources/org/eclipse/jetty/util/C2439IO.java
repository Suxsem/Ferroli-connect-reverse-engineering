package org.eclipse.jetty.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.thread.QueuedThreadPool;

/* renamed from: org.eclipse.jetty.util.IO */
public class C2439IO {
    public static final String CRLF = "\r\n";
    public static final byte[] CRLF_BYTES = {13, 10};
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) C2439IO.class);
    private static ClosedIS __closedStream = new ClosedIS();
    private static PrintWriter __nullPrintWriter = new PrintWriter(__nullWriter);
    private static NullOS __nullStream = new NullOS();
    private static NullWrite __nullWriter = new NullWrite();
    public static int bufferSize = 65536;

    /* renamed from: org.eclipse.jetty.util.IO$Singleton */
    private static class Singleton {
        static final QueuedThreadPool __pool = new QueuedThreadPool();

        private Singleton() {
        }

        static {
            try {
                __pool.start();
            } catch (Exception e) {
                C2439IO.LOG.warn(e);
                System.exit(1);
            }
        }
    }

    /* renamed from: org.eclipse.jetty.util.IO$Job */
    static class Job implements Runnable {

        /* renamed from: in */
        InputStream f4112in;
        OutputStream out;
        Reader read;
        Writer write;

        Job(InputStream inputStream, OutputStream outputStream) {
            this.f4112in = inputStream;
            this.out = outputStream;
            this.read = null;
            this.write = null;
        }

        Job(Reader reader, Writer writer) {
            this.f4112in = null;
            this.out = null;
            this.read = reader;
            this.write = writer;
        }

        public void run() {
            try {
                if (this.f4112in != null) {
                    C2439IO.copy(this.f4112in, this.out, -1);
                } else {
                    C2439IO.copy(this.read, this.write, -1);
                }
            } catch (IOException e) {
                C2439IO.LOG.ignore(e);
                try {
                    if (this.out != null) {
                        this.out.close();
                    }
                    if (this.write != null) {
                        this.write.close();
                    }
                } catch (IOException e2) {
                    C2439IO.LOG.ignore(e2);
                }
            }
        }
    }

    public static void copyThread(InputStream inputStream, OutputStream outputStream) {
        try {
            Job job = new Job(inputStream, outputStream);
            if (!Singleton.__pool.dispatch(job)) {
                job.run();
            }
        } catch (Exception e) {
            LOG.warn(e);
        }
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        copy(inputStream, outputStream, -1);
    }

    public static void copyThread(Reader reader, Writer writer) {
        try {
            Job job = new Job(reader, writer);
            if (!Singleton.__pool.dispatch(job)) {
                job.run();
            }
        } catch (Exception e) {
            LOG.warn(e);
        }
    }

    public static void copy(Reader reader, Writer writer) throws IOException {
        copy(reader, writer, -1);
    }

    public static void copy(InputStream inputStream, OutputStream outputStream, long j) throws IOException {
        byte[] bArr = new byte[bufferSize];
        if (j >= 0) {
            while (j > 0) {
                int i = bufferSize;
                if (j < ((long) i)) {
                    i = (int) j;
                }
                int read = inputStream.read(bArr, 0, i);
                if (read != -1) {
                    j -= (long) read;
                    outputStream.write(bArr, 0, read);
                } else {
                    return;
                }
            }
            return;
        }
        while (true) {
            int read2 = inputStream.read(bArr, 0, bufferSize);
            if (read2 >= 0) {
                outputStream.write(bArr, 0, read2);
            } else {
                return;
            }
        }
    }

    public static void copy(Reader reader, Writer writer, long j) throws IOException {
        int read;
        int i;
        char[] cArr = new char[bufferSize];
        if (j >= 0) {
            while (j > 0) {
                int i2 = bufferSize;
                if (j < ((long) i2)) {
                    i = reader.read(cArr, 0, (int) j);
                } else {
                    i = reader.read(cArr, 0, i2);
                }
                if (i != -1) {
                    j -= (long) i;
                    writer.write(cArr, 0, i);
                } else {
                    return;
                }
            }
        } else if (writer instanceof PrintWriter) {
            PrintWriter printWriter = (PrintWriter) writer;
            while (!printWriter.checkError() && (read = reader.read(cArr, 0, bufferSize)) != -1) {
                writer.write(cArr, 0, read);
            }
        } else {
            while (true) {
                int read2 = reader.read(cArr, 0, bufferSize);
                if (read2 != -1) {
                    writer.write(cArr, 0, read2);
                } else {
                    return;
                }
            }
        }
    }

    public static void copy(File file, File file2) throws IOException {
        if (file.isDirectory()) {
            copyDir(file, file2);
        } else {
            copyFile(file, file2);
        }
    }

    public static void copyDir(File file, File file2) throws IOException {
        if (!file2.exists()) {
            file2.mkdirs();
        } else if (!file2.isDirectory()) {
            throw new IllegalArgumentException(file2.toString());
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                String name = listFiles[i].getName();
                if (!".".equals(name) && !"..".equals(name)) {
                    copy(listFiles[i], new File(file2, name));
                }
            }
        }
    }

    public static void copyFile(File file, File file2) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        copy((InputStream) fileInputStream, (OutputStream) fileOutputStream);
        fileInputStream.close();
        fileOutputStream.close();
    }

    public static String toString(InputStream inputStream) throws IOException {
        return toString(inputStream, (String) null);
    }

    public static String toString(InputStream inputStream, String str) throws IOException {
        StringWriter stringWriter = new StringWriter();
        copy((Reader) str == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, str), (Writer) stringWriter);
        return stringWriter.toString();
    }

    public static String toString(Reader reader) throws IOException {
        StringWriter stringWriter = new StringWriter();
        copy(reader, (Writer) stringWriter);
        return stringWriter.toString();
    }

    public static boolean delete(File file) {
        int i = 0;
        if (!file.exists()) {
            return false;
        }
        if (file.isDirectory()) {
            File[] listFiles = file.listFiles();
            while (listFiles != null && i < listFiles.length) {
                delete(listFiles[i]);
                i++;
            }
        }
        return file.delete();
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                LOG.ignore(e);
            }
        }
    }

    public static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                LOG.ignore(e);
            }
        }
    }

    public static void close(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                LOG.ignore(e);
            }
        }
    }

    public static void close(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                LOG.ignore(e);
            }
        }
    }

    public static byte[] readBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        copy(inputStream, (OutputStream) byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public static void close(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                LOG.ignore(e);
            }
        }
    }

    public static OutputStream getNullStream() {
        return __nullStream;
    }

    public static InputStream getClosedStream() {
        return __closedStream;
    }

    /* renamed from: org.eclipse.jetty.util.IO$NullOS */
    private static class NullOS extends OutputStream {
        public void close() {
        }

        public void flush() {
        }

        public void write(int i) {
        }

        public void write(byte[] bArr) {
        }

        public void write(byte[] bArr, int i, int i2) {
        }

        private NullOS() {
        }
    }

    /* renamed from: org.eclipse.jetty.util.IO$ClosedIS */
    private static class ClosedIS extends InputStream {
        public int read() throws IOException {
            return -1;
        }

        private ClosedIS() {
        }
    }

    public static Writer getNullWriter() {
        return __nullWriter;
    }

    public static PrintWriter getNullPrintWriter() {
        return __nullPrintWriter;
    }

    /* renamed from: org.eclipse.jetty.util.IO$NullWrite */
    private static class NullWrite extends Writer {
        public void close() {
        }

        public void flush() {
        }

        public void write(int i) {
        }

        public void write(String str) {
        }

        public void write(String str, int i, int i2) {
        }

        public void write(char[] cArr) {
        }

        public void write(char[] cArr, int i, int i2) {
        }

        private NullWrite() {
        }
    }
}
