package org.eclipse.jetty.http;

import java.io.UnsupportedEncodingException;
import org.eclipse.jetty.util.MultiMap;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.UrlEncoded;
import org.eclipse.jetty.util.Utf8StringBuffer;

public class EncodedHttpURI extends HttpURI {
    private final String _encoding;

    public EncodedHttpURI(String str) {
        this._encoding = str;
    }

    public String getScheme() {
        if (this._scheme == this._authority) {
            return null;
        }
        int i = this._authority - this._scheme;
        if (i == 5 && this._raw[this._scheme] == 104 && this._raw[this._scheme + 1] == 116 && this._raw[this._scheme + 2] == 116 && this._raw[this._scheme + 3] == 112) {
            return "http";
        }
        if (i == 6 && this._raw[this._scheme] == 104 && this._raw[this._scheme + 1] == 116 && this._raw[this._scheme + 2] == 116 && this._raw[this._scheme + 3] == 112 && this._raw[this._scheme + 4] == 115) {
            return "https";
        }
        return StringUtil.toString(this._raw, this._scheme, (this._authority - this._scheme) - 1, this._encoding);
    }

    public String getAuthority() {
        if (this._authority == this._path) {
            return null;
        }
        return StringUtil.toString(this._raw, this._authority, this._path - this._authority, this._encoding);
    }

    public String getHost() {
        if (this._host == this._port) {
            return null;
        }
        return StringUtil.toString(this._raw, this._host, this._port - this._host, this._encoding);
    }

    public int getPort() {
        if (this._port == this._path) {
            return -1;
        }
        return TypeUtil.parseInt(this._raw, this._port + 1, (this._path - this._port) - 1, 10);
    }

    public String getPath() {
        if (this._path == this._param) {
            return null;
        }
        return StringUtil.toString(this._raw, this._path, this._param - this._path, this._encoding);
    }

    public String getDecodedPath() {
        if (this._path == this._param) {
            return null;
        }
        return URIUtil.decodePath(this._raw, this._path, this._param - this._path);
    }

    public String getPathAndParam() {
        if (this._path == this._query) {
            return null;
        }
        return StringUtil.toString(this._raw, this._path, this._query - this._path, this._encoding);
    }

    public String getCompletePath() {
        if (this._path == this._end) {
            return null;
        }
        return StringUtil.toString(this._raw, this._path, this._end - this._path, this._encoding);
    }

    public String getParam() {
        if (this._param == this._query) {
            return null;
        }
        return StringUtil.toString(this._raw, this._param + 1, (this._query - this._param) - 1, this._encoding);
    }

    public String getQuery() {
        if (this._query == this._fragment) {
            return null;
        }
        return StringUtil.toString(this._raw, this._query + 1, (this._fragment - this._query) - 1, this._encoding);
    }

    public boolean hasQuery() {
        return this._fragment > this._query;
    }

    public String getFragment() {
        if (this._fragment == this._end) {
            return null;
        }
        return StringUtil.toString(this._raw, this._fragment + 1, (this._end - this._fragment) - 1, this._encoding);
    }

    public void decodeQueryTo(MultiMap multiMap) {
        if (this._query != this._fragment) {
            UrlEncoded.decodeTo(StringUtil.toString(this._raw, this._query + 1, (this._fragment - this._query) - 1, this._encoding), multiMap, this._encoding);
        }
    }

    public void decodeQueryTo(MultiMap multiMap, String str) throws UnsupportedEncodingException {
        if (this._query != this._fragment) {
            if (str == null) {
                str = this._encoding;
            }
            UrlEncoded.decodeTo(StringUtil.toString(this._raw, this._query + 1, (this._fragment - this._query) - 1, str), multiMap, str);
        }
    }

    public String toString() {
        if (this._rawString == null) {
            this._rawString = StringUtil.toString(this._raw, this._scheme, this._end - this._scheme, this._encoding);
        }
        return this._rawString;
    }

    public void writeTo(Utf8StringBuffer utf8StringBuffer) {
        utf8StringBuffer.getStringBuffer().append(toString());
    }
}
