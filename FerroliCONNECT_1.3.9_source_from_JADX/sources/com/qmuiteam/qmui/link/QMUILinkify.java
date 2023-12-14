package com.qmuiteam.qmui.link;

import android.content.res.ColorStateList;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import com.qmuiteam.qmui.span.QMUIOnSpanClickListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QMUILinkify {
    public static final int ALL = 15;
    public static final int EMAIL_ADDRESSES = 2;
    public static final int MAP_ADDRESSES = 8;
    private static final int MAX_NUMBER = 21;
    public static final Pattern NOT_PHONE = Pattern.compile("^\\d+(\\.\\d+)+(-\\d+)*$");
    public static final int PHONE_NUMBERS = 4;
    private static final int PHONE_NUMBER_MINIMUM_DIGITS = 7;
    private static final String UrlEndAppendNextChars = "[$]";
    public static final int WEB_URLS = 1;
    public static final Pattern WECHAT_PHONE = Pattern.compile("\\+?(\\d{2,8}([- ]?\\d{3,8}){2,6}|\\d{5,20})");
    public static final MatchFilter sPhoneNumberMatchFilter = new MatchFilter() {
        public final boolean acceptMatch(CharSequence charSequence, int i, int i2) {
            int i3 = 0;
            while (i < i2) {
                if (Character.isDigit(charSequence.charAt(i)) && (i3 = i3 + 1) >= 7) {
                    return true;
                }
                i++;
            }
            return false;
        }
    };
    public static final TransformFilter sPhoneNumberTransformFilter = new TransformFilter() {
        public final String transformUrl(Matcher matcher, String str) {
            return Patterns.digitsAndPlusOnly(matcher);
        }
    };
    public static final MatchFilter sUrlMatchFilter = new MatchFilter() {
        public final boolean acceptMatch(CharSequence charSequence, int i, int i2) {
            int i3 = i;
            while (i3 < i2) {
                try {
                    if (charSequence.charAt(i3) > 256) {
                        return false;
                    }
                    i3++;
                } catch (Exception unused) {
                }
            }
            try {
                char charAt = charSequence.charAt(i2);
                if (charAt < 256 && QMUILinkify.UrlEndAppendNextChars.indexOf(charAt) < 0 && !Character.isWhitespace(charAt)) {
                    return false;
                }
            } catch (Exception unused2) {
            }
            if (i == 0) {
                return true;
            }
            return charSequence.charAt(i - 1) != '@';
        }
    };

    public interface MatchFilter {
        boolean acceptMatch(CharSequence charSequence, int i, int i2);
    }

    public interface TransformFilter {
        String transformUrl(Matcher matcher, String str);
    }

    public static boolean addLinks(Spannable spannable, int i, ColorStateList colorStateList, ColorStateList colorStateList2, QMUIOnSpanClickListener qMUIOnSpanClickListener) {
        if (i == 0) {
            return false;
        }
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int length = uRLSpanArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(uRLSpanArr[length]);
        }
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            gatherLinks(arrayList, spannable, Patterns.WEB_URL, new String[]{"http://", "https://", "rtsp://"}, sUrlMatchFilter, (TransformFilter) null);
        }
        if ((i & 2) != 0) {
            gatherLinks(arrayList, spannable, Patterns.EMAIL_ADDRESS, new String[]{"mailto:"}, (MatchFilter) null, (TransformFilter) null);
        }
        if ((i & 4) != 0) {
            gatherPhoneLinks(arrayList, spannable, WECHAT_PHONE, new Pattern[]{NOT_PHONE}, new String[]{"tel:"}, sPhoneNumberMatchFilter, sPhoneNumberTransformFilter);
        }
        if ((i & 8) != 0) {
            gatherMapLinks(arrayList, spannable);
        }
        pruneOverlaps(arrayList);
        if (arrayList.size() == 0) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            LinkSpec linkSpec = (LinkSpec) it.next();
            applyLink(linkSpec.url, linkSpec.start, linkSpec.end, spannable, colorStateList, colorStateList2, qMUIOnSpanClickListener);
        }
        return true;
    }

    public static boolean addLinks(TextView textView, int i, ColorStateList colorStateList, ColorStateList colorStateList2, QMUIOnSpanClickListener qMUIOnSpanClickListener) {
        if (i == 0) {
            return false;
        }
        CharSequence text = textView.getText();
        if (!(text instanceof Spannable)) {
            SpannableString valueOf = SpannableString.valueOf(text);
            if (!addLinks((Spannable) valueOf, i, colorStateList, colorStateList2, qMUIOnSpanClickListener)) {
                return false;
            }
            addLinkMovementMethod(textView);
            textView.setText(valueOf);
            return true;
        } else if (!addLinks((Spannable) text, i, colorStateList, colorStateList2, qMUIOnSpanClickListener)) {
            return false;
        } else {
            addLinkMovementMethod(textView);
            return true;
        }
    }

    private static void addLinkMovementMethod(TextView textView) {
        MovementMethod movementMethod = textView.getMovementMethod();
        if ((movementMethod == null || !(movementMethod instanceof LinkMovementMethod)) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static void addLinks(TextView textView, Pattern pattern, String str) {
        addLinks(textView, pattern, str, (MatchFilter) null, (TransformFilter) null);
    }

    public static void addLinks(TextView textView, Pattern pattern, String str, MatchFilter matchFilter, TransformFilter transformFilter) {
        SpannableString valueOf = SpannableString.valueOf(textView.getText());
        if (addLinks((Spannable) valueOf, pattern, str, matchFilter, transformFilter)) {
            textView.setText(valueOf);
            addLinkMovementMethod(textView);
        }
    }

    public static boolean addLinks(Spannable spannable, Pattern pattern, String str) {
        return addLinks(spannable, pattern, str, (MatchFilter) null, (TransformFilter) null);
    }

    public static boolean addLinks(Spannable spannable, Pattern pattern, String str, MatchFilter matchFilter, TransformFilter transformFilter) {
        String lowerCase = str == null ? "" : str.toLowerCase(Locale.ROOT);
        Matcher matcher = pattern.matcher(spannable);
        boolean z = false;
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter != null ? matchFilter.acceptMatch(spannable, start, end) : true) {
                applyLink(makeUrl(matcher.group(0), new String[]{lowerCase}, matcher, transformFilter), start, end, spannable, (ColorStateList) null, (ColorStateList) null, (QMUIOnSpanClickListener) null);
                z = true;
            }
        }
        return z;
    }

    private static void applyLink(String str, int i, int i2, Spannable spannable, final ColorStateList colorStateList, final ColorStateList colorStateList2, QMUIOnSpanClickListener qMUIOnSpanClickListener) {
        spannable.setSpan(new StyleableURLSpan(str, qMUIOnSpanClickListener) {
            public void updateDrawState(TextPaint textPaint) {
                ColorStateList colorStateList = colorStateList;
                if (colorStateList != null) {
                    int colorForState = colorStateList.getColorForState(new int[]{16842910, -16842919}, 0);
                    int colorForState2 = colorStateList.getColorForState(new int[]{16842919}, colorForState);
                    if (this.mPressed) {
                        colorForState = colorForState2;
                    }
                    textPaint.linkColor = colorForState;
                }
                ColorStateList colorStateList2 = colorStateList2;
                if (colorStateList2 != null) {
                    int colorForState3 = colorStateList2.getColorForState(new int[]{16842910, -16842919}, 0);
                    int colorForState4 = colorStateList2.getColorForState(new int[]{16842919}, colorForState3);
                    if (this.mPressed) {
                        colorForState3 = colorForState4;
                    }
                    textPaint.bgColor = colorForState3;
                }
                super.updateDrawState(textPaint);
                textPaint.setUnderlineText(false);
            }
        }, i, i2, 33);
    }

    private static abstract class StyleableURLSpan extends URLSpan implements ITouchableSpan {
        protected QMUIOnSpanClickListener mOnSpanClickListener;
        protected boolean mPressed = false;
        protected String mUrl;

        public StyleableURLSpan(String str, QMUIOnSpanClickListener qMUIOnSpanClickListener) {
            super(str);
            this.mUrl = str;
            this.mOnSpanClickListener = qMUIOnSpanClickListener;
        }

        public void setPressed(boolean z) {
            this.mPressed = z;
        }

        public void onClick(View view) {
            if (!this.mOnSpanClickListener.onSpanClick(this.mUrl)) {
                super.onClick(view);
            }
        }
    }

    private static String makeUrl(String str, String[] strArr, Matcher matcher, TransformFilter transformFilter) {
        boolean z;
        if (transformFilter != null) {
            str = transformFilter.transformUrl(matcher, str);
        }
        int length = strArr.length;
        int i = 0;
        while (true) {
            z = true;
            if (i >= length) {
                z = false;
                break;
            }
            String str2 = strArr[i];
            if (str.regionMatches(true, 0, str2, 0, str2.length())) {
                if (!str.regionMatches(false, 0, str2, 0, str2.length())) {
                    str = str2 + str.substring(str2.length());
                }
            } else {
                i++;
            }
        }
        if (z) {
            return str;
        }
        return strArr[0] + str;
    }

    private static void gatherLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern pattern, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (matchFilter == null || matchFilter.acceptMatch(spannable, start, end)) {
                LinkSpec linkSpec = new LinkSpec();
                linkSpec.url = makeUrl(matcher.group(0), strArr, matcher, transformFilter);
                linkSpec.start = start;
                linkSpec.end = end;
                arrayList.add(linkSpec);
            }
        }
    }

    private static void gatherPhoneLinks(ArrayList<LinkSpec> arrayList, Spannable spannable, Pattern pattern, Pattern[] patternArr, String[] strArr, MatchFilter matchFilter, TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            if (!isInExcepts(matcher.group(), patternArr)) {
                int start = matcher.start();
                int end = matcher.end();
                if (matchFilter == null || matchFilter.acceptMatch(spannable, start, end)) {
                    LinkSpec linkSpec = new LinkSpec();
                    linkSpec.url = makeUrl(matcher.group(0), strArr, matcher, transformFilter);
                    linkSpec.start = start;
                    linkSpec.end = end;
                    arrayList.add(linkSpec);
                }
            }
        }
    }

    private static boolean isInExcepts(CharSequence charSequence, Pattern[] patternArr) {
        for (Pattern matcher : patternArr) {
            if (matcher.matcher(charSequence).find()) {
                return true;
            }
        }
        return isTooLarge(charSequence);
    }

    private static boolean isTooLarge(CharSequence charSequence) {
        if (charSequence.length() <= 21) {
            return false;
        }
        int length = charSequence.length();
        int i = 0;
        for (int i2 = 0; i2 < length; i2++) {
            if (Character.isDigit(charSequence.charAt(i2)) && (i = i + 1) > 21) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:1:0x0005 */
    /* JADX WARNING: Removed duplicated region for block: B:1:0x0005 A[LOOP:0: B:1:0x0005->B:14:0x0005, LOOP_START, PHI: r0 r6 
      PHI: (r0v1 int) = (r0v0 int), (r0v2 int) binds: [B:0:0x0000, B:14:0x0005] A[DONT_GENERATE, DONT_INLINE]
      PHI: (r6v2 java.lang.String) = (r6v1 java.lang.String), (r6v3 java.lang.String) binds: [B:0:0x0000, B:14:0x0005] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC, Splitter:B:1:0x0005] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void gatherMapLinks(java.util.ArrayList<com.qmuiteam.qmui.link.QMUILinkify.LinkSpec> r5, android.text.Spannable r6) {
        /*
            java.lang.String r6 = r6.toString()
            r0 = 0
        L_0x0005:
            java.lang.String r1 = android.webkit.WebView.findAddress(r6)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            if (r1 == 0) goto L_0x0044
            int r2 = r6.indexOf(r1)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            if (r2 >= 0) goto L_0x0012
            goto L_0x0044
        L_0x0012:
            com.qmuiteam.qmui.link.QMUILinkify$LinkSpec r3 = new com.qmuiteam.qmui.link.QMUILinkify$LinkSpec     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r4 = 0
            r3.<init>()     // Catch:{ UnsupportedOperationException -> 0x0044 }
            int r4 = r1.length()     // Catch:{ UnsupportedOperationException -> 0x0044 }
            int r4 = r4 + r2
            int r2 = r2 + r0
            r3.start = r2     // Catch:{ UnsupportedOperationException -> 0x0044 }
            int r0 = r0 + r4
            r3.end = r0     // Catch:{ UnsupportedOperationException -> 0x0044 }
            java.lang.String r6 = r6.substring(r4)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            java.lang.String r2 = "UTF-8"
            java.lang.String r1 = java.net.URLEncoder.encode(r1, r2)     // Catch:{ UnsupportedEncodingException -> 0x0005 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r2.<init>()     // Catch:{ UnsupportedOperationException -> 0x0044 }
            java.lang.String r4 = "geo:0,0?q="
            r2.append(r4)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r2.append(r1)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            java.lang.String r1 = r2.toString()     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r3.url = r1     // Catch:{ UnsupportedOperationException -> 0x0044 }
            r5.add(r3)     // Catch:{ UnsupportedOperationException -> 0x0044 }
            goto L_0x0005
        L_0x0044:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.link.QMUILinkify.gatherMapLinks(java.util.ArrayList, android.text.Spannable):void");
    }

    private static void pruneOverlaps(ArrayList<LinkSpec> arrayList) {
        int i;
        Collections.sort(arrayList, new Comparator<LinkSpec>() {
            public final int compare(LinkSpec linkSpec, LinkSpec linkSpec2) {
                if (linkSpec.start < linkSpec2.start) {
                    return -1;
                }
                if (linkSpec.start > linkSpec2.start || linkSpec.end < linkSpec2.end) {
                    return 1;
                }
                if (linkSpec.end > linkSpec2.end) {
                    return -1;
                }
                return 0;
            }
        });
        int size = arrayList.size();
        int i2 = 0;
        while (i2 < size - 1) {
            LinkSpec linkSpec = arrayList.get(i2);
            int i3 = i2 + 1;
            LinkSpec linkSpec2 = arrayList.get(i3);
            if (linkSpec.start <= linkSpec2.start && linkSpec.end > linkSpec2.start) {
                if (linkSpec2.end > linkSpec.end && linkSpec.end - linkSpec.start <= linkSpec2.end - linkSpec2.start) {
                    i = linkSpec.end - linkSpec.start < linkSpec2.end - linkSpec2.start ? i2 : -1;
                } else {
                    i = i3;
                }
                if (i != -1) {
                    arrayList.remove(i);
                    size--;
                }
            }
            i2 = i3;
        }
    }

    private static class LinkSpec {
        int end;
        int start;
        String url;

        private LinkSpec() {
        }
    }
}
