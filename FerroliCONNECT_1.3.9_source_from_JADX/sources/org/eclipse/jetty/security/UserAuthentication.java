package org.eclipse.jetty.security;

import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.UserIdentity;

public class UserAuthentication implements Authentication.User {
    private final String _method;
    private final UserIdentity _userIdentity;

    public UserAuthentication(String str, UserIdentity userIdentity) {
        this._method = str;
        this._userIdentity = userIdentity;
    }

    public String getAuthMethod() {
        return this._method;
    }

    public UserIdentity getUserIdentity() {
        return this._userIdentity;
    }

    public boolean isUserInRole(UserIdentity.Scope scope, String str) {
        return this._userIdentity.isUserInRole(str, scope);
    }

    public String toString() {
        return "{User," + getAuthMethod() + "," + this._userIdentity + "}";
    }

    public void logout() {
        SecurityHandler currentSecurityHandler = SecurityHandler.getCurrentSecurityHandler();
        if (currentSecurityHandler != null) {
            currentSecurityHandler.logout(this);
        }
    }
}
