package org.eclipse.jetty.util.ajax;

import anetwork.channel.util.RequestConstant;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.QuotedStringTokenizer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class JSON {
    public static final JSON DEFAULT = new JSON();
    static final Logger LOG = Log.getLogger((Class<?>) JSON.class);
    private Map<String, Convertor> _convertors = new ConcurrentHashMap();
    private int _stringBufferSize = 1024;

    public interface Convertible {
        void fromJSON(Map map);

        void toJSON(Output output);
    }

    public interface Convertor {
        Object fromJSON(Map map);

        void toJSON(Object obj, Output output);
    }

    public interface Generator {
        void addJSON(Appendable appendable);
    }

    public interface Output {
        void add(Object obj);

        void add(String str, double d);

        void add(String str, long j);

        void add(String str, Object obj);

        void add(String str, boolean z);

        void addClass(Class cls);
    }

    public interface Source {
        boolean hasNext();

        char next();

        char peek();

        char[] scratchBuffer();
    }

    @Deprecated
    public static void setDefault(JSON json) {
    }

    /* access modifiers changed from: protected */
    public JSON contextFor(String str) {
        return this;
    }

    /* access modifiers changed from: protected */
    public JSON contextForArray() {
        return this;
    }

    public int getStringBufferSize() {
        return this._stringBufferSize;
    }

    public void setStringBufferSize(int i) {
        this._stringBufferSize = i;
    }

    public static void registerConvertor(Class cls, Convertor convertor) {
        DEFAULT.addConvertor(cls, convertor);
    }

    public static JSON getDefault() {
        return DEFAULT;
    }

    public static String toString(Object obj) {
        StringBuilder sb = new StringBuilder(DEFAULT.getStringBufferSize());
        DEFAULT.append((Appendable) sb, obj);
        return sb.toString();
    }

    public static String toString(Map map) {
        StringBuilder sb = new StringBuilder(DEFAULT.getStringBufferSize());
        DEFAULT.appendMap((Appendable) sb, (Map<?, ?>) map);
        return sb.toString();
    }

    public static String toString(Object[] objArr) {
        StringBuilder sb = new StringBuilder(DEFAULT.getStringBufferSize());
        DEFAULT.appendArray((Appendable) sb, (Object) objArr);
        return sb.toString();
    }

    public static Object parse(String str) {
        return DEFAULT.parse((Source) new StringSource(str), false);
    }

    public static Object parse(String str, boolean z) {
        return DEFAULT.parse((Source) new StringSource(str), z);
    }

    public static Object parse(Reader reader) throws IOException {
        return DEFAULT.parse((Source) new ReaderSource(reader), false);
    }

    public static Object parse(Reader reader, boolean z) throws IOException {
        return DEFAULT.parse((Source) new ReaderSource(reader), z);
    }

    @Deprecated
    public static Object parse(InputStream inputStream) throws IOException {
        return DEFAULT.parse((Source) new StringSource(C2439IO.toString(inputStream)), false);
    }

    @Deprecated
    public static Object parse(InputStream inputStream, boolean z) throws IOException {
        return DEFAULT.parse((Source) new StringSource(C2439IO.toString(inputStream)), z);
    }

    public String toJSON(Object obj) {
        StringBuilder sb = new StringBuilder(getStringBufferSize());
        append((Appendable) sb, obj);
        return sb.toString();
    }

    public Object fromJSON(String str) {
        return parse((Source) new StringSource(str));
    }

    @Deprecated
    public void append(StringBuffer stringBuffer, Object obj) {
        append((Appendable) stringBuffer, obj);
    }

    public void append(Appendable appendable, Object obj) {
        if (obj == null) {
            try {
                appendable.append("null");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (obj instanceof Map) {
            appendMap(appendable, (Map<?, ?>) (Map) obj);
        } else if (obj instanceof String) {
            appendString(appendable, (String) obj);
        } else if (obj instanceof Number) {
            appendNumber(appendable, (Number) obj);
        } else if (obj instanceof Boolean) {
            appendBoolean(appendable, (Boolean) obj);
        } else if (obj.getClass().isArray()) {
            appendArray(appendable, obj);
        } else if (obj instanceof Character) {
            appendString(appendable, obj.toString());
        } else if (obj instanceof Convertible) {
            appendJSON(appendable, (Convertible) obj);
        } else if (obj instanceof Generator) {
            appendJSON(appendable, (Generator) obj);
        } else {
            Convertor convertor = getConvertor(obj.getClass());
            if (convertor != null) {
                appendJSON(appendable, convertor, obj);
            } else if (obj instanceof Collection) {
                appendArray(appendable, (Collection) obj);
            } else {
                appendString(appendable, obj.toString());
            }
        }
    }

    @Deprecated
    public void appendNull(StringBuffer stringBuffer) {
        appendNull((Appendable) stringBuffer);
    }

    public void appendNull(Appendable appendable) {
        try {
            appendable.append("null");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public void appendJSON(StringBuffer stringBuffer, Convertor convertor, Object obj) {
        appendJSON((Appendable) stringBuffer, convertor, obj);
    }

    public void appendJSON(Appendable appendable, final Convertor convertor, final Object obj) {
        appendJSON(appendable, (Convertible) new Convertible() {
            public void fromJSON(Map map) {
            }

            public void toJSON(Output output) {
                convertor.toJSON(obj, output);
            }
        });
    }

    @Deprecated
    public void appendJSON(StringBuffer stringBuffer, Convertible convertible) {
        appendJSON((Appendable) stringBuffer, convertible);
    }

    public void appendJSON(Appendable appendable, Convertible convertible) {
        ConvertableOutput convertableOutput = new ConvertableOutput(appendable);
        convertible.toJSON(convertableOutput);
        convertableOutput.complete();
    }

    @Deprecated
    public void appendJSON(StringBuffer stringBuffer, Generator generator) {
        generator.addJSON(stringBuffer);
    }

    public void appendJSON(Appendable appendable, Generator generator) {
        generator.addJSON(appendable);
    }

    @Deprecated
    public void appendMap(StringBuffer stringBuffer, Map<?, ?> map) {
        appendMap((Appendable) stringBuffer, map);
    }

    public void appendMap(Appendable appendable, Map<?, ?> map) {
        if (map == null) {
            try {
                appendNull(appendable);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            appendable.append('{');
            Iterator<Map.Entry<?, ?>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry next = it.next();
                QuotedStringTokenizer.quote(appendable, next.getKey().toString());
                appendable.append(':');
                append(appendable, next.getValue());
                if (it.hasNext()) {
                    appendable.append(',');
                }
            }
            appendable.append('}');
        }
    }

    @Deprecated
    public void appendArray(StringBuffer stringBuffer, Collection collection) {
        appendArray((Appendable) stringBuffer, collection);
    }

    public void appendArray(Appendable appendable, Collection collection) {
        if (collection == null) {
            try {
                appendNull(appendable);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            appendable.append('[');
            boolean z = true;
            for (Object append : collection) {
                if (!z) {
                    appendable.append(',');
                }
                z = false;
                append(appendable, append);
            }
            appendable.append(']');
        }
    }

    @Deprecated
    public void appendArray(StringBuffer stringBuffer, Object obj) {
        appendArray((Appendable) stringBuffer, obj);
    }

    public void appendArray(Appendable appendable, Object obj) {
        if (obj == null) {
            try {
                appendNull(appendable);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            appendable.append('[');
            int length = Array.getLength(obj);
            for (int i = 0; i < length; i++) {
                if (i != 0) {
                    appendable.append(',');
                }
                append(appendable, Array.get(obj, i));
            }
            appendable.append(']');
        }
    }

    @Deprecated
    public void appendBoolean(StringBuffer stringBuffer, Boolean bool) {
        appendBoolean((Appendable) stringBuffer, bool);
    }

    public void appendBoolean(Appendable appendable, Boolean bool) {
        if (bool == null) {
            try {
                appendNull(appendable);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            appendable.append(bool.booleanValue() ? RequestConstant.TRUE : RequestConstant.FALSE);
        }
    }

    @Deprecated
    public void appendNumber(StringBuffer stringBuffer, Number number) {
        appendNumber((Appendable) stringBuffer, number);
    }

    public void appendNumber(Appendable appendable, Number number) {
        if (number == null) {
            try {
                appendNull(appendable);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            appendable.append(String.valueOf(number));
        }
    }

    @Deprecated
    public void appendString(StringBuffer stringBuffer, String str) {
        appendString((Appendable) stringBuffer, str);
    }

    public void appendString(Appendable appendable, String str) {
        if (str == null) {
            appendNull(appendable);
        } else {
            QuotedStringTokenizer.quote(appendable, str);
        }
    }

    /* access modifiers changed from: protected */
    public String toString(char[] cArr, int i, int i2) {
        return new String(cArr, i, i2);
    }

    /* access modifiers changed from: protected */
    public Map<String, Object> newMap() {
        return new HashMap();
    }

    /* access modifiers changed from: protected */
    public Object[] newArray(int i) {
        return new Object[i];
    }

    /* access modifiers changed from: protected */
    public Object convertTo(Class cls, Map map) {
        if (cls == null || !Convertible.class.isAssignableFrom(cls)) {
            Convertor convertor = getConvertor(cls);
            return convertor != null ? convertor.fromJSON(map) : map;
        }
        try {
            Convertible convertible = (Convertible) cls.newInstance();
            convertible.fromJSON(map);
            return convertible;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addConvertor(Class cls, Convertor convertor) {
        this._convertors.put(cls.getName(), convertor);
    }

    /* access modifiers changed from: protected */
    public Convertor getConvertor(Class<? super Object> cls) {
        JSON json;
        Convertor convertor = this._convertors.get(cls.getName());
        if (convertor == null && this != (json = DEFAULT)) {
            convertor = json.getConvertor(cls);
        }
        while (convertor == null && cls != Object.class) {
            Class[] interfaces = cls.getInterfaces();
            int i = 0;
            while (convertor == null && interfaces != null && i < interfaces.length) {
                convertor = this._convertors.get(interfaces[i].getName());
                i++;
            }
            if (convertor == null) {
                cls = cls.getSuperclass();
                convertor = this._convertors.get(cls.getName());
            }
        }
        return convertor;
    }

    public void addConvertorFor(String str, Convertor convertor) {
        this._convertors.put(str, convertor);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:2:0x000a, code lost:
        r1 = DEFAULT;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.jetty.util.ajax.JSON.Convertor getConvertorFor(java.lang.String r3) {
        /*
            r2 = this;
            java.util.Map<java.lang.String, org.eclipse.jetty.util.ajax.JSON$Convertor> r0 = r2._convertors
            java.lang.Object r0 = r0.get(r3)
            org.eclipse.jetty.util.ajax.JSON$Convertor r0 = (org.eclipse.jetty.util.ajax.JSON.Convertor) r0
            if (r0 != 0) goto L_0x0012
            org.eclipse.jetty.util.ajax.JSON r1 = DEFAULT
            if (r2 == r1) goto L_0x0012
            org.eclipse.jetty.util.ajax.JSON$Convertor r0 = r1.getConvertorFor(r3)
        L_0x0012:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.getConvertorFor(java.lang.String):org.eclipse.jetty.util.ajax.JSON$Convertor");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0043, code lost:
        if (r4 != 13) goto L_0x005a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object parse(org.eclipse.jetty.util.ajax.JSON.Source r10, boolean r11) {
        /*
            r9 = this;
            if (r11 != 0) goto L_0x0007
            java.lang.Object r10 = r9.parse((org.eclipse.jetty.util.ajax.JSON.Source) r10)
            return r10
        L_0x0007:
            r11 = 0
            r0 = 0
            r1 = 1
            r2 = 0
            r3 = 1
        L_0x000c:
            boolean r4 = r10.hasNext()
            if (r4 == 0) goto L_0x005e
            char r4 = r10.peek()
            r5 = 3
            r6 = 47
            r7 = 42
            r8 = 2
            if (r2 != r1) goto L_0x002a
            if (r4 == r7) goto L_0x0025
            if (r4 == r6) goto L_0x0023
            goto L_0x005a
        L_0x0023:
            r2 = -1
            goto L_0x005a
        L_0x0025:
            if (r3 != r1) goto L_0x0030
            r2 = 0
            r3 = 2
            goto L_0x005a
        L_0x002a:
            if (r2 <= r1) goto L_0x003b
            if (r4 == r7) goto L_0x0039
            if (r4 == r6) goto L_0x0032
        L_0x0030:
            r2 = 2
            goto L_0x005a
        L_0x0032:
            if (r2 != r5) goto L_0x0030
            if (r3 != r8) goto L_0x0037
            return r11
        L_0x0037:
            r2 = 0
            goto L_0x005a
        L_0x0039:
            r2 = 3
            goto L_0x005a
        L_0x003b:
            if (r2 >= 0) goto L_0x0046
            r5 = 10
            if (r4 == r5) goto L_0x0037
            r5 = 13
            if (r4 == r5) goto L_0x0037
            goto L_0x005a
        L_0x0046:
            boolean r8 = java.lang.Character.isWhitespace(r4)
            if (r8 != 0) goto L_0x005a
            if (r4 != r6) goto L_0x0050
            r2 = 1
            goto L_0x005a
        L_0x0050:
            if (r4 != r7) goto L_0x0053
            goto L_0x0039
        L_0x0053:
            if (r11 != 0) goto L_0x005a
            java.lang.Object r11 = r9.parse((org.eclipse.jetty.util.ajax.JSON.Source) r10)
            goto L_0x000c
        L_0x005a:
            r10.next()
            goto L_0x000c
        L_0x005e:
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.parse(org.eclipse.jetty.util.ajax.JSON$Source, boolean):java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0028, code lost:
        if (r1 != 3) goto L_0x0025;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object parse(org.eclipse.jetty.util.ajax.JSON.Source r10) {
        /*
            r9 = this;
            r0 = 0
            r1 = 0
        L_0x0002:
            boolean r2 = r10.hasNext()
            r3 = 0
            if (r2 == 0) goto L_0x00b4
            char r2 = r10.peek()
            r4 = 3
            r5 = 42
            r6 = 2
            r7 = 47
            r8 = 1
            if (r1 != r8) goto L_0x001f
            if (r2 == r5) goto L_0x0025
            if (r2 == r7) goto L_0x001c
            goto L_0x00a5
        L_0x001c:
            r1 = -1
            goto L_0x00a5
        L_0x001f:
            if (r1 <= r8) goto L_0x002e
            if (r2 == r5) goto L_0x002b
            if (r2 == r7) goto L_0x0028
        L_0x0025:
            r1 = 2
            goto L_0x00a5
        L_0x0028:
            if (r1 != r4) goto L_0x0025
            goto L_0x0039
        L_0x002b:
            r1 = 3
            goto L_0x00a5
        L_0x002e:
            if (r1 >= 0) goto L_0x003b
            r3 = 10
            if (r2 == r3) goto L_0x0039
            r3 = 13
            if (r2 == r3) goto L_0x0039
            goto L_0x00a5
        L_0x0039:
            r1 = 0
            goto L_0x00a5
        L_0x003b:
            r4 = 34
            if (r2 == r4) goto L_0x00af
            r4 = 45
            if (r2 == r4) goto L_0x00aa
            if (r2 == r7) goto L_0x00a4
            r4 = 78
            if (r2 == r4) goto L_0x009e
            r4 = 91
            if (r2 == r4) goto L_0x0099
            r4 = 102(0x66, float:1.43E-43)
            if (r2 == r4) goto L_0x0091
            r4 = 110(0x6e, float:1.54E-43)
            if (r2 == r4) goto L_0x008b
            r4 = 123(0x7b, float:1.72E-43)
            if (r2 == r4) goto L_0x0086
            r4 = 116(0x74, float:1.63E-43)
            if (r2 == r4) goto L_0x007e
            r4 = 117(0x75, float:1.64E-43)
            if (r2 == r4) goto L_0x0078
            boolean r3 = java.lang.Character.isDigit(r2)
            if (r3 == 0) goto L_0x006c
            java.lang.Number r10 = r9.parseNumber(r10)
            return r10
        L_0x006c:
            boolean r3 = java.lang.Character.isWhitespace(r2)
            if (r3 == 0) goto L_0x0073
            goto L_0x00a5
        L_0x0073:
            java.lang.Object r10 = r9.handleUnknown(r10, r2)
            return r10
        L_0x0078:
            java.lang.String r0 = "undefined"
            complete(r0, r10)
            return r3
        L_0x007e:
            java.lang.String r0 = "true"
            complete(r0, r10)
            java.lang.Boolean r10 = java.lang.Boolean.TRUE
            return r10
        L_0x0086:
            java.lang.Object r10 = r9.parseObject(r10)
            return r10
        L_0x008b:
            java.lang.String r0 = "null"
            complete(r0, r10)
            return r3
        L_0x0091:
            java.lang.String r0 = "false"
            complete(r0, r10)
            java.lang.Boolean r10 = java.lang.Boolean.FALSE
            return r10
        L_0x0099:
            java.lang.Object r10 = r9.parseArray(r10)
            return r10
        L_0x009e:
            java.lang.String r0 = "NaN"
            complete(r0, r10)
            return r3
        L_0x00a4:
            r1 = 1
        L_0x00a5:
            r10.next()
            goto L_0x0002
        L_0x00aa:
            java.lang.Number r10 = r9.parseNumber(r10)
            return r10
        L_0x00af:
            java.lang.String r10 = r9.parseString(r10)
            return r10
        L_0x00b4:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.parse(org.eclipse.jetty.util.ajax.JSON$Source):java.lang.Object");
    }

    /* access modifiers changed from: protected */
    public Object handleUnknown(Source source, char c) {
        throw new IllegalStateException("unknown char '" + c + "'(" + c + ") in " + source);
    }

    /* access modifiers changed from: protected */
    public Object parseObject(Source source) {
        if (source.next() == '{') {
            Map<String, Object> newMap = newMap();
            char seekTo = seekTo("\"}", source);
            while (true) {
                if (!source.hasNext()) {
                    break;
                } else if (seekTo == '}') {
                    source.next();
                    break;
                } else {
                    String parseString = parseString(source);
                    seekTo(':', source);
                    source.next();
                    newMap.put(parseString, contextFor(parseString).parse(source));
                    seekTo(",}", source);
                    if (source.next() == '}') {
                        break;
                    }
                    seekTo = seekTo("\"}", source);
                }
            }
            String str = (String) newMap.get("x-class");
            if (str != null) {
                Convertor convertorFor = getConvertorFor(str);
                if (convertorFor != null) {
                    return convertorFor.fromJSON(newMap);
                }
                LOG.warn("No Convertor for x-class '{}'", str);
            }
            String str2 = (String) newMap.get("class");
            if (str2 != null) {
                try {
                    return convertTo(Loader.loadClass(JSON.class, str2), newMap);
                } catch (ClassNotFoundException unused) {
                    LOG.warn("No Class for '{}'", str2);
                }
            }
            return newMap;
        }
        throw new IllegalStateException();
    }

    /* access modifiers changed from: protected */
    public Object parseArray(Source source) {
        if (source.next() == '[') {
            ArrayList arrayList = null;
            Object obj = null;
            boolean z = true;
            int i = 0;
            while (source.hasNext()) {
                char peek = source.peek();
                if (peek != ',') {
                    if (peek == ']') {
                        source.next();
                        if (i == 0) {
                            return newArray(0);
                        }
                        if (i != 1) {
                            return arrayList.toArray(newArray(arrayList.size()));
                        }
                        Object[] newArray = newArray(1);
                        Array.set(newArray, 0, obj);
                        return newArray;
                    } else if (Character.isWhitespace(peek)) {
                        source.next();
                    } else {
                        int i2 = i + 1;
                        if (i == 0) {
                            obj = contextForArray().parse(source);
                        } else if (arrayList == null) {
                            ArrayList arrayList2 = new ArrayList();
                            arrayList2.add(obj);
                            arrayList2.add(contextForArray().parse(source));
                            obj = null;
                            arrayList = arrayList2;
                        } else {
                            arrayList.add(contextForArray().parse(source));
                            obj = null;
                        }
                        i = i2;
                        z = false;
                    }
                } else if (!z) {
                    source.next();
                    z = true;
                } else {
                    throw new IllegalStateException();
                }
            }
            throw new IllegalStateException("unexpected end of array");
        }
        throw new IllegalStateException();
    }

    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01a8 A[EDGE_INSN: B:101:0x01a8->B:78:0x01a8 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00f4  */
    protected java.lang.String parseString(org.eclipse.jetty.util.ajax.JSON.Source r21) {
        /*
            r20 = this;
            r0 = r20
            char r1 = r21.next()
            r2 = 34
            if (r1 != r2) goto L_0x01ad
            char[] r3 = r21.scratchBuffer()
            r6 = 117(0x75, float:1.64E-43)
            r7 = 116(0x74, float:1.63E-43)
            r8 = 114(0x72, float:1.6E-43)
            r9 = 110(0x6e, float:1.54E-43)
            r10 = 102(0x66, float:1.43E-43)
            r11 = 98
            r12 = 12
            r14 = 47
            r15 = 92
            r1 = 0
            r17 = 1
            if (r3 == 0) goto L_0x00e3
            r5 = 0
        L_0x0026:
            r18 = 0
        L_0x0028:
            boolean r19 = r21.hasNext()
            if (r19 == 0) goto L_0x00d7
            int r4 = r3.length
            if (r5 < r4) goto L_0x0040
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            int r13 = r3.length
            int r13 = r13 * 2
            r4.<init>(r13)
            r4.append(r3, r1, r5)
            r16 = r4
            goto L_0x00d9
        L_0x0040:
            char r4 = r21.next()
            if (r18 == 0) goto L_0x00c3
            if (r4 == r2) goto L_0x00bc
            if (r4 == r14) goto L_0x00b7
            if (r4 == r15) goto L_0x00b2
            if (r4 == r11) goto L_0x00ab
            if (r4 == r10) goto L_0x00a6
            if (r4 == r9) goto L_0x009f
            if (r4 == r8) goto L_0x0098
            if (r4 == r7) goto L_0x0091
            if (r4 == r6) goto L_0x005d
            int r13 = r5 + 1
            r3[r5] = r4
            goto L_0x00c0
        L_0x005d:
            char r4 = r21.next()
            byte r4 = (byte) r4
            byte r4 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((byte) r4)
            int r4 = r4 << r12
            char r13 = r21.next()
            byte r13 = (byte) r13
            byte r13 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((byte) r13)
            r18 = 8
            int r13 = r13 << 8
            int r4 = r4 + r13
            char r13 = r21.next()
            byte r13 = (byte) r13
            byte r13 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((byte) r13)
            int r13 = r13 << 4
            int r4 = r4 + r13
            char r13 = r21.next()
            byte r13 = (byte) r13
            byte r13 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((byte) r13)
            int r4 = r4 + r13
            char r4 = (char) r4
            int r13 = r5 + 1
            r3[r5] = r4
            goto L_0x00c0
        L_0x0091:
            int r13 = r5 + 1
            r4 = 9
            r3[r5] = r4
            goto L_0x00c0
        L_0x0098:
            int r13 = r5 + 1
            r4 = 13
            r3[r5] = r4
            goto L_0x00c0
        L_0x009f:
            int r13 = r5 + 1
            r4 = 10
            r3[r5] = r4
            goto L_0x00c0
        L_0x00a6:
            int r13 = r5 + 1
            r3[r5] = r12
            goto L_0x00c0
        L_0x00ab:
            int r13 = r5 + 1
            r4 = 8
            r3[r5] = r4
            goto L_0x00c0
        L_0x00b2:
            int r13 = r5 + 1
            r3[r5] = r15
            goto L_0x00c0
        L_0x00b7:
            int r13 = r5 + 1
            r3[r5] = r14
            goto L_0x00c0
        L_0x00bc:
            int r13 = r5 + 1
            r3[r5] = r2
        L_0x00c0:
            r5 = r13
            goto L_0x0026
        L_0x00c3:
            if (r4 != r15) goto L_0x00c9
            r18 = 1
            goto L_0x0028
        L_0x00c9:
            if (r4 != r2) goto L_0x00d0
            java.lang.String r1 = r0.toString(r3, r1, r5)
            return r1
        L_0x00d0:
            int r13 = r5 + 1
            r3[r5] = r4
            r5 = r13
            goto L_0x0028
        L_0x00d7:
            r16 = 0
        L_0x00d9:
            if (r16 != 0) goto L_0x00e0
            java.lang.String r1 = r0.toString(r3, r1, r5)
            return r1
        L_0x00e0:
            r3 = r16
            goto L_0x00ee
        L_0x00e3:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            int r4 = r20.getStringBufferSize()
            r3.<init>(r4)
        L_0x00ec:
            r18 = 0
        L_0x00ee:
            boolean r4 = r21.hasNext()
            if (r4 == 0) goto L_0x01a8
            char r4 = r21.next()
            if (r18 == 0) goto L_0x0194
            if (r4 == r2) goto L_0x0187
            if (r4 == r14) goto L_0x017d
            if (r4 == r15) goto L_0x0173
            if (r4 == r11) goto L_0x0169
            if (r4 == r10) goto L_0x015f
            if (r4 == r9) goto L_0x0157
            if (r4 == r8) goto L_0x014f
            if (r4 == r7) goto L_0x0149
            if (r4 == r6) goto L_0x0117
            r3.append(r4)
        L_0x010f:
            r4 = 8
            r5 = 13
        L_0x0113:
            r13 = 10
            goto L_0x0190
        L_0x0117:
            char r4 = r21.next()
            byte r4 = (byte) r4
            byte r4 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((byte) r4)
            int r4 = r4 << r12
            char r5 = r21.next()
            byte r5 = (byte) r5
            byte r5 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((byte) r5)
            r13 = 8
            int r5 = r5 << r13
            int r4 = r4 + r5
            char r5 = r21.next()
            byte r5 = (byte) r5
            byte r5 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((byte) r5)
            int r5 = r5 << 4
            int r4 = r4 + r5
            char r5 = r21.next()
            byte r5 = (byte) r5
            byte r5 = org.eclipse.jetty.util.TypeUtil.convertHexDigit((byte) r5)
            int r4 = r4 + r5
            char r4 = (char) r4
            r3.append(r4)
            goto L_0x010f
        L_0x0149:
            r4 = 9
            r3.append(r4)
            goto L_0x010f
        L_0x014f:
            r5 = 13
            r3.append(r5)
            r4 = 8
            goto L_0x0113
        L_0x0157:
            r5 = 13
            r13 = 10
            r3.append(r13)
            goto L_0x0166
        L_0x015f:
            r5 = 13
            r13 = 10
            r3.append(r12)
        L_0x0166:
            r4 = 8
            goto L_0x0190
        L_0x0169:
            r4 = 8
            r5 = 13
            r13 = 10
            r3.append(r4)
            goto L_0x0190
        L_0x0173:
            r4 = 8
            r5 = 13
            r13 = 10
            r3.append(r15)
            goto L_0x0190
        L_0x017d:
            r4 = 8
            r5 = 13
            r13 = 10
            r3.append(r14)
            goto L_0x0190
        L_0x0187:
            r4 = 8
            r5 = 13
            r13 = 10
            r3.append(r2)
        L_0x0190:
            r16 = 8
            goto L_0x00ec
        L_0x0194:
            r5 = 13
            r13 = 10
            r16 = 8
            if (r4 != r15) goto L_0x01a0
            r18 = 1
            goto L_0x00ee
        L_0x01a0:
            if (r4 != r2) goto L_0x01a3
            goto L_0x01a8
        L_0x01a3:
            r3.append(r4)
            goto L_0x00ee
        L_0x01a8:
            java.lang.String r1 = r3.toString()
            return r1
        L_0x01ad:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            r1.<init>()
            goto L_0x01b4
        L_0x01b3:
            throw r1
        L_0x01b4:
            goto L_0x01b3
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.parseString(org.eclipse.jetty.util.ajax.JSON$Source):java.lang.String");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005a, code lost:
        r0 = null;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Number parseNumber(org.eclipse.jetty.util.ajax.JSON.Source r12) {
        /*
            r11 = this;
            r0 = 0
            r2 = 0
            r2 = r0
            r4 = 0
        L_0x0005:
            boolean r5 = r12.hasNext()
            r6 = 46
            r7 = 101(0x65, float:1.42E-43)
            r8 = 69
            r9 = 43
            r10 = 45
            if (r5 == 0) goto L_0x005a
            char r5 = r12.peek()
            if (r5 == r9) goto L_0x0049
            if (r5 == r8) goto L_0x0033
            if (r5 == r7) goto L_0x0033
            if (r5 == r10) goto L_0x0049
            if (r5 == r6) goto L_0x0033
            switch(r5) {
                case 48: goto L_0x0027;
                case 49: goto L_0x0027;
                case 50: goto L_0x0027;
                case 51: goto L_0x0027;
                case 52: goto L_0x0027;
                case 53: goto L_0x0027;
                case 54: goto L_0x0027;
                case 55: goto L_0x0027;
                case 56: goto L_0x0027;
                case 57: goto L_0x0027;
                default: goto L_0x0026;
            }
        L_0x0026:
            goto L_0x005a
        L_0x0027:
            r6 = 10
            long r2 = r2 * r6
            int r5 = r5 + -48
            long r5 = (long) r5
            long r2 = r2 + r5
            r12.next()
            goto L_0x0005
        L_0x0033:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r1 = 16
            r0.<init>(r1)
            if (r4 == 0) goto L_0x003f
            r0.append(r10)
        L_0x003f:
            r0.append(r2)
            r0.append(r5)
            r12.next()
            goto L_0x005b
        L_0x0049:
            int r4 = (r2 > r0 ? 1 : (r2 == r0 ? 0 : -1))
            if (r4 != 0) goto L_0x0052
            r4 = 1
            r12.next()
            goto L_0x0005
        L_0x0052:
            java.lang.IllegalStateException r12 = new java.lang.IllegalStateException
            java.lang.String r0 = "bad number"
            r12.<init>(r0)
            throw r12
        L_0x005a:
            r0 = 0
        L_0x005b:
            if (r0 != 0) goto L_0x0068
            if (r4 == 0) goto L_0x0063
            r0 = -1
            long r2 = r2 * r0
        L_0x0063:
            java.lang.Long r12 = java.lang.Long.valueOf(r2)
            return r12
        L_0x0068:
            boolean r1 = r12.hasNext()
            if (r1 == 0) goto L_0x0087
            char r1 = r12.peek()
            if (r1 == r9) goto L_0x0080
            if (r1 == r8) goto L_0x0080
            if (r1 == r7) goto L_0x0080
            if (r1 == r10) goto L_0x0080
            if (r1 == r6) goto L_0x0080
            switch(r1) {
                case 48: goto L_0x0080;
                case 49: goto L_0x0080;
                case 50: goto L_0x0080;
                case 51: goto L_0x0080;
                case 52: goto L_0x0080;
                case 53: goto L_0x0080;
                case 54: goto L_0x0080;
                case 55: goto L_0x0080;
                case 56: goto L_0x0080;
                case 57: goto L_0x0080;
                default: goto L_0x007f;
            }
        L_0x007f:
            goto L_0x0087
        L_0x0080:
            r0.append(r1)
            r12.next()
            goto L_0x0068
        L_0x0087:
            java.lang.Double r12 = new java.lang.Double
            java.lang.String r0 = r0.toString()
            r12.<init>(r0)
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.ajax.JSON.parseNumber(org.eclipse.jetty.util.ajax.JSON$Source):java.lang.Number");
    }

    /* access modifiers changed from: protected */
    public void seekTo(char c, Source source) {
        while (source.hasNext()) {
            char peek = source.peek();
            if (peek != c) {
                if (Character.isWhitespace(peek)) {
                    source.next();
                } else {
                    throw new IllegalStateException("Unexpected '" + peek + " while seeking '" + c + "'");
                }
            } else {
                return;
            }
        }
        throw new IllegalStateException("Expected '" + c + "'");
    }

    /* access modifiers changed from: protected */
    public char seekTo(String str, Source source) {
        while (source.hasNext()) {
            char peek = source.peek();
            if (str.indexOf(peek) >= 0) {
                return peek;
            }
            if (Character.isWhitespace(peek)) {
                source.next();
            } else {
                throw new IllegalStateException("Unexpected '" + peek + "' while seeking one of '" + str + "'");
            }
        }
        throw new IllegalStateException("Expected one of '" + str + "'");
    }

    protected static void complete(String str, Source source) {
        int i = 0;
        while (source.hasNext() && i < str.length()) {
            char next = source.next();
            int i2 = i + 1;
            if (next == str.charAt(i)) {
                i = i2;
            } else {
                throw new IllegalStateException("Unexpected '" + next + " while seeking  \"" + str + "\"");
            }
        }
        if (i < str.length()) {
            throw new IllegalStateException("Expected \"" + str + "\"");
        }
    }

    private final class ConvertableOutput implements Output {
        private final Appendable _buffer;

        /* renamed from: c */
        char f4116c;

        private ConvertableOutput(Appendable appendable) {
            this.f4116c = '{';
            this._buffer = appendable;
        }

        public void complete() {
            try {
                if (this.f4116c == '{') {
                    this._buffer.append("{}");
                } else if (this.f4116c != 0) {
                    this._buffer.append("}");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void add(Object obj) {
            if (this.f4116c != 0) {
                JSON.this.append(this._buffer, obj);
                this.f4116c = 0;
                return;
            }
            throw new IllegalStateException();
        }

        public void addClass(Class cls) {
            try {
                if (this.f4116c != 0) {
                    this._buffer.append(this.f4116c);
                    this._buffer.append("\"class\":");
                    JSON.this.append(this._buffer, (Object) cls.getName());
                    this.f4116c = ',';
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void add(String str, Object obj) {
            try {
                if (this.f4116c != 0) {
                    this._buffer.append(this.f4116c);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.append(this._buffer, obj);
                    this.f4116c = ',';
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void add(String str, double d) {
            try {
                if (this.f4116c != 0) {
                    this._buffer.append(this.f4116c);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.appendNumber(this._buffer, (Number) Double.valueOf(d));
                    this.f4116c = ',';
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void add(String str, long j) {
            try {
                if (this.f4116c != 0) {
                    this._buffer.append(this.f4116c);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.appendNumber(this._buffer, (Number) Long.valueOf(j));
                    this.f4116c = ',';
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void add(String str, boolean z) {
            try {
                if (this.f4116c != 0) {
                    this._buffer.append(this.f4116c);
                    QuotedStringTokenizer.quote(this._buffer, str);
                    this._buffer.append(':');
                    JSON.this.appendBoolean(this._buffer, z ? Boolean.TRUE : Boolean.FALSE);
                    this.f4116c = ',';
                    return;
                }
                throw new IllegalStateException();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class StringSource implements Source {
        private int index;
        private char[] scratch;
        private final String string;

        public StringSource(String str) {
            this.string = str;
        }

        public boolean hasNext() {
            if (this.index < this.string.length()) {
                return true;
            }
            this.scratch = null;
            return false;
        }

        public char next() {
            String str = this.string;
            int i = this.index;
            this.index = i + 1;
            return str.charAt(i);
        }

        public char peek() {
            return this.string.charAt(this.index);
        }

        public String toString() {
            return this.string.substring(0, this.index) + "|||" + this.string.substring(this.index);
        }

        public char[] scratchBuffer() {
            if (this.scratch == null) {
                this.scratch = new char[this.string.length()];
            }
            return this.scratch;
        }
    }

    public static class ReaderSource implements Source {
        private int _next = -1;
        private Reader _reader;
        private char[] scratch;

        public ReaderSource(Reader reader) {
            this._reader = reader;
        }

        public void setReader(Reader reader) {
            this._reader = reader;
            this._next = -1;
        }

        public boolean hasNext() {
            getNext();
            if (this._next >= 0) {
                return true;
            }
            this.scratch = null;
            return false;
        }

        public char next() {
            getNext();
            char c = (char) this._next;
            this._next = -1;
            return c;
        }

        public char peek() {
            getNext();
            return (char) this._next;
        }

        private void getNext() {
            if (this._next < 0) {
                try {
                    this._next = this._reader.read();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        public char[] scratchBuffer() {
            if (this.scratch == null) {
                this.scratch = new char[1024];
            }
            return this.scratch;
        }
    }

    public static class Literal implements Generator {
        private String _json;

        public Literal(String str) {
            if (JSON.LOG.isDebugEnabled()) {
                JSON.parse(str);
            }
            this._json = str;
        }

        public String toString() {
            return this._json;
        }

        public void addJSON(Appendable appendable) {
            try {
                appendable.append(this._json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
