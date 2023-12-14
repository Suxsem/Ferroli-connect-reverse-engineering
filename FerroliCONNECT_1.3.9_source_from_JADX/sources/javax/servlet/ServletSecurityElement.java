package javax.servlet;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import javax.servlet.annotation.HttpMethodConstraint;
import javax.servlet.annotation.ServletSecurity;

public class ServletSecurityElement extends HttpConstraintElement {
    private Collection<HttpMethodConstraintElement> methodConstraints;
    private Collection<String> methodNames;

    public ServletSecurityElement() {
        this.methodConstraints = new HashSet();
        this.methodNames = Collections.emptySet();
    }

    public ServletSecurityElement(HttpConstraintElement httpConstraintElement) {
        super(httpConstraintElement.getEmptyRoleSemantic(), httpConstraintElement.getTransportGuarantee(), httpConstraintElement.getRolesAllowed());
        this.methodConstraints = new HashSet();
        this.methodNames = Collections.emptySet();
    }

    public ServletSecurityElement(Collection<HttpMethodConstraintElement> collection) {
        this.methodConstraints = collection == null ? new HashSet<>() : collection;
        this.methodNames = checkMethodNames(this.methodConstraints);
    }

    public ServletSecurityElement(HttpConstraintElement httpConstraintElement, Collection<HttpMethodConstraintElement> collection) {
        super(httpConstraintElement.getEmptyRoleSemantic(), httpConstraintElement.getTransportGuarantee(), httpConstraintElement.getRolesAllowed());
        this.methodConstraints = collection == null ? new HashSet<>() : collection;
        this.methodNames = checkMethodNames(this.methodConstraints);
    }

    public ServletSecurityElement(ServletSecurity servletSecurity) {
        super(servletSecurity.value().value(), servletSecurity.value().transportGuarantee(), servletSecurity.value().rolesAllowed());
        this.methodConstraints = new HashSet();
        for (HttpMethodConstraint httpMethodConstraint : servletSecurity.httpMethodConstraints()) {
            this.methodConstraints.add(new HttpMethodConstraintElement(httpMethodConstraint.value(), new HttpConstraintElement(httpMethodConstraint.emptyRoleSemantic(), httpMethodConstraint.transportGuarantee(), httpMethodConstraint.rolesAllowed())));
        }
        this.methodNames = checkMethodNames(this.methodConstraints);
    }

    public Collection<HttpMethodConstraintElement> getHttpMethodConstraints() {
        return Collections.unmodifiableCollection(this.methodConstraints);
    }

    public Collection<String> getMethodNames() {
        return Collections.unmodifiableCollection(this.methodNames);
    }

    private Collection<String> checkMethodNames(Collection<HttpMethodConstraintElement> collection) {
        HashSet hashSet = new HashSet();
        for (HttpMethodConstraintElement methodName : collection) {
            String methodName2 = methodName.getMethodName();
            if (!hashSet.add(methodName2)) {
                throw new IllegalArgumentException("Duplicate HTTP method name: " + methodName2);
            }
        }
        return hashSet;
    }
}
