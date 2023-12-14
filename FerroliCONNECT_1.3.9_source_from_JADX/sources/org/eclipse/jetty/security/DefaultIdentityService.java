package org.eclipse.jetty.security;

import java.security.Principal;
import javax.security.auth.Subject;
import org.eclipse.jetty.server.UserIdentity;

public class DefaultIdentityService implements IdentityService {
    public Object associate(UserIdentity userIdentity) {
        return null;
    }

    public void disassociate(Object obj) {
    }

    public UserIdentity getSystemUserIdentity() {
        return null;
    }

    public Object setRunAs(UserIdentity userIdentity, RunAsToken runAsToken) {
        return runAsToken;
    }

    public void unsetRunAs(Object obj) {
    }

    public RunAsToken newRunAsToken(String str) {
        return new RoleRunAsToken(str);
    }

    public UserIdentity newUserIdentity(Subject subject, Principal principal, String[] strArr) {
        return new DefaultUserIdentity(subject, principal, strArr);
    }
}
