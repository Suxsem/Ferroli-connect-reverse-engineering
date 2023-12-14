package org.eclipse.jetty.util.security;

import java.io.Serializable;
import java.util.Arrays;

public class Constraint implements Cloneable, Serializable {
    public static final String ANY_ROLE = "*";
    public static final int DC_CONFIDENTIAL = 2;
    public static final int DC_FORBIDDEN = 3;
    public static final int DC_INTEGRAL = 1;
    public static final int DC_NONE = 0;
    public static final int DC_UNSET = -1;
    public static final String NONE = "NONE";
    public static final String __BASIC_AUTH = "BASIC";
    public static final String __CERT_AUTH = "CLIENT_CERT";
    public static final String __CERT_AUTH2 = "CLIENT-CERT";
    public static final String __DIGEST_AUTH = "DIGEST";
    public static final String __FORM_AUTH = "FORM";
    public static final String __NEGOTIATE_AUTH = "NEGOTIATE";
    public static final String __SPNEGO_AUTH = "SPNEGO";
    private boolean _anyRole = false;
    private boolean _authenticate = false;
    private int _dataConstraint = -1;
    private String _name;
    private String[] _roles;

    public static boolean validateMethod(String str) {
        if (str == null) {
            return false;
        }
        String trim = str.trim();
        if (trim.equals("FORM") || trim.equals("BASIC") || trim.equals("DIGEST") || trim.equals("CLIENT_CERT") || trim.equals(__CERT_AUTH2) || trim.equals(__SPNEGO_AUTH) || trim.equals(__NEGOTIATE_AUTH)) {
            return true;
        }
        return false;
    }

    public Constraint() {
    }

    public Constraint(String str, String str2) {
        setName(str);
        setRoles(new String[]{str2});
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void setName(String str) {
        this._name = str;
    }

    public void setRoles(String[] strArr) {
        this._roles = strArr;
        this._anyRole = false;
        if (strArr != null) {
            int length = strArr.length;
            while (true) {
                boolean z = this._anyRole;
                if (!z) {
                    int i = length - 1;
                    if (length > 0) {
                        this._anyRole = ANY_ROLE.equals(strArr[i]) | z;
                        length = i;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }

    public boolean isAnyRole() {
        return this._anyRole;
    }

    public String[] getRoles() {
        return this._roles;
    }

    public boolean hasRole(String str) {
        if (this._anyRole) {
            return true;
        }
        String[] strArr = this._roles;
        if (strArr == null) {
            return false;
        }
        int length = strArr.length;
        while (true) {
            int i = length - 1;
            if (length <= 0) {
                return false;
            }
            if (str.equals(this._roles[i])) {
                return true;
            }
            length = i;
        }
    }

    public void setAuthenticate(boolean z) {
        this._authenticate = z;
    }

    public boolean getAuthenticate() {
        return this._authenticate;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0008, code lost:
        r0 = r1._roles;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isForbidden() {
        /*
            r1 = this;
            boolean r0 = r1._authenticate
            if (r0 == 0) goto L_0x0011
            boolean r0 = r1._anyRole
            if (r0 != 0) goto L_0x0011
            java.lang.String[] r0 = r1._roles
            if (r0 == 0) goto L_0x000f
            int r0 = r0.length
            if (r0 != 0) goto L_0x0011
        L_0x000f:
            r0 = 1
            goto L_0x0012
        L_0x0011:
            r0 = 0
        L_0x0012:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.util.security.Constraint.isForbidden():boolean");
    }

    public void setDataConstraint(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("Constraint out of range");
        }
        this._dataConstraint = i;
    }

    public int getDataConstraint() {
        return this._dataConstraint;
    }

    public boolean hasDataConstraint() {
        return this._dataConstraint >= 0;
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("SC{");
        sb.append(this._name);
        sb.append(",");
        if (this._anyRole) {
            str = ANY_ROLE;
        } else {
            String[] strArr = this._roles;
            str = strArr == null ? "-" : Arrays.asList(strArr).toString();
        }
        sb.append(str);
        sb.append(",");
        int i = this._dataConstraint;
        sb.append(i == -1 ? "DC_UNSET}" : i == 0 ? "NONE}" : i == 1 ? "INTEGRAL}" : "CONFIDENTIAL}");
        return sb.toString();
    }
}
