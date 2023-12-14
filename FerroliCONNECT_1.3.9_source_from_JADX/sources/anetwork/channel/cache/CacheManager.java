package anetwork.channel.cache;

import anet.channel.util.ALog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: Taobao */
public class CacheManager {

    /* renamed from: a */
    private static List<C0624a> f636a = new ArrayList();

    /* renamed from: b */
    private static final ReentrantReadWriteLock f637b = new ReentrantReadWriteLock();

    /* renamed from: c */
    private static final ReentrantReadWriteLock.ReadLock f638c = f637b.readLock();

    /* renamed from: d */
    private static final ReentrantReadWriteLock.WriteLock f639d = f637b.writeLock();

    /* renamed from: anetwork.channel.cache.CacheManager$a */
    /* compiled from: Taobao */
    private static class C0624a implements Comparable<C0624a> {

        /* renamed from: a */
        final Cache f640a;

        /* renamed from: b */
        final CachePrediction f641b;

        /* renamed from: c */
        final int f642c;

        C0624a(Cache cache, CachePrediction cachePrediction, int i) {
            this.f640a = cache;
            this.f641b = cachePrediction;
            this.f642c = i;
        }

        /* renamed from: a */
        public int compareTo(C0624a aVar) {
            return this.f642c - aVar.f642c;
        }
    }

    public static void addCache(Cache cache, CachePrediction cachePrediction, int i) {
        if (cache == null) {
            throw new IllegalArgumentException("cache is null");
        } else if (cachePrediction != null) {
            try {
                f639d.lock();
                f636a.add(new C0624a(cache, cachePrediction, i));
                Collections.sort(f636a);
            } finally {
                f639d.unlock();
            }
        } else {
            throw new IllegalArgumentException("prediction is null");
        }
    }

    public static void removeCache(Cache cache) {
        try {
            f639d.lock();
            ListIterator<C0624a> listIterator = f636a.listIterator();
            while (true) {
                if (listIterator.hasNext()) {
                    if (listIterator.next().f640a == cache) {
                        listIterator.remove();
                        break;
                    }
                } else {
                    break;
                }
            }
        } finally {
            f639d.unlock();
        }
    }

    public static Cache getCache(String str, Map<String, String> map) {
        Cache cache;
        try {
            f638c.lock();
            Iterator<C0624a> it = f636a.iterator();
            while (true) {
                if (!it.hasNext()) {
                    cache = null;
                    break;
                }
                C0624a next = it.next();
                if (next.f641b.handleCache(str, map)) {
                    cache = next.f640a;
                    break;
                }
            }
            return cache;
        } finally {
            f638c.unlock();
        }
    }

    public static void clearAllCache() {
        ALog.m330w("anet.CacheManager", "clearAllCache", (String) null, new Object[0]);
        for (C0624a aVar : f636a) {
            try {
                aVar.f640a.clear();
            } catch (Exception unused) {
            }
        }
    }
}
