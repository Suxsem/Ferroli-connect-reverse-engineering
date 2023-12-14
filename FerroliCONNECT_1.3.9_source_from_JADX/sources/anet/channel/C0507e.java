package anet.channel;

import anet.channel.entity.C0519c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* renamed from: anet.channel.e */
/* compiled from: Taobao */
class C0507e {

    /* renamed from: a */
    private final Map<SessionRequest, List<Session>> f219a = new HashMap();

    /* renamed from: b */
    private final ReentrantReadWriteLock f220b = new ReentrantReadWriteLock();

    /* renamed from: c */
    private final ReentrantReadWriteLock.ReadLock f221c = this.f220b.readLock();

    /* renamed from: d */
    private final ReentrantReadWriteLock.WriteLock f222d = this.f220b.writeLock();

    C0507e() {
    }

    /* renamed from: a */
    public void mo8792a(SessionRequest sessionRequest, Session session) {
        if (sessionRequest != null && sessionRequest.mo8695a() != null && session != null) {
            this.f222d.lock();
            try {
                List list = this.f219a.get(sessionRequest);
                if (list == null) {
                    list = new ArrayList();
                    this.f219a.put(sessionRequest, list);
                }
                if (list.indexOf(session) == -1) {
                    list.add(session);
                    Collections.sort(list);
                    this.f222d.unlock();
                }
            } finally {
                this.f222d.unlock();
            }
        }
    }

    /* renamed from: b */
    public void mo8793b(SessionRequest sessionRequest, Session session) {
        this.f222d.lock();
        try {
            List list = this.f219a.get(sessionRequest);
            if (list != null) {
                list.remove(session);
                if (list.size() == 0) {
                    this.f219a.remove(sessionRequest);
                }
                this.f222d.unlock();
            }
        } finally {
            this.f222d.unlock();
        }
    }

    /* renamed from: a */
    public List<Session> mo8791a(SessionRequest sessionRequest) {
        this.f221c.lock();
        try {
            List list = this.f219a.get(sessionRequest);
            if (list != null) {
                return new ArrayList(list);
            }
            List<Session> list2 = Collections.EMPTY_LIST;
            this.f221c.unlock();
            return list2;
        } finally {
            this.f221c.unlock();
        }
    }

    /* renamed from: a */
    public Session mo8789a(SessionRequest sessionRequest, int i) {
        this.f221c.lock();
        try {
            List list = this.f219a.get(sessionRequest);
            Session session = null;
            if (list != null) {
                if (!list.isEmpty()) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Session session2 = (Session) it.next();
                        if (session2 != null && session2.isAvailable()) {
                            if (i == C0519c.f251c || session2.f98j.getType() == i) {
                                session = session2;
                            }
                        }
                    }
                    this.f221c.unlock();
                    return session;
                }
            }
            return null;
        } finally {
            this.f221c.unlock();
        }
    }

    /* renamed from: a */
    public List<SessionRequest> mo8790a() {
        List<SessionRequest> list = Collections.EMPTY_LIST;
        this.f221c.lock();
        try {
            if (this.f219a.isEmpty()) {
                return list;
            }
            ArrayList arrayList = new ArrayList(this.f219a.keySet());
            this.f221c.unlock();
            return arrayList;
        } finally {
            this.f221c.unlock();
        }
    }

    /* renamed from: c */
    public boolean mo8794c(SessionRequest sessionRequest, Session session) {
        this.f221c.lock();
        try {
            List list = this.f219a.get(sessionRequest);
            boolean z = false;
            if (list != null) {
                if (list.indexOf(session) != -1) {
                    z = true;
                }
            }
            return z;
        } finally {
            this.f221c.unlock();
        }
    }
}
