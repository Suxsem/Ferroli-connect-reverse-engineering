package javax.servlet;

public class HttpMethodConstraintElement extends HttpConstraintElement {
    private String methodName;

    public HttpMethodConstraintElement(String str) {
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("invalid HTTP method name");
        }
        this.methodName = str;
    }

    public HttpMethodConstraintElement(String str, HttpConstraintElement httpConstraintElement) {
        super(httpConstraintElement.getEmptyRoleSemantic(), httpConstraintElement.getTransportGuarantee(), httpConstraintElement.getRolesAllowed());
        if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("invalid HTTP method name");
        }
        this.methodName = str;
    }

    public String getMethodName() {
        return this.methodName;
    }
}
