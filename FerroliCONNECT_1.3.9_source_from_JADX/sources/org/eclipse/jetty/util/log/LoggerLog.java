package org.eclipse.jetty.util.log;

import java.lang.reflect.Method;

public class LoggerLog extends AbstractLogger {
    private volatile boolean _debug;
    private final Method _debugMAA;
    private final Method _debugMT;
    private final Method _getLoggerN;
    private final Method _getName;
    private final Method _infoMAA;
    private final Method _infoMT;
    private final Object _logger;
    private final Method _setDebugEnabledE;
    private final Method _warnMAA;
    private final Method _warnMT;

    public LoggerLog(Object obj) {
        try {
            this._logger = obj;
            Class<?> cls = obj.getClass();
            this._debugMT = cls.getMethod(MqttServiceConstants.TRACE_DEBUG, new Class[]{String.class, Throwable.class});
            this._debugMAA = cls.getMethod(MqttServiceConstants.TRACE_DEBUG, new Class[]{String.class, Object[].class});
            this._infoMT = cls.getMethod("info", new Class[]{String.class, Throwable.class});
            this._infoMAA = cls.getMethod("info", new Class[]{String.class, Object[].class});
            this._warnMT = cls.getMethod("warn", new Class[]{String.class, Throwable.class});
            this._warnMAA = cls.getMethod("warn", new Class[]{String.class, Object[].class});
            Method method = cls.getMethod("isDebugEnabled", new Class[0]);
            this._setDebugEnabledE = cls.getMethod("setDebugEnabled", new Class[]{Boolean.TYPE});
            this._getLoggerN = cls.getMethod("getLogger", new Class[]{String.class});
            this._getName = cls.getMethod("getName", new Class[0]);
            this._debug = ((Boolean) method.invoke(this._logger, new Object[0])).booleanValue();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public String getName() {
        try {
            return (String) this._getName.invoke(this._logger, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void warn(String str, Object... objArr) {
        try {
            this._warnMAA.invoke(this._logger, objArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void warn(Throwable th) {
        warn("", th);
    }

    public void warn(String str, Throwable th) {
        try {
            this._warnMT.invoke(this._logger, new Object[]{str, th});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(String str, Object... objArr) {
        try {
            this._infoMAA.invoke(this._logger, objArr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void info(Throwable th) {
        info("", th);
    }

    public void info(String str, Throwable th) {
        try {
            this._infoMT.invoke(this._logger, new Object[]{str, th});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isDebugEnabled() {
        return this._debug;
    }

    public void setDebugEnabled(boolean z) {
        try {
            this._setDebugEnabledE.invoke(this._logger, new Object[]{Boolean.valueOf(z)});
            this._debug = z;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void debug(String str, Object... objArr) {
        if (this._debug) {
            try {
                this._debugMAA.invoke(this._logger, objArr);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void debug(Throwable th) {
        debug("", th);
    }

    public void debug(String str, Throwable th) {
        if (this._debug) {
            try {
                this._debugMT.invoke(this._logger, new Object[]{str, th});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void ignore(Throwable th) {
        if (Log.isIgnored()) {
            warn(Log.IGNORED, th);
        }
    }

    /* access modifiers changed from: protected */
    public Logger newLogger(String str) {
        try {
            return new LoggerLog(this._getLoggerN.invoke(this._logger, new Object[]{str}));
        } catch (Exception e) {
            e.printStackTrace();
            return this;
        }
    }
}
