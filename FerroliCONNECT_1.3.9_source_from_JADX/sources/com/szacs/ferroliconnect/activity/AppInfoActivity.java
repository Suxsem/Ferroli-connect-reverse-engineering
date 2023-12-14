package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.p003v7.app.ActionBar;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.util.ButtonUtils;
import com.szacs.ferroliconnect.util.NavigationBarUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.szacs.ferroliconnect.util.VersionChecker;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005¢\u0006\u0002\u0010\u0004J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\nH\u0014J\b\u0010\u000e\u001a\u00020\nH\u0002J\u0006\u0010\u000f\u001a\u00020\nJ\u0010\u0010\u0010\u001a\u00020\n2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0012\u0010\u0013\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006XD¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0016"}, mo22149d2 = {"Lcom/szacs/ferroliconnect/activity/AppInfoActivity;", "Lcom/szacs/ferroliconnect/activity/MyAppCompatActivity;", "Landroid/view/View$OnClickListener;", "Lcom/szacs/ferroliconnect/util/VersionChecker$UpdateListener;", "()V", "a", "", "getA", "()Ljava/lang/String;", "OnFindViewVersion", "", "hasNewVersion", "", "initToolbar", "initWidget", "method", "onClick", "v", "Landroid/view/View;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "app_ferroliRelease"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: AppInfoActivity.kt */
public final class AppInfoActivity extends MyAppCompatActivity implements View.OnClickListener, VersionChecker.UpdateListener {
    private HashMap _$_findViewCache;
    @NotNull

    /* renamed from: a */
    private final String f3124a = "aaa";

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

    public final void method() {
    }

    @NotNull
    public final String getA() {
        return this.f3124a;
    }

    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        setContentView((int) C1683R.C1686layout.activity_app_info);
        initToolbar();
        initWidget();
        TextView textView = (TextView) _$_findCachedViewById(C1683R.C1685id.tv_cur_version);
        Intrinsics.checkExpressionValueIsNotNull(textView, "tv_cur_version");
        textView.setText("v1.3.9");
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null) {
            Intrinsics.throwNpe();
        }
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.myToolbar.setNavigationOnClickListener(new AppInfoActivity$initToolbar$1(this));
    }

    private final void initWidget() {
        QMUIRoundButton qMUIRoundButton = (QMUIRoundButton) _$_findCachedViewById(C1683R.C1685id.btCheckUpdate);
        if (qMUIRoundButton == null) {
            Intrinsics.throwNpe();
        }
        View.OnClickListener onClickListener = this;
        qMUIRoundButton.setOnClickListener(onClickListener);
        QMUIRoundButton qMUIRoundButton2 = (QMUIRoundButton) _$_findCachedViewById(C1683R.C1685id.tvPrivacyPolicy);
        if (qMUIRoundButton2 == null) {
            Intrinsics.throwNpe();
        }
        qMUIRoundButton2.setOnClickListener(onClickListener);
        setTitle(getResources().getString(C1683R.string.menu_about));
    }

    public void onClick(@NotNull View view) {
        Intrinsics.checkParameterIsNotNull(view, "v");
        if (!ButtonUtils.isFastDoubleClick(view.getId())) {
            view.startAnimation(AnimationUtils.loadAnimation(this.mContext, C1683R.anim.f3107an));
            int id = view.getId();
            if (id == C1683R.C1685id.btCheckUpdate) {
                checkNewVersion();
            } else if (id == C1683R.C1685id.tvPrivacyPolicy) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), HelpActivity.class);
                intent.putExtra("PDF_TYPE", 1);
                startActivity(intent);
            }
        }
    }

    public void OnFindViewVersion(boolean z) {
        if (!isFinishing()) {
            if (z) {
                showUpdateDialog();
            } else if (!z) {
                ToastUtil.showShortToast(this.mContext, getString(C1683R.string.app_update_new));
            }
        }
    }
}
