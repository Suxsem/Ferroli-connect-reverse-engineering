package com.p107tb.appyunsdk.listener;

import com.p107tb.appyunsdk.bean.UserResponse;

/* renamed from: com.tb.appyunsdk.listener.IYunManagerLoginListener */
public interface IYunManagerLoginListener {
    void userLoginFailure(int i, String str);

    void userLoginSuccess(String str, UserResponse userResponse);
}
