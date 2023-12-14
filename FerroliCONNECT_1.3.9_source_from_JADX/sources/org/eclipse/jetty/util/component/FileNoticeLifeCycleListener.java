package org.eclipse.jetty.util.component;

import java.io.FileWriter;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class FileNoticeLifeCycleListener implements LifeCycle.Listener {
    Logger LOG = Log.getLogger((Class<?>) FileNoticeLifeCycleListener.class);
    private final String _filename;

    public FileNoticeLifeCycleListener(String str) {
        this._filename = str;
    }

    private void writeState(String str, LifeCycle lifeCycle) {
        try {
            FileWriter fileWriter = new FileWriter(this._filename, true);
            fileWriter.append(str).append(" ").append(lifeCycle.toString()).append("\n");
            fileWriter.close();
        } catch (Exception e) {
            this.LOG.warn(e);
        }
    }

    public void lifeCycleStarting(LifeCycle lifeCycle) {
        writeState(AbstractLifeCycle.STARTING, lifeCycle);
    }

    public void lifeCycleStarted(LifeCycle lifeCycle) {
        writeState(AbstractLifeCycle.STARTED, lifeCycle);
    }

    public void lifeCycleFailure(LifeCycle lifeCycle, Throwable th) {
        writeState(AbstractLifeCycle.FAILED, lifeCycle);
    }

    public void lifeCycleStopping(LifeCycle lifeCycle) {
        writeState(AbstractLifeCycle.STOPPING, lifeCycle);
    }

    public void lifeCycleStopped(LifeCycle lifeCycle) {
        writeState(AbstractLifeCycle.STOPPED, lifeCycle);
    }
}
