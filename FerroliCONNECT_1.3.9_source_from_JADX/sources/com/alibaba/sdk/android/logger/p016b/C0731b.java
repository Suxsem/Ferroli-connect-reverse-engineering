package com.alibaba.sdk.android.logger.p016b;

import com.alibaba.sdk.android.logger.LogLevel;

/* renamed from: com.alibaba.sdk.android.logger.b.b */
public class C0731b {

    /* renamed from: a */
    private static final LogLevel f934a = LogLevel.WARN;

    /* renamed from: b */
    private boolean f935b = true;

    /* renamed from: c */
    private LogLevel f936c = f934a;

    /* renamed from: a */
    public void mo9725a(LogLevel logLevel) {
        this.f936c = logLevel;
    }

    /* renamed from: a */
    public void mo9726a(boolean z) {
        this.f935b = z;
    }

    /* renamed from: b */
    public boolean mo9727b(LogLevel logLevel) {
        return this.f935b && logLevel.ordinal() >= this.f936c.ordinal();
    }
}
