package org.eclipse.jetty.security.authentication;

import java.security.Principal;
import javax.security.auth.Subject;
import org.eclipse.jetty.security.IdentityService;

public class LoginCallbackImpl implements LoginCallback {
    private Object credential;
    private String[] roles = IdentityService.NO_ROLES;
    private final Subject subject;
    private boolean success;
    private final String userName;
    private Principal userPrincipal;

    public LoginCallbackImpl(Subject subject2, String str, Object obj) {
        this.subject = subject2;
        this.userName = str;
        this.credential = obj;
    }

    public Subject getSubject() {
        return this.subject;
    }

    public String getUserName() {
        return this.userName;
    }

    public Object getCredential() {
        return this.credential;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean z) {
        this.success = z;
    }

    public Principal getUserPrincipal() {
        return this.userPrincipal;
    }

    public void setUserPrincipal(Principal principal) {
        this.userPrincipal = principal;
    }

    public String[] getRoles() {
        return this.roles;
    }

    public void setRoles(String[] strArr) {
        this.roles = strArr;
    }

    public void clearPassword() {
        if (this.credential != null) {
            this.credential = null;
        }
    }
}
