package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.internal.FileLock;
import org.eclipse.paho.client.mqttv3.internal.MqttPersistentData;

public class MqttDefaultFilePersistence implements MqttClientPersistence {
    private static FilenameFilter FILENAME_FILTER = null;
    private static final String LOCK_FILENAME = ".lck";
    private static final String MESSAGE_BACKUP_FILE_EXTENSION = ".bup";
    private static final String MESSAGE_FILE_EXTENSION = ".msg";
    private File clientDir;
    private File dataDir;
    private FileLock fileLock;

    private static FilenameFilter getFilenameFilter() {
        if (FILENAME_FILTER == null) {
            FILENAME_FILTER = new PersistanceFileNameFilter(MESSAGE_FILE_EXTENSION);
        }
        return FILENAME_FILTER;
    }

    public MqttDefaultFilePersistence() {
        this(System.getProperty("user.dir"));
    }

    public MqttDefaultFilePersistence(String str) {
        this.clientDir = null;
        this.fileLock = null;
        this.dataDir = new File(str);
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(8:21|22|(2:24|(1:26))|27|28|29|30|31) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x0078 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void open(java.lang.String r6, java.lang.String r7) throws org.eclipse.paho.client.mqttv3.MqttPersistenceException {
        /*
            r5 = this;
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.exists()
            if (r0 == 0) goto L_0x0017
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.isDirectory()
            if (r0 == 0) goto L_0x0011
            goto L_0x0017
        L_0x0011:
            org.eclipse.paho.client.mqttv3.MqttPersistenceException r6 = new org.eclipse.paho.client.mqttv3.MqttPersistenceException
            r6.<init>()
            throw r6
        L_0x0017:
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.exists()
            if (r0 != 0) goto L_0x002e
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.mkdirs()
            if (r0 == 0) goto L_0x0028
            goto L_0x002e
        L_0x0028:
            org.eclipse.paho.client.mqttv3.MqttPersistenceException r6 = new org.eclipse.paho.client.mqttv3.MqttPersistenceException
            r6.<init>()
            throw r6
        L_0x002e:
            java.io.File r0 = r5.dataDir
            boolean r0 = r0.canWrite()
            if (r0 == 0) goto L_0x00a2
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 0
            r2 = 0
        L_0x003d:
            int r3 = r6.length()
            if (r2 < r3) goto L_0x0092
            java.lang.String r6 = "-"
            r0.append(r6)
        L_0x0048:
            int r6 = r7.length()
            if (r1 < r6) goto L_0x0082
            monitor-enter(r5)
            java.io.File r6 = r5.clientDir     // Catch:{ all -> 0x007f }
            if (r6 != 0) goto L_0x006d
            java.lang.String r6 = r0.toString()     // Catch:{ all -> 0x007f }
            java.io.File r7 = new java.io.File     // Catch:{ all -> 0x007f }
            java.io.File r0 = r5.dataDir     // Catch:{ all -> 0x007f }
            r7.<init>(r0, r6)     // Catch:{ all -> 0x007f }
            r5.clientDir = r7     // Catch:{ all -> 0x007f }
            java.io.File r6 = r5.clientDir     // Catch:{ all -> 0x007f }
            boolean r6 = r6.exists()     // Catch:{ all -> 0x007f }
            if (r6 != 0) goto L_0x006d
            java.io.File r6 = r5.clientDir     // Catch:{ all -> 0x007f }
            r6.mkdir()     // Catch:{ all -> 0x007f }
        L_0x006d:
            org.eclipse.paho.client.mqttv3.internal.FileLock r6 = new org.eclipse.paho.client.mqttv3.internal.FileLock     // Catch:{ Exception -> 0x0078 }
            java.io.File r7 = r5.clientDir     // Catch:{ Exception -> 0x0078 }
            java.lang.String r0 = ".lck"
            r6.<init>(r7, r0)     // Catch:{ Exception -> 0x0078 }
            r5.fileLock = r6     // Catch:{ Exception -> 0x0078 }
        L_0x0078:
            java.io.File r6 = r5.clientDir     // Catch:{ all -> 0x007f }
            r5.restoreBackups(r6)     // Catch:{ all -> 0x007f }
            monitor-exit(r5)     // Catch:{ all -> 0x007f }
            return
        L_0x007f:
            r6 = move-exception
            monitor-exit(r5)     // Catch:{ all -> 0x007f }
            throw r6
        L_0x0082:
            char r6 = r7.charAt(r1)
            boolean r2 = r5.isSafeChar(r6)
            if (r2 == 0) goto L_0x008f
            r0.append(r6)
        L_0x008f:
            int r1 = r1 + 1
            goto L_0x0048
        L_0x0092:
            char r3 = r6.charAt(r2)
            boolean r4 = r5.isSafeChar(r3)
            if (r4 == 0) goto L_0x009f
            r0.append(r3)
        L_0x009f:
            int r2 = r2 + 1
            goto L_0x003d
        L_0x00a2:
            org.eclipse.paho.client.mqttv3.MqttPersistenceException r6 = new org.eclipse.paho.client.mqttv3.MqttPersistenceException
            r6.<init>()
            goto L_0x00a9
        L_0x00a8:
            throw r6
        L_0x00a9:
            goto L_0x00a8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence.open(java.lang.String, java.lang.String):void");
    }

    private void checkIsOpen() throws MqttPersistenceException {
        if (this.clientDir == null) {
            throw new MqttPersistenceException();
        }
    }

    public void close() throws MqttPersistenceException {
        synchronized (this) {
            if (this.fileLock != null) {
                this.fileLock.release();
            }
            if (getFiles().length == 0) {
                this.clientDir.delete();
            }
            this.clientDir = null;
        }
    }

    public void put(String str, MqttPersistable mqttPersistable) throws MqttPersistenceException {
        checkIsOpen();
        File file = this.clientDir;
        File file2 = new File(file, String.valueOf(str) + MESSAGE_FILE_EXTENSION);
        File file3 = this.clientDir;
        File file4 = new File(file3, String.valueOf(str) + MESSAGE_FILE_EXTENSION + MESSAGE_BACKUP_FILE_EXTENSION);
        if (file2.exists() && !file2.renameTo(file4)) {
            file4.delete();
            file2.renameTo(file4);
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            fileOutputStream.write(mqttPersistable.getHeaderBytes(), mqttPersistable.getHeaderOffset(), mqttPersistable.getHeaderLength());
            if (mqttPersistable.getPayloadBytes() != null) {
                fileOutputStream.write(mqttPersistable.getPayloadBytes(), mqttPersistable.getPayloadOffset(), mqttPersistable.getPayloadLength());
            }
            fileOutputStream.getFD().sync();
            fileOutputStream.close();
            if (file4.exists()) {
                file4.delete();
            }
            if (file4.exists() && !file4.renameTo(file2)) {
                file2.delete();
                file4.renameTo(file2);
            }
        } catch (IOException e) {
            throw new MqttPersistenceException((Throwable) e);
        } catch (Throwable th) {
            if (file4.exists() && !file4.renameTo(file2)) {
                file2.delete();
                file4.renameTo(file2);
            }
            throw th;
        }
    }

    public MqttPersistable get(String str) throws MqttPersistenceException {
        checkIsOpen();
        try {
            File file = this.clientDir;
            FileInputStream fileInputStream = new FileInputStream(new File(file, String.valueOf(str) + MESSAGE_FILE_EXTENSION));
            int available = fileInputStream.available();
            byte[] bArr = new byte[available];
            for (int i = 0; i < available; i += fileInputStream.read(bArr, i, available - i)) {
            }
            fileInputStream.close();
            return new MqttPersistentData(str, bArr, 0, bArr.length, (byte[]) null, 0, 0);
        } catch (IOException e) {
            throw new MqttPersistenceException((Throwable) e);
        }
    }

    public void remove(String str) throws MqttPersistenceException {
        checkIsOpen();
        File file = this.clientDir;
        File file2 = new File(file, String.valueOf(str) + MESSAGE_FILE_EXTENSION);
        if (file2.exists()) {
            file2.delete();
        }
    }

    public Enumeration keys() throws MqttPersistenceException {
        checkIsOpen();
        File[] files = getFiles();
        Vector vector = new Vector(files.length);
        for (File name : files) {
            String name2 = name.getName();
            vector.addElement(name2.substring(0, name2.length() - 4));
        }
        return vector.elements();
    }

    private File[] getFiles() throws MqttPersistenceException {
        checkIsOpen();
        File[] listFiles = this.clientDir.listFiles(getFilenameFilter());
        if (listFiles != null) {
            return listFiles;
        }
        throw new MqttPersistenceException();
    }

    private boolean isSafeChar(char c) {
        return Character.isJavaIdentifierPart(c) || c == '-';
    }

    private void restoreBackups(File file) throws MqttPersistenceException {
        File[] listFiles = file.listFiles(new PersistanceFileFilter(MESSAGE_BACKUP_FILE_EXTENSION));
        if (listFiles != null) {
            for (int i = 0; i < listFiles.length; i++) {
                File file2 = new File(file, listFiles[i].getName().substring(0, listFiles[i].getName().length() - 4));
                if (!listFiles[i].renameTo(file2)) {
                    file2.delete();
                    listFiles[i].renameTo(file2);
                }
            }
            return;
        }
        throw new MqttPersistenceException();
    }

    public boolean containsKey(String str) throws MqttPersistenceException {
        checkIsOpen();
        File file = this.clientDir;
        return new File(file, String.valueOf(str) + MESSAGE_FILE_EXTENSION).exists();
    }

    public void clear() throws MqttPersistenceException {
        checkIsOpen();
        File[] files = getFiles();
        for (File delete : files) {
            delete.delete();
        }
        this.clientDir.delete();
    }
}
