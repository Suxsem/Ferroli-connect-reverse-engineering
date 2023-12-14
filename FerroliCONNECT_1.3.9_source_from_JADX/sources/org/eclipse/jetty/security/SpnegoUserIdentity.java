package org.eclipse.jetty.security;

import java.security.Principal;
import java.util.List;
import javax.security.auth.Subject;
import org.eclipse.jetty.server.UserIdentity;

public class SpnegoUserIdentity implements UserIdentity {
    private Principal _principal;
    private List<String> _roles;
    private Subject _subject;

    public SpnegoUserIdentity(Subject subject, Principal principal, List<String> list) {
        this._subject = subject;
        this._principal = principal;
        this._roles = list;
    }

    public Subject getSubject() {
        return this._subject;
    }

    public Principal getUserPrincipal() {
        return this._principal;
    }

    public boolean isUserInRole(String str, UserIdentity.Scope scope) {
        return this._roles.contains(str);
    }
}
