package com.szacs.ferroliconnect;

import android.app.Application;
import android.os.Bundle;
import android.support.p003v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0014J\u0006\u0010\u000f\u001a\u00020\fR\u001a\u0010\u0003\u001a\u00020\u0004X.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u0004¢\u0006\u0002\n\u0000¨\u0006\u0010"}, mo22149d2 = {"Lcom/szacs/ferroliconnect/MainActivity;", "Landroid/support/v7/app/AppCompatActivity;", "()V", "application", "Lcom/szacs/ferroliconnect/MainApplication;", "getApplication", "()Lcom/szacs/ferroliconnect/MainApplication;", "setApplication", "(Lcom/szacs/ferroliconnect/MainApplication;)V", "tvTest", "Landroid/widget/TextView;", "onCreate", "", "savedInstanceState", "Landroid/os/Bundle;", "toLogin", "app_ferroliRelease"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: MainActivity.kt */
public final class MainActivity extends AppCompatActivity {
    private HashMap _$_findViewCache;
    @NotNull
    public MainApplication application;
    private final TextView tvTest;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    public final void toLogin() {
    }

    @NotNull
    public final MainApplication getApplication() {
        MainApplication mainApplication = this.application;
        if (mainApplication == null) {
            Intrinsics.throwUninitializedPropertyAccessException("application");
        }
        return mainApplication;
    }

    public final void setApplication(@NotNull MainApplication mainApplication) {
        Intrinsics.checkParameterIsNotNull(mainApplication, "<set-?>");
        this.application = mainApplication;
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1683R.C1686layout.activity_base);
        Application application2 = getApplication();
        if (application2 != null) {
            this.application = (MainApplication) application2;
            toLogin();
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.szacs.ferroliconnect.MainApplication");
    }
}
