package com.taobao.accs.utl;

/* compiled from: Taobao */
public abstract class RomInfoCollecter {
    protected RomInfoCollecter mNextCollecter;

    public abstract String collect();

    public static RomInfoCollecter getCollecter() {
        return new C2088e();
    }
}
