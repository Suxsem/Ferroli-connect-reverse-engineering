package com.alibaba.sdk.android.logger;

public interface ILoggerWithControl extends ILogger {
    boolean isEnabled();

    boolean isPrint(LogLevel logLevel);
}
