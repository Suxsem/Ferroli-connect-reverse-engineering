package org.eclipse.jetty.security.authentication;

import java.security.Principal;
import javax.security.auth.Subject;

public interface LoginCallback {
    void clearPassword();

    Object getCredential();

    String[] getRoles();

    Subject getSubject();

    String getUserName();

    Principal getUserPrincipal();

    boolean isSuccess();

    void setRoles(String[] strArr);

    void setSuccess(boolean z);

    void setUserPrincipal(Principal principal);
}
