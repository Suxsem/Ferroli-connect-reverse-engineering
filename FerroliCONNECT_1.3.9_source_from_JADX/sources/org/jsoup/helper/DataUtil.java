package org.jsoup.helper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.eclipse.jetty.util.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Parser;

public final class DataUtil {
    static final int boundaryLength = 32;
    private static final int bufferSize = 60000;
    private static final Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*(?:\"|')?([^\\s,;\"']*)");
    static final String defaultCharset = "UTF-8";
    private static final char[] mimeBoundaryChars = "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private DataUtil() {
    }

    public static Document load(File file, String str, String str2) throws IOException {
        return parseByteData(readFileToByteBuffer(file), str, str2, Parser.htmlParser());
    }

    public static Document load(InputStream inputStream, String str, String str2) throws IOException {
        return parseByteData(readToByteBuffer(inputStream), str, str2, Parser.htmlParser());
    }

    public static Document load(InputStream inputStream, String str, String str2, Parser parser) throws IOException {
        return parseByteData(readToByteBuffer(inputStream), str, str2, parser);
    }

    static void crossStreams(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[bufferSize];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    static Document parseByteData(ByteBuffer byteBuffer, String str, String str2, Parser parser) {
        String str3;
        String str4;
        String detectCharsetFromBom = detectCharsetFromBom(byteBuffer, str);
        Document document = null;
        if (detectCharsetFromBom == null) {
            str3 = Charset.forName("UTF-8").decode(byteBuffer).toString();
            Document parseInput = parser.parseInput(str3, str2);
            Element first = parseInput.select("meta[http-equiv=content-type], meta[charset]").first();
            if (first != null) {
                String charsetFromContentType = first.hasAttr("http-equiv") ? getCharsetFromContentType(first.attr("content")) : null;
                str4 = (charsetFromContentType != null || !first.hasAttr("charset")) ? charsetFromContentType : first.attr("charset");
            } else {
                str4 = null;
            }
            if (str4 == null && parseInput.childNodeSize() > 0 && (parseInput.childNode(0) instanceof XmlDeclaration)) {
                XmlDeclaration xmlDeclaration = (XmlDeclaration) parseInput.childNode(0);
                if (xmlDeclaration.name().equals("xml")) {
                    str4 = xmlDeclaration.attr("encoding");
                }
            }
            String validateCharset = validateCharset(str4);
            if (validateCharset == null || validateCharset.equals("UTF-8")) {
                document = parseInput;
            } else {
                detectCharsetFromBom = validateCharset.trim().replaceAll("[\"']", "");
                byteBuffer.rewind();
                str3 = Charset.forName(detectCharsetFromBom).decode(byteBuffer).toString();
            }
        } else {
            Validate.notEmpty(detectCharsetFromBom, "Must set charset arg to character set of file to parse. Set to null to attempt to detect from HTML");
            str3 = Charset.forName(detectCharsetFromBom).decode(byteBuffer).toString();
        }
        if (document != null) {
            return document;
        }
        Document parseInput2 = parser.parseInput(str3, str2);
        parseInput2.outputSettings().charset(detectCharsetFromBom);
        return parseInput2;
    }

    public static ByteBuffer readToByteBuffer(InputStream inputStream, int i) throws IOException {
        int read;
        boolean z = true;
        Validate.isTrue(i >= 0, "maxSize must be 0 (unlimited) or larger");
        if (i <= 0) {
            z = false;
        }
        int i2 = bufferSize;
        byte[] bArr = new byte[((!z || i >= bufferSize) ? bufferSize : i)];
        if (z) {
            i2 = i;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(i2);
        while (true) {
            if (Thread.interrupted() || (read = inputStream.read(bArr)) == -1) {
                break;
            }
            if (z) {
                if (read > i) {
                    byteArrayOutputStream.write(bArr, 0, i);
                    break;
                }
                i -= read;
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
        return ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
    }

    static ByteBuffer readToByteBuffer(InputStream inputStream) throws IOException {
        return readToByteBuffer(inputStream, 0);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0020  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.nio.ByteBuffer readFileToByteBuffer(java.io.File r4) throws java.io.IOException {
        /*
            r0 = 0
            java.io.RandomAccessFile r1 = new java.io.RandomAccessFile     // Catch:{ all -> 0x001c }
            java.lang.String r2 = "r"
            r1.<init>(r4, r2)     // Catch:{ all -> 0x001c }
            long r2 = r1.length()     // Catch:{ all -> 0x001a }
            int r4 = (int) r2     // Catch:{ all -> 0x001a }
            byte[] r4 = new byte[r4]     // Catch:{ all -> 0x001a }
            r1.readFully(r4)     // Catch:{ all -> 0x001a }
            java.nio.ByteBuffer r4 = java.nio.ByteBuffer.wrap(r4)     // Catch:{ all -> 0x001a }
            r1.close()
            return r4
        L_0x001a:
            r4 = move-exception
            goto L_0x001e
        L_0x001c:
            r4 = move-exception
            r1 = r0
        L_0x001e:
            if (r1 == 0) goto L_0x0023
            r1.close()
        L_0x0023:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.helper.DataUtil.readFileToByteBuffer(java.io.File):java.nio.ByteBuffer");
    }

    static ByteBuffer emptyByteBuffer() {
        return ByteBuffer.allocate(0);
    }

    static String getCharsetFromContentType(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = charsetPattern.matcher(str);
        if (matcher.find()) {
            return validateCharset(matcher.group(1).trim().replace("charset=", ""));
        }
        return null;
    }

    private static String validateCharset(String str) {
        if (!(str == null || str.length() == 0)) {
            String replaceAll = str.trim().replaceAll("[\"']", "");
            try {
                if (Charset.isSupported(replaceAll)) {
                    return replaceAll;
                }
                String upperCase = replaceAll.toUpperCase(Locale.ENGLISH);
                if (Charset.isSupported(upperCase)) {
                    return upperCase;
                }
            } catch (IllegalCharsetNameException unused) {
            }
        }
        return null;
    }

    static String mimeBoundary() {
        StringBuilder sb = new StringBuilder(32);
        Random random = new Random();
        for (int i = 0; i < 32; i++) {
            char[] cArr = mimeBoundaryChars;
            sb.append(cArr[random.nextInt(cArr.length)]);
        }
        return sb.toString();
    }

    private static String detectCharsetFromBom(ByteBuffer byteBuffer, String str) {
        byteBuffer.mark();
        byte[] bArr = new byte[4];
        if (byteBuffer.remaining() >= bArr.length) {
            byteBuffer.get(bArr);
            byteBuffer.rewind();
        }
        if ((bArr[0] == 0 && bArr[1] == 0 && bArr[2] == -2 && bArr[3] == -1) || (bArr[0] == -1 && bArr[1] == -2 && bArr[2] == 0 && bArr[3] == 0)) {
            return "UTF-32";
        }
        if ((bArr[0] == -2 && bArr[1] == -1) || (bArr[0] == -1 && bArr[1] == -2)) {
            return StringUtil.__UTF16;
        }
        if (bArr[0] != -17 || bArr[1] != -69 || bArr[2] != -65) {
            return str;
        }
        byteBuffer.position(3);
        return "UTF-8";
    }
}
