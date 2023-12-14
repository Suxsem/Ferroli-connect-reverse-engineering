package org.eclipse.paho.client.mqttv3.logging;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class LoggerFactory {
    private static final String CLASS_NAME = "org.eclipse.paho.client.mqttv3.logging.LoggerFactory";
    public static final String MQTT_CLIENT_MSG_CAT = "org.eclipse.paho.client.mqttv3.internal.nls.logcat";
    private static String jsr47LoggerClassName = JSR47Logger.class.getName();
    private static String overrideloggerClassName;

    public static Logger getLogger(String str, String str2) {
        String str3 = overrideloggerClassName;
        if (str3 == null) {
            str3 = jsr47LoggerClassName;
        }
        Logger logger = getLogger(str3, ResourceBundle.getBundle(str), str2, (String) null);
        if (logger != null) {
            return logger;
        }
        throw new MissingResourceException("Error locating the logging class", CLASS_NAME, str2);
    }

    private static Logger getLogger(String str, ResourceBundle resourceBundle, String str2, String str3) {
        try {
            Class<?> cls = Class.forName(str);
            if (cls == null) {
                return null;
            }
            try {
                Logger logger = (Logger) cls.newInstance();
                logger.initialise(resourceBundle, str2, str3);
                return logger;
            } catch (ExceptionInInitializerError | IllegalAccessException | InstantiationException | SecurityException unused) {
                return null;
            }
        } catch (ClassNotFoundException | NoClassDefFoundError unused2) {
            return null;
        }
    }

    public static String getLoggingProperty(String str) {
        try {
            Class<?> cls = Class.forName("java.util.logging.LogManager");
            Object invoke = cls.getMethod("getLogManager", new Class[0]).invoke((Object) null, (Object[]) null);
            return (String) cls.getMethod("getProperty", new Class[]{String.class}).invoke(invoke, new Object[]{str});
        } catch (Exception unused) {
            return null;
        }
    }

    public static void setLogger(String str) {
        overrideloggerClassName = str;
    }
}
