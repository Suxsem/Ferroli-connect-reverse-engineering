package org.eclipse.jetty.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ErrorHandler;

public class ErrorPageErrorHandler extends ErrorHandler implements ErrorHandler.ErrorPageMapper {
    public static final String GLOBAL_ERROR_PAGE = "org.eclipse.jetty.server.error_page.global";
    private final List<ErrorCodeRange> _errorPageList = new ArrayList();
    private final Map<String, String> _errorPages = new HashMap();
    protected ServletContext _servletContext;

    public String getErrorPage(HttpServletRequest httpServletRequest) {
        String str;
        Integer num;
        Class cls = (Class) httpServletRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION_TYPE);
        if (ServletException.class.equals(cls)) {
            str = this._errorPages.get(cls.getName());
            if (str == null) {
                Throwable th = (Throwable) httpServletRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
                while (th instanceof ServletException) {
                    th = ((ServletException) th).getRootCause();
                }
                if (th != null) {
                    cls = th.getClass();
                }
            }
        } else {
            str = null;
        }
        while (str == null && cls != null) {
            str = this._errorPages.get(cls.getName());
            cls = cls.getSuperclass();
        }
        if (str == null && (num = (Integer) httpServletRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)) != null && (str = this._errorPages.get(Integer.toString(num.intValue()))) == null && this._errorPageList != null) {
            int i = 0;
            while (true) {
                if (i >= this._errorPageList.size()) {
                    break;
                }
                ErrorCodeRange errorCodeRange = this._errorPageList.get(i);
                if (errorCodeRange.isInRange(num.intValue())) {
                    str = errorCodeRange.getUri();
                    break;
                }
                i++;
            }
        }
        return str == null ? this._errorPages.get(GLOBAL_ERROR_PAGE) : str;
    }

    public Map<String, String> getErrorPages() {
        return this._errorPages;
    }

    public void setErrorPages(Map<String, String> map) {
        this._errorPages.clear();
        if (map != null) {
            this._errorPages.putAll(map);
        }
    }

    public void addErrorPage(Class<? extends Throwable> cls, String str) {
        this._errorPages.put(cls.getName(), str);
    }

    public void addErrorPage(String str, String str2) {
        this._errorPages.put(str, str2);
    }

    public void addErrorPage(int i, String str) {
        this._errorPages.put(Integer.toString(i), str);
    }

    public void addErrorPage(int i, int i2, String str) {
        this._errorPageList.add(new ErrorCodeRange(i, i2, str));
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        super.doStart();
        this._servletContext = ContextHandler.getCurrentContext();
    }

    private class ErrorCodeRange {
        private int _from;
        private int _to;
        private String _uri;

        ErrorCodeRange(int i, int i2, String str) throws IllegalArgumentException {
            if (i <= i2) {
                this._from = i;
                this._to = i2;
                this._uri = str;
                return;
            }
            throw new IllegalArgumentException("from>to");
        }

        /* access modifiers changed from: package-private */
        public boolean isInRange(int i) {
            return i >= this._from && i <= this._to;
        }

        /* access modifiers changed from: package-private */
        public String getUri() {
            return this._uri;
        }

        public String toString() {
            return "from: " + this._from + ",to: " + this._to + ",uri: " + this._uri;
        }
    }
}
