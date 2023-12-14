package org.eclipse.jetty.security;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HashCrossContextPsuedoSession<T> implements CrossContextPsuedoSession<T> {
    private final String _cookieName;
    private final String _cookiePath;
    private final Map<String, T> _data = new HashMap();
    private final Random _random = new SecureRandom();

    public HashCrossContextPsuedoSession(String str, String str2) {
        this._cookieName = str;
        this._cookiePath = str2 == null ? "/" : str2;
    }

    public T fetch(HttpServletRequest httpServletRequest) {
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (this._cookieName.equals(cookie.getName())) {
                return this._data.get(cookie.getValue());
            }
        }
        return null;
    }

    public void store(T t, HttpServletResponse httpServletResponse) {
        String l;
        synchronized (this._data) {
            do {
                l = Long.toString(Math.abs(this._random.nextLong()), ((int) (System.currentTimeMillis() % 7)) + 30);
            } while (this._data.containsKey(l));
            this._data.put(l, t);
        }
        Cookie cookie = new Cookie(this._cookieName, l);
        cookie.setPath(this._cookiePath);
        httpServletResponse.addCookie(cookie);
    }

    public void clear(HttpServletRequest httpServletRequest) {
        for (Cookie cookie : httpServletRequest.getCookies()) {
            if (this._cookieName.equals(cookie.getName())) {
                this._data.remove(cookie.getValue());
                return;
            }
        }
    }
}
