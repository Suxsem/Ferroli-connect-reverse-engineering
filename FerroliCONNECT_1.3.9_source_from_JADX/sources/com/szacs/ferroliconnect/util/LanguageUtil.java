package com.szacs.ferroliconnect.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import java.util.Locale;

public class LanguageUtil {
    private static final String LANGUAGE_STR_CHINESE = "中文";
    private static final String LANGUAGE_STR_ENGLISH = "English";
    private static final String LANGUAGE_STR_ES = "España";
    private static final String LANGUAGE_STR_FR = "Français";
    private static final String LANGUAGE_STR_ITALY = "Italiano";
    private static final String LANGUAGE_STR_NL = "Nederlands";
    private static final String LANGUAGE_STR_PL = "Polski";
    private static final String LANGUAGE_STR_PT = "Português";
    private static final String LANGUAGE_STR_RO = "România";
    private static final String LANGUAGE_STR_RU = "Русский";
    private static final String LANGUAGE_STR_TR = "Türkiye";
    private static final String LANGUAGE_STR_UK = "Українська";
    public static final String LOGOGRAM_CHINESE = "zh";
    public static final String LOGOGRAM_ENGLISH = "en";
    public static final String LOGOGRAM_ES = "es";
    public static final String LOGOGRAM_FR = "fr";
    public static final String LOGOGRAM_ITALY = "it";
    public static final String LOGOGRAM_NL = "nl";
    public static final String LOGOGRAM_PL = "pl";
    public static final String LOGOGRAM_PT = "pt";
    public static final String LOGOGRAM_RO = "ro";
    public static final String LOGOGRAM_RU = "ru";
    public static final String LOGOGRAM_TR = "tr";
    public static final String LOGOGRAM_UK = "uk";
    private static final String TAG = "LanguageUtil";

    public static void changeAppLanguage(Context context, String str) {
        Log.i(TAG, "language: " + str);
        if (!TextUtils.isEmpty(str)) {
            Resources resources = context.getResources();
            Configuration configuration = resources.getConfiguration();
            Locale localeByLanguage = getLocaleByLanguage(str);
            if (Build.VERSION.SDK_INT < 24) {
                configuration.setLocale(localeByLanguage);
            } else {
                configuration.setLocale(localeByLanguage);
                configuration.setLocales(new LocaleList(new Locale[]{localeByLanguage}));
            }
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Locale getSetLanguageLocale(android.content.Context r10) {
        /*
            com.szacs.ferroliconnect.util.SpUtil r0 = com.szacs.ferroliconnect.util.SpUtil.getInstance(r10)
            java.lang.String r1 = "language"
            java.lang.String r0 = r0.getString(r1)
            int r1 = r0.hashCode()
            java.lang.String r2 = "uk"
            java.lang.String r3 = "tr"
            java.lang.String r4 = "ru"
            java.lang.String r5 = "ro"
            java.lang.String r6 = "pt"
            java.lang.String r7 = "pl"
            java.lang.String r8 = "nl"
            java.lang.String r9 = "es"
            switch(r1) {
                case 3241: goto L_0x0086;
                case 3246: goto L_0x007e;
                case 3276: goto L_0x0074;
                case 3371: goto L_0x006a;
                case 3518: goto L_0x0061;
                case 3580: goto L_0x0058;
                case 3588: goto L_0x004f;
                case 3645: goto L_0x0047;
                case 3651: goto L_0x003f;
                case 3710: goto L_0x0037;
                case 3734: goto L_0x002e;
                case 3886: goto L_0x0023;
                default: goto L_0x0021;
            }
        L_0x0021:
            goto L_0x0090
        L_0x0023:
            java.lang.String r1 = "zh"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0090
            r0 = 0
            goto L_0x0091
        L_0x002e:
            boolean r0 = r0.equals(r2)
            if (r0 == 0) goto L_0x0090
            r0 = 8
            goto L_0x0091
        L_0x0037:
            boolean r0 = r0.equals(r3)
            if (r0 == 0) goto L_0x0090
            r0 = 6
            goto L_0x0091
        L_0x003f:
            boolean r0 = r0.equals(r4)
            if (r0 == 0) goto L_0x0090
            r0 = 7
            goto L_0x0091
        L_0x0047:
            boolean r0 = r0.equals(r5)
            if (r0 == 0) goto L_0x0090
            r0 = 5
            goto L_0x0091
        L_0x004f:
            boolean r0 = r0.equals(r6)
            if (r0 == 0) goto L_0x0090
            r0 = 11
            goto L_0x0091
        L_0x0058:
            boolean r0 = r0.equals(r7)
            if (r0 == 0) goto L_0x0090
            r0 = 10
            goto L_0x0091
        L_0x0061:
            boolean r0 = r0.equals(r8)
            if (r0 == 0) goto L_0x0090
            r0 = 9
            goto L_0x0091
        L_0x006a:
            java.lang.String r1 = "it"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0090
            r0 = 2
            goto L_0x0091
        L_0x0074:
            java.lang.String r1 = "fr"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0090
            r0 = 4
            goto L_0x0091
        L_0x007e:
            boolean r0 = r0.equals(r9)
            if (r0 == 0) goto L_0x0090
            r0 = 3
            goto L_0x0091
        L_0x0086:
            java.lang.String r1 = "en"
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x0090
            r0 = 1
            goto L_0x0091
        L_0x0090:
            r0 = -1
        L_0x0091:
            switch(r0) {
                case 0: goto L_0x00e2;
                case 1: goto L_0x00df;
                case 2: goto L_0x00dc;
                case 3: goto L_0x00d4;
                case 4: goto L_0x00d1;
                case 5: goto L_0x00c9;
                case 6: goto L_0x00c1;
                case 7: goto L_0x00b9;
                case 8: goto L_0x00b1;
                case 9: goto L_0x00a9;
                case 10: goto L_0x00a1;
                case 11: goto L_0x0099;
                default: goto L_0x0094;
            }
        L_0x0094:
            java.util.Locale r10 = getSystemLocale(r10)
            return r10
        L_0x0099:
            java.util.Locale r10 = new java.util.Locale
            java.lang.String r0 = "PT"
            r10.<init>(r6, r0)
            return r10
        L_0x00a1:
            java.util.Locale r10 = new java.util.Locale
            java.lang.String r0 = "PL"
            r10.<init>(r7, r0)
            return r10
        L_0x00a9:
            java.util.Locale r10 = new java.util.Locale
            java.lang.String r0 = "NL"
            r10.<init>(r8, r0)
            return r10
        L_0x00b1:
            java.util.Locale r10 = new java.util.Locale
            java.lang.String r0 = "UA"
            r10.<init>(r2, r0)
            return r10
        L_0x00b9:
            java.util.Locale r10 = new java.util.Locale
            java.lang.String r0 = "RU"
            r10.<init>(r4, r0)
            return r10
        L_0x00c1:
            java.util.Locale r10 = new java.util.Locale
            java.lang.String r0 = "TR"
            r10.<init>(r3, r0)
            return r10
        L_0x00c9:
            java.util.Locale r10 = new java.util.Locale
            java.lang.String r0 = "RO"
            r10.<init>(r5, r0)
            return r10
        L_0x00d1:
            java.util.Locale r10 = java.util.Locale.FRANCE
            return r10
        L_0x00d4:
            java.util.Locale r10 = new java.util.Locale
            java.lang.String r0 = "ES"
            r10.<init>(r9, r0)
            return r10
        L_0x00dc:
            java.util.Locale r10 = java.util.Locale.ITALIAN
            return r10
        L_0x00df:
            java.util.Locale r10 = java.util.Locale.ENGLISH
            return r10
        L_0x00e2:
            java.util.Locale r10 = java.util.Locale.CHINA
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.LanguageUtil.getSetLanguageLocale(android.content.Context):java.util.Locale");
    }

    public static void setApplicationLanguage(Context context) {
        Resources resources = context.getApplicationContext().getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        Locale setLanguageLocale = getSetLanguageLocale(context);
        configuration.locale = setLanguageLocale;
        if (Build.VERSION.SDK_INT >= 24) {
            LocaleList localeList = new LocaleList(new Locale[]{setLanguageLocale});
            LocaleList.setDefault(localeList);
            configuration.setLocales(localeList);
            context.getApplicationContext().createConfigurationContext(configuration);
            Locale.setDefault(setLanguageLocale);
        }
        resources.updateConfiguration(configuration, displayMetrics);
    }

    public static Locale getLocaleByLanguage(String str) {
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        if (str.equals(LanguageType.CHINESE.getLanguage())) {
            locale = Locale.SIMPLIFIED_CHINESE;
        } else if (str.equals(LanguageType.ENGLISH.getLanguage())) {
            locale = Locale.ENGLISH;
        } else if (str.equals(LanguageType.THAILAND.getLanguage())) {
            locale = Locale.forLanguageTag(str);
        } else if (str.equals(LanguageType.ITALY.getLanguage())) {
            locale = Locale.ITALIAN;
        } else if (str.equals(LOGOGRAM_ES)) {
            locale = Locale.forLanguageTag(str);
        } else if (str.equals(LOGOGRAM_FR)) {
            locale = Locale.FRANCE;
        } else if (str.equals(LOGOGRAM_RO)) {
            locale = Locale.forLanguageTag(str);
        } else if (str.equals(LOGOGRAM_RU)) {
            locale = Locale.forLanguageTag(str);
        } else if (str.equals(LOGOGRAM_UK)) {
            locale = Locale.forLanguageTag(str);
        } else if (str.equals(LOGOGRAM_TR)) {
            locale = Locale.forLanguageTag(str);
        } else if (str.equals(LOGOGRAM_NL)) {
            locale = Locale.forLanguageTag(str);
        } else if (str.equals(LOGOGRAM_PL)) {
            locale = Locale.forLanguageTag(str);
        } else if (str.equals(LOGOGRAM_PT)) {
            locale = Locale.forLanguageTag(str);
        }
        Log.d(TAG, "getLocaleByLanguage: " + locale.getDisplayName());
        return locale;
    }

    public static Context attachBaseContext(Context context, String str) {
        Log.d(TAG, "attachBaseContext: " + Build.VERSION.SDK_INT);
        return Build.VERSION.SDK_INT >= 24 ? updateResources(context, str) : context;
    }

    @TargetApi(24)
    private static Context updateResources(Context context, String str) {
        Resources resources = context.getResources();
        Locale localeByLanguage = getLocaleByLanguage(str);
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(localeByLanguage);
        configuration.setLocales(new LocaleList(new Locale[]{localeByLanguage}));
        return context.createConfigurationContext(configuration);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String convertLogogram2Name(java.lang.String r1) {
        /*
            int r0 = r1.hashCode()
            switch(r0) {
                case 3241: goto L_0x007d;
                case 3246: goto L_0x0073;
                case 3276: goto L_0x0069;
                case 3371: goto L_0x005f;
                case 3518: goto L_0x0054;
                case 3580: goto L_0x0049;
                case 3588: goto L_0x003e;
                case 3645: goto L_0x0034;
                case 3651: goto L_0x002a;
                case 3710: goto L_0x0020;
                case 3734: goto L_0x0014;
                case 3886: goto L_0x0009;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x0087
        L_0x0009:
            java.lang.String r0 = "zh"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 0
            goto L_0x0088
        L_0x0014:
            java.lang.String r0 = "uk"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 8
            goto L_0x0088
        L_0x0020:
            java.lang.String r0 = "tr"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 7
            goto L_0x0088
        L_0x002a:
            java.lang.String r0 = "ru"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 5
            goto L_0x0088
        L_0x0034:
            java.lang.String r0 = "ro"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 6
            goto L_0x0088
        L_0x003e:
            java.lang.String r0 = "pt"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 11
            goto L_0x0088
        L_0x0049:
            java.lang.String r0 = "pl"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 10
            goto L_0x0088
        L_0x0054:
            java.lang.String r0 = "nl"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 9
            goto L_0x0088
        L_0x005f:
            java.lang.String r0 = "it"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 2
            goto L_0x0088
        L_0x0069:
            java.lang.String r0 = "fr"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 4
            goto L_0x0088
        L_0x0073:
            java.lang.String r0 = "es"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 3
            goto L_0x0088
        L_0x007d:
            java.lang.String r0 = "en"
            boolean r1 = r1.equals(r0)
            if (r1 == 0) goto L_0x0087
            r1 = 1
            goto L_0x0088
        L_0x0087:
            r1 = -1
        L_0x0088:
            switch(r1) {
                case 0: goto L_0x00af;
                case 1: goto L_0x00ac;
                case 2: goto L_0x00a9;
                case 3: goto L_0x00a6;
                case 4: goto L_0x00a3;
                case 5: goto L_0x00a0;
                case 6: goto L_0x009d;
                case 7: goto L_0x009a;
                case 8: goto L_0x0097;
                case 9: goto L_0x0094;
                case 10: goto L_0x0091;
                case 11: goto L_0x008e;
                default: goto L_0x008b;
            }
        L_0x008b:
            java.lang.String r1 = ""
            goto L_0x00b1
        L_0x008e:
            java.lang.String r1 = "Português"
            goto L_0x00b1
        L_0x0091:
            java.lang.String r1 = "Polski"
            goto L_0x00b1
        L_0x0094:
            java.lang.String r1 = "Nederlands"
            goto L_0x00b1
        L_0x0097:
            java.lang.String r1 = "Українська"
            goto L_0x00b1
        L_0x009a:
            java.lang.String r1 = "Türkiye"
            goto L_0x00b1
        L_0x009d:
            java.lang.String r1 = "România"
            goto L_0x00b1
        L_0x00a0:
            java.lang.String r1 = "Русский"
            goto L_0x00b1
        L_0x00a3:
            java.lang.String r1 = "Français"
            goto L_0x00b1
        L_0x00a6:
            java.lang.String r1 = "España"
            goto L_0x00b1
        L_0x00a9:
            java.lang.String r1 = "Italiano"
            goto L_0x00b1
        L_0x00ac:
            java.lang.String r1 = "English"
            goto L_0x00b1
        L_0x00af:
            java.lang.String r1 = "中文"
        L_0x00b1:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.util.LanguageUtil.convertLogogram2Name(java.lang.String):java.lang.String");
    }

    public static Context setLocal(Context context) {
        return updateLocal(context, getSetLanguageLocale(context));
    }

    private static Context updateLocal(Context context, Locale locale) {
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            configuration.setLocale(locale);
            return context.createConfigurationContext(configuration);
        }
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public static Locale getSystemLocale(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return LocaleList.getDefault().get(0);
        }
        return Locale.getDefault();
    }
}
