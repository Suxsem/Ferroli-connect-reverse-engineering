package com.szacs.ferroliconnect.activity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.p000v4.app.ActivityCompat;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.app.AppCompatActivity;
import android.support.p003v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.igexin.sdk.PushConsts;
import com.igexin.sdk.PushManager;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.broadcast.NetReceiver;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.SpUtil;
import com.szacs.ferroliconnect.util.StatusBarCompat;
import com.szacs.ferroliconnect.util.VersionChecker;
import com.topband.tsmart.TSmartPush;
import java.util.ArrayList;
import java.util.Locale;

public class MyAppCompatActivity extends AppCompatActivity {
    private static Handler mHandler = new Handler();
    protected String TAG;
    protected boolean allowRefreshUI;
    private long firstTime = 0;
    protected Context mContext;
    protected MainApplication mainApplication;
    protected Toolbar myToolbar;
    private NetReceiver netReceiver;
    protected KProgressHUD progress;

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        this.mContext = this;
        this.TAG = getClass().getSimpleName();
        this.mainApplication = MainApplication.getInstance();
        StatusBarCompat.compat(this, C1683R.color.ferroli_green);
        registerNetReceiver();
    }

    private void registerNetReceiver() {
        this.netReceiver = new NetReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PushConsts.ACTION_BROADCAST_NETWORK_CHANGE);
        registerReceiver(this.netReceiver, intentFilter);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        if (toString().substring(0, toString().indexOf("@")).equals(getSharedPreferences("activityManagement", 0).getString("bottomActivity", "Unknown"))) {
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        Process.killProcess(Process.myPid());
    }

    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    /* access modifiers changed from: protected */
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        if (intent.getBooleanExtra("logout", false)) {
            SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
            edit.putBoolean("keepSignedIn", false);
            edit.commit();
            Intent intent2 = new Intent();
            intent2.setClass(this, LoginActivity.class);
            startActivity(intent2);
            finish();
        }
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        this.myToolbar = (Toolbar) findViewById(C1683R.C1685id.my_toolbar);
        setSupportActionBar(this.myToolbar);
        this.myToolbar.setTitle((CharSequence) "");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        StatusBarCompat.compat(this, getResources().getColor(C1683R.color.ferroli_green));
    }

    /* access modifiers changed from: protected */
    public void setTitle(String str) {
        if (findViewById(C1683R.C1685id.title) != null) {
            ((TextView) findViewById(C1683R.C1685id.title)).setText(str);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != 16908332) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    /* access modifiers changed from: protected */
    public void logout() {
        QMUIDialog.MessageDialogBuilder messageDialogBuilder = new QMUIDialog.MessageDialogBuilder(this);
        messageDialogBuilder.setMessage((int) C1683R.string.dialog_logout_confirm);
        messageDialogBuilder.setTitle((int) C1683R.string.menu_logout);
        messageDialogBuilder.setCanceledOnTouchOutside(true);
        messageDialogBuilder.setCancelable(true);
        messageDialogBuilder.addAction((int) C1683R.string.public_cancel, (QMUIDialogAction.ActionListener) new QMUIDialogAction.ActionListener() {
            public void onClick(QMUIDialog qMUIDialog, int i) {
                qMUIDialog.dismiss();
            }
        });
        messageDialogBuilder.addAction((int) C1683R.string.public_confirm, (QMUIDialogAction.ActionListener) new QMUIDialogAction.ActionListener() {
            public void onClick(QMUIDialog qMUIDialog, int i) {
                MyAppCompatActivity.this.confirmToLogout();
                qMUIDialog.dismiss();
            }
        });
        messageDialogBuilder.create().show();
    }

    /* access modifiers changed from: protected */
    public void confirmToLogout() {
        SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
        edit.putBoolean("keepSignedIn", false);
        edit.commit();
        PushManager.getInstance().turnOffPush(getApplicationContext());
        String slug = UserCenter.getUserInfo().getSlug();
        TSmartPush.instance().setUserId("");
        TSmartPush.instance().setLoginStatus(false);
        boolean unBindAlias = PushManager.getInstance().unBindAlias(this, slug, false);
        String str = this.TAG;
        Log.d(str, " confirmToLogout unbindAlias result = " + unBindAlias + "UserSlug = " + slug);
        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(getPackageName());
        launchIntentForPackage.addFlags(67108864);
        launchIntentForPackage.addFlags(32768);
        startActivity(launchIntentForPackage);
    }

    /* access modifiers changed from: protected */
    public void onPause() {
        super.onPause();
        this.allowRefreshUI = false;
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        this.allowRefreshUI = true;
    }

    /* access modifiers changed from: protected */
    public void showLanguage(String str) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (str.equals(LanguageUtil.LOGOGRAM_CHINESE)) {
            configuration.locale = Locale.SIMPLIFIED_CHINESE;
        } else {
            configuration.locale = Locale.ENGLISH;
        }
        resources.updateConfiguration(configuration, displayMetrics);
        if (this instanceof GatewayListActivity) {
            MainApplication.setLanguage(str);
        }
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4) {
            if (toString().substring(0, toString().indexOf("@")).equals(getSharedPreferences("activityManagement", 0).getString("bottomActivity", "Unknown"))) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.firstTime > 2000) {
                    Snackbar.make((View) this.myToolbar, (CharSequence) getString(C1683R.string.room_click_again_to_quit), -1).show();
                    this.firstTime = currentTimeMillis;
                    return true;
                }
                System.exit(0);
            }
        }
        return super.onKeyUp(i, keyEvent);
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        SpUtil.getInstance(MainApplication.getInstance()).getString("language");
        super.attachBaseContext(LanguageUtil.setLocal(context));
    }

    public void permissionNotify(int i, String... strArr) {
        ArrayList arrayList = new ArrayList();
        for (String str : strArr) {
            if (ContextCompat.checkSelfPermission(this, str) != 0) {
                arrayList.add(str);
            }
        }
        String[] strArr2 = new String[arrayList.size()];
        arrayList.toArray(strArr2);
        ActivityCompat.requestPermissions(this, strArr2, i);
    }

    /* access modifiers changed from: protected */
    public void ShowProgressDialog(String str) {
        Log.e(this.TAG, "show progress dialog");
        KProgressHUD kProgressHUD = this.progress;
        if (kProgressHUD == null) {
            this.progress = KProgressHUD.create(this).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setLabel(str).setCancellable(true).setAnimationSpeed(1).setDimAmount(0.5f);
        } else {
            kProgressHUD.setLabel(str);
        }
        if (!this.progress.isShowing()) {
            this.progress.show();
            mHandler.postDelayed(new Runnable() {
                public void run() {
                    Log.i(MyAppCompatActivity.this.TAG, "hide100");
                    MyAppCompatActivity.this.HideProgressDialog();
                }
            }, 10000);
        }
    }

    /* access modifiers changed from: protected */
    public void HideProgressDialog() {
        Log.e(this.TAG, "hide progress dialog");
        KProgressHUD kProgressHUD = this.progress;
        if (kProgressHUD != null && kProgressHUD.isShowing()) {
            this.progress.dismiss();
        }
    }

    /* access modifiers changed from: protected */
    public void showUpdateDialog() {
        QMUIDialog.MessageDialogBuilder messageDialogBuilder = new QMUIDialog.MessageDialogBuilder(this);
        messageDialogBuilder.setTitle(getString(C1683R.string.update_download_newversion));
        messageDialogBuilder.setMessage((int) C1683R.string.app_info_want_to_update);
        messageDialogBuilder.setCanceledOnTouchOutside(false);
        messageDialogBuilder.setCancelable(false);
        messageDialogBuilder.addAction((int) C1683R.string.public_cancel, (QMUIDialogAction.ActionListener) new QMUIDialogAction.ActionListener() {
            public void onClick(QMUIDialog qMUIDialog, int i) {
                qMUIDialog.dismiss();
            }
        });
        messageDialogBuilder.addAction((int) C1683R.string.dialog_update_app_confirm, (QMUIDialogAction.ActionListener) new QMUIDialogAction.ActionListener() {
            public void onClick(QMUIDialog qMUIDialog, int i) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("market://details?id=" + MyAppCompatActivity.this.getPackageName()));
                if (intent.resolveActivity(MyAppCompatActivity.this.getPackageManager()) != null) {
                    MyAppCompatActivity.this.startActivity(intent);
                    return;
                }
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + MyAppCompatActivity.this.getPackageName()));
                MyAppCompatActivity.this.startActivity(intent);
            }
        });
        messageDialogBuilder.create().show();
    }

    /* access modifiers changed from: protected */
    public void checkNewVersion() {
        VersionChecker versionChecker = new VersionChecker();
        if (this instanceof VersionChecker.UpdateListener) {
            versionChecker.setListener((VersionChecker.UpdateListener) this);
        }
        versionChecker.execute(new String[0]);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        super.onDestroy();
        NetReceiver netReceiver2 = this.netReceiver;
        if (netReceiver2 != null) {
            unregisterReceiver(netReceiver2);
        }
        Handler handler = mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages((Object) null);
        }
    }
}
