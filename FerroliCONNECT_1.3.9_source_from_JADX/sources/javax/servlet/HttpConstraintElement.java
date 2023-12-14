package javax.servlet;

import javax.servlet.annotation.ServletSecurity;

public class HttpConstraintElement {
    private ServletSecurity.EmptyRoleSemantic emptyRoleSemantic;
    private String[] rolesAllowed;
    private ServletSecurity.TransportGuarantee transportGuarantee;

    public HttpConstraintElement() {
        this(ServletSecurity.EmptyRoleSemantic.PERMIT);
    }

    public HttpConstraintElement(ServletSecurity.EmptyRoleSemantic emptyRoleSemantic2) {
        this(emptyRoleSemantic2, ServletSecurity.TransportGuarantee.NONE, new String[0]);
    }

    public HttpConstraintElement(ServletSecurity.TransportGuarantee transportGuarantee2, String... strArr) {
        this(ServletSecurity.EmptyRoleSemantic.PERMIT, transportGuarantee2, strArr);
    }

    public HttpConstraintElement(ServletSecurity.EmptyRoleSemantic emptyRoleSemantic2, ServletSecurity.TransportGuarantee transportGuarantee2, String... strArr) {
        if (emptyRoleSemantic2 != ServletSecurity.EmptyRoleSemantic.DENY || strArr.length <= 0) {
            this.emptyRoleSemantic = emptyRoleSemantic2;
            this.transportGuarantee = transportGuarantee2;
            this.rolesAllowed = strArr;
            return;
        }
        throw new IllegalArgumentException("Deny semantic with rolesAllowed");
    }

    public ServletSecurity.EmptyRoleSemantic getEmptyRoleSemantic() {
        return this.emptyRoleSemantic;
    }

    public ServletSecurity.TransportGuarantee getTransportGuarantee() {
        return this.transportGuarantee;
    }

    public String[] getRolesAllowed() {
        return this.rolesAllowed;
    }
}
