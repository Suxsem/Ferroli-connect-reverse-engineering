package org.eclipse.jetty.security.authentication;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.BitSet;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.eclipse.jetty.http.HttpTokens;
import org.eclipse.jetty.security.Authenticator;
import org.eclipse.jetty.security.ServerAuthException;
import org.eclipse.jetty.server.Authentication;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.B64Code;
import org.eclipse.jetty.util.StringUtil;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Credential;

public class DigestAuthenticator extends LoginAuthenticator {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) DigestAuthenticator.class);
    private int _maxNC = 1024;
    private long _maxNonceAgeMs = 60000;
    private ConcurrentMap<String, Nonce> _nonceMap = new ConcurrentHashMap();
    private Queue<Nonce> _nonceQueue = new ConcurrentLinkedQueue();
    SecureRandom _random = new SecureRandom();

    public String getAuthMethod() {
        return "DIGEST";
    }

    public boolean secureResponse(ServletRequest servletRequest, ServletResponse servletResponse, boolean z, Authentication.User user) throws ServerAuthException {
        return true;
    }

    private static class Nonce {
        final String _nonce;
        final BitSet _seen;
        final long _ts;

        public Nonce(String str, long j, int i) {
            this._nonce = str;
            this._ts = j;
            this._seen = new BitSet(i);
        }

        public boolean seen(int i) {
            synchronized (this) {
                if (i >= this._seen.size()) {
                    return true;
                }
                boolean z = this._seen.get(i);
                this._seen.set(i);
                return z;
            }
        }
    }

    public void setConfiguration(Authenticator.AuthConfiguration authConfiguration) {
        super.setConfiguration(authConfiguration);
        String initParameter = authConfiguration.getInitParameter("maxNonceAge");
        if (initParameter != null) {
            this._maxNonceAgeMs = Long.valueOf(initParameter).longValue();
        }
    }

    public int getMaxNonceCount() {
        return this._maxNC;
    }

    public void setMaxNonceCount(int i) {
        this._maxNC = i;
    }

    public void setMaxNonceAge(long j) {
        this._maxNonceAgeMs = j;
    }

    public long getMaxNonceAge() {
        return this._maxNonceAgeMs;
    }

    /* JADX WARNING: Removed duplicated region for block: B:62:0x00f2 A[Catch:{ IOException -> 0x0144 }] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0141 A[Catch:{ IOException -> 0x0144 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.eclipse.jetty.server.Authentication validateRequest(javax.servlet.ServletRequest r11, javax.servlet.ServletResponse r12, boolean r13) throws org.eclipse.jetty.security.ServerAuthException {
        /*
            r10 = this;
            if (r13 != 0) goto L_0x0008
            org.eclipse.jetty.security.authentication.DeferredAuthentication r11 = new org.eclipse.jetty.security.authentication.DeferredAuthentication
            r11.<init>(r10)
            return r11
        L_0x0008:
            r13 = r11
            javax.servlet.http.HttpServletRequest r13 = (javax.servlet.http.HttpServletRequest) r13
            javax.servlet.http.HttpServletResponse r12 = (javax.servlet.http.HttpServletResponse) r12
            java.lang.String r0 = "Authorization"
            java.lang.String r0 = r13.getHeader(r0)
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x00eb
            org.eclipse.jetty.util.log.Logger r3 = LOG     // Catch:{ IOException -> 0x0144 }
            boolean r3 = r3.isDebugEnabled()     // Catch:{ IOException -> 0x0144 }
            if (r3 == 0) goto L_0x0037
            org.eclipse.jetty.util.log.Logger r3 = LOG     // Catch:{ IOException -> 0x0144 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0144 }
            r4.<init>()     // Catch:{ IOException -> 0x0144 }
            java.lang.String r5 = "Credentials: "
            r4.append(r5)     // Catch:{ IOException -> 0x0144 }
            r4.append(r0)     // Catch:{ IOException -> 0x0144 }
            java.lang.String r4 = r4.toString()     // Catch:{ IOException -> 0x0144 }
            java.lang.Object[] r5 = new java.lang.Object[r2]     // Catch:{ IOException -> 0x0144 }
            r3.debug((java.lang.String) r4, (java.lang.Object[]) r5)     // Catch:{ IOException -> 0x0144 }
        L_0x0037:
            org.eclipse.jetty.util.QuotedStringTokenizer r3 = new org.eclipse.jetty.util.QuotedStringTokenizer     // Catch:{ IOException -> 0x0144 }
            java.lang.String r4 = "=, "
            r3.<init>(r0, r4, r1, r2)     // Catch:{ IOException -> 0x0144 }
            org.eclipse.jetty.security.authentication.DigestAuthenticator$Digest r0 = new org.eclipse.jetty.security.authentication.DigestAuthenticator$Digest     // Catch:{ IOException -> 0x0144 }
            java.lang.String r4 = r13.getMethod()     // Catch:{ IOException -> 0x0144 }
            r0.<init>(r4)     // Catch:{ IOException -> 0x0144 }
            r4 = 0
            r5 = r4
            r6 = r5
        L_0x004a:
            boolean r7 = r3.hasMoreTokens()     // Catch:{ IOException -> 0x0144 }
            if (r7 == 0) goto L_0x00cd
            java.lang.String r7 = r3.nextToken()     // Catch:{ IOException -> 0x0144 }
            int r8 = r7.length()     // Catch:{ IOException -> 0x0144 }
            if (r8 != r1) goto L_0x005f
            char r8 = r7.charAt(r2)     // Catch:{ IOException -> 0x0144 }
            goto L_0x0060
        L_0x005f:
            r8 = 0
        L_0x0060:
            r9 = 32
            if (r8 == r9) goto L_0x004a
            r9 = 44
            if (r8 == r9) goto L_0x00ca
            r9 = 61
            if (r8 == r9) goto L_0x00c8
            if (r5 == 0) goto L_0x00c6
            java.lang.String r6 = "username"
            boolean r6 = r6.equalsIgnoreCase(r5)     // Catch:{ IOException -> 0x0144 }
            if (r6 == 0) goto L_0x0079
            r0.username = r7     // Catch:{ IOException -> 0x0144 }
            goto L_0x00c5
        L_0x0079:
            java.lang.String r6 = "realm"
            boolean r6 = r6.equalsIgnoreCase(r5)     // Catch:{ IOException -> 0x0144 }
            if (r6 == 0) goto L_0x0084
            r0.realm = r7     // Catch:{ IOException -> 0x0144 }
            goto L_0x00c5
        L_0x0084:
            java.lang.String r6 = "nonce"
            boolean r6 = r6.equalsIgnoreCase(r5)     // Catch:{ IOException -> 0x0144 }
            if (r6 == 0) goto L_0x008f
            r0.nonce = r7     // Catch:{ IOException -> 0x0144 }
            goto L_0x00c5
        L_0x008f:
            java.lang.String r6 = "nc"
            boolean r6 = r6.equalsIgnoreCase(r5)     // Catch:{ IOException -> 0x0144 }
            if (r6 == 0) goto L_0x009a
            r0.f4111nc = r7     // Catch:{ IOException -> 0x0144 }
            goto L_0x00c5
        L_0x009a:
            java.lang.String r6 = "cnonce"
            boolean r6 = r6.equalsIgnoreCase(r5)     // Catch:{ IOException -> 0x0144 }
            if (r6 == 0) goto L_0x00a5
            r0.cnonce = r7     // Catch:{ IOException -> 0x0144 }
            goto L_0x00c5
        L_0x00a5:
            java.lang.String r6 = "qop"
            boolean r6 = r6.equalsIgnoreCase(r5)     // Catch:{ IOException -> 0x0144 }
            if (r6 == 0) goto L_0x00b0
            r0.qop = r7     // Catch:{ IOException -> 0x0144 }
            goto L_0x00c5
        L_0x00b0:
            java.lang.String r6 = "uri"
            boolean r6 = r6.equalsIgnoreCase(r5)     // Catch:{ IOException -> 0x0144 }
            if (r6 == 0) goto L_0x00bb
            r0.uri = r7     // Catch:{ IOException -> 0x0144 }
            goto L_0x00c5
        L_0x00bb:
            java.lang.String r6 = "response"
            boolean r5 = r6.equalsIgnoreCase(r5)     // Catch:{ IOException -> 0x0144 }
            if (r5 == 0) goto L_0x00c5
            r0.response = r7     // Catch:{ IOException -> 0x0144 }
        L_0x00c5:
            r5 = r4
        L_0x00c6:
            r6 = r7
            goto L_0x004a
        L_0x00c8:
            r5 = r6
            goto L_0x00c6
        L_0x00ca:
            r5 = r4
            goto L_0x004a
        L_0x00cd:
            r3 = r13
            org.eclipse.jetty.server.Request r3 = (org.eclipse.jetty.server.Request) r3     // Catch:{ IOException -> 0x0144 }
            int r3 = r10.checkNonce(r0, r3)     // Catch:{ IOException -> 0x0144 }
            if (r3 <= 0) goto L_0x00e8
            java.lang.String r1 = r0.username     // Catch:{ IOException -> 0x0144 }
            org.eclipse.jetty.server.UserIdentity r11 = r10.login(r1, r0, r11)     // Catch:{ IOException -> 0x0144 }
            if (r11 == 0) goto L_0x00eb
            org.eclipse.jetty.security.UserAuthentication r12 = new org.eclipse.jetty.security.UserAuthentication     // Catch:{ IOException -> 0x0144 }
            java.lang.String r13 = r10.getAuthMethod()     // Catch:{ IOException -> 0x0144 }
            r12.<init>(r13, r11)     // Catch:{ IOException -> 0x0144 }
            return r12
        L_0x00e8:
            if (r3 != 0) goto L_0x00eb
            goto L_0x00ec
        L_0x00eb:
            r1 = 0
        L_0x00ec:
            boolean r11 = org.eclipse.jetty.security.authentication.DeferredAuthentication.isDeferred(r12)     // Catch:{ IOException -> 0x0144 }
            if (r11 != 0) goto L_0x0141
            java.lang.String r11 = r13.getContextPath()     // Catch:{ IOException -> 0x0144 }
            if (r11 != 0) goto L_0x00fa
            java.lang.String r11 = "/"
        L_0x00fa:
            java.lang.String r0 = "WWW-Authenticate"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0144 }
            r2.<init>()     // Catch:{ IOException -> 0x0144 }
            java.lang.String r3 = "Digest realm=\""
            r2.append(r3)     // Catch:{ IOException -> 0x0144 }
            org.eclipse.jetty.security.LoginService r3 = r10._loginService     // Catch:{ IOException -> 0x0144 }
            java.lang.String r3 = r3.getName()     // Catch:{ IOException -> 0x0144 }
            r2.append(r3)     // Catch:{ IOException -> 0x0144 }
            java.lang.String r3 = "\", domain=\""
            r2.append(r3)     // Catch:{ IOException -> 0x0144 }
            r2.append(r11)     // Catch:{ IOException -> 0x0144 }
            java.lang.String r11 = "\", nonce=\""
            r2.append(r11)     // Catch:{ IOException -> 0x0144 }
            org.eclipse.jetty.server.Request r13 = (org.eclipse.jetty.server.Request) r13     // Catch:{ IOException -> 0x0144 }
            java.lang.String r11 = r10.newNonce(r13)     // Catch:{ IOException -> 0x0144 }
            r2.append(r11)     // Catch:{ IOException -> 0x0144 }
            java.lang.String r11 = "\", algorithm=MD5, qop=\"auth\","
            r2.append(r11)     // Catch:{ IOException -> 0x0144 }
            java.lang.String r11 = " stale="
            r2.append(r11)     // Catch:{ IOException -> 0x0144 }
            r2.append(r1)     // Catch:{ IOException -> 0x0144 }
            java.lang.String r11 = r2.toString()     // Catch:{ IOException -> 0x0144 }
            r12.setHeader(r0, r11)     // Catch:{ IOException -> 0x0144 }
            r11 = 401(0x191, float:5.62E-43)
            r12.sendError(r11)     // Catch:{ IOException -> 0x0144 }
            org.eclipse.jetty.server.Authentication r11 = org.eclipse.jetty.server.Authentication.SEND_CONTINUE     // Catch:{ IOException -> 0x0144 }
            return r11
        L_0x0141:
            org.eclipse.jetty.server.Authentication r11 = org.eclipse.jetty.server.Authentication.UNAUTHENTICATED     // Catch:{ IOException -> 0x0144 }
            return r11
        L_0x0144:
            r11 = move-exception
            org.eclipse.jetty.security.ServerAuthException r12 = new org.eclipse.jetty.security.ServerAuthException
            r12.<init>((java.lang.Throwable) r11)
            goto L_0x014c
        L_0x014b:
            throw r12
        L_0x014c:
            goto L_0x014b
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.security.authentication.DigestAuthenticator.validateRequest(javax.servlet.ServletRequest, javax.servlet.ServletResponse, boolean):org.eclipse.jetty.server.Authentication");
    }

    public String newNonce(Request request) {
        Nonce nonce;
        do {
            byte[] bArr = new byte[24];
            this._random.nextBytes(bArr);
            nonce = new Nonce(new String(B64Code.encode(bArr)), request.getTimeStamp(), this._maxNC);
        } while (this._nonceMap.putIfAbsent(nonce._nonce, nonce) != null);
        this._nonceQueue.add(nonce);
        return nonce._nonce;
    }

    private int checkNonce(Digest digest, Request request) {
        long timeStamp = request.getTimeStamp() - this._maxNonceAgeMs;
        Nonce peek = this._nonceQueue.peek();
        while (peek != null && peek._ts < timeStamp) {
            this._nonceQueue.remove(peek);
            this._nonceMap.remove(peek._nonce);
            peek = this._nonceQueue.peek();
        }
        try {
            Nonce nonce = (Nonce) this._nonceMap.get(digest.nonce);
            if (nonce == null) {
                return 0;
            }
            long parseLong = Long.parseLong(digest.f4111nc, 16);
            if (parseLong >= ((long) this._maxNC)) {
                return 0;
            }
            if (nonce.seen((int) parseLong)) {
                return -1;
            }
            return 1;
        } catch (Exception e) {
            LOG.ignore(e);
            return -1;
        }
    }

    private static class Digest extends Credential {
        private static final long serialVersionUID = -2484639019549527724L;
        String cnonce = "";
        final String method;

        /* renamed from: nc */
        String f4111nc = "";
        String nonce = "";
        String qop = "";
        String realm = "";
        String response = "";
        String uri = "";
        String username = "";

        Digest(String str) {
            this.method = str;
        }

        public boolean check(Object obj) {
            byte[] bArr;
            if (obj instanceof char[]) {
                obj = new String((char[]) obj);
            }
            String obj2 = obj instanceof String ? (String) obj : obj.toString();
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                if (obj instanceof Credential.MD5) {
                    bArr = ((Credential.MD5) obj).getDigest();
                } else {
                    instance.update(this.username.getBytes(StringUtil.__ISO_8859_1));
                    instance.update(HttpTokens.COLON);
                    instance.update(this.realm.getBytes(StringUtil.__ISO_8859_1));
                    instance.update(HttpTokens.COLON);
                    instance.update(obj2.getBytes(StringUtil.__ISO_8859_1));
                    bArr = instance.digest();
                }
                instance.reset();
                instance.update(this.method.getBytes(StringUtil.__ISO_8859_1));
                instance.update(HttpTokens.COLON);
                instance.update(this.uri.getBytes(StringUtil.__ISO_8859_1));
                byte[] digest = instance.digest();
                instance.update(TypeUtil.toString(bArr, 16).getBytes(StringUtil.__ISO_8859_1));
                instance.update(HttpTokens.COLON);
                instance.update(this.nonce.getBytes(StringUtil.__ISO_8859_1));
                instance.update(HttpTokens.COLON);
                instance.update(this.f4111nc.getBytes(StringUtil.__ISO_8859_1));
                instance.update(HttpTokens.COLON);
                instance.update(this.cnonce.getBytes(StringUtil.__ISO_8859_1));
                instance.update(HttpTokens.COLON);
                instance.update(this.qop.getBytes(StringUtil.__ISO_8859_1));
                instance.update(HttpTokens.COLON);
                instance.update(TypeUtil.toString(digest, 16).getBytes(StringUtil.__ISO_8859_1));
                return TypeUtil.toString(instance.digest(), 16).equalsIgnoreCase(this.response);
            } catch (Exception e) {
                DigestAuthenticator.LOG.warn(e);
                return false;
            }
        }

        public String toString() {
            return this.username + "," + this.response;
        }
    }
}
