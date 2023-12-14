package android.support.p000v4.text;

import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

/* renamed from: android.support.v4.text.ICUCompat */
public final class ICUCompat {
    private static final String TAG = "ICUCompat";
    private static Method sAddLikelySubtagsMethod;
    private static Method sGetScriptMethod;

    static {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                sAddLikelySubtagsMethod = Class.forName("libcore.icu.ICU").getMethod("addLikelySubtags", new Class[]{Locale.class});
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        } else {
            try {
                Class<?> cls = Class.forName("libcore.icu.ICU");
                if (cls != null) {
                    sGetScriptMethod = cls.getMethod("getScript", new Class[]{String.class});
                    sAddLikelySubtagsMethod = cls.getMethod("addLikelySubtags", new Class[]{String.class});
                }
            } catch (Exception e2) {
                sGetScriptMethod = null;
                sAddLikelySubtagsMethod = null;
                Log.w(TAG, e2);
            }
        }
    }

    @Nullable
    public static String maximizeAndGetScript(Locale locale) {
        if (Build.VERSION.SDK_INT >= 21) {
            try {
                return ((Locale) sAddLikelySubtagsMethod.invoke((Object) null, new Object[]{locale})).getScript();
            } catch (InvocationTargetException e) {
                Log.w(TAG, e);
                return locale.getScript();
            } catch (IllegalAccessException e2) {
                Log.w(TAG, e2);
                return locale.getScript();
            }
        } else {
            String addLikelySubtags = addLikelySubtags(locale);
            if (addLikelySubtags != null) {
                return getScript(addLikelySubtags);
            }
            return null;
        }
    }

    private static String getScript(String str) {
        try {
            if (sGetScriptMethod != null) {
                return (String) sGetScriptMethod.invoke((Object) null, new Object[]{str});
            }
        } catch (IllegalAccessException e) {
            Log.w(TAG, e);
        } catch (InvocationTargetException e2) {
            Log.w(TAG, e2);
        }
        return null;
    }

    private static String addLikelySubtags(Locale locale) {
        String locale2 = locale.toString();
        try {
            if (sAddLikelySubtagsMethod != null) {
                return (String) sAddLikelySubtagsMethod.invoke((Object) null, new Object[]{locale2});
            }
        } catch (IllegalAccessException e) {
            Log.w(TAG, e);
        } catch (InvocationTargetException e2) {
            Log.w(TAG, e2);
        }
        return locale2;
    }

    private ICUCompat() {
    }
}
