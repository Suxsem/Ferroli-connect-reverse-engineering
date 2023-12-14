package com.alibaba.sdk.android.tbrest.rest;

/* renamed from: com.alibaba.sdk.android.tbrest.rest.b */
/* compiled from: RestHttpUtils */
public class C0884b {
    /* JADX WARNING: Removed duplicated region for block: B:108:0x01c4 A[Catch:{ IOException -> 0x01de }, LOOP:1: B:106:0x01bd->B:108:0x01c4, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x01c8 A[EDGE_INSN: B:109:0x01c8->B:110:? ?: BREAK  , SYNTHETIC, Splitter:B:109:0x01c8] */
    /* JADX WARNING: Removed duplicated region for block: B:115:0x01d7  */
    /* JADX WARNING: Removed duplicated region for block: B:117:0x01dc A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:126:0x01ec A[SYNTHETIC, Splitter:B:126:0x01ec] */
    /* JADX WARNING: Removed duplicated region for block: B:133:0x01fd A[SYNTHETIC, Splitter:B:133:0x01fd] */
    /* JADX WARNING: Removed duplicated region for block: B:152:0x0224 A[SYNTHETIC, Splitter:B:152:0x0224] */
    /* JADX WARNING: Removed duplicated region for block: B:176:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] m1013a(int r18, java.lang.String r19, java.util.Map<java.lang.String, java.lang.Object> r20, boolean r21) {
        /*
            r1 = r18
            r2 = r20
            java.lang.String r3 = "write out error!"
            java.lang.String r4 = "connection error!"
            java.lang.String r5 = "out close error!"
            boolean r0 = com.alibaba.sdk.android.tbrest.utils.StringUtils.isEmpty(r19)
            r6 = 0
            if (r0 == 0) goto L_0x0012
            return r6
        L_0x0012:
            java.net.URL r0 = new java.net.URL     // Catch:{ MalformedURLException -> 0x023b, IOException -> 0x0236 }
            r7 = r19
            r0.<init>(r7)     // Catch:{ MalformedURLException -> 0x023b, IOException -> 0x0236 }
            java.net.URLConnection r0 = r0.openConnection()     // Catch:{ MalformedURLException -> 0x023b, IOException -> 0x0236 }
            r7 = r0
            java.net.HttpURLConnection r7 = (java.net.HttpURLConnection) r7     // Catch:{ MalformedURLException -> 0x023b, IOException -> 0x0236 }
            if (r7 == 0) goto L_0x0234
            r8 = 3
            r9 = 1
            r10 = 2
            if (r1 == r10) goto L_0x0029
            if (r1 != r8) goto L_0x002c
        L_0x0029:
            r7.setDoOutput(r9)
        L_0x002c:
            r7.setDoInput(r9)
            if (r1 == r10) goto L_0x003a
            if (r1 != r8) goto L_0x0034
            goto L_0x003a
        L_0x0034:
            java.lang.String r0 = "GET"
            r7.setRequestMethod(r0)     // Catch:{ ProtocolException -> 0x022e }
            goto L_0x003f
        L_0x003a:
            java.lang.String r0 = "POST"
            r7.setRequestMethod(r0)     // Catch:{ ProtocolException -> 0x022e }
        L_0x003f:
            r4 = 0
            r7.setUseCaches(r4)
            r0 = 10000(0x2710, float:1.4013E-41)
            r7.setConnectTimeout(r0)
            r0 = 60000(0xea60, float:8.4078E-41)
            r7.setReadTimeout(r0)
            java.lang.String r0 = "Connection"
            java.lang.String r11 = "close"
            r7.setRequestProperty(r0, r11)
            if (r21 == 0) goto L_0x005e
            java.lang.String r0 = "Accept-Encoding"
            java.lang.String r11 = "gzip,deflate"
            r7.setRequestProperty(r0, r11)
        L_0x005e:
            r7.setInstanceFollowRedirects(r9)
            if (r1 == r10) goto L_0x0065
            if (r1 != r8) goto L_0x015e
        L_0x0065:
            java.lang.String r0 = "Content-Type"
            if (r1 != r10) goto L_0x006f
            java.lang.String r11 = "multipart/form-data; boundary=GJircTeP"
            r7.setRequestProperty(r0, r11)
            goto L_0x0076
        L_0x006f:
            if (r1 != r8) goto L_0x0076
            java.lang.String r11 = "application/x-www-form-urlencoded"
            r7.setRequestProperty(r0, r11)
        L_0x0076:
            if (r2 == 0) goto L_0x014f
            int r0 = r20.size()
            if (r0 <= 0) goto L_0x014f
            java.io.ByteArrayOutputStream r11 = new java.io.ByteArrayOutputStream
            r11.<init>()
            java.util.Set r0 = r20.keySet()
            int r12 = r0.size()
            java.lang.String[] r12 = new java.lang.String[r12]
            r0.toArray(r12)
            com.alibaba.sdk.android.tbrest.rest.c r0 = com.alibaba.sdk.android.tbrest.rest.C0885c.m1014a()
            java.lang.String[] r12 = r0.mo10138a(r12, r9)
            int r13 = r12.length
            r14 = 0
        L_0x009a:
            java.lang.String r15 = "write lBaos error!"
            if (r14 >= r13) goto L_0x0139
            r0 = r12[r14]
            if (r1 != r10) goto L_0x00dc
            java.lang.Object r16 = r2.get(r0)
            byte[] r16 = (byte[]) r16
            r6 = r16
            byte[] r6 = (byte[]) r6
            if (r6 == 0) goto L_0x00d9
            java.lang.String r8 = "--GJircTeP\r\nContent-Disposition: form-data; name=\"%s\"; filename=\"%s\"\r\nContent-Type: application/octet-stream \r\n\r\n"
            java.lang.Object[] r9 = new java.lang.Object[r10]     // Catch:{ IOException -> 0x00d2 }
            r9[r4] = r0     // Catch:{ IOException -> 0x00d2 }
            r16 = 1
            r9[r16] = r0     // Catch:{ IOException -> 0x00d0 }
            java.lang.String r0 = java.lang.String.format(r8, r9)     // Catch:{ IOException -> 0x00d0 }
            byte[] r0 = r0.getBytes()     // Catch:{ IOException -> 0x00d0 }
            r11.write(r0)     // Catch:{ IOException -> 0x00d0 }
            r11.write(r6)     // Catch:{ IOException -> 0x00d0 }
            java.lang.String r0 = "\r\n"
            byte[] r0 = r0.getBytes()     // Catch:{ IOException -> 0x00d0 }
            r11.write(r0)     // Catch:{ IOException -> 0x00d0 }
            goto L_0x0131
        L_0x00d0:
            r0 = move-exception
            goto L_0x00d5
        L_0x00d2:
            r0 = move-exception
            r16 = 1
        L_0x00d5:
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r15, r0)
            goto L_0x0131
        L_0x00d9:
            r16 = 1
            goto L_0x0131
        L_0x00dc:
            r6 = 3
            r16 = 1
            if (r1 != r6) goto L_0x0131
            java.lang.Object r6 = r2.get(r0)
            java.lang.String r6 = (java.lang.String) r6
            int r8 = r11.size()
            java.lang.String r9 = "="
            if (r8 <= 0) goto L_0x0113
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x010e }
            r8.<init>()     // Catch:{ IOException -> 0x010e }
            java.lang.String r4 = "&"
            r8.append(r4)     // Catch:{ IOException -> 0x010e }
            r8.append(r0)     // Catch:{ IOException -> 0x010e }
            r8.append(r9)     // Catch:{ IOException -> 0x010e }
            r8.append(r6)     // Catch:{ IOException -> 0x010e }
            java.lang.String r0 = r8.toString()     // Catch:{ IOException -> 0x010e }
            byte[] r0 = r0.getBytes()     // Catch:{ IOException -> 0x010e }
            r11.write(r0)     // Catch:{ IOException -> 0x010e }
            goto L_0x0131
        L_0x010e:
            r0 = move-exception
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r15, r0)
            goto L_0x0131
        L_0x0113:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x012d }
            r4.<init>()     // Catch:{ IOException -> 0x012d }
            r4.append(r0)     // Catch:{ IOException -> 0x012d }
            r4.append(r9)     // Catch:{ IOException -> 0x012d }
            r4.append(r6)     // Catch:{ IOException -> 0x012d }
            java.lang.String r0 = r4.toString()     // Catch:{ IOException -> 0x012d }
            byte[] r0 = r0.getBytes()     // Catch:{ IOException -> 0x012d }
            r11.write(r0)     // Catch:{ IOException -> 0x012d }
            goto L_0x0131
        L_0x012d:
            r0 = move-exception
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r15, r0)
        L_0x0131:
            int r14 = r14 + 1
            r4 = 0
            r6 = 0
            r8 = 3
            r9 = 1
            goto L_0x009a
        L_0x0139:
            if (r1 != r10) goto L_0x0149
            java.lang.String r0 = "--GJircTeP--\r\n"
            byte[] r0 = r0.getBytes()     // Catch:{ IOException -> 0x0145 }
            r11.write(r0)     // Catch:{ IOException -> 0x0145 }
            goto L_0x0149
        L_0x0145:
            r0 = move-exception
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r15, r0)
        L_0x0149:
            byte[] r0 = r11.toByteArray()
            r6 = r0
            goto L_0x0150
        L_0x014f:
            r6 = 0
        L_0x0150:
            if (r6 == 0) goto L_0x0154
            int r4 = r6.length
            goto L_0x0155
        L_0x0154:
            r4 = 0
        L_0x0155:
            java.lang.String r0 = java.lang.String.valueOf(r4)
            java.lang.String r2 = "Content-Length"
            r7.setRequestProperty(r2, r0)
        L_0x015e:
            r7.connect()     // Catch:{ Exception -> 0x020c, all -> 0x0207 }
            if (r1 == r10) goto L_0x0166
            r2 = 3
            if (r1 != r2) goto L_0x0185
        L_0x0166:
            if (r6 == 0) goto L_0x0185
            int r0 = r6.length     // Catch:{ Exception -> 0x020c, all -> 0x0207 }
            if (r0 <= 0) goto L_0x0185
            java.io.DataOutputStream r1 = new java.io.DataOutputStream     // Catch:{ Exception -> 0x020c, all -> 0x0207 }
            java.io.OutputStream r0 = r7.getOutputStream()     // Catch:{ Exception -> 0x020c, all -> 0x0207 }
            r1.<init>(r0)     // Catch:{ Exception -> 0x020c, all -> 0x0207 }
            r1.write(r6)     // Catch:{ Exception -> 0x0181, all -> 0x017b }
            r1.flush()     // Catch:{ Exception -> 0x0181, all -> 0x017b }
            goto L_0x0186
        L_0x017b:
            r0 = move-exception
            r17 = r1
            r1 = r0
            goto L_0x0222
        L_0x0181:
            r0 = move-exception
            r6 = r1
            goto L_0x020e
        L_0x0185:
            r1 = 0
        L_0x0186:
            if (r1 == 0) goto L_0x0191
            r1.close()     // Catch:{ IOException -> 0x018c }
            goto L_0x0191
        L_0x018c:
            r0 = move-exception
            r1 = r0
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r5, r1)
        L_0x0191:
            java.io.ByteArrayOutputStream r1 = new java.io.ByteArrayOutputStream
            r1.<init>()
            if (r21 == 0) goto L_0x01ae
            java.lang.String r0 = "gzip"
            java.lang.String r2 = r7.getContentEncoding()     // Catch:{ IOException -> 0x01e5, all -> 0x01e0 }
            boolean r0 = r0.equals(r2)     // Catch:{ IOException -> 0x01e5, all -> 0x01e0 }
            if (r0 == 0) goto L_0x01ae
            java.util.zip.GZIPInputStream r0 = new java.util.zip.GZIPInputStream     // Catch:{ IOException -> 0x01e5, all -> 0x01e0 }
            java.io.InputStream r2 = r7.getInputStream()     // Catch:{ IOException -> 0x01e5, all -> 0x01e0 }
            r0.<init>(r2)     // Catch:{ IOException -> 0x01e5, all -> 0x01e0 }
            goto L_0x01b7
        L_0x01ae:
            java.io.DataInputStream r0 = new java.io.DataInputStream     // Catch:{ IOException -> 0x01e5, all -> 0x01e0 }
            java.io.InputStream r2 = r7.getInputStream()     // Catch:{ IOException -> 0x01e5, all -> 0x01e0 }
            r0.<init>(r2)     // Catch:{ IOException -> 0x01e5, all -> 0x01e0 }
        L_0x01b7:
            r6 = r0
            r0 = 2048(0x800, float:2.87E-42)
            byte[] r2 = new byte[r0]     // Catch:{ IOException -> 0x01de }
            r4 = 0
        L_0x01bd:
            int r7 = r6.read(r2, r4, r0)     // Catch:{ IOException -> 0x01de }
            r8 = -1
            if (r7 == r8) goto L_0x01c8
            r1.write(r2, r4, r7)     // Catch:{ IOException -> 0x01de }
            goto L_0x01bd
        L_0x01c8:
            r6.close()     // Catch:{ Exception -> 0x01cc }
            goto L_0x01d1
        L_0x01cc:
            r0 = move-exception
            r2 = r0
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r5, r2)
        L_0x01d1:
            int r0 = r1.size()
            if (r0 <= 0) goto L_0x01dc
            byte[] r0 = r1.toByteArray()
            return r0
        L_0x01dc:
            r1 = 0
            return r1
        L_0x01de:
            r0 = move-exception
            goto L_0x01e7
        L_0x01e0:
            r0 = move-exception
            r1 = r0
            r17 = 0
            goto L_0x01fb
        L_0x01e5:
            r0 = move-exception
            r6 = 0
        L_0x01e7:
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r3, r0)     // Catch:{ all -> 0x01f7 }
            if (r6 == 0) goto L_0x01f5
            r6.close()     // Catch:{ Exception -> 0x01f0 }
            goto L_0x01f5
        L_0x01f0:
            r0 = move-exception
            r1 = r0
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r5, r1)
        L_0x01f5:
            r1 = 0
            return r1
        L_0x01f7:
            r0 = move-exception
            r1 = r0
            r17 = r6
        L_0x01fb:
            if (r17 == 0) goto L_0x0206
            r17.close()     // Catch:{ Exception -> 0x0201 }
            goto L_0x0206
        L_0x0201:
            r0 = move-exception
            r2 = r0
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r5, r2)
        L_0x0206:
            throw r1
        L_0x0207:
            r0 = move-exception
            r1 = r0
            r17 = 0
            goto L_0x0222
        L_0x020c:
            r0 = move-exception
            r6 = 0
        L_0x020e:
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r3, r0)     // Catch:{ all -> 0x021e }
            if (r6 == 0) goto L_0x021c
            r6.close()     // Catch:{ IOException -> 0x0217 }
            goto L_0x021c
        L_0x0217:
            r0 = move-exception
            r1 = r0
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r5, r1)
        L_0x021c:
            r1 = 0
            return r1
        L_0x021e:
            r0 = move-exception
            r1 = r0
            r17 = r6
        L_0x0222:
            if (r17 == 0) goto L_0x022d
            r17.close()     // Catch:{ IOException -> 0x0228 }
            goto L_0x022d
        L_0x0228:
            r0 = move-exception
            r2 = r0
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r5, r2)
        L_0x022d:
            throw r1
        L_0x022e:
            r0 = move-exception
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r4, r0)
        L_0x0232:
            r1 = 0
            return r1
        L_0x0234:
            r1 = r6
            return r1
        L_0x0236:
            r0 = move-exception
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r4, r0)
            goto L_0x0232
        L_0x023b:
            r0 = move-exception
            r1 = r6
            com.alibaba.sdk.android.tbrest.utils.LogUtil.m1031e(r4, r0)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.sdk.android.tbrest.rest.C0884b.m1013a(int, java.lang.String, java.util.Map, boolean):byte[]");
    }
}
