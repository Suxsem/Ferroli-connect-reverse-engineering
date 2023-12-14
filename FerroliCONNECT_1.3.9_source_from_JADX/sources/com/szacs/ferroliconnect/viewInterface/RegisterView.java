package com.szacs.ferroliconnect.viewInterface;

public interface RegisterView {
    void onRegisterFailed(int i, boolean z);

    void onRegisterSuccess();

    void setLocation(String str, String str2, String str3, String str4, String str5, String str6);
}
