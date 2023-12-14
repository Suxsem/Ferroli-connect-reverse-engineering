package com.szacs.ferroliconnect.presenter;

import android.content.SharedPreferences;
import android.os.Handler;
import com.p107tb.appyunsdk.Constant;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.viewInterface.LoginView;

public class LoginPresenter {
    private Runnable autoLoginRunnable = new Runnable() {
        public void run() {
            SharedPreferences sharedPreferences = LoginPresenter.this.context.getSharedPreferences("ferroli_user", 0);
            String string = sharedPreferences.getString(Constant.APP_USERNAME, "");
            String string2 = sharedPreferences.getString("password", "");
            if (sharedPreferences.getBoolean("keepSignedIn", false)) {
                LoginPresenter.this.loginView.setUsername(string);
                LoginPresenter.this.loginView.setPassword(string2);
                LoginPresenter.this.loginView.setKeepSignedIn(true);
                return;
            }
            LoginPresenter.this.loginView.hideWelcomePage();
        }
    };
    private boolean checkResponse = false;
    /* access modifiers changed from: private */
    public MainApplication context;
    private Handler handler;
    /* access modifiers changed from: private */
    public LoginView loginView;
    private String password;
    private String username;

    public LoginPresenter(LoginView loginView2) {
        this.loginView = loginView2;
        this.context = MainApplication.getInstance();
    }

    public boolean loginAutomatically() {
        this.handler = new Handler();
        this.handler.postDelayed(this.autoLoginRunnable, 2000);
        return true;
    }
}
