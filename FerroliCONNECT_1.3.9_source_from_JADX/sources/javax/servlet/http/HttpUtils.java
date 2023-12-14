package javax.servlet.http;

import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.HttpConstant;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import javax.servlet.ServletInputStream;

public class HttpUtils {
    private static final String LSTRING_FILE = "javax.servlet.http.LocalStrings";
    private static ResourceBundle lStrings = ResourceBundle.getBundle(LSTRING_FILE);

    public static Hashtable<String, String[]> parseQueryString(String str) {
        String[] strArr;
        if (str != null) {
            Hashtable<String, String[]> hashtable = new Hashtable<>();
            StringBuilder sb = new StringBuilder();
            StringTokenizer stringTokenizer = new StringTokenizer(str, DispatchConstants.SIGN_SPLIT_SYMBOL);
            while (stringTokenizer.hasMoreTokens()) {
                String nextToken = stringTokenizer.nextToken();
                int indexOf = nextToken.indexOf(61);
                if (indexOf != -1) {
                    String parseName = parseName(nextToken.substring(0, indexOf), sb);
                    String parseName2 = parseName(nextToken.substring(indexOf + 1, nextToken.length()), sb);
                    if (hashtable.containsKey(parseName)) {
                        String[] strArr2 = hashtable.get(parseName);
                        strArr = new String[(strArr2.length + 1)];
                        for (int i = 0; i < strArr2.length; i++) {
                            strArr[i] = strArr2[i];
                        }
                        strArr[strArr2.length] = parseName2;
                    } else {
                        strArr = new String[]{parseName2};
                    }
                    hashtable.put(parseName, strArr);
                } else {
                    throw new IllegalArgumentException();
                }
            }
            return hashtable;
        }
        throw new IllegalArgumentException();
    }

    public static Hashtable<String, String[]> parsePostData(int i, ServletInputStream servletInputStream) {
        if (i <= 0) {
            return new Hashtable<>();
        }
        if (servletInputStream != null) {
            byte[] bArr = new byte[i];
            int i2 = 0;
            do {
                try {
                    int read = servletInputStream.read(bArr, i2, i - i2);
                    if (read > 0) {
                        i2 += read;
                    } else {
                        throw new IllegalArgumentException(lStrings.getString("err.io.short_read"));
                    }
                } catch (IOException e) {
                    throw new IllegalArgumentException(e.getMessage());
                }
            } while (i - i2 > 0);
            try {
                return parseQueryString(new String(bArr, 0, i, "8859_1"));
            } catch (UnsupportedEncodingException e2) {
                throw new IllegalArgumentException(e2.getMessage());
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private static String parseName(String str, StringBuilder sb) {
        int i = 0;
        sb.setLength(0);
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt == '%') {
                int i2 = i + 1;
                try {
                    sb.append((char) Integer.parseInt(str.substring(i2, i + 3), 16));
                    i += 2;
                } catch (NumberFormatException unused) {
                    throw new IllegalArgumentException();
                } catch (StringIndexOutOfBoundsException unused2) {
                    String substring = str.substring(i);
                    sb.append(substring);
                    if (substring.length() == 2) {
                        i = i2;
                    }
                }
            } else if (charAt != '+') {
                sb.append(charAt);
            } else {
                sb.append(' ');
            }
            i++;
        }
        return sb.toString();
    }

    public static StringBuffer getRequestURL(HttpServletRequest httpServletRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        String scheme = httpServletRequest.getScheme();
        int serverPort = httpServletRequest.getServerPort();
        String requestURI = httpServletRequest.getRequestURI();
        stringBuffer.append(scheme);
        stringBuffer.append(HttpConstant.SCHEME_SPLIT);
        stringBuffer.append(httpServletRequest.getServerName());
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            stringBuffer.append(':');
            stringBuffer.append(httpServletRequest.getServerPort());
        }
        stringBuffer.append(requestURI);
        return stringBuffer;
    }
}
