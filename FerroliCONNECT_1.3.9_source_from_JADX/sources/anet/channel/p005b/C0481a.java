package anet.channel.p005b;

import android.support.p000v4.media.session.PlaybackStateCompat;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import anetwork.channel.cache.Cache;
import com.taobao.alivfssdk.cache.AVFSCache;
import com.taobao.alivfssdk.cache.AVFSCacheConfig;
import com.taobao.alivfssdk.cache.AVFSCacheManager;
import com.taobao.alivfssdk.cache.IAVFSCache;

/* renamed from: anet.channel.b.a */
/* compiled from: Taobao */
public class C0481a implements Cache {

    /* renamed from: a */
    private static boolean f163a = false;

    /* renamed from: b */
    private static Object f164b;

    /* renamed from: c */
    private static Object f165c;

    /* renamed from: d */
    private static Object f166d;

    static {
        try {
            Class.forName("com.taobao.alivfssdk.cache.AVFSCacheManager");
            f164b = new C0482b();
            f165c = new C0483c();
            f166d = new C0484d();
        } catch (ClassNotFoundException unused) {
            ALog.m330w("anet.AVFSCacheImpl", "no alivfs sdk!", (String) null, new Object[0]);
        }
    }

    /* renamed from: a */
    public void mo8724a() {
        AVFSCache cacheForModule;
        if (f163a && (cacheForModule = AVFSCacheManager.getInstance().cacheForModule("networksdk.httpcache")) != null) {
            AVFSCacheConfig aVFSCacheConfig = new AVFSCacheConfig();
            aVFSCacheConfig.limitSize = 5242880L;
            aVFSCacheConfig.fileMemMaxSize = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
            cacheForModule.moduleConfig(aVFSCacheConfig);
        }
    }

    public Cache.Entry get(String str) {
        if (!f163a) {
            return null;
        }
        try {
            IAVFSCache b = m56b();
            if (b != null) {
                return (Cache.Entry) b.objectForKey(StringUtils.md5ToHex(str));
            }
        } catch (Exception e) {
            ALog.m326e("anet.AVFSCacheImpl", "get cache failed", (String) null, e, new Object[0]);
        }
        return null;
    }

    public void put(String str, Cache.Entry entry) {
        if (f163a) {
            try {
                IAVFSCache b = m56b();
                if (b != null) {
                    b.setObjectForKey(StringUtils.md5ToHex(str), entry, (IAVFSCache.OnObjectSetCallback) f164b);
                }
            } catch (Exception e) {
                ALog.m326e("anet.AVFSCacheImpl", "put cache failed", (String) null, e, new Object[0]);
            }
        }
    }

    public void remove(String str) {
        if (f163a) {
            try {
                IAVFSCache b = m56b();
                if (b != null) {
                    b.removeObjectForKey(StringUtils.md5ToHex(str), (IAVFSCache.OnObjectRemoveCallback) f165c);
                }
            } catch (Exception e) {
                ALog.m326e("anet.AVFSCacheImpl", "remove cache failed", (String) null, e, new Object[0]);
            }
        }
    }

    public void clear() {
        if (f163a) {
            try {
                IAVFSCache b = m56b();
                if (b != null) {
                    b.removeAllObject((IAVFSCache.OnAllObjectRemoveCallback) f166d);
                }
            } catch (Exception e) {
                ALog.m326e("anet.AVFSCacheImpl", "clear cache failed", (String) null, e, new Object[0]);
            }
        }
    }

    /* renamed from: b */
    private IAVFSCache m56b() {
        AVFSCache cacheForModule = AVFSCacheManager.getInstance().cacheForModule("networksdk.httpcache");
        if (cacheForModule != null) {
            return cacheForModule.getFileCache();
        }
        return null;
    }
}
