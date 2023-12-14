package org.eclipse.paho.android.service;

public interface MqttTraceHandler {
    void traceDebug(String str, String str2);

    void traceError(String str, String str2);

    void traceException(String str, String str2, Exception exc);
}
