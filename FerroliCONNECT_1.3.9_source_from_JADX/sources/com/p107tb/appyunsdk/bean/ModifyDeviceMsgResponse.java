package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.ModifyDeviceMsgResponse */
public class ModifyDeviceMsgResponse implements Serializable {
    private String name;
    private String owner_slug;

    public String getOwner_slug() {
        return this.owner_slug;
    }

    public void setOwner_slug(String str) {
        this.owner_slug = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String toString() {
        return "ModifyDeviceMsgResponse{owner_slug='" + this.owner_slug + '\'' + ", name='" + this.name + '\'' + '}';
    }
}
