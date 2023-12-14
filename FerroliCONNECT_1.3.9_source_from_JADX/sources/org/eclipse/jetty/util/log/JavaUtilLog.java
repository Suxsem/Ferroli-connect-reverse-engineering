package org.eclipse.jetty.util.log;

import anetwork.channel.util.RequestConstant;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaUtilLog extends AbstractLogger {
    private Logger _logger;
    private Level configuredLevel;

    public JavaUtilLog() {
        this("org.eclipse.jetty.util.log");
    }

    public JavaUtilLog(String str) {
        this._logger = Logger.getLogger(str);
        if (Boolean.parseBoolean(Log.__props.getProperty("org.eclipse.jetty.util.log.DEBUG", RequestConstant.FALSE))) {
            this._logger.setLevel(Level.FINE);
        }
        this.configuredLevel = this._logger.getLevel();
    }

    public String getName() {
        return this._logger.getName();
    }

    public void warn(String str, Object... objArr) {
        this._logger.log(Level.WARNING, format(str, objArr));
    }

    public void warn(Throwable th) {
        warn("", th);
    }

    public void warn(String str, Throwable th) {
        this._logger.log(Level.WARNING, str, th);
    }

    public void info(String str, Object... objArr) {
        this._logger.log(Level.INFO, format(str, objArr));
    }

    public void info(Throwable th) {
        info("", th);
    }

    public void info(String str, Throwable th) {
        this._logger.log(Level.INFO, str, th);
    }

    public boolean isDebugEnabled() {
        return this._logger.isLoggable(Level.FINE);
    }

    public void setDebugEnabled(boolean z) {
        if (z) {
            this.configuredLevel = this._logger.getLevel();
            this._logger.setLevel(Level.FINE);
            return;
        }
        this._logger.setLevel(this.configuredLevel);
    }

    public void debug(String str, Object... objArr) {
        this._logger.log(Level.FINE, format(str, objArr));
    }

    public void debug(Throwable th) {
        debug("", th);
    }

    public void debug(String str, Throwable th) {
        this._logger.log(Level.FINE, str, th);
    }

    /* access modifiers changed from: protected */
    public Logger newLogger(String str) {
        return new JavaUtilLog(str);
    }

    public void ignore(Throwable th) {
        if (Log.isIgnored()) {
            warn(Log.IGNORED, th);
        }
    }

    private String format(String str, Object... objArr) {
        String valueOf = String.valueOf(str);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (Object obj : objArr) {
            int indexOf = valueOf.indexOf("{}", i);
            if (indexOf < 0) {
                sb.append(valueOf.substring(i));
                sb.append(" ");
                sb.append(obj);
                i = valueOf.length();
            } else {
                sb.append(valueOf.substring(i, indexOf));
                sb.append(String.valueOf(obj));
                i = indexOf + 2;
            }
        }
        sb.append(valueOf.substring(i));
        return sb.toString();
    }
}
