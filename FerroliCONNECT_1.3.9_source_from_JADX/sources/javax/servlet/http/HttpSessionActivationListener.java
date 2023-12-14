package javax.servlet.http;

import java.util.EventListener;

public interface HttpSessionActivationListener extends EventListener {
    void sessionDidActivate(HttpSessionEvent httpSessionEvent);

    void sessionWillPassivate(HttpSessionEvent httpSessionEvent);
}
