package org.eclipse.paho.client.mqttv3.internal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileLock {
    private RandomAccessFile file;
    private Object fileLock;
    private File lockFile;

    public FileLock(File file2, String str) throws Exception {
        this.lockFile = new File(file2, str);
        if (ExceptionHelper.isClassAvailable("java.nio.channels.FileLock")) {
            try {
                this.file = new RandomAccessFile(this.lockFile, "rw");
                Object invoke = this.file.getClass().getMethod("getChannel", new Class[0]).invoke(this.file, new Object[0]);
                this.fileLock = invoke.getClass().getMethod("tryLock", new Class[0]).invoke(invoke, new Object[0]);
            } catch (NoSuchMethodException unused) {
                this.fileLock = null;
            } catch (IllegalArgumentException unused2) {
                this.fileLock = null;
            } catch (IllegalAccessException unused3) {
                this.fileLock = null;
            }
            if (this.fileLock == null) {
                release();
                throw new Exception("Problem obtaining file lock");
            }
        }
    }

    public void release() {
        try {
            if (this.fileLock != null) {
                this.fileLock.getClass().getMethod("release", new Class[0]).invoke(this.fileLock, new Object[0]);
                this.fileLock = null;
            }
        } catch (Exception unused) {
        }
        RandomAccessFile randomAccessFile = this.file;
        if (randomAccessFile != null) {
            try {
                randomAccessFile.close();
            } catch (IOException unused2) {
            }
            this.file = null;
        }
        File file2 = this.lockFile;
        if (file2 != null && file2.exists()) {
            this.lockFile.delete();
        }
        this.lockFile = null;
    }
}
