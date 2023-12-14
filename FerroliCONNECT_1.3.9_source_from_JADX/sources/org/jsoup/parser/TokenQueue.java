package org.jsoup.parser;

import org.jsoup.helper.StringUtil;
import org.jsoup.helper.Validate;

public class TokenQueue {
    private static final char ESC = '\\';
    private int pos = 0;
    private String queue;

    public TokenQueue(String str) {
        Validate.notNull(str);
        this.queue = str;
    }

    public boolean isEmpty() {
        return remainingLength() == 0;
    }

    private int remainingLength() {
        return this.queue.length() - this.pos;
    }

    public char peek() {
        if (isEmpty()) {
            return 0;
        }
        return this.queue.charAt(this.pos);
    }

    public void addFirst(Character ch) {
        addFirst(ch.toString());
    }

    public void addFirst(String str) {
        this.queue = str + this.queue.substring(this.pos);
        this.pos = 0;
    }

    public boolean matches(String str) {
        return this.queue.regionMatches(true, this.pos, str, 0, str.length());
    }

    public boolean matchesCS(String str) {
        return this.queue.startsWith(str, this.pos);
    }

    public boolean matchesAny(String... strArr) {
        for (String matches : strArr) {
            if (matches(matches)) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesAny(char... cArr) {
        if (isEmpty()) {
            return false;
        }
        for (char c : cArr) {
            if (this.queue.charAt(this.pos) == c) {
                return true;
            }
        }
        return false;
    }

    public boolean matchesStartTag() {
        return remainingLength() >= 2 && this.queue.charAt(this.pos) == '<' && Character.isLetter(this.queue.charAt(this.pos + 1));
    }

    public boolean matchChomp(String str) {
        if (!matches(str)) {
            return false;
        }
        this.pos += str.length();
        return true;
    }

    public boolean matchesWhitespace() {
        return !isEmpty() && StringUtil.isWhitespace(this.queue.charAt(this.pos));
    }

    public boolean matchesWord() {
        return !isEmpty() && Character.isLetterOrDigit(this.queue.charAt(this.pos));
    }

    public void advance() {
        if (!isEmpty()) {
            this.pos++;
        }
    }

    public char consume() {
        String str = this.queue;
        int i = this.pos;
        this.pos = i + 1;
        return str.charAt(i);
    }

    public void consume(String str) {
        if (matches(str)) {
            int length = str.length();
            if (length <= remainingLength()) {
                this.pos += length;
                return;
            }
            throw new IllegalStateException("Queue not long enough to consume sequence");
        }
        throw new IllegalStateException("Queue did not match expected sequence");
    }

    public String consumeTo(String str) {
        int indexOf = this.queue.indexOf(str, this.pos);
        if (indexOf == -1) {
            return remainder();
        }
        String substring = this.queue.substring(this.pos, indexOf);
        this.pos += substring.length();
        return substring;
    }

    public String consumeToIgnoreCase(String str) {
        int i = this.pos;
        String substring = str.substring(0, 1);
        boolean equals = substring.toLowerCase().equals(substring.toUpperCase());
        while (!isEmpty() && !matches(str)) {
            if (equals) {
                int indexOf = this.queue.indexOf(substring, this.pos);
                int i2 = this.pos;
                int i3 = indexOf - i2;
                if (i3 == 0) {
                    this.pos = i2 + 1;
                } else if (i3 < 0) {
                    this.pos = this.queue.length();
                } else {
                    this.pos = i2 + i3;
                }
            } else {
                this.pos++;
            }
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeToAny(String... strArr) {
        int i = this.pos;
        while (!isEmpty() && !matchesAny(strArr)) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String chompTo(String str) {
        String consumeTo = consumeTo(str);
        matchChomp(str);
        return consumeTo;
    }

    public String chompToIgnoreCase(String str) {
        String consumeToIgnoreCase = consumeToIgnoreCase(str);
        matchChomp(str);
        return consumeToIgnoreCase;
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0067 A[EDGE_INSN: B:32:0x0067->B:28:0x0067 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String chompBalanced(char r9, char r10) {
        /*
            r8 = this;
            r0 = 0
            r1 = -1
            r2 = 0
            r3 = -1
            r4 = 0
            r5 = -1
        L_0x0006:
            boolean r6 = r8.isEmpty()
            if (r6 == 0) goto L_0x000d
            goto L_0x0067
        L_0x000d:
            char r6 = r8.consume()
            java.lang.Character r6 = java.lang.Character.valueOf(r6)
            if (r0 == 0) goto L_0x001b
            r7 = 92
            if (r0 == r7) goto L_0x005b
        L_0x001b:
            r7 = 39
            java.lang.Character r7 = java.lang.Character.valueOf(r7)
            boolean r7 = r6.equals(r7)
            if (r7 != 0) goto L_0x0033
            r7 = 34
            java.lang.Character r7 = java.lang.Character.valueOf(r7)
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x003b
        L_0x0033:
            char r7 = r6.charValue()
            if (r7 == r9) goto L_0x003b
            r2 = r2 ^ 1
        L_0x003b:
            if (r2 == 0) goto L_0x003e
            goto L_0x0065
        L_0x003e:
            java.lang.Character r7 = java.lang.Character.valueOf(r9)
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x004f
            int r4 = r4 + 1
            if (r3 != r1) goto L_0x005b
            int r3 = r8.pos
            goto L_0x005b
        L_0x004f:
            java.lang.Character r7 = java.lang.Character.valueOf(r10)
            boolean r7 = r6.equals(r7)
            if (r7 == 0) goto L_0x005b
            int r4 = r4 + -1
        L_0x005b:
            if (r4 <= 0) goto L_0x0061
            if (r0 == 0) goto L_0x0061
            int r5 = r8.pos
        L_0x0061:
            char r0 = r6.charValue()
        L_0x0065:
            if (r4 > 0) goto L_0x0006
        L_0x0067:
            if (r5 < 0) goto L_0x0070
            java.lang.String r9 = r8.queue
            java.lang.String r9 = r9.substring(r3, r5)
            goto L_0x0072
        L_0x0070:
            java.lang.String r9 = ""
        L_0x0072:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: org.jsoup.parser.TokenQueue.chompBalanced(char, char):java.lang.String");
    }

    public static String unescape(String str) {
        StringBuilder sb = new StringBuilder();
        char[] charArray = str.toCharArray();
        int length = charArray.length;
        int i = 0;
        char c = 0;
        while (i < length) {
            char c2 = charArray[i];
            if (c2 != '\\') {
                sb.append(c2);
            } else if (c != 0 && c == '\\') {
                sb.append(c2);
            }
            i++;
            c = c2;
        }
        return sb.toString();
    }

    public boolean consumeWhitespace() {
        boolean z = false;
        while (matchesWhitespace()) {
            this.pos++;
            z = true;
        }
        return z;
    }

    public String consumeWord() {
        int i = this.pos;
        while (matchesWord()) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeTagName() {
        int i = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny(':', '_', '-'))) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeElementSelector() {
        int i = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny("*|", "|", "_", "-"))) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeCssIdentifier() {
        int i = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny('-', '_'))) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String consumeAttributeKey() {
        int i = this.pos;
        while (!isEmpty() && (matchesWord() || matchesAny('-', '_', ':'))) {
            this.pos++;
        }
        return this.queue.substring(i, this.pos);
    }

    public String remainder() {
        String str = this.queue;
        String substring = str.substring(this.pos, str.length());
        this.pos = this.queue.length();
        return substring;
    }

    public String toString() {
        return this.queue.substring(this.pos);
    }
}
