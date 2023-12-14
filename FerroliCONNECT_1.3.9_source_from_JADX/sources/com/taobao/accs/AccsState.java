package com.taobao.accs;

import android.os.SystemClock;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Taobao */
public class AccsState {
    public static final String ALL = "all";
    public static final String BIND_APP_FROM_CACHE = "bfc";
    public static final String CONNECTION_CHANGE = "cc";
    public static final String LAST_MSG_RECEIVE_TIME = "lmrt";
    public static final String LAST_MSG_SEND_TIME = "lmst";
    public static final String RECENT_ERRORS = "re";
    public static final String SDK_VERSION = "sv";

    /* renamed from: a */
    private HashMap<String, C2000c> f3199a = new HashMap<>();

    /* renamed from: b */
    private long f3200b = -1;

    /* renamed from: c */
    private long f3201c = -1;

    /* renamed from: com.taobao.accs.AccsState$a */
    /* compiled from: Taobao */
    private static class C1998a {
        /* access modifiers changed from: private */

        /* renamed from: a */
        public static final AccsState f3202a = new AccsState();

        private C1998a() {
        }
    }

    public static AccsState getInstance() {
        return C1998a.f3202a;
    }

    protected AccsState() {
    }

    /* renamed from: a */
    public synchronized void mo18226a(String str, Object obj) {
        m3408a(ALL).mo18234a(str, obj, m3412b());
    }

    /* renamed from: b */
    public synchronized void mo18228b(String str, Object obj) {
        m3408a(ALL).mo18237b(str, obj, m3412b());
    }

    public synchronized String getState() {
        if (!m3410a(this.f3199a)) {
            return "{}";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("t", this.f3201c);
            Iterator it = new ArrayList(this.f3199a.entrySet()).iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                jSONObject.put((String) entry.getKey(), ((C2000c) entry.getValue()).mo18233a());
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return jSONObject.toString();
    }

    public synchronized String getStateByKey(String str) {
        if (!m3411a(this.f3199a, str)) {
            return "{}";
        }
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("t", this.f3201c);
            Iterator it = new ArrayList(this.f3199a.entrySet()).iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (((C2000c) entry.getValue()).mo18235a(str)) {
                    jSONObject.put((String) entry.getKey(), ((C2000c) entry.getValue()).mo18236b(str));
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return jSONObject.toString();
    }

    /* renamed from: a */
    public synchronized void mo18227a(String str, String str2, Object obj) {
        m3408a(str).mo18234a(str2, obj, m3412b());
    }

    /* renamed from: b */
    public synchronized void mo18229b(String str, String str2, Object obj) {
        m3408a(str).mo18237b(str2, obj, m3412b());
    }

    /* renamed from: a */
    private C2000c m3408a(String str) {
        C2000c cVar = this.f3199a.get(str);
        if (cVar != null) {
            return cVar;
        }
        C2000c cVar2 = new C2000c();
        this.f3199a.put(str, cVar2);
        return cVar2;
    }

    /* renamed from: a */
    private void m3409a() {
        if (this.f3201c < 0 || this.f3200b < 0) {
            this.f3201c = System.currentTimeMillis();
            this.f3200b = SystemClock.elapsedRealtime();
        }
    }

    /* renamed from: b */
    private long m3412b() {
        m3409a();
        return SystemClock.elapsedRealtime() - this.f3200b;
    }

    /* renamed from: a */
    private boolean m3410a(HashMap<String, C2000c> hashMap) {
        return hashMap.size() > 0;
    }

    /* renamed from: a */
    private boolean m3411a(HashMap<String, C2000c> hashMap, String str) {
        Iterator it = new ArrayList(hashMap.values()).iterator();
        while (it.hasNext()) {
            if (((C2000c) it.next()).mo18235a(str)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: com.taobao.accs.AccsState$c */
    /* compiled from: Taobao */
    private static class C2000c {
        public static final int MAX_HISTORY = 5;

        /* renamed from: a */
        private HashMap<String, C1999b> f3206a;

        /* renamed from: b */
        private HashMap<String, ArrayList<C1999b>> f3207b;

        private C2000c() {
            this.f3206a = new HashMap<>();
            this.f3207b = new HashMap<>();
        }

        /* renamed from: a */
        public void mo18234a(String str, Object obj, long j) {
            this.f3206a.put(str, new C1999b(j, str, m3419a(obj)));
        }

        /* renamed from: b */
        public void mo18237b(String str, Object obj, long j) {
            ArrayList arrayList = this.f3207b.get(str);
            if (arrayList == null) {
                arrayList = new ArrayList();
                this.f3207b.put(str, arrayList);
            }
            arrayList.add(new C1999b(j, str, m3419a(obj)));
            while (arrayList.size() > 5) {
                arrayList.remove(0);
            }
        }

        /* renamed from: a */
        private static String m3419a(Object obj) {
            return obj == null ? "null" : obj.toString();
        }

        /* renamed from: a */
        public boolean mo18235a(String str) {
            return this.f3206a.keySet().contains(str) || this.f3207b.keySet().contains(str);
        }

        /* renamed from: b */
        public JSONArray mo18236b(String str) {
            JSONArray jSONArray = new JSONArray();
            C1999b bVar = this.f3206a.get(str);
            if (bVar != null) {
                jSONArray.put(bVar.mo18232a());
            }
            ArrayList arrayList = this.f3207b.get(str);
            if (arrayList != null) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    jSONArray.put(((C1999b) it.next()).mo18232a());
                }
            }
            return jSONArray;
        }

        /* renamed from: a */
        public JSONArray mo18233a() {
            JSONArray jSONArray = new JSONArray();
            Iterator it = new ArrayList(this.f3206a.values()).iterator();
            while (it.hasNext()) {
                jSONArray.put(((C1999b) it.next()).mo18232a());
            }
            ArrayList arrayList = new ArrayList();
            for (ArrayList<C1999b> addAll : this.f3207b.values()) {
                arrayList.addAll(addAll);
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                jSONArray.put(((C1999b) it2.next()).mo18232a());
            }
            return jSONArray;
        }
    }

    /* renamed from: com.taobao.accs.AccsState$b */
    /* compiled from: Taobao */
    private static class C1999b {

        /* renamed from: a */
        public long f3203a;

        /* renamed from: b */
        public String f3204b;

        /* renamed from: c */
        public String f3205c;

        public C1999b(long j, String str, String str2) {
            this.f3203a = j;
            this.f3204b = str;
            this.f3205c = str2;
        }

        /* renamed from: a */
        public JSONArray mo18232a() {
            JSONArray jSONArray = new JSONArray();
            jSONArray.put(this.f3203a);
            jSONArray.put(this.f3204b);
            jSONArray.put(this.f3205c);
            return jSONArray;
        }
    }
}
