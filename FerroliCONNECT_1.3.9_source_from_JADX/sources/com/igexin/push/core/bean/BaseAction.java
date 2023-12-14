package com.igexin.push.core.bean;

public class BaseAction {

    /* renamed from: a */
    private String f1919a;

    /* renamed from: b */
    private String f1920b;

    /* renamed from: c */
    private String f1921c;

    /* renamed from: d */
    private boolean f1922d = true;

    public String getActionId() {
        return this.f1919a;
    }

    public String getDoActionId() {
        return this.f1921c;
    }

    public String getType() {
        return this.f1920b;
    }

    public boolean isSupportExt() {
        return this.f1922d;
    }

    public void setActionId(String str) {
        this.f1919a = str;
    }

    public void setDoActionId(String str) {
        this.f1921c = str;
    }

    public void setSupportExt(boolean z) {
        this.f1922d = z;
    }

    public void setType(String str) {
        this.f1920b = str;
    }
}
