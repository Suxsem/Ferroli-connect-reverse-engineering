package p117me.jessyan.autosize;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import p117me.jessyan.autosize.external.ExternalAdaptInfo;
import p117me.jessyan.autosize.internal.CustomAdapt;
import p117me.jessyan.autosize.unit.Subunits;
import p117me.jessyan.autosize.utils.Preconditions;

/* renamed from: me.jessyan.autosize.AutoSizeCompat */
public final class AutoSizeCompat {
    private static Map<String, DisplayMetricsInfo> mCache = new ConcurrentHashMap();

    private AutoSizeCompat() {
        throw new IllegalStateException("you can't instantiate me!");
    }

    public static void autoConvertDensityOfGlobal(Resources resources) {
        if (AutoSizeConfig.getInstance().isBaseOnWidth()) {
            autoConvertDensityBaseOnWidth(resources, (float) AutoSizeConfig.getInstance().getDesignWidthInDp());
        } else {
            autoConvertDensityBaseOnHeight(resources, (float) AutoSizeConfig.getInstance().getDesignHeightInDp());
        }
    }

    public static void autoConvertDensityOfCustomAdapt(Resources resources, CustomAdapt customAdapt) {
        int i;
        Preconditions.checkNotNull(customAdapt, "customAdapt == null");
        float sizeInDp = customAdapt.getSizeInDp();
        if (sizeInDp <= 0.0f) {
            if (customAdapt.isBaseOnWidth()) {
                i = AutoSizeConfig.getInstance().getDesignWidthInDp();
            } else {
                i = AutoSizeConfig.getInstance().getDesignHeightInDp();
            }
            sizeInDp = (float) i;
        }
        autoConvertDensity(resources, sizeInDp, customAdapt.isBaseOnWidth());
    }

    public static void autoConvertDensityOfExternalAdaptInfo(Resources resources, ExternalAdaptInfo externalAdaptInfo) {
        int i;
        Preconditions.checkNotNull(externalAdaptInfo, "externalAdaptInfo == null");
        float sizeInDp = externalAdaptInfo.getSizeInDp();
        if (sizeInDp <= 0.0f) {
            if (externalAdaptInfo.isBaseOnWidth()) {
                i = AutoSizeConfig.getInstance().getDesignWidthInDp();
            } else {
                i = AutoSizeConfig.getInstance().getDesignHeightInDp();
            }
            sizeInDp = (float) i;
        }
        autoConvertDensity(resources, sizeInDp, externalAdaptInfo.isBaseOnWidth());
    }

    public static void autoConvertDensityBaseOnWidth(Resources resources, float f) {
        autoConvertDensity(resources, f, true);
    }

    public static void autoConvertDensityBaseOnHeight(Resources resources, float f) {
        autoConvertDensity(resources, f, false);
    }

    public static void autoConvertDensity(Resources resources, float f, boolean z) {
        float f2;
        int i;
        float f3;
        float f4;
        int i2;
        float f5;
        int i3;
        float f6;
        int i4;
        Preconditions.checkNotNull(resources, "resources == null");
        if (z) {
            f2 = AutoSizeConfig.getInstance().getUnitsManager().getDesignWidth();
        } else {
            f2 = AutoSizeConfig.getInstance().getUnitsManager().getDesignHeight();
        }
        if (f2 <= 0.0f) {
            f2 = f;
        }
        if (z) {
            i = AutoSizeConfig.getInstance().getScreenWidth();
        } else {
            i = AutoSizeConfig.getInstance().getScreenHeight();
        }
        String str = f + "|" + f2 + "|" + z + "|" + AutoSizeConfig.getInstance().isUseDeviceSize() + "|" + AutoSizeConfig.getInstance().getInitScaledDensity() + "|" + i;
        DisplayMetricsInfo displayMetricsInfo = mCache.get(str);
        if (displayMetricsInfo == null) {
            if (z) {
                i3 = AutoSizeConfig.getInstance().getScreenWidth();
            } else {
                i3 = AutoSizeConfig.getInstance().getScreenHeight();
            }
            f5 = (((float) i3) * 1.0f) / f;
            if (AutoSizeConfig.getInstance().isExcludeFontScale()) {
                f6 = 1.0f;
            } else {
                f6 = (AutoSizeConfig.getInstance().getInitScaledDensity() * 1.0f) / AutoSizeConfig.getInstance().getInitDensity();
            }
            f4 = f6 * f5;
            i2 = (int) (160.0f * f5);
            if (z) {
                i4 = AutoSizeConfig.getInstance().getScreenWidth();
            } else {
                i4 = AutoSizeConfig.getInstance().getScreenHeight();
            }
            f3 = (((float) i4) * 1.0f) / f2;
            mCache.put(str, new DisplayMetricsInfo(f5, i2, f4, f3));
        } else {
            f5 = displayMetricsInfo.getDensity();
            i2 = displayMetricsInfo.getDensityDpi();
            f4 = displayMetricsInfo.getScaledDensity();
            f3 = displayMetricsInfo.getXdpi();
        }
        setDensity(resources, f5, i2, f4, f3);
    }

    /* renamed from: me.jessyan.autosize.AutoSizeCompat$1 */
    static /* synthetic */ class C23221 {
        static final /* synthetic */ int[] $SwitchMap$me$jessyan$autosize$unit$Subunits = new int[Subunits.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(10:0|1|2|3|4|5|6|7|8|10) */
        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|(3:7|8|10)) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        static {
            /*
                me.jessyan.autosize.unit.Subunits[] r0 = p117me.jessyan.autosize.unit.Subunits.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$me$jessyan$autosize$unit$Subunits = r0
                int[] r0 = $SwitchMap$me$jessyan$autosize$unit$Subunits     // Catch:{ NoSuchFieldError -> 0x0014 }
                me.jessyan.autosize.unit.Subunits r1 = p117me.jessyan.autosize.unit.Subunits.f4053PT     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$me$jessyan$autosize$unit$Subunits     // Catch:{ NoSuchFieldError -> 0x001f }
                me.jessyan.autosize.unit.Subunits r1 = p117me.jessyan.autosize.unit.Subunits.MM     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$me$jessyan$autosize$unit$Subunits     // Catch:{ NoSuchFieldError -> 0x002a }
                me.jessyan.autosize.unit.Subunits r1 = p117me.jessyan.autosize.unit.Subunits.NONE     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$me$jessyan$autosize$unit$Subunits     // Catch:{ NoSuchFieldError -> 0x0035 }
                me.jessyan.autosize.unit.Subunits r1 = p117me.jessyan.autosize.unit.Subunits.IN     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: p117me.jessyan.autosize.AutoSizeCompat.C23221.<clinit>():void");
        }
    }

    public static void cancelAdapt(Resources resources) {
        float f;
        float initXdpi = AutoSizeConfig.getInstance().getInitXdpi();
        int i = C23221.$SwitchMap$me$jessyan$autosize$unit$Subunits[AutoSizeConfig.getInstance().getUnitsManager().getSupportSubunits().ordinal()];
        if (i != 1) {
            if (i == 2) {
                f = 25.4f;
            }
            setDensity(resources, AutoSizeConfig.getInstance().getInitDensity(), AutoSizeConfig.getInstance().getInitDensityDpi(), AutoSizeConfig.getInstance().getInitScaledDensity(), initXdpi);
        }
        f = 72.0f;
        initXdpi /= f;
        setDensity(resources, AutoSizeConfig.getInstance().getInitDensity(), AutoSizeConfig.getInstance().getInitDensityDpi(), AutoSizeConfig.getInstance().getInitScaledDensity(), initXdpi);
    }

    private static void setDensity(Resources resources, float f, int i, float f2, float f3) {
        DisplayMetrics metricsOnMiui = getMetricsOnMiui(resources);
        DisplayMetrics metricsOnMiui2 = getMetricsOnMiui(AutoSizeConfig.getInstance().getApplication().getResources());
        if (metricsOnMiui != null) {
            setDensity(metricsOnMiui, f, i, f2, f3);
        } else {
            setDensity(resources.getDisplayMetrics(), f, i, f2, f3);
        }
        if (metricsOnMiui2 != null) {
            setDensity(metricsOnMiui2, f, i, f2, f3);
        } else {
            setDensity(AutoSizeConfig.getInstance().getApplication().getResources().getDisplayMetrics(), f, i, f2, f3);
        }
    }

    private static void setDensity(DisplayMetrics displayMetrics, float f, int i, float f2, float f3) {
        if (AutoSizeConfig.getInstance().getUnitsManager().isSupportDP()) {
            displayMetrics.density = f;
            displayMetrics.densityDpi = i;
        }
        if (AutoSizeConfig.getInstance().getUnitsManager().isSupportSP()) {
            displayMetrics.scaledDensity = f2;
        }
        int i2 = C23221.$SwitchMap$me$jessyan$autosize$unit$Subunits[AutoSizeConfig.getInstance().getUnitsManager().getSupportSubunits().ordinal()];
        if (i2 == 1) {
            displayMetrics.xdpi = f3 * 72.0f;
        } else if (i2 == 2) {
            displayMetrics.xdpi = f3 * 25.4f;
        } else if (i2 != 3 && i2 == 4) {
            displayMetrics.xdpi = f3;
        }
    }

    private static DisplayMetrics getMetricsOnMiui(Resources resources) {
        if (AutoSizeConfig.getInstance().isMiui() && AutoSizeConfig.getInstance().getTmpMetricsField() != null) {
            try {
                return (DisplayMetrics) AutoSizeConfig.getInstance().getTmpMetricsField().get(resources);
            } catch (Exception unused) {
            }
        }
        return null;
    }
}
