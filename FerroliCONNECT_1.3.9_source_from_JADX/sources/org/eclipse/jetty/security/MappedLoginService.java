package org.eclipse.jetty.security;

import java.io.IOException;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.security.auth.Subject;
import org.eclipse.jetty.server.UserIdentity;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.security.Credential;

public abstract class MappedLoginService extends AbstractLifeCycle implements LoginService {
    private static final Logger LOG = Log.getLogger((Class<?>) MappedLoginService.class);
    protected IdentityService _identityService = new DefaultIdentityService();
    protected String _name;
    protected final ConcurrentMap<String, UserIdentity> _users = new ConcurrentHashMap();

    public static class Anonymous implements UserPrincipal, Serializable {
        private static final long serialVersionUID = 1097640442553284845L;

        public boolean authenticate(Object obj) {
            return false;
        }

        public String getName() {
            return "Anonymous";
        }

        public boolean isAuthenticated() {
            return false;
        }
    }

    public interface UserPrincipal extends Principal, Serializable {
        boolean authenticate(Object obj);

        boolean isAuthenticated();
    }

    /* access modifiers changed from: protected */
    public abstract UserIdentity loadUser(String str);

    /* access modifiers changed from: protected */
    public abstract void loadUsers() throws IOException;

    protected MappedLoginService() {
    }

    public String getName() {
        return this._name;
    }

    public IdentityService getIdentityService() {
        return this._identityService;
    }

    public ConcurrentMap<String, UserIdentity> getUsers() {
        return this._users;
    }

    public void setIdentityService(IdentityService identityService) {
        if (!isRunning()) {
            this._identityService = identityService;
            return;
        }
        throw new IllegalStateException("Running");
    }

    public void setName(String str) {
        if (!isRunning()) {
            this._name = str;
            return;
        }
        throw new IllegalStateException("Running");
    }

    public void setUsers(Map<String, UserIdentity> map) {
        if (!isRunning()) {
            this._users.clear();
            this._users.putAll(map);
            return;
        }
        throw new IllegalStateException("Running");
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        loadUsers();
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        super.doStop();
    }

    public void logout(UserIdentity userIdentity) {
        LOG.debug("logout {}", userIdentity);
    }

    public String toString() {
        return getClass().getSimpleName() + "[" + this._name + "]";
    }

    /* access modifiers changed from: protected */
    public synchronized UserIdentity putUser(String str, Object obj) {
        UserIdentity userIdentity;
        if (obj instanceof UserIdentity) {
            userIdentity = (UserIdentity) obj;
        } else {
            Credential credential = obj instanceof Credential ? (Credential) obj : Credential.getCredential(obj.toString());
            KnownUser knownUser = new KnownUser(str, credential);
            Subject subject = new Subject();
            subject.getPrincipals().add(knownUser);
            subject.getPrivateCredentials().add(credential);
            subject.setReadOnly();
            userIdentity = this._identityService.newUserIdentity(subject, knownUser, IdentityService.NO_ROLES);
        }
        this._users.put(str, userIdentity);
        return userIdentity;
    }

    public synchronized UserIdentity putUser(String str, Credential credential, String[] strArr) {
        UserIdentity newUserIdentity;
        KnownUser knownUser = new KnownUser(str, credential);
        Subject subject = new Subject();
        subject.getPrincipals().add(knownUser);
        subject.getPrivateCredentials().add(credential);
        if (strArr != null) {
            for (String rolePrincipal : strArr) {
                subject.getPrincipals().add(new RolePrincipal(rolePrincipal));
            }
        }
        subject.setReadOnly();
        newUserIdentity = this._identityService.newUserIdentity(subject, knownUser, strArr);
        this._users.put(str, newUserIdentity);
        return newUserIdentity;
    }

    public void removeUser(String str) {
        this._users.remove(str);
    }

    public UserIdentity login(String str, Object obj) {
        UserIdentity userIdentity = (UserIdentity) this._users.get(str);
        if (userIdentity == null) {
            userIdentity = loadUser(str);
        }
        if (userIdentity == null || !((UserPrincipal) userIdentity.getUserPrincipal()).authenticate(obj)) {
            return null;
        }
        return userIdentity;
    }

    public boolean validate(UserIdentity userIdentity) {
        if (!this._users.containsKey(userIdentity.getUserPrincipal().getName()) && loadUser(userIdentity.getUserPrincipal().getName()) == null) {
            return false;
        }
        return true;
    }

    public static class RolePrincipal implements Principal, Serializable {
        private static final long serialVersionUID = 2998397924051854402L;
        private final String _roleName;

        public RolePrincipal(String str) {
            this._roleName = str;
        }

        public String getName() {
            return this._roleName;
        }
    }

    public static class KnownUser implements UserPrincipal, Serializable {
        private static final long serialVersionUID = -6226920753748399662L;
        private final Credential _credential;
        private final String _name;

        public boolean isAuthenticated() {
            return true;
        }

        public KnownUser(String str, Credential credential) {
            this._name = str;
            this._credential = credential;
        }

        public boolean authenticate(Object obj) {
            Credential credential = this._credential;
            return credential != null && credential.check(obj);
        }

        public String getName() {
            return this._name;
        }

        public String toString() {
            return this._name;
        }
    }
}
