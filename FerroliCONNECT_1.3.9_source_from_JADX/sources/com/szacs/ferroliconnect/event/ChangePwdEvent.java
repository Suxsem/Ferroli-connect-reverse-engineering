package com.szacs.ferroliconnect.event;

public class ChangePwdEvent {
    private String newPassword;
    private String oldPassword;

    public ChangePwdEvent(String str, String str2) {
        this.newPassword = str;
        this.oldPassword = str2;
    }

    public String getNewPassword() {
        return this.newPassword;
    }

    public void setNewPassword(String str) {
        this.newPassword = str;
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public void setOldPassword(String str) {
        this.oldPassword = str;
    }

    public String toString() {
        return "ChangePwdEvent{newPassword='" + this.newPassword + '\'' + ", oldPassword='" + this.oldPassword + '\'' + '}';
    }
}
