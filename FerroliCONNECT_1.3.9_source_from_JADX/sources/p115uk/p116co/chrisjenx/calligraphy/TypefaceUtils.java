package p115uk.p116co.chrisjenx.calligraphy;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

/* renamed from: uk.co.chrisjenx.calligraphy.TypefaceUtils */
public final class TypefaceUtils {
    private static final Map<String, Typeface> sCachedFonts = new HashMap();
    private static final Map<Typeface, CalligraphyTypefaceSpan> sCachedSpans = new HashMap();

    public static Typeface load(AssetManager assetManager, String str) {
        synchronized (sCachedFonts) {
            try {
                if (!sCachedFonts.containsKey(str)) {
                    Typeface createFromAsset = Typeface.createFromAsset(assetManager, str);
                    sCachedFonts.put(str, createFromAsset);
                    return createFromAsset;
                }
                Typeface typeface = sCachedFonts.get(str);
                return typeface;
            } catch (Exception e) {
                Log.w("Calligraphy", "Can't create asset from " + str + ". Make sure you have passed in the correct path and file name.", e);
                sCachedFonts.put(str, (Object) null);
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public static CalligraphyTypefaceSpan getSpan(Typeface typeface) {
        if (typeface == null) {
            return null;
        }
        synchronized (sCachedSpans) {
            if (!sCachedSpans.containsKey(typeface)) {
                CalligraphyTypefaceSpan calligraphyTypefaceSpan = new CalligraphyTypefaceSpan(typeface);
                sCachedSpans.put(typeface, calligraphyTypefaceSpan);
                return calligraphyTypefaceSpan;
            }
            CalligraphyTypefaceSpan calligraphyTypefaceSpan2 = sCachedSpans.get(typeface);
            return calligraphyTypefaceSpan2;
        }
    }

    public static boolean isLoaded(Typeface typeface) {
        return typeface != null && sCachedFonts.containsValue(typeface);
    }

    private TypefaceUtils() {
    }
}
