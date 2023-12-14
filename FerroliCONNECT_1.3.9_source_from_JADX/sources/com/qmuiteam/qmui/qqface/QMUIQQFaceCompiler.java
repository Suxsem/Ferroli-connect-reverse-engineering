package com.qmuiteam.qmui.qqface;

import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.util.LruCache;
import com.qmuiteam.qmui.span.QMUITouchableSpan;
import com.qmuiteam.qmui.util.QMUILangHelper;
import java.util.ArrayList;
import java.util.List;

public class QMUIQQFaceCompiler {
    private static final int SPAN_COLUMN = 2;
    private static volatile QMUIQQFaceCompiler sInstance;
    private LruCache<CharSequence, ElementList> mCache = new LruCache<>(30);
    private IQMUIQQFaceManager mQQFaceManager;

    public enum ElementType {
        TEXT,
        DRAWABLE,
        SPECIAL_BOUNDS_DRAWABLE,
        SPAN,
        NEXTLINE
    }

    public static QMUIQQFaceCompiler getInstance(IQMUIQQFaceManager iQMUIQQFaceManager) {
        if (sInstance == null) {
            synchronized (QMUIQQFaceCompiler.class) {
                if (sInstance == null) {
                    sInstance = new QMUIQQFaceCompiler(iQMUIQQFaceManager);
                }
            }
        }
        return sInstance;
    }

    private QMUIQQFaceCompiler(IQMUIQQFaceManager iQMUIQQFaceManager) {
        this.mQQFaceManager = iQMUIQQFaceManager;
    }

    public int getSpecialBoundsMaxHeight() {
        return this.mQQFaceManager.getSpecialDrawableMaxHeight();
    }

    public ElementList compile(CharSequence charSequence) {
        if (QMUILangHelper.isNullOrEmpty(charSequence)) {
            return null;
        }
        return compile(charSequence, 0, charSequence.length());
    }

    public ElementList compile(CharSequence charSequence, int i, int i2) {
        return compile(charSequence, i, i2, false);
    }

    /* access modifiers changed from: private */
    public ElementList compile(CharSequence charSequence, int i, int i2, boolean z) {
        int[] iArr;
        QMUITouchableSpan[] qMUITouchableSpanArr;
        boolean z2;
        int[] iArr2 = null;
        if (QMUILangHelper.isNullOrEmpty(charSequence)) {
            return null;
        }
        if (i < 0 || i >= charSequence.length()) {
            throw new IllegalArgumentException("start must >= 0 and < text.length");
        } else if (i2 > i) {
            int length = charSequence.length();
            int i3 = i2 > length ? length : i2;
            if (z || !(charSequence instanceof Spannable)) {
                qMUITouchableSpanArr = null;
                iArr = null;
                z2 = false;
            } else {
                Spannable spannable = (Spannable) charSequence;
                QMUITouchableSpan[] qMUITouchableSpanArr2 = (QMUITouchableSpan[]) spannable.getSpans(0, charSequence.length() - 1, QMUITouchableSpan.class);
                z2 = qMUITouchableSpanArr2.length > 0;
                if (z2) {
                    iArr2 = new int[(qMUITouchableSpanArr2.length * 2)];
                    for (int i4 = 0; i4 < qMUITouchableSpanArr2.length; i4++) {
                        int i5 = i4 * 2;
                        iArr2[i5] = spannable.getSpanStart(qMUITouchableSpanArr2[i4]);
                        iArr2[i5 + 1] = spannable.getSpanEnd(qMUITouchableSpanArr2[i4]);
                    }
                }
                qMUITouchableSpanArr = qMUITouchableSpanArr2;
                iArr = iArr2;
            }
            ElementList elementList = this.mCache.get(charSequence);
            if (!z2 && elementList != null && i == elementList.getStart() && i3 == elementList.getEnd()) {
                return elementList;
            }
            ElementList realCompile = realCompile(charSequence, i, i3, qMUITouchableSpanArr, iArr);
            this.mCache.put(charSequence, realCompile);
            return realCompile;
        } else {
            throw new IllegalArgumentException("end must > start");
        }
    }

    public void setCache(LruCache<CharSequence, ElementList> lruCache) {
        this.mCache = lruCache;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:68:0x0137, code lost:
        r4 = java.lang.Character.codePointAt(r1, r4);
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.ElementList realCompile(java.lang.CharSequence r18, int r19, int r20, com.qmuiteam.qmui.span.QMUITouchableSpan[] r21, int[] r22) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            r2 = r19
            r3 = r20
            r4 = r21
            int r5 = r18.length()
            r7 = 1
            r8 = 0
            if (r4 == 0) goto L_0x001d
            int r9 = r4.length
            if (r9 <= 0) goto L_0x001d
            r9 = r22[r8]
            r10 = r22[r7]
            r11 = r10
            r10 = r9
            r9 = 0
            goto L_0x0024
        L_0x001d:
            r9 = -1
            r10 = 2147483647(0x7fffffff, float:NaN)
            r11 = 2147483647(0x7fffffff, float:NaN)
        L_0x0024:
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$ElementList r12 = new com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$ElementList
            r12.<init>(r2, r3)
            if (r2 <= 0) goto L_0x0036
            java.lang.CharSequence r13 = r1.subSequence(r8, r2)
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r13 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createTextElement(r13)
            r12.add(r13)
        L_0x0036:
            r14 = r9
            r15 = r11
            r13 = 0
            r9 = r2
            r11 = r10
            r10 = r9
        L_0x003c:
            if (r9 >= r3) goto L_0x0169
            if (r9 != r11) goto L_0x007a
            int r16 = r9 - r10
            if (r16 <= 0) goto L_0x0054
            if (r13 == 0) goto L_0x0049
            int r10 = r10 + -1
            r13 = 0
        L_0x0049:
            java.lang.CharSequence r9 = r1.subSequence(r10, r9)
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r9 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createTextElement(r9)
            r12.add(r9)
        L_0x0054:
            java.lang.CharSequence r9 = r1.subSequence(r11, r15)
            r10 = r4[r14]
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r9 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createTouchSpanElement(r9, r10, r0)
            r12.add(r9)
            int r14 = r14 + 1
            int r9 = r4.length
            if (r14 < r9) goto L_0x006f
            r9 = r15
            r10 = r9
            r11 = 2147483647(0x7fffffff, float:NaN)
            r15 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x003c
        L_0x006f:
            int r9 = r14 * 2
            r11 = r22[r9]
            int r9 = r9 + r7
            r9 = r22[r9]
            r10 = r15
            r15 = r9
            r9 = r10
            goto L_0x003c
        L_0x007a:
            char r6 = r1.charAt(r9)
            r7 = 91
            if (r6 != r7) goto L_0x0098
            int r6 = r9 - r10
            if (r6 <= 0) goto L_0x0091
            java.lang.CharSequence r6 = r1.subSequence(r10, r9)
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r6 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createTextElement(r6)
            r12.add(r6)
        L_0x0091:
            int r6 = r9 + 1
            r10 = r9
            r7 = 1
            r13 = 1
            r9 = r6
            goto L_0x003c
        L_0x0098:
            r7 = 93
            if (r6 != r7) goto L_0x00d0
            if (r13 == 0) goto L_0x00d0
            int r9 = r9 + 1
            int r6 = r9 - r10
            if (r6 <= 0) goto L_0x00cc
            java.lang.CharSequence r6 = r1.subSequence(r10, r9)
            java.lang.String r6 = r6.toString()
            com.qmuiteam.qmui.qqface.IQMUIQQFaceManager r7 = r0.mQQFaceManager
            android.graphics.drawable.Drawable r7 = r7.getSpecialBoundsDrawable(r6)
            if (r7 == 0) goto L_0x00bc
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r6 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createSpeaicalBoundsDrawableElement(r7)
            r12.add(r6)
            goto L_0x00cb
        L_0x00bc:
            com.qmuiteam.qmui.qqface.IQMUIQQFaceManager r7 = r0.mQQFaceManager
            int r6 = r7.getQQfaceResource(r6)
            if (r6 == 0) goto L_0x00cc
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r6 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createDrawableElement(r6)
            r12.add(r6)
        L_0x00cb:
            r10 = r9
        L_0x00cc:
            r7 = 1
            r13 = 0
            goto L_0x003c
        L_0x00d0:
            r7 = 10
            if (r6 != r7) goto L_0x00f3
            if (r13 == 0) goto L_0x00d7
            r13 = 0
        L_0x00d7:
            int r6 = r9 - r10
            if (r6 <= 0) goto L_0x00e6
            java.lang.CharSequence r6 = r1.subSequence(r10, r9)
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r6 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createTextElement(r6)
            r12.add(r6)
        L_0x00e6:
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r6 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createNextLineElement()
            r12.add(r6)
            int r10 = r9 + 1
            r9 = r10
            r7 = 1
            goto L_0x003c
        L_0x00f3:
            if (r13 == 0) goto L_0x0103
            int r7 = r9 - r10
            r8 = 8
            if (r7 <= r8) goto L_0x00fd
            r13 = 0
            goto L_0x0103
        L_0x00fd:
            int r9 = r9 + 1
        L_0x00ff:
            r7 = 1
            r8 = 0
            goto L_0x003c
        L_0x0103:
            com.qmuiteam.qmui.qqface.IQMUIQQFaceManager r7 = r0.mQQFaceManager
            boolean r7 = r7.maybeSoftBankEmoji(r6)
            if (r7 == 0) goto L_0x0117
            com.qmuiteam.qmui.qqface.IQMUIQQFaceManager r7 = r0.mQQFaceManager
            int r8 = r7.getSoftbankEmojiResource(r6)
            if (r8 != 0) goto L_0x0115
            r6 = 0
            goto L_0x0119
        L_0x0115:
            r6 = 1
            goto L_0x0119
        L_0x0117:
            r6 = 0
            r8 = 0
        L_0x0119:
            if (r8 != 0) goto L_0x014b
            int r6 = java.lang.Character.codePointAt(r1, r9)
            int r7 = java.lang.Character.charCount(r6)
            com.qmuiteam.qmui.qqface.IQMUIQQFaceManager r4 = r0.mQQFaceManager
            boolean r4 = r4.maybeEmoji(r6)
            if (r4 == 0) goto L_0x0131
            com.qmuiteam.qmui.qqface.IQMUIQQFaceManager r4 = r0.mQQFaceManager
            int r8 = r4.getEmojiResource(r6)
        L_0x0131:
            if (r8 != 0) goto L_0x014a
            int r4 = r2 + r7
            if (r4 >= r3) goto L_0x014a
            int r4 = java.lang.Character.codePointAt(r1, r4)
            com.qmuiteam.qmui.qqface.IQMUIQQFaceManager r8 = r0.mQQFaceManager
            int r8 = r8.getDoubleUnicodeEmoji(r6, r4)
            if (r8 == 0) goto L_0x014a
            int r4 = java.lang.Character.charCount(r4)
            int r6 = r7 + r4
            goto L_0x014b
        L_0x014a:
            r6 = r7
        L_0x014b:
            if (r8 == 0) goto L_0x0164
            if (r10 == r9) goto L_0x015a
            java.lang.CharSequence r4 = r1.subSequence(r10, r9)
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r4 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createTextElement(r4)
            r12.add(r4)
        L_0x015a:
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r4 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createDrawableElement(r8)
            r12.add(r4)
            int r9 = r9 + r6
            r10 = r9
            goto L_0x0166
        L_0x0164:
            int r9 = r9 + 1
        L_0x0166:
            r4 = r21
            goto L_0x00ff
        L_0x0169:
            if (r10 >= r3) goto L_0x0176
            java.lang.CharSequence r1 = r1.subSequence(r10, r5)
            com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$Element r1 = com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.Element.createTextElement(r1)
            r12.add(r1)
        L_0x0176:
            return r12
        */
        throw new UnsupportedOperationException("Method not decompiled: com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler.realCompile(java.lang.CharSequence, int, int, com.qmuiteam.qmui.span.QMUITouchableSpan[], int[]):com.qmuiteam.qmui.qqface.QMUIQQFaceCompiler$ElementList");
    }

    public static class Element {
        private ElementList mChildList;
        private int mDrawableRes;
        private Drawable mSpecialBoundsDrawable;
        private CharSequence mText;
        private QMUITouchableSpan mTouchableSpan;
        private ElementType mType;

        public ElementType getType() {
            return this.mType;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public int getDrawableRes() {
            return this.mDrawableRes;
        }

        public ElementList getChildList() {
            return this.mChildList;
        }

        public QMUITouchableSpan getTouchableSpan() {
            return this.mTouchableSpan;
        }

        public Drawable getSpecialBoundsDrawable() {
            return this.mSpecialBoundsDrawable;
        }

        public static Element createTextElement(CharSequence charSequence) {
            Element element = new Element();
            element.mType = ElementType.TEXT;
            element.mText = charSequence;
            return element;
        }

        public static Element createDrawableElement(int i) {
            Element element = new Element();
            element.mType = ElementType.DRAWABLE;
            element.mDrawableRes = i;
            return element;
        }

        public static Element createSpeaicalBoundsDrawableElement(Drawable drawable) {
            Element element = new Element();
            element.mType = ElementType.SPECIAL_BOUNDS_DRAWABLE;
            element.mSpecialBoundsDrawable = drawable;
            return element;
        }

        public static Element createTouchSpanElement(CharSequence charSequence, QMUITouchableSpan qMUITouchableSpan, QMUIQQFaceCompiler qMUIQQFaceCompiler) {
            Element element = new Element();
            element.mType = ElementType.SPAN;
            element.mChildList = qMUIQQFaceCompiler.compile(charSequence, 0, charSequence.length(), true);
            element.mTouchableSpan = qMUITouchableSpan;
            return element;
        }

        public static Element createNextLineElement() {
            Element element = new Element();
            element.mType = ElementType.NEXTLINE;
            return element;
        }
    }

    public static class ElementList {
        private List<Element> mElements;
        private int mEnd;
        private int mNewLineCount = 0;
        private int mQQFaceCount = 0;
        private int mStart;

        public ElementList(int i, int i2) {
            this.mStart = i;
            this.mEnd = i2;
            this.mElements = new ArrayList();
        }

        public int getStart() {
            return this.mStart;
        }

        public int getEnd() {
            return this.mEnd;
        }

        public int getNewLineCount() {
            return this.mNewLineCount;
        }

        public int getQQFaceCount() {
            return this.mQQFaceCount;
        }

        public void add(Element element) {
            if (element.getType() == ElementType.DRAWABLE) {
                this.mQQFaceCount++;
            } else if (element.getType() == ElementType.NEXTLINE) {
                this.mNewLineCount++;
            } else if (element.getType() == ElementType.SPAN) {
                this.mQQFaceCount += element.getChildList().getQQFaceCount();
                this.mNewLineCount += element.getChildList().getNewLineCount();
            }
            this.mElements.add(element);
        }

        public List<Element> getElements() {
            return this.mElements;
        }
    }
}
