package com.alibaba.sdk.android.tool;

import java.util.Locale;

public class ThreadUtils {
    public static String dumpThread(Thread thread) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append(String.format("Thread Name: '%s'\n", new Object[]{thread.getName()}));
            sb.append(String.format(Locale.ENGLISH, "\"%s\" prio=%d tid=%d %s\n", new Object[]{thread.getName(), Integer.valueOf(thread.getPriority()), Long.valueOf(thread.getId()), thread.getState()}));
            StackTraceElement[] stackTrace = thread.getStackTrace();
            int length = stackTrace.length;
            for (int i = 0; i < length; i++) {
                sb.append(String.format("\tat %s\n", new Object[]{stackTrace[i].toString()}));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void threadWaitForSec(double d) {
        try {
            Thread.sleep(((long) d) * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
