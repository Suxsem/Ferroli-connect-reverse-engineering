package org.eclipse.jetty.server;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.UserIdentity;

public interface Authentication {
    public static final Authentication NOT_CHECKED = new Authentication() {
        public String toString() {
            return "NOT CHECKED";
        }
    };
    public static final Authentication SEND_CONTINUE = new Challenge() {
        public String toString() {
            return "CHALLENGE";
        }
    };
    public static final Authentication SEND_FAILURE = new Failure() {
        public String toString() {
            return "FAILURE";
        }
    };
    public static final Authentication SEND_SUCCESS = new SendSuccess() {
        public String toString() {
            return "SEND_SUCCESS";
        }
    };
    public static final Authentication UNAUTHENTICATED = new Authentication() {
        public String toString() {
            return "UNAUTHENTICATED";
        }
    };

    public interface Challenge extends ResponseSent {
    }

    public interface Deferred extends Authentication {
        Authentication authenticate(ServletRequest servletRequest);

        Authentication authenticate(ServletRequest servletRequest, ServletResponse servletResponse);

        Authentication login(String str, Object obj, ServletRequest servletRequest);
    }

    public interface Failure extends ResponseSent {
    }

    public interface ResponseSent extends Authentication {
    }

    public interface SendSuccess extends ResponseSent {
    }

    public interface User extends Authentication {
        String getAuthMethod();

        UserIdentity getUserIdentity();

        boolean isUserInRole(UserIdentity.Scope scope, String str);

        void logout();
    }

    public interface Wrapped extends Authentication {
        HttpServletRequest getHttpServletRequest();

        HttpServletResponse getHttpServletResponse();
    }
}
