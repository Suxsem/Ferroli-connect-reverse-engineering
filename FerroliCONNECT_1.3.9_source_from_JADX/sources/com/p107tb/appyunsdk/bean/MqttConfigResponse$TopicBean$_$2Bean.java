package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.MqttConfigResponse$TopicBean$_$2Bean */
public class MqttConfigResponse$TopicBean$_$2Bean implements Serializable {
    private String REQ_SER;
    private String TO_DEV;

    public String getREQ_SER() {
        return this.REQ_SER;
    }

    public void setREQ_SER(String str) {
        this.REQ_SER = str;
    }

    public String getTO_DEV() {
        return this.TO_DEV;
    }

    public void setTO_DEV(String str) {
        this.TO_DEV = str;
    }

    public String toString() {
        return "_$2Bean{REQ_SER='" + this.REQ_SER + '\'' + ", TO_DEV='" + this.TO_DEV + '\'' + '}';
    }
}
