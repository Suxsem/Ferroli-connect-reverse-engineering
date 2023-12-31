package org.eclipse.jetty.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CrossContextPsuedoSession<T> {
    void clear(HttpServletRequest httpServletRequest);

    T fetch(HttpServletRequest httpServletRequest);

    void store(T t, HttpServletResponse httpServletResponse);
}
