package org.eclipse.jetty.security;

import anet.channel.util.HttpConstant;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.servlet.HttpConstraintElement;
import javax.servlet.HttpMethodConstraintElement;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.ServletSecurity;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.StringMap;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.security.Constraint;

public class ConstraintSecurityHandler extends SecurityHandler implements ConstraintAware {
    private static final String OMISSION_SUFFIX = ".omission";
    private final PathMap _constraintMap = new PathMap();
    private final List<ConstraintMapping> _constraintMappings = new CopyOnWriteArrayList();
    private final Set<String> _roles = new CopyOnWriteArraySet();
    private boolean _strict = true;

    public static Constraint createConstraint() {
        return new Constraint();
    }

    public static Constraint createConstraint(Constraint constraint) {
        try {
            return (Constraint) constraint.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Constraint createConstraint(String str, boolean z, String[] strArr, int i) {
        Constraint createConstraint = createConstraint();
        if (str != null) {
            createConstraint.setName(str);
        }
        createConstraint.setAuthenticate(z);
        createConstraint.setRoles(strArr);
        createConstraint.setDataConstraint(i);
        return createConstraint;
    }

    public static Constraint createConstraint(String str, HttpConstraintElement httpConstraintElement) {
        return createConstraint(str, httpConstraintElement.getRolesAllowed(), httpConstraintElement.getEmptyRoleSemantic(), httpConstraintElement.getTransportGuarantee());
    }

    public static Constraint createConstraint(String str, String[] strArr, ServletSecurity.EmptyRoleSemantic emptyRoleSemantic, ServletSecurity.TransportGuarantee transportGuarantee) {
        Constraint createConstraint = createConstraint();
        int i = 0;
        if (strArr != null && strArr.length != 0) {
            createConstraint.setAuthenticate(true);
            createConstraint.setRoles(strArr);
            createConstraint.setName(str + "-RolesAllowed");
        } else if (emptyRoleSemantic.equals(ServletSecurity.EmptyRoleSemantic.DENY)) {
            createConstraint.setName(str + "-Deny");
            createConstraint.setAuthenticate(true);
        } else {
            createConstraint.setName(str + "-Permit");
            createConstraint.setAuthenticate(false);
        }
        if (transportGuarantee.equals(ServletSecurity.TransportGuarantee.CONFIDENTIAL)) {
            i = 2;
        }
        createConstraint.setDataConstraint(i);
        return createConstraint;
    }

    public static List<ConstraintMapping> getConstraintMappingsForPath(String str, List<ConstraintMapping> list) {
        if (str == null || "".equals(str.trim()) || list == null || list.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (ConstraintMapping next : list) {
            if (str.equals(next.getPathSpec())) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public static List<ConstraintMapping> removeConstraintMappingsForPath(String str, List<ConstraintMapping> list) {
        if (str == null || "".equals(str.trim()) || list == null || list.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (ConstraintMapping next : list) {
            if (!str.equals(next.getPathSpec())) {
                arrayList.add(next);
            }
        }
        return arrayList;
    }

    public static List<ConstraintMapping> createConstraintsWithMappingsForPath(String str, String str2, ServletSecurityElement servletSecurityElement) {
        ArrayList arrayList = new ArrayList();
        Constraint createConstraint = createConstraint(str, servletSecurityElement);
        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setPathSpec(str2);
        constraintMapping.setConstraint(createConstraint);
        arrayList.add(constraintMapping);
        ArrayList arrayList2 = new ArrayList();
        Collection<HttpMethodConstraintElement> httpMethodConstraints = servletSecurityElement.getHttpMethodConstraints();
        if (httpMethodConstraints != null) {
            for (HttpMethodConstraintElement next : httpMethodConstraints) {
                Constraint createConstraint2 = createConstraint(str, next);
                ConstraintMapping constraintMapping2 = new ConstraintMapping();
                constraintMapping2.setConstraint(createConstraint2);
                constraintMapping2.setPathSpec(str2);
                if (next.getMethodName() != null) {
                    constraintMapping2.setMethod(next.getMethodName());
                    arrayList2.add(next.getMethodName());
                }
                arrayList.add(constraintMapping2);
            }
        }
        if (arrayList2.size() > 0) {
            constraintMapping.setMethodOmissions((String[]) arrayList2.toArray(new String[arrayList2.size()]));
        }
        return arrayList;
    }

    public boolean isStrict() {
        return this._strict;
    }

    public void setStrict(boolean z) {
        this._strict = z;
    }

    public List<ConstraintMapping> getConstraintMappings() {
        return this._constraintMappings;
    }

    public Set<String> getRoles() {
        return this._roles;
    }

    public void setConstraintMappings(List<ConstraintMapping> list) {
        setConstraintMappings(list, (Set<String>) null);
    }

    public void setConstraintMappings(ConstraintMapping[] constraintMappingArr) {
        setConstraintMappings(Arrays.asList(constraintMappingArr), (Set<String>) null);
    }

    public void setConstraintMappings(List<ConstraintMapping> list, Set<String> set) {
        this._constraintMappings.clear();
        this._constraintMappings.addAll(list);
        if (set == null) {
            set = new HashSet<>();
            for (ConstraintMapping constraint : list) {
                String[] roles = constraint.getConstraint().getRoles();
                if (roles != null) {
                    for (String str : roles) {
                        if (!Constraint.ANY_ROLE.equals(str)) {
                            set.add(str);
                        }
                    }
                }
            }
        }
        setRoles(set);
        if (isStarted()) {
            for (ConstraintMapping processConstraintMapping : this._constraintMappings) {
                processConstraintMapping(processConstraintMapping);
            }
        }
    }

    public void setRoles(Set<String> set) {
        this._roles.clear();
        this._roles.addAll(set);
    }

    public void addConstraintMapping(ConstraintMapping constraintMapping) {
        this._constraintMappings.add(constraintMapping);
        if (!(constraintMapping.getConstraint() == null || constraintMapping.getConstraint().getRoles() == null)) {
            for (String addRole : constraintMapping.getConstraint().getRoles()) {
                addRole(addRole);
            }
        }
        if (isStarted()) {
            processConstraintMapping(constraintMapping);
        }
    }

    public void addRole(String str) {
        boolean add = this._roles.add(str);
        if (isStarted() && add && this._strict) {
            for (Map values : this._constraintMap.values()) {
                for (RoleInfo roleInfo : values.values()) {
                    if (roleInfo.isAnyRole()) {
                        roleInfo.addRole(str);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        this._constraintMap.clear();
        List<ConstraintMapping> list = this._constraintMappings;
        if (list != null) {
            for (ConstraintMapping processConstraintMapping : list) {
                processConstraintMapping(processConstraintMapping);
            }
        }
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        this._constraintMap.clear();
        this._constraintMappings.clear();
        this._roles.clear();
        super.doStop();
    }

    /* access modifiers changed from: protected */
    public void processConstraintMapping(ConstraintMapping constraintMapping) {
        Map map = (Map) this._constraintMap.get(constraintMapping.getPathSpec());
        if (map == null) {
            map = new StringMap();
            this._constraintMap.put(constraintMapping.getPathSpec(), map);
        }
        RoleInfo roleInfo = (RoleInfo) map.get((Object) null);
        if (roleInfo != null && roleInfo.isForbidden()) {
            return;
        }
        if (constraintMapping.getMethodOmissions() == null || constraintMapping.getMethodOmissions().length <= 0) {
            String method = constraintMapping.getMethod();
            RoleInfo roleInfo2 = (RoleInfo) map.get(method);
            if (roleInfo2 == null) {
                roleInfo2 = new RoleInfo();
                map.put(method, roleInfo2);
                if (roleInfo != null) {
                    roleInfo2.combine(roleInfo);
                }
            }
            if (!roleInfo2.isForbidden()) {
                configureRoleInfo(roleInfo2, constraintMapping);
                if (roleInfo2.isForbidden()) {
                    if (method == null) {
                        map.clear();
                        map.put((Object) null, roleInfo2);
                    }
                } else if (method == null) {
                    for (Map.Entry entry : map.entrySet()) {
                        if (entry.getKey() != null) {
                            ((RoleInfo) entry.getValue()).combine(roleInfo2);
                        }
                    }
                }
            }
        } else {
            processConstraintMappingWithMethodOmissions(constraintMapping, map);
        }
    }

    /* access modifiers changed from: protected */
    public void processConstraintMappingWithMethodOmissions(ConstraintMapping constraintMapping, Map<String, RoleInfo> map) {
        for (String str : constraintMapping.getMethodOmissions()) {
            RoleInfo roleInfo = map.get(str + OMISSION_SUFFIX);
            if (roleInfo == null) {
                roleInfo = new RoleInfo();
                map.put(str + OMISSION_SUFFIX, roleInfo);
            }
            configureRoleInfo(roleInfo, constraintMapping);
        }
    }

    /* access modifiers changed from: protected */
    public void configureRoleInfo(RoleInfo roleInfo, ConstraintMapping constraintMapping) {
        roleInfo.setForbidden(constraintMapping.getConstraint().isForbidden());
        roleInfo.setUserDataConstraint(UserDataConstraint.get(constraintMapping.getConstraint().getDataConstraint()));
        if (!roleInfo.isForbidden()) {
            roleInfo.setChecked(constraintMapping.getConstraint().getAuthenticate());
            if (!roleInfo.isChecked()) {
                return;
            }
            if (!constraintMapping.getConstraint().isAnyRole()) {
                String[] roles = constraintMapping.getConstraint().getRoles();
                int length = roles.length;
                int i = 0;
                while (i < length) {
                    String str = roles[i];
                    if (!this._strict || this._roles.contains(str)) {
                        roleInfo.addRole(str);
                        i++;
                    } else {
                        throw new IllegalArgumentException("Attempt to use undeclared role: " + str + ", known roles: " + this._roles);
                    }
                }
            } else if (this._strict) {
                for (String addRole : this._roles) {
                    roleInfo.addRole(addRole);
                }
            } else {
                roleInfo.setAnyRole(true);
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object prepareConstraintInfo(String str, Request request) {
        Map map = (Map) this._constraintMap.match(str);
        if (map == null) {
            return null;
        }
        String method = request.getMethod();
        RoleInfo roleInfo = (RoleInfo) map.get(method);
        if (roleInfo != null) {
            return roleInfo;
        }
        ArrayList<RoleInfo> arrayList = new ArrayList<>();
        RoleInfo roleInfo2 = (RoleInfo) map.get((Object) null);
        if (roleInfo2 != null) {
            arrayList.add(roleInfo2);
        }
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey() != null && ((String) entry.getKey()).contains(OMISSION_SUFFIX)) {
                if (!(method + OMISSION_SUFFIX).equals(entry.getKey())) {
                    arrayList.add(entry.getValue());
                }
            }
        }
        if (arrayList.size() == 1) {
            return (RoleInfo) arrayList.get(0);
        }
        RoleInfo roleInfo3 = new RoleInfo();
        roleInfo3.setUserDataConstraint(UserDataConstraint.None);
        for (RoleInfo combine : arrayList) {
            roleInfo3.combine(combine);
        }
        return roleInfo3;
    }

    /* access modifiers changed from: protected */
    public boolean checkUserDataPermissions(String str, Request request, Response response, Object obj) throws IOException {
        String str2;
        String str3;
        if (obj == null) {
            return true;
        }
        RoleInfo roleInfo = (RoleInfo) obj;
        if (roleInfo.isForbidden()) {
            return false;
        }
        UserDataConstraint userDataConstraint = roleInfo.getUserDataConstraint();
        if (userDataConstraint == null || userDataConstraint == UserDataConstraint.None) {
            return true;
        }
        Connector connector = AbstractHttpConnection.getCurrentConnection().getConnector();
        if (userDataConstraint == UserDataConstraint.Integral) {
            if (connector.isIntegral(request)) {
                return true;
            }
            if (connector.getIntegralPort() > 0) {
                String integralScheme = connector.getIntegralScheme();
                int integralPort = connector.getIntegralPort();
                if (!"https".equalsIgnoreCase(integralScheme) || integralPort != 443) {
                    str3 = integralScheme + HttpConstant.SCHEME_SPLIT + request.getServerName() + ":" + integralPort + request.getRequestURI();
                } else {
                    str3 = "https://" + request.getServerName() + request.getRequestURI();
                }
                if (request.getQueryString() != null) {
                    str3 = str3 + "?" + request.getQueryString();
                }
                response.setContentLength(0);
                response.sendRedirect(str3);
            } else {
                response.sendError(403, "!Integral");
            }
            request.setHandled(true);
            return false;
        } else if (userDataConstraint != UserDataConstraint.Confidential) {
            throw new IllegalArgumentException("Invalid dataConstraint value: " + userDataConstraint);
        } else if (connector.isConfidential(request)) {
            return true;
        } else {
            if (connector.getConfidentialPort() > 0) {
                String confidentialScheme = connector.getConfidentialScheme();
                int confidentialPort = connector.getConfidentialPort();
                if (!"https".equalsIgnoreCase(confidentialScheme) || confidentialPort != 443) {
                    str2 = confidentialScheme + HttpConstant.SCHEME_SPLIT + request.getServerName() + ":" + confidentialPort + request.getRequestURI();
                } else {
                    str2 = "https://" + request.getServerName() + request.getRequestURI();
                }
                if (request.getQueryString() != null) {
                    str2 = str2 + "?" + request.getQueryString();
                }
                response.setContentLength(0);
                response.sendRedirect(str2);
            } else {
                response.sendError(403, "!Confidential");
            }
            request.setHandled(true);
            return false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean isAuthMandatory(Request request, Response response, Object obj) {
        if (obj == null) {
            return false;
        }
        return ((RoleInfo) obj).isChecked();
    }

    /* access modifiers changed from: protected */
    public boolean checkWebResourcePermissions(String str, Request request, Response response, Object obj, UserIdentity userIdentity) throws IOException {
        if (obj == null) {
            return true;
        }
        RoleInfo roleInfo = (RoleInfo) obj;
        if (!roleInfo.isChecked()) {
            return true;
        }
        if (roleInfo.isAnyRole() && request.getAuthType() != null) {
            return true;
        }
        for (String isUserInRole : roleInfo.getRoles()) {
            if (userIdentity.isUserInRole(isUserInRole, (UserIdentity.Scope) null)) {
                return true;
            }
        }
        return false;
    }

    public void dump(Appendable appendable, String str) throws IOException {
        dumpThis(appendable);
        dump(appendable, str, Collections.singleton(getLoginService()), Collections.singleton(getIdentityService()), Collections.singleton(getAuthenticator()), Collections.singleton(this._roles), this._constraintMap.entrySet(), getBeans(), TypeUtil.asList(getHandlers()));
    }
}
