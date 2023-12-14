package com.szacs.ferroliconnect.viewInterface;

public interface LoginView {
    void forcedUpdate();

    boolean getKeepSignedIn();

    String getPassword();

    String getUsername();

    void hideWelcomePage();

    void onLoginFailed(int i, boolean z);

    void onLoginSuccess();

    void setKeepSignedIn(boolean z);

    void setPassword(String str);

    void setUsername(String str);

    void showWelcomePage();
}
