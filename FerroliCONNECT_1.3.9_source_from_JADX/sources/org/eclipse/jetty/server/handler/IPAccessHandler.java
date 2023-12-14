package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.p119io.EndPoint;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.IPAddressMap;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class IPAccessHandler extends HandlerWrapper {
    private static final Logger LOG = Log.getLogger((Class<?>) IPAccessHandler.class);
    IPAddressMap<PathMap> _black = new IPAddressMap<>();
    IPAddressMap<PathMap> _white = new IPAddressMap<>();

    public IPAccessHandler() {
    }

    public IPAccessHandler(String[] strArr, String[] strArr2) {
        if (strArr != null && strArr.length > 0) {
            setWhite(strArr);
        }
        if (strArr2 != null && strArr2.length > 0) {
            setBlack(strArr2);
        }
    }

    public void addWhite(String str) {
        add(str, this._white);
    }

    public void addBlack(String str) {
        add(str, this._black);
    }

    public void setWhite(String[] strArr) {
        set(strArr, this._white);
    }

    public void setBlack(String[] strArr) {
        set(strArr, this._black);
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        EndPoint endPoint;
        String remoteAddr;
        AbstractHttpConnection connection = request.getConnection();
        if (connection == null || (endPoint = connection.getEndPoint()) == null || (remoteAddr = endPoint.getRemoteAddr()) == null || isAddrUriAllowed(remoteAddr, request.getPathInfo())) {
            getHandler().handle(str, request, httpServletRequest, httpServletResponse);
            return;
        }
        httpServletResponse.sendError(403);
        request.setHandled(true);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:11:0x0023  */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x002b  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x003a  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:37:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void add(java.lang.String r7, org.eclipse.jetty.util.IPAddressMap<org.eclipse.jetty.http.PathMap> r8) {
        /*
            r6 = this;
            if (r7 == 0) goto L_0x008f
            int r0 = r7.length()
            if (r0 <= 0) goto L_0x008f
            r0 = 124(0x7c, float:1.74E-43)
            int r1 = r7.indexOf(r0)
            r2 = 1
            r3 = 0
            if (r1 <= 0) goto L_0x0018
            int r0 = r7.indexOf(r0)
        L_0x0016:
            r1 = 0
            goto L_0x0021
        L_0x0018:
            r0 = 47
            int r0 = r7.indexOf(r0)
            if (r0 < 0) goto L_0x0016
            r1 = 1
        L_0x0021:
            if (r0 <= 0) goto L_0x0028
            java.lang.String r4 = r7.substring(r3, r0)
            goto L_0x0029
        L_0x0028:
            r4 = r7
        L_0x0029:
            if (r0 <= 0) goto L_0x0030
            java.lang.String r0 = r7.substring(r0)
            goto L_0x0032
        L_0x0030:
            java.lang.String r0 = "/*"
        L_0x0032:
            java.lang.String r5 = "."
            boolean r5 = r4.endsWith(r5)
            if (r5 == 0) goto L_0x003b
            r1 = 1
        L_0x003b:
            if (r0 == 0) goto L_0x0051
            java.lang.String r5 = "|"
            boolean r5 = r0.startsWith(r5)
            if (r5 != 0) goto L_0x004d
            java.lang.String r5 = "/*."
            boolean r5 = r0.startsWith(r5)
            if (r5 == 0) goto L_0x0051
        L_0x004d:
            java.lang.String r0 = r0.substring(r2)
        L_0x0051:
            java.lang.Object r5 = r8.get(r4)
            org.eclipse.jetty.http.PathMap r5 = (org.eclipse.jetty.http.PathMap) r5
            if (r5 != 0) goto L_0x0061
            org.eclipse.jetty.http.PathMap r5 = new org.eclipse.jetty.http.PathMap
            r5.<init>((boolean) r2)
            r8.put((java.lang.String) r4, r5)
        L_0x0061:
            if (r0 == 0) goto L_0x006e
            java.lang.String r8 = ""
            boolean r8 = r8.equals(r0)
            if (r8 != 0) goto L_0x006e
            r5.put(r0, r0)
        L_0x006e:
            if (r1 == 0) goto L_0x008f
            org.eclipse.jetty.util.log.Logger r8 = LOG
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = r6.toString()
            r0.append(r1)
            java.lang.String r1 = " - deprecated specification syntax: "
            r0.append(r1)
            r0.append(r7)
            java.lang.String r7 = r0.toString()
            java.lang.Object[] r0 = new java.lang.Object[r3]
            r8.debug((java.lang.String) r7, (java.lang.Object[]) r0)
        L_0x008f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.server.handler.IPAccessHandler.add(java.lang.String, org.eclipse.jetty.util.IPAddressMap):void");
    }

    /* access modifiers changed from: protected */
    public void set(String[] strArr, IPAddressMap<PathMap> iPAddressMap) {
        iPAddressMap.clear();
        if (strArr != null && strArr.length > 0) {
            for (String add : strArr) {
                add(add, iPAddressMap);
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean isAddrUriAllowed(String str, String str2) {
        Object lazyMatches;
        boolean z;
        if (this._white.size() > 0) {
            Object lazyMatches2 = this._white.getLazyMatches(str);
            if (lazyMatches2 != null) {
                z = false;
                for (Map.Entry value : lazyMatches2 instanceof List ? (List) lazyMatches2 : Collections.singletonList(lazyMatches2)) {
                    PathMap pathMap = (PathMap) value.getValue();
                    if (pathMap == null || (pathMap.size() != 0 && pathMap.match(str2) == null)) {
                        z = false;
                        continue;
                    } else {
                        z = true;
                        continue;
                    }
                    if (z) {
                        break;
                    }
                }
            } else {
                z = false;
            }
            if (!z) {
                return false;
            }
        }
        if (this._black.size() > 0 && (lazyMatches = this._black.getLazyMatches(str)) != null) {
            for (Map.Entry value2 : lazyMatches instanceof List ? (List) lazyMatches : Collections.singletonList(lazyMatches)) {
                PathMap pathMap2 = (PathMap) value2.getValue();
                if (pathMap2 != null && (pathMap2.size() == 0 || pathMap2.match(str2) != null)) {
                    return false;
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        super.doStart();
        if (LOG.isDebugEnabled()) {
            System.err.println(dump());
        }
    }

    public String dump() {
        StringBuilder sb = new StringBuilder();
        sb.append(toString());
        sb.append(" WHITELIST:\n");
        dump(sb, this._white);
        sb.append(toString());
        sb.append(" BLACKLIST:\n");
        dump(sb, this._black);
        return sb.toString();
    }

    /* access modifiers changed from: protected */
    public void dump(StringBuilder sb, IPAddressMap<PathMap> iPAddressMap) {
        for (String str : iPAddressMap.keySet()) {
            for (Object next : iPAddressMap.get(str).values()) {
                sb.append("# ");
                sb.append(str);
                sb.append("|");
                sb.append(next);
                sb.append("\n");
            }
        }
    }
}
