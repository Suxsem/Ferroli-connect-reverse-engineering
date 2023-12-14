package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p000v4.p002os.ConfigurationCompat;
import android.view.View;
import butterknife.ButterKnife;
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.util.LanguageType;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.SpUtil;
import java.util.Locale;

public class SelectLanguageActivity extends MyAppCompatActivity implements View.OnClickListener {
    private QMUIRoundButton zhBtn;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1683R.C1686layout.select_language);
        ButterKnife.bind((Activity) this);
        initToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SelectLanguageActivity.this.finish();
            }
        });
        this.zhBtn = (QMUIRoundButton) findViewById(C1683R.C1685id.btn_language_zh);
        if (LanguageUtil.getSetLanguageLocale(this).getLanguage().equals(LanguageUtil.LOGOGRAM_CHINESE)) {
            this.zhBtn.setVisibility(0);
        } else {
            this.zhBtn.setVisibility(8);
        }
        findViewById(C1683R.C1685id.btn_system_language).setOnClickListener(this);
        this.zhBtn.setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_en).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_italia).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_french).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_romanian).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_russian).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_spanish).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_turkish).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_ukrainian).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_nederlands).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_polski).setOnClickListener(this);
        findViewById(C1683R.C1685id.btn_language_portuguese).setOnClickListener(this);
    }

    public void onClick(View view) {
        String str;
        int id = view.getId();
        if (id != C1683R.C1685id.btn_system_language) {
            switch (id) {
                case C1683R.C1685id.btn_language_en:
                    str = LanguageType.ENGLISH.getLanguage();
                    break;
                case C1683R.C1685id.btn_language_french:
                    str = LanguageUtil.LOGOGRAM_FR;
                    break;
                case C1683R.C1685id.btn_language_italia:
                    str = LanguageType.ITALY.getLanguage();
                    break;
                case C1683R.C1685id.btn_language_nederlands:
                    str = LanguageUtil.LOGOGRAM_NL;
                    break;
                case C1683R.C1685id.btn_language_polski:
                    str = LanguageUtil.LOGOGRAM_PL;
                    break;
                case C1683R.C1685id.btn_language_portuguese:
                    str = LanguageUtil.LOGOGRAM_PT;
                    break;
                case C1683R.C1685id.btn_language_romanian:
                    str = LanguageUtil.LOGOGRAM_RO;
                    break;
                case C1683R.C1685id.btn_language_russian:
                    str = LanguageUtil.LOGOGRAM_RU;
                    break;
                case C1683R.C1685id.btn_language_spanish:
                    str = LanguageUtil.LOGOGRAM_ES;
                    break;
                case C1683R.C1685id.btn_language_turkish:
                    str = LanguageUtil.LOGOGRAM_TR;
                    break;
                case C1683R.C1685id.btn_language_ukrainian:
                    str = LanguageUtil.LOGOGRAM_UK;
                    break;
                case C1683R.C1685id.btn_language_zh:
                    str = LanguageType.CHINESE.getLanguage();
                    break;
                default:
                    str = null;
                    break;
            }
        } else {
            str = Build.VERSION.SDK_INT >= 24 ? ConfigurationCompat.getLocales(Resources.getSystem().getConfiguration()).get(0).getLanguage() : Locale.getDefault().getLanguage();
        }
        changeLanguage(str);
    }

    private void changeLanguage(String str) {
        LanguageUtil.changeAppLanguage(this, str);
        SpUtil.getInstance(this).putString("language", str);
        Intent intent = new Intent(this, GatewayListActivity.class);
        intent.setFlags(268468224);
        startActivity(intent);
        finish();
    }
}
