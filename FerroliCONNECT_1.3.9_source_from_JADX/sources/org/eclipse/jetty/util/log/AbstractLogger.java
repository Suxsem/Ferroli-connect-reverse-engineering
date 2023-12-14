package org.eclipse.jetty.util.log;

public abstract class AbstractLogger implements Logger {
    /* access modifiers changed from: protected */
    public abstract Logger newLogger(String str);

    public final Logger getLogger(String str) {
        if (isBlank(str)) {
            return this;
        }
        String name = getName();
        if (!isBlank(name) && Log.getRootLogger() != this) {
            str = name + "." + str;
        }
        Logger logger = Log.getLoggers().get(str);
        if (logger != null) {
            return logger;
        }
        Logger newLogger = newLogger(str);
        Logger putIfAbsent = Log.getMutableLoggers().putIfAbsent(str, newLogger);
        return putIfAbsent == null ? newLogger : putIfAbsent;
    }

    private static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
