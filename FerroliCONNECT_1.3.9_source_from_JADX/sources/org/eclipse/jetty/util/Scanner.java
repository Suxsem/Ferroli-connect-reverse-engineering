package org.eclipse.jetty.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class Scanner extends AbstractLifeCycle {
    private static final Logger LOG = Log.getLogger((Class<?>) Scanner.class);
    private static int __scannerId = 0;
    private final Map<String, TimeNSize> _currentScan = new HashMap();
    private FilenameFilter _filter;
    private final List<Listener> _listeners = new ArrayList();
    private final Map<String, Notification> _notifications = new HashMap();
    private final Map<String, TimeNSize> _prevScan = new HashMap();
    private boolean _reportDirs = true;
    private boolean _reportExisting = true;
    private volatile boolean _running = false;
    private int _scanCount = 0;
    private int _scanDepth = 0;
    private final List<File> _scanDirs = new ArrayList();
    private int _scanInterval;
    private TimerTask _task;
    private Timer _timer;

    public interface BulkListener extends Listener {
        void filesChanged(List<String> list) throws Exception;
    }

    public interface DiscreteListener extends Listener {
        void fileAdded(String str) throws Exception;

        void fileChanged(String str) throws Exception;

        void fileRemoved(String str) throws Exception;
    }

    public interface Listener {
    }

    public enum Notification {
        ADDED,
        CHANGED,
        REMOVED
    }

    public interface ScanCycleListener extends Listener {
        void scanEnded(int i) throws Exception;

        void scanStarted(int i) throws Exception;
    }

    public interface ScanListener extends Listener {
        void scan();
    }

    static class TimeNSize {
        final long _lastModified;
        final long _size;

        public TimeNSize(long j, long j2) {
            this._lastModified = j;
            this._size = j2;
        }

        public int hashCode() {
            return ((int) this._size) ^ ((int) this._lastModified);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof TimeNSize)) {
                return false;
            }
            TimeNSize timeNSize = (TimeNSize) obj;
            if (timeNSize._lastModified == this._lastModified && timeNSize._size == this._size) {
                return true;
            }
            return false;
        }

        public String toString() {
            return "[lm=" + this._lastModified + ",s=" + this._size + "]";
        }
    }

    public int getScanInterval() {
        return this._scanInterval;
    }

    public synchronized void setScanInterval(int i) {
        this._scanInterval = i;
        schedule();
    }

    @Deprecated
    public void setScanDir(File file) {
        this._scanDirs.clear();
        this._scanDirs.add(file);
    }

    @Deprecated
    public File getScanDir() {
        List<File> list = this._scanDirs;
        if (list == null) {
            return null;
        }
        return list.get(0);
    }

    public void setScanDirs(List<File> list) {
        this._scanDirs.clear();
        this._scanDirs.addAll(list);
    }

    public synchronized void addScanDir(File file) {
        this._scanDirs.add(file);
    }

    public List<File> getScanDirs() {
        return Collections.unmodifiableList(this._scanDirs);
    }

    public void setRecursive(boolean z) {
        this._scanDepth = z ? -1 : 0;
    }

    public boolean getRecursive() {
        return this._scanDepth == -1;
    }

    public int getScanDepth() {
        return this._scanDepth;
    }

    public void setScanDepth(int i) {
        this._scanDepth = i;
    }

    public void setFilenameFilter(FilenameFilter filenameFilter) {
        this._filter = filenameFilter;
    }

    public FilenameFilter getFilenameFilter() {
        return this._filter;
    }

    public void setReportExistingFilesOnStartup(boolean z) {
        this._reportExisting = z;
    }

    public boolean getReportExistingFilesOnStartup() {
        return this._reportExisting;
    }

    public void setReportDirs(boolean z) {
        this._reportDirs = z;
    }

    public boolean getReportDirs() {
        return this._reportDirs;
    }

    public synchronized void addListener(Listener listener) {
        if (listener != null) {
            this._listeners.add(listener);
        }
    }

    public synchronized void removeListener(Listener listener) {
        if (listener != null) {
            this._listeners.remove(listener);
        }
    }

    public synchronized void doStart() {
        if (!this._running) {
            this._running = true;
            if (this._reportExisting) {
                scan();
                scan();
            } else {
                scanFiles();
                this._prevScan.putAll(this._currentScan);
            }
            schedule();
        }
    }

    public TimerTask newTimerTask() {
        return new TimerTask() {
            public void run() {
                Scanner.this.scan();
            }
        };
    }

    public Timer newTimer() {
        StringBuilder sb = new StringBuilder();
        sb.append("Scanner-");
        int i = __scannerId;
        __scannerId = i + 1;
        sb.append(i);
        return new Timer(sb.toString(), true);
    }

    public void schedule() {
        if (this._running) {
            Timer timer = this._timer;
            if (timer != null) {
                timer.cancel();
            }
            TimerTask timerTask = this._task;
            if (timerTask != null) {
                timerTask.cancel();
            }
            if (getScanInterval() > 0) {
                this._timer = newTimer();
                this._task = newTimerTask();
                this._timer.schedule(this._task, ((long) getScanInterval()) * 1010, 1010 * ((long) getScanInterval()));
            }
        }
    }

    public synchronized void doStop() {
        if (this._running) {
            this._running = false;
            if (this._timer != null) {
                this._timer.cancel();
            }
            if (this._task != null) {
                this._task.cancel();
            }
            this._task = null;
            this._timer = null;
        }
    }

    public synchronized void scan() {
        int i = this._scanCount + 1;
        this._scanCount = i;
        reportScanStart(i);
        scanFiles();
        reportDifferences(this._currentScan, this._prevScan);
        this._prevScan.clear();
        this._prevScan.putAll(this._currentScan);
        reportScanEnd(this._scanCount);
        for (Listener next : this._listeners) {
            try {
                if (next instanceof ScanListener) {
                    ((ScanListener) next).scan();
                }
            } catch (Exception e) {
                LOG.warn(e);
            } catch (Error e2) {
                LOG.warn(e2);
            }
        }
    }

    public synchronized void scanFiles() {
        if (this._scanDirs != null) {
            this._currentScan.clear();
            for (File next : this._scanDirs) {
                if (next != null && next.exists()) {
                    try {
                        scanFile(next.getCanonicalFile(), this._currentScan, 0);
                    } catch (IOException e) {
                        LOG.warn("Error scanning files.", (Throwable) e);
                    }
                }
            }
        }
    }

    public synchronized void reportDifferences(Map<String, TimeNSize> map, Map<String, TimeNSize> map2) {
        Notification put;
        Notification put2;
        HashSet hashSet = new HashSet(map2.keySet());
        for (Map.Entry<String, TimeNSize> key : map.entrySet()) {
            String str = (String) key.getKey();
            if (!hashSet.contains(str)) {
                Notification put3 = this._notifications.put(str, Notification.ADDED);
                if (put3 != null) {
                    int i = C24452.$SwitchMap$org$eclipse$jetty$util$Scanner$Notification[put3.ordinal()];
                    if (i == 1 || i == 2) {
                        this._notifications.put(str, Notification.CHANGED);
                    }
                }
            } else if (!map2.get(str).equals(map.get(str)) && (put2 = this._notifications.put(str, Notification.CHANGED)) != null) {
                if (C24452.$SwitchMap$org$eclipse$jetty$util$Scanner$Notification[put2.ordinal()] == 3) {
                    this._notifications.put(str, Notification.ADDED);
                }
            }
        }
        for (String next : map2.keySet()) {
            if (!map.containsKey(next) && (put = this._notifications.put(next, Notification.REMOVED)) != null) {
                if (C24452.$SwitchMap$org$eclipse$jetty$util$Scanner$Notification[put.ordinal()] == 3) {
                    this._notifications.remove(next);
                }
            }
        }
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("scanned " + this._scanDirs + ": " + this._notifications, new Object[0]);
        }
        ArrayList arrayList = new ArrayList();
        Iterator<Map.Entry<String, Notification>> it = this._notifications.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry next2 = it.next();
            String str2 = (String) next2.getKey();
            if (map2.containsKey(str2)) {
                if (!map2.get(str2).equals(map.get(str2))) {
                }
            } else if (map.containsKey(str2)) {
            }
            it.remove();
            arrayList.add(str2);
            int i2 = C24452.$SwitchMap$org$eclipse$jetty$util$Scanner$Notification[((Notification) next2.getValue()).ordinal()];
            if (i2 == 1) {
                reportRemoval(str2);
            } else if (i2 == 2) {
                reportChange(str2);
            } else if (i2 == 3) {
                reportAddition(str2);
            }
        }
        if (!arrayList.isEmpty()) {
            reportBulkChanges(arrayList);
        }
    }

    /* renamed from: org.eclipse.jetty.util.Scanner$2 */
    static /* synthetic */ class C24452 {
        static final /* synthetic */ int[] $SwitchMap$org$eclipse$jetty$util$Scanner$Notification = new int[Notification.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                org.eclipse.jetty.util.Scanner$Notification[] r0 = org.eclipse.jetty.util.Scanner.Notification.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$eclipse$jetty$util$Scanner$Notification = r0
                int[] r0 = $SwitchMap$org$eclipse$jetty$util$Scanner$Notification     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.eclipse.jetty.util.Scanner$Notification r1 = org.eclipse.jetty.util.Scanner.Notification.REMOVED     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$eclipse$jetty$util$Scanner$Notification     // Catch:{ NoSuchFieldError -> 0x001f }
                org.eclipse.jetty.util.Scanner$Notification r1 = org.eclipse.jetty.util.Scanner.Notification.CHANGED     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$eclipse$jetty$util$Scanner$Notification     // Catch:{ NoSuchFieldError -> 0x002a }
                org.eclipse.jetty.util.Scanner$Notification r1 = org.eclipse.jetty.util.Scanner.Notification.ADDED     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.Scanner.C24452.<clinit>():void");
        }
    }

    private void scanFile(File file, Map<String, TimeNSize> map, int i) {
        try {
            if (file.exists()) {
                if ((file.isFile() || (i > 0 && this._reportDirs && file.isDirectory())) && (this._filter == null || (this._filter != null && this._filter.accept(file.getParentFile(), file.getName())))) {
                    map.put(file.getCanonicalPath(), new TimeNSize(file.lastModified(), file.length()));
                }
                if (!file.isDirectory()) {
                    return;
                }
                if (i < this._scanDepth || this._scanDepth == -1 || this._scanDirs.contains(file)) {
                    File[] listFiles = file.listFiles();
                    if (listFiles != null) {
                        for (File scanFile : listFiles) {
                            scanFile(scanFile, map, i + 1);
                        }
                        return;
                    }
                    LOG.warn("Error listing files in directory {}", file);
                }
            }
        } catch (IOException e) {
            LOG.warn("Error scanning watched files", (Throwable) e);
        }
    }

    private void warn(Object obj, String str, Throwable th) {
        Logger logger = LOG;
        logger.warn(obj + " failed on '" + str, th);
    }

    private void reportAddition(String str) {
        for (Listener next : this._listeners) {
            try {
                if (next instanceof DiscreteListener) {
                    ((DiscreteListener) next).fileAdded(str);
                }
            } catch (Exception e) {
                warn(next, str, e);
            } catch (Error e2) {
                warn(next, str, e2);
            }
        }
    }

    private void reportRemoval(String str) {
        for (Listener next : this._listeners) {
            try {
                if (next instanceof DiscreteListener) {
                    ((DiscreteListener) next).fileRemoved(str);
                }
            } catch (Exception e) {
                warn(next, str, e);
            } catch (Error e2) {
                warn(next, str, e2);
            }
        }
    }

    private void reportChange(String str) {
        for (Listener next : this._listeners) {
            try {
                if (next instanceof DiscreteListener) {
                    ((DiscreteListener) next).fileChanged(str);
                }
            } catch (Exception e) {
                warn(next, str, e);
            } catch (Error e2) {
                warn(next, str, e2);
            }
        }
    }

    private void reportBulkChanges(List<String> list) {
        for (Listener next : this._listeners) {
            try {
                if (next instanceof BulkListener) {
                    ((BulkListener) next).filesChanged(list);
                }
            } catch (Exception e) {
                warn(next, list.toString(), e);
            } catch (Error e2) {
                warn(next, list.toString(), e2);
            }
        }
    }

    private void reportScanStart(int i) {
        for (Listener next : this._listeners) {
            try {
                if (next instanceof ScanCycleListener) {
                    ((ScanCycleListener) next).scanStarted(i);
                }
            } catch (Exception e) {
                Logger logger = LOG;
                logger.warn(next + " failed on scan start for cycle " + i, (Throwable) e);
            }
        }
    }

    private void reportScanEnd(int i) {
        for (Listener next : this._listeners) {
            try {
                if (next instanceof ScanCycleListener) {
                    ((ScanCycleListener) next).scanEnded(i);
                }
            } catch (Exception e) {
                Logger logger = LOG;
                logger.warn(next + " failed on scan end for cycle " + i, (Throwable) e);
            }
        }
    }
}
