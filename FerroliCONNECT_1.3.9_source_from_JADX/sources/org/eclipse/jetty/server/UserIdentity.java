package org.eclipse.jetty.server;

import java.security.Principal;
import java.util.Map;
import javax.security.auth.Subject;

public interface UserIdentity {
    public static final UserIdentity UNAUTHENTICATED_IDENTITY = new UnauthenticatedUserIdentity() {
        public Subject getSubject() {
            return null;
        }

        public Principal getUserPrincipal() {
            return null;
        }

        public boolean isUserInRole(String str, Scope scope) {
            return false;
        }

        public String toString() {
            return "UNAUTHENTICATED";
        }
    };

    public interface Scope {
        String getContextPath();

        String getName();

        Map<String, String> getRoleRefMap();
    }

    public interface UnauthenticatedUserIdentity extends UserIdentity {
    }

    Subject getSubject();

    Principal getUserPrincipal();

    boolean isUserInRole(String str, Scope scope);
}
