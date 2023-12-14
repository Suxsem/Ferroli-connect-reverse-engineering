package com.p107tb.appyunsdk.bean;

import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.EmailCodeResponse */
public class EmailCodeResponse implements Serializable {
    private int check_num;
    private String code;
    private String email;
    private String email_template;
    private boolean is_active;
    private String partner;
    private String request_ip;
    private String send_time;
    private String subject;
    private String token;

    public String getPartner() {
        return this.partner;
    }

    public void setPartner(String str) {
        this.partner = str;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(String str) {
        this.subject = str;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String str) {
        this.token = str;
    }

    public String getRequest_ip() {
        return this.request_ip;
    }

    public void setRequest_ip(String str) {
        this.request_ip = str;
    }

    public String getEmail_template() {
        return this.email_template;
    }

    public void setEmail_template(String str) {
        this.email_template = str;
    }

    public int getCheck_num() {
        return this.check_num;
    }

    public void setCheck_num(int i) {
        this.check_num = i;
    }

    public String getSend_time() {
        return this.send_time;
    }

    public void setSend_time(String str) {
        this.send_time = str;
    }

    public boolean isIs_active() {
        return this.is_active;
    }

    public void setIs_active(boolean z) {
        this.is_active = z;
    }
}
