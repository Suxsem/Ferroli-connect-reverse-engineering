package org.eclipse.jetty.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

public class Slf4jLog extends AbstractLogger {
    private final Logger _logger;

    public Slf4jLog() throws Exception {
        this("org.eclipse.jetty.util.log");
    }

    public Slf4jLog(String str) {
        LocationAwareLogger logger = LoggerFactory.getLogger(str);
        if (logger instanceof LocationAwareLogger) {
            this._logger = new JettyAwareLogger(logger);
        } else {
            this._logger = logger;
        }
    }

    public String getName() {
        return this._logger.getName();
    }

    public void warn(String str, Object... objArr) {
        this._logger.warn(str, objArr);
    }

    public void warn(Throwable th) {
        warn("", th);
    }

    public void warn(String str, Throwable th) {
        this._logger.warn(str, th);
    }

    public void info(String str, Object... objArr) {
        this._logger.info(str, objArr);
    }

    public void info(Throwable th) {
        info("", th);
    }

    public void info(String str, Throwable th) {
        this._logger.info(str, th);
    }

    public void debug(String str, Object... objArr) {
        this._logger.debug(str, objArr);
    }

    public void debug(Throwable th) {
        debug("", th);
    }

    public void debug(String str, Throwable th) {
        this._logger.debug(str, th);
    }

    public boolean isDebugEnabled() {
        return this._logger.isDebugEnabled();
    }

    public void setDebugEnabled(boolean z) {
        warn("setDebugEnabled not implemented", null, null);
    }

    /* access modifiers changed from: protected */
    public Logger newLogger(String str) {
        return new Slf4jLog(str);
    }

    public void ignore(Throwable th) {
        if (Log.isIgnored()) {
            warn(Log.IGNORED, th);
        }
    }

    public String toString() {
        return this._logger.toString();
    }
}
