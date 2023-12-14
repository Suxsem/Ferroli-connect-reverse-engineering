package javax.servlet.http;

import java.util.Enumeration;

public interface HttpSessionContext {
    Enumeration<String> getIds();

    HttpSession getSession(String str);
}
