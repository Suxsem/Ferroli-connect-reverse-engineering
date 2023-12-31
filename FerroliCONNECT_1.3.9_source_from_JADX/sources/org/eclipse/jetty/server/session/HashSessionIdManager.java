package org.eclipse.jetty.server.session;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HashSessionIdManager extends AbstractSessionIdManager {
    private final Map<String, Set<WeakReference<HttpSession>>> _sessions = new HashMap();

    public HashSessionIdManager() {
    }

    public HashSessionIdManager(Random random) {
        super(random);
    }

    public Collection<String> getSessions() {
        return Collections.unmodifiableCollection(this._sessions.keySet());
    }

    public Collection<HttpSession> getSession(String str) {
        ArrayList arrayList = new ArrayList();
        Set<WeakReference> set = this._sessions.get(str);
        if (set != null) {
            for (WeakReference weakReference : set) {
                HttpSession httpSession = (HttpSession) weakReference.get();
                if (httpSession != null) {
                    arrayList.add(httpSession);
                }
            }
        }
        return arrayList;
    }

    public String getNodeId(String str, HttpServletRequest httpServletRequest) {
        String str2 = httpServletRequest == null ? null : (String) httpServletRequest.getAttribute("org.eclipse.jetty.ajp.JVMRoute");
        if (str2 != null) {
            return str + '.' + str2;
        } else if (this._workerName == null) {
            return str;
        } else {
            return str + '.' + this._workerName;
        }
    }

    public String getClusterId(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return lastIndexOf > 0 ? str.substring(0, lastIndexOf) : str;
    }

    /* access modifiers changed from: protected */
    public void doStart() throws Exception {
        super.doStart();
    }

    /* access modifiers changed from: protected */
    public void doStop() throws Exception {
        this._sessions.clear();
        super.doStop();
    }

    public boolean idInUse(String str) {
        boolean containsKey;
        synchronized (this) {
            containsKey = this._sessions.containsKey(str);
        }
        return containsKey;
    }

    public void addSession(HttpSession httpSession) {
        String clusterId = getClusterId(httpSession.getId());
        WeakReference weakReference = new WeakReference(httpSession);
        synchronized (this) {
            Set set = this._sessions.get(clusterId);
            if (set == null) {
                set = new HashSet();
                this._sessions.put(clusterId, set);
            }
            set.add(weakReference);
        }
    }

    public void removeSession(HttpSession httpSession) {
        String clusterId = getClusterId(httpSession.getId());
        synchronized (this) {
            Collection collection = this._sessions.get(clusterId);
            if (collection != null) {
                Iterator it = collection.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    HttpSession httpSession2 = (HttpSession) ((WeakReference) it.next()).get();
                    if (httpSession2 == null) {
                        it.remove();
                    } else if (httpSession2 == httpSession) {
                        it.remove();
                        break;
                    }
                }
                if (collection.isEmpty()) {
                    this._sessions.remove(clusterId);
                }
            }
        }
    }

    public void invalidateAll(String str) {
        Collection<WeakReference> remove;
        synchronized (this) {
            remove = this._sessions.remove(str);
        }
        if (remove != null) {
            for (WeakReference weakReference : remove) {
                AbstractSession abstractSession = (AbstractSession) weakReference.get();
                if (abstractSession != null && abstractSession.isValid()) {
                    abstractSession.invalidate();
                }
            }
            remove.clear();
        }
    }
}
