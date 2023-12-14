package p115uk.p116co.chrisjenx.calligraphy;

import android.os.Build;
import android.support.p003v7.widget.AppCompatAutoCompleteTextView;
import android.support.p003v7.widget.AppCompatButton;
import android.support.p003v7.widget.AppCompatCheckBox;
import android.support.p003v7.widget.AppCompatCheckedTextView;
import android.support.p003v7.widget.AppCompatEditText;
import android.support.p003v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.p003v7.widget.AppCompatRadioButton;
import android.support.p003v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* renamed from: uk.co.chrisjenx.calligraphy.CalligraphyConfig */
public class CalligraphyConfig {
    private static final Map<Class<? extends TextView>, Integer> DEFAULT_STYLES = new HashMap();
    private static CalligraphyConfig sInstance;
    private final Set<Class<?>> hasTypefaceViews;
    private final int mAttrId;
    private final Map<Class<? extends TextView>, Integer> mClassStyleAttributeMap;
    private final boolean mCustomViewCreation;
    private final boolean mCustomViewTypefaceSupport;
    private final String mFontPath;
    private final boolean mIsFontSet;
    private final boolean mReflection;

    static {
        DEFAULT_STYLES.put(TextView.class, 16842884);
        DEFAULT_STYLES.put(Button.class, 16842824);
        DEFAULT_STYLES.put(EditText.class, 16842862);
        DEFAULT_STYLES.put(AutoCompleteTextView.class, 16842859);
        DEFAULT_STYLES.put(MultiAutoCompleteTextView.class, 16842859);
        DEFAULT_STYLES.put(CheckBox.class, 16842860);
        DEFAULT_STYLES.put(RadioButton.class, 16842878);
        DEFAULT_STYLES.put(ToggleButton.class, 16842827);
        if (CalligraphyUtils.canAddV7AppCompatViews()) {
            addAppCompatViews();
        }
    }

    private static void addAppCompatViews() {
        DEFAULT_STYLES.put(AppCompatTextView.class, 16842884);
        DEFAULT_STYLES.put(AppCompatButton.class, 16842824);
        DEFAULT_STYLES.put(AppCompatEditText.class, 16842862);
        DEFAULT_STYLES.put(AppCompatAutoCompleteTextView.class, 16842859);
        DEFAULT_STYLES.put(AppCompatMultiAutoCompleteTextView.class, 16842859);
        DEFAULT_STYLES.put(AppCompatCheckBox.class, 16842860);
        DEFAULT_STYLES.put(AppCompatRadioButton.class, 16842878);
        DEFAULT_STYLES.put(AppCompatCheckedTextView.class, 16843720);
    }

    public static void initDefault(CalligraphyConfig calligraphyConfig) {
        sInstance = calligraphyConfig;
    }

    public static CalligraphyConfig get() {
        if (sInstance == null) {
            sInstance = new CalligraphyConfig(new Builder());
        }
        return sInstance;
    }

    protected CalligraphyConfig(Builder builder) {
        this.mIsFontSet = builder.isFontSet;
        this.mFontPath = builder.fontAssetPath;
        this.mAttrId = builder.attrId;
        this.mReflection = builder.reflection;
        this.mCustomViewCreation = builder.customViewCreation;
        this.mCustomViewTypefaceSupport = builder.customViewTypefaceSupport;
        HashMap hashMap = new HashMap(DEFAULT_STYLES);
        hashMap.putAll(builder.mStyleClassMap);
        this.mClassStyleAttributeMap = Collections.unmodifiableMap(hashMap);
        this.hasTypefaceViews = Collections.unmodifiableSet(builder.mHasTypefaceClasses);
    }

    public String getFontPath() {
        return this.mFontPath;
    }

    /* access modifiers changed from: package-private */
    public boolean isFontSet() {
        return this.mIsFontSet;
    }

    public boolean isReflection() {
        return this.mReflection;
    }

    public boolean isCustomViewCreation() {
        return this.mCustomViewCreation;
    }

    public boolean isCustomViewTypefaceSupport() {
        return this.mCustomViewTypefaceSupport;
    }

    public boolean isCustomViewHasTypeface(View view) {
        return this.hasTypefaceViews.contains(view.getClass());
    }

    /* access modifiers changed from: package-private */
    public Map<Class<? extends TextView>, Integer> getClassStyles() {
        return this.mClassStyleAttributeMap;
    }

    public int getAttrId() {
        return this.mAttrId;
    }

    /* renamed from: uk.co.chrisjenx.calligraphy.CalligraphyConfig$Builder */
    public static class Builder {
        public static final int INVALID_ATTR_ID = -1;
        /* access modifiers changed from: private */
        public int attrId;
        /* access modifiers changed from: private */
        public boolean customViewCreation;
        /* access modifiers changed from: private */
        public boolean customViewTypefaceSupport;
        /* access modifiers changed from: private */
        public String fontAssetPath;
        /* access modifiers changed from: private */
        public boolean isFontSet;
        /* access modifiers changed from: private */
        public Set<Class<?>> mHasTypefaceClasses;
        /* access modifiers changed from: private */
        public Map<Class<? extends TextView>, Integer> mStyleClassMap;
        /* access modifiers changed from: private */
        public boolean reflection;

        public Builder() {
            this.reflection = Build.VERSION.SDK_INT >= 11;
            this.customViewCreation = true;
            this.customViewTypefaceSupport = false;
            this.attrId = C2601R.attr.fontPath;
            this.isFontSet = false;
            this.fontAssetPath = null;
            this.mStyleClassMap = new HashMap();
            this.mHasTypefaceClasses = new HashSet();
        }

        public Builder setFontAttrId(int i) {
            if (i == -1) {
                i = -1;
            }
            this.attrId = i;
            return this;
        }

        public Builder setDefaultFontPath(String str) {
            this.isFontSet = !TextUtils.isEmpty(str);
            this.fontAssetPath = str;
            return this;
        }

        public Builder disablePrivateFactoryInjection() {
            this.reflection = false;
            return this;
        }

        public Builder disableCustomViewInflation() {
            this.customViewCreation = false;
            return this;
        }

        public Builder addCustomStyle(Class<? extends TextView> cls, int i) {
            if (!(cls == null || i == 0)) {
                this.mStyleClassMap.put(cls, Integer.valueOf(i));
            }
            return this;
        }

        public Builder addCustomViewWithSetTypeface(Class<?> cls) {
            this.customViewTypefaceSupport = true;
            this.mHasTypefaceClasses.add(cls);
            return this;
        }

        public CalligraphyConfig build() {
            this.isFontSet = !TextUtils.isEmpty(this.fontAssetPath);
            return new CalligraphyConfig(this);
        }
    }
}
