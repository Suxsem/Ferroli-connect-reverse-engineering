package org.eclipse.jetty.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import kotlin.jvm.internal.CharCompanionObject;
import kotlin.text.Typography;

public class QuotedStringTokenizer extends StringTokenizer {
    private static final String __delim = "\t\n\r";
    private static final char[] escapes = new char[32];
    private String _delim;
    private boolean _double;
    private boolean _hasToken;

    /* renamed from: _i */
    private int f4113_i;
    private int _lastStart;
    private boolean _returnDelimiters;
    private boolean _returnQuotes;
    private boolean _single;
    private String _string;
    private StringBuffer _token;

    private static boolean isValidEscaping(char c) {
        return c == 'n' || c == 'r' || c == 't' || c == 'f' || c == 'b' || c == '\\' || c == '/' || c == '\"' || c == 'u';
    }

    public int countTokens() {
        return -1;
    }

    public QuotedStringTokenizer(String str, String str2, boolean z, boolean z2) {
        super("");
        this._delim = __delim;
        this._returnQuotes = false;
        this._returnDelimiters = false;
        this._hasToken = false;
        this.f4113_i = 0;
        this._lastStart = 0;
        this._double = true;
        this._single = true;
        this._string = str;
        if (str2 != null) {
            this._delim = str2;
        }
        this._returnDelimiters = z;
        this._returnQuotes = z2;
        if (this._delim.indexOf(39) >= 0 || this._delim.indexOf(34) >= 0) {
            throw new Error("Can't use quotes as delimiters: " + this._delim);
        }
        this._token = new StringBuffer(this._string.length() > 1024 ? 512 : this._string.length() / 2);
    }

    public QuotedStringTokenizer(String str, String str2, boolean z) {
        this(str, str2, z, false);
    }

    public QuotedStringTokenizer(String str, String str2) {
        this(str, str2, false, false);
    }

    public QuotedStringTokenizer(String str) {
        this(str, (String) null, false, false);
    }

    public boolean hasMoreTokens() {
        if (this._hasToken) {
            return true;
        }
        this._lastStart = this.f4113_i;
        char c = 0;
        while (true) {
            boolean z = false;
            while (this.f4113_i < this._string.length()) {
                String str = this._string;
                int i = this.f4113_i;
                this.f4113_i = i + 1;
                char charAt = str.charAt(i);
                if (c != 0) {
                    if (c != 1) {
                        if (c == 2) {
                            this._hasToken = true;
                            if (z) {
                                this._token.append(charAt);
                            } else if (charAt == '\'') {
                                if (this._returnQuotes) {
                                    this._token.append(charAt);
                                }
                                c = 1;
                            } else if (charAt != '\\') {
                                this._token.append(charAt);
                            } else if (this._returnQuotes) {
                                this._token.append(charAt);
                            }
                        } else if (c != 3) {
                            continue;
                        } else {
                            this._hasToken = true;
                            if (z) {
                                this._token.append(charAt);
                            } else if (charAt == '\"') {
                                if (this._returnQuotes) {
                                    this._token.append(charAt);
                                }
                                c = 1;
                            } else if (charAt != '\\') {
                                this._token.append(charAt);
                            } else if (this._returnQuotes) {
                                this._token.append(charAt);
                            }
                        }
                        z = true;
                    } else {
                        this._hasToken = true;
                        if (this._delim.indexOf(charAt) >= 0) {
                            if (this._returnDelimiters) {
                                this.f4113_i--;
                            }
                            return this._hasToken;
                        } else if (charAt != '\'' || !this._single) {
                            if (charAt != '\"' || !this._double) {
                                this._token.append(charAt);
                            } else {
                                if (this._returnQuotes) {
                                    this._token.append(charAt);
                                }
                                c = 3;
                            }
                        } else if (this._returnQuotes) {
                            this._token.append(charAt);
                        }
                    }
                } else if (this._delim.indexOf(charAt) >= 0) {
                    if (this._returnDelimiters) {
                        this._token.append(charAt);
                        this._hasToken = true;
                        return true;
                    }
                } else if (charAt != '\'' || !this._single) {
                    if (charAt != '\"' || !this._double) {
                        this._token.append(charAt);
                        this._hasToken = true;
                        c = 1;
                    } else {
                        if (this._returnQuotes) {
                            this._token.append(charAt);
                        }
                        c = 3;
                    }
                } else if (this._returnQuotes) {
                    this._token.append(charAt);
                }
                c = 2;
            }
            return this._hasToken;
        }
    }

    public String nextToken() throws NoSuchElementException {
        StringBuffer stringBuffer;
        if (!hasMoreTokens() || (stringBuffer = this._token) == null) {
            throw new NoSuchElementException();
        }
        String stringBuffer2 = stringBuffer.toString();
        this._token.setLength(0);
        this._hasToken = false;
        return stringBuffer2;
    }

    public String nextToken(String str) throws NoSuchElementException {
        this._delim = str;
        this.f4113_i = this._lastStart;
        this._token.setLength(0);
        this._hasToken = false;
        return nextToken();
    }

    public boolean hasMoreElements() {
        return hasMoreTokens();
    }

    public Object nextElement() throws NoSuchElementException {
        return nextToken();
    }

    public static String quoteIfNeeded(String str, String str2) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "\"\"";
        }
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\\' || charAt == '\"' || charAt == '\'' || Character.isWhitespace(charAt) || str2.indexOf(charAt) >= 0) {
                StringBuffer stringBuffer = new StringBuffer(str.length() + 8);
                quote(stringBuffer, str);
                return stringBuffer.toString();
            }
        }
        return str;
    }

    public static String quote(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return "\"\"";
        }
        StringBuffer stringBuffer = new StringBuffer(str.length() + 8);
        quote(stringBuffer, str);
        return stringBuffer.toString();
    }

    static {
        Arrays.fill(escapes, CharCompanionObject.MAX_VALUE);
        char[] cArr = escapes;
        cArr[8] = 'b';
        cArr[9] = 't';
        cArr[10] = 'n';
        cArr[12] = 'f';
        cArr[13] = 'r';
    }

    public static void quote(Appendable appendable, String str) {
        try {
            appendable.append(Typography.quote);
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (charAt >= ' ') {
                    if (charAt == '\"' || charAt == '\\') {
                        appendable.append('\\');
                    }
                    appendable.append(charAt);
                } else {
                    char c = escapes[charAt];
                    if (c == 65535) {
                        appendable.append('\\').append('u').append('0').append('0');
                        if (charAt < 16) {
                            appendable.append('0');
                        }
                        appendable.append(Integer.toString(charAt, 16));
                    } else {
                        appendable.append('\\').append(c);
                    }
                }
            }
            appendable.append(Typography.quote);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean quoteIfNeeded(Appendable appendable, String str, String str2) {
        for (int i = 0; i < str.length(); i++) {
            if (str2.indexOf(str.charAt(i)) >= 0) {
                quote(appendable, str);
                return true;
            }
        }
        try {
            appendable.append(str);
            return false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String unquoteOnly(String str) {
        return unquoteOnly(str, false);
    }

    public static String unquoteOnly(String str, boolean z) {
        char charAt;
        if (str == null) {
            return null;
        }
        if (str.length() < 2 || (charAt = str.charAt(0)) != str.charAt(str.length() - 1)) {
            return str;
        }
        if (charAt != '\"' && charAt != '\'') {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() - 2);
        boolean z2 = false;
        for (int i = 1; i < str.length() - 1; i++) {
            char charAt2 = str.charAt(i);
            if (z2) {
                if (z && !isValidEscaping(charAt2)) {
                    sb.append('\\');
                }
                sb.append(charAt2);
                z2 = false;
            } else if (charAt2 == '\\') {
                z2 = true;
            } else {
                sb.append(charAt2);
            }
        }
        return sb.toString();
    }

    public static String unquote(String str) {
        return unquote(str, false);
    }

    public static String unquote(String str, boolean z) {
        char charAt;
        if (str == null) {
            return null;
        }
        if (str.length() < 2 || (charAt = str.charAt(0)) != str.charAt(str.length() - 1)) {
            return str;
        }
        if (charAt != '\"' && charAt != '\'') {
            return str;
        }
        StringBuilder sb = new StringBuilder(str.length() - 2);
        int i = 1;
        boolean z2 = false;
        while (i < str.length() - 1) {
            char charAt2 = str.charAt(i);
            if (z2) {
                if (charAt2 == '\"') {
                    sb.append(Typography.quote);
                } else if (charAt2 == '/') {
                    sb.append('/');
                } else if (charAt2 == '\\') {
                    sb.append('\\');
                } else if (charAt2 == 'b') {
                    sb.append(8);
                } else if (charAt2 == 'f') {
                    sb.append(12);
                } else if (charAt2 == 'n') {
                    sb.append(10);
                } else if (charAt2 == 'r') {
                    sb.append(13);
                } else if (charAt2 == 't') {
                    sb.append(9);
                } else if (charAt2 != 'u') {
                    if (z && !isValidEscaping(charAt2)) {
                        sb.append('\\');
                    }
                    sb.append(charAt2);
                } else {
                    int i2 = i + 1;
                    int i3 = i2 + 1;
                    int convertHexDigit = (TypeUtil.convertHexDigit((byte) str.charAt(i)) << 24) + (TypeUtil.convertHexDigit((byte) str.charAt(i2)) << 16);
                    int i4 = i3 + 1;
                    int convertHexDigit2 = convertHexDigit + (TypeUtil.convertHexDigit((byte) str.charAt(i3)) << 8);
                    sb.append((char) (convertHexDigit2 + TypeUtil.convertHexDigit((byte) str.charAt(i4))));
                    i = i4 + 1;
                }
                z2 = false;
            } else if (charAt2 == '\\') {
                z2 = true;
            } else {
                sb.append(charAt2);
            }
            i++;
        }
        return sb.toString();
    }

    public boolean getDouble() {
        return this._double;
    }

    public void setDouble(boolean z) {
        this._double = z;
    }

    public boolean getSingle() {
        return this._single;
    }

    public void setSingle(boolean z) {
        this._single = z;
    }
}
