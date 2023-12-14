package com.szacs.ferroliconnect.viewInterface;

public interface UserInfoView {
    void changePassword(String str, String str2);

    void onChangePasswordFailed(int i, boolean z);

    void onChangePasswordSuccess();

    void onGetUserInfoFailed(int i, boolean z);

    void onGetUserInfoSuccess();

    void onSetLocationFailed(int i, boolean z);

    void onSetLocationSuccess();

    void onSetPortraitFailed(int i, boolean z);

    void onSetPortraitSuccess();

    void setLocation(String str, String str2, String str3, String str4, String str5, String str6);
}
