package com.alibaba.sdk.android.push.common.util.p021a;

/* renamed from: com.alibaba.sdk.android.push.common.util.a.d */
public enum C0812d {
    UNKNOWN_TYPE(0, "unknown"),
    CONFIG(100, "config"),
    BIND_ACCOUNT(105, "Bind account"),
    UNBIND_ACCOUNT(106, "Unbind account"),
    BIND_TAG_TO_DEVICE(101, "Bind tag to device"),
    BIND_TAG_TO_ACCOUNT(102, "Bind tag to account"),
    BIND_TAG_TO_ALIAS(103, "Bind tag to alias"),
    BIND_ALIAS(104, "Add alias to device"),
    UNBIND_TAG_TO_DEVICE(1101, "Unbind tag from device"),
    UNBIND_TAG_TO_ACCOUNT(1102, "Unbind tag from account"),
    UNBIND_TAG_TO_ALIAS(1103, "Unbind tag from alias"),
    UNBIND_ALIAS(1104, "Remove alias"),
    LIST_TAGS(1201, "List tags of device"),
    LIST_ALIASES(1202, "List aliases of device"),
    TURN_OFF_PUSH(1203, "Turn off push"),
    TURN_ON_PUSH(1204, "Turn on push"),
    CHECK_PUSH_STATUS(1205, "Check push status"),
    BIND_PHONE_NUMBER(1206, "bind phone number to devices"),
    UNBIND_PHONE_NUMBER(1207, "unbind phone number to devices"),
    ON_APP_START(1208, "on app start");
    

    /* renamed from: u */
    public static String f1148u;

    /* renamed from: v */
    private int f1150v;

    /* renamed from: w */
    private String f1151w;

    static {
        f1148u = "VipRequestType";
    }

    private C0812d(int i, String str) {
        this.f1150v = i;
        this.f1151w = str;
    }

    /* renamed from: b */
    public static int m786b() {
        return 100;
    }

    /* renamed from: a */
    public int mo9894a() {
        return this.f1150v;
    }
}
