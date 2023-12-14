package org.eclipse.paho.client.mqttv3.util;

import java.util.Enumeration;
import java.util.Properties;
import org.eclipse.paho.client.mqttv3.internal.ClientComms;
import org.eclipse.paho.client.mqttv3.logging.Logger;
import org.eclipse.paho.client.mqttv3.logging.LoggerFactory;

public class Debug {
    private static final String CLASS_NAME = ClientComms.class.getName();
    private static final String lineSep = System.getProperty("line.separator", "\n");
    private static final Logger log = LoggerFactory.getLogger(LoggerFactory.MQTT_CLIENT_MSG_CAT, CLASS_NAME);
    private static final String separator = "==============";
    private String clientID;
    private ClientComms comms;

    public Debug(String str, ClientComms clientComms) {
        this.clientID = str;
        this.comms = clientComms;
        log.setResourceName(str);
    }

    public void dumpClientDebug() {
        dumpClientComms();
        dumpConOptions();
        dumpClientState();
        dumpBaseDebug();
    }

    public void dumpBaseDebug() {
        dumpVersion();
        dumpSystemProperties();
        dumpMemoryTrace();
    }

    /* access modifiers changed from: protected */
    public void dumpMemoryTrace() {
        log.dumpTrace();
    }

    /* access modifiers changed from: protected */
    public void dumpVersion() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(String.valueOf(lineSep) + separator + " Version Info " + separator + lineSep);
        StringBuilder sb = new StringBuilder(String.valueOf(left("Version", 20, ' ')));
        sb.append(":  ");
        sb.append(ClientComms.VERSION);
        sb.append(lineSep);
        stringBuffer.append(sb.toString());
        stringBuffer.append(String.valueOf(left("Build Level", 20, ' ')) + ":  " + ClientComms.BUILD_LEVEL + lineSep);
        StringBuilder sb2 = new StringBuilder("==========================================");
        sb2.append(lineSep);
        stringBuffer.append(sb2.toString());
        log.fine(CLASS_NAME, "dumpVersion", stringBuffer.toString());
    }

    public void dumpSystemProperties() {
        log.fine(CLASS_NAME, "dumpSystemProperties", dumpProperties(System.getProperties(), "SystemProperties").toString());
    }

    public void dumpClientState() {
        ClientComms clientComms = this.comms;
        if (clientComms != null && clientComms.getClientState() != null) {
            Properties debug = this.comms.getClientState().getDebug();
            Logger logger = log;
            String str = CLASS_NAME;
            logger.fine(str, "dumpClientState", dumpProperties(debug, String.valueOf(this.clientID) + " : ClientState").toString());
        }
    }

    public void dumpClientComms() {
        ClientComms clientComms = this.comms;
        if (clientComms != null) {
            Properties debug = clientComms.getDebug();
            Logger logger = log;
            String str = CLASS_NAME;
            logger.fine(str, "dumpClientComms", dumpProperties(debug, String.valueOf(this.clientID) + " : ClientComms").toString());
        }
    }

    public void dumpConOptions() {
        ClientComms clientComms = this.comms;
        if (clientComms != null) {
            Properties debug = clientComms.getConOptions().getDebug();
            Logger logger = log;
            String str = CLASS_NAME;
            logger.fine(str, "dumpConOptions", dumpProperties(debug, String.valueOf(this.clientID) + " : Connect Options").toString());
        }
    }

    public static String dumpProperties(Properties properties, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        Enumeration<?> propertyNames = properties.propertyNames();
        stringBuffer.append(String.valueOf(lineSep) + separator + " " + str + " " + separator + lineSep);
        while (propertyNames.hasMoreElements()) {
            String str2 = (String) propertyNames.nextElement();
            stringBuffer.append(String.valueOf(left(str2, 28, ' ')) + ":  " + properties.get(str2) + lineSep);
        }
        stringBuffer.append("==========================================" + lineSep);
        return stringBuffer.toString();
    }

    public static String left(String str, int i, char c) {
        if (str.length() >= i) {
            return str;
        }
        StringBuffer stringBuffer = new StringBuffer(i);
        stringBuffer.append(str);
        int length = i - str.length();
        while (true) {
            length--;
            if (length < 0) {
                return stringBuffer.toString();
            }
            stringBuffer.append(c);
        }
    }
}
