package com.taobao.accs.utl;

/* renamed from: com.taobao.accs.utl.e */
/* compiled from: Taobao */
public class C2088e extends RomInfoCollecter {
    public String collect() {
        String g = UtilityImpl.m3764g();
        return (g != null || this.mNextCollecter == null) ? g : this.mNextCollecter.collect();
    }
}
