package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.Group;
import android.support.constraint.Guideline;
import android.support.p000v4.app.ActivityCompat;
import android.support.p000v4.content.ContextCompat;
import android.support.p003v7.app.AppCompatActivity;
import android.support.p003v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.igexin.sdk.PushManager;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.bean.HttpTokenResponse;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.p107tb.appyunsdk.listener.IYunManagerLoginListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.HttpError;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.dialog.PrivacyContentDialog;
import com.szacs.ferroliconnect.dialog.PrivacyDialog;
import com.szacs.ferroliconnect.fragment.ChangeDomainNameDialog;
import com.szacs.ferroliconnect.gtpush.GtIntentService;
import com.szacs.ferroliconnect.gtpush.GtPushService;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.NavigationBarUtil;
import com.szacs.ferroliconnect.util.SpUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.szacs.ferroliconnect.util.VersionChecker;
import com.szacs.ferroliconnect.widget.MyProgressDialog;
import com.topband.tsmart.TSmartPush;

public class LoginActivity extends AppCompatActivity implements VersionChecker.UpdateListener, View.OnClickListener, PrivacyDialog.PrivacyDialogListener {
    private static final String GOOGLE_PLAY = "com.android.vending";
    private static final int REQUEST_PERMISSION = 0;
    private final int MY_PERMISSIONS_REQUEST_WRITE_EX_STORAGE = 1;
    protected String TAG;
    private CheckBox appCB;
    @BindView(2131296353)
    Button buttonLogin;
    @BindView(2131296355)
    Button buttonRegister;
    @BindView(2131296374)
    CheckBox checkBoxSignedIn;
    private int clickChangeDomainCount = 0;
    @BindView(2131296417)
    EditText editTextPassword;
    @BindView(2131296420)
    EditText editTextUsername;
    @BindView(2131296454)
    Guideline glOne;
    @BindView(2131296455)
    Guideline glThree;
    @BindView(2131296457)
    Guideline glTwo;
    @BindView(2131296459)
    Group gpWelcome;
    @BindView(2131296538)
    ImageView ivPassword;
    @BindView(2131296541)
    ImageView ivUserName;
    private Handler mUIHandler;
    @BindView(2131296620)
    Toolbar myToolbar;
    private SharedPreferences preferences;
    private PrivacyContentDialog privacyContentDialog;
    private PrivacyDialog privacyDialog;
    /* access modifiers changed from: private */
    public MyProgressDialog progressDialog;
    private CheckBox pushCB;
    @BindView(2131296769)
    TextView textViewForget;
    @BindView(2131296895)
    TextView tvVersionNumber;
    @BindView(2131296941)
    ImageView vBg;
    @BindView(2131296945)
    View view1;
    @BindView(2131296946)
    View view2;

    private void showPriDialog() {
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setTheme(C1683R.style.AppTheme);
        setContentView((int) C1683R.C1686layout.activity_login);
        if (NavigationBarUtil.hasNavigationBar(this)) {
            NavigationBarUtil.initActivity(findViewById(16908290));
        }
        ButterKnife.bind((Activity) this);
        getWindow().getDecorView().setSystemUiVisibility(12032);
        this.TAG = getClass().getSimpleName();
        this.mUIHandler = new Handler();
        Toolbar toolbar = (Toolbar) findViewById(C1683R.C1685id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(0);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION", "android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE"}, 1);
        }
        this.progressDialog = new MyProgressDialog(this);
        this.progressDialog.setMessage(getString(C1683R.string.login_loginning));
        this.preferences = getSharedPreferences("ferroli_user", 0);
        this.tvVersionNumber.setText(getVersion());
        if (!isFinishing()) {
            PushManager.getInstance().initialize(getApplicationContext(), GtPushService.class);
            if (!this.preferences.getBoolean("ferroli_show_privacy", false)) {
                showPrivacyDialog();
            } else {
                checkAutoLogin();
            }
            findViewById(C1683R.C1685id.buttonLogin).setOnClickListener(this);
            findViewById(C1683R.C1685id.buttonRegister).setOnClickListener(this);
            findViewById(C1683R.C1685id.textViewForget).setOnClickListener(this);
            this.ivUserName.setOnClickListener(this);
        }
    }

    private void checkAutoLogin() {
        String string = this.preferences.getString("account", "");
        String string2 = this.preferences.getString("password", "");
        boolean z = this.preferences.getBoolean("keepSignedIn", false);
        this.preferences.getString("token", "");
        if (z) {
            this.editTextUsername.setText(string);
            this.editTextPassword.setText(string2);
            this.checkBoxSignedIn.setChecked(true);
        }
        if (z) {
            doLoginByAccount(string, string2);
            return;
        }
        this.gpWelcome.setVisibility(8);
        checkNewVersion();
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C1683R.C1685id.buttonLogin) {
            doLoginByAccount(this.editTextUsername.getText().toString(), this.editTextPassword.getText().toString());
        } else if (id == C1683R.C1685id.buttonRegister) {
            Intent intent = new Intent();
            intent.setClass(this, RegisterActivity.class);
            startActivity(intent);
        } else if (id == C1683R.C1685id.textViewForget) {
            Intent intent2 = new Intent();
            intent2.setClass(this, ForgetPwdActivity.class);
            startActivity(intent2);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() != C1683R.C1685id.muConfigWiFi) {
            return super.onOptionsItemSelected(menuItem);
        }
        Intent intent = new Intent();
        intent.setClass(getApplicationContext(), ConfigWiFiActivity.class);
        intent.putExtra("login", false);
        startActivity(intent);
        return true;
    }

    public String getVersion() {
        try {
            String str = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            return "v " + str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void verifyToken(String str) {
        String str2 = this.TAG;
        LogUtil.m3318e(str2, "token: " + str);
        AppYunManager.getInstance().verifyHttpToken(str, new IAppYunResponseListener<HttpTokenResponse>() {
            public void onSuccess(HttpTokenResponse httpTokenResponse) {
                String str = LoginActivity.this.TAG;
                LogUtil.m3318e(str, "token: " + httpTokenResponse.getToken());
                LoginActivity.this.doLoginByToken(httpTokenResponse.getToken());
            }

            public void onFailure(int i, String str) {
                LoginActivity.this.gpWelcome.setVisibility(8);
                LoginActivity.this.checkNewVersion();
                LoginActivity.this.clearPushInfo();
            }
        });
    }

    /* access modifiers changed from: private */
    public void doLoginByToken(String str) {
        AppYunManager.getInstance().loginByToken(str, new IYunManagerLoginListener() {
            public void userLoginSuccess(String str, UserResponse userResponse) {
                LoginActivity.this.SaveLoginParams(str, userResponse);
                LoginActivity.this.setMqttClientId();
                AppYunManager.getInstance().initMqtt();
            }

            public void userLoginFailure(int i, String str) {
                String str2 = LoginActivity.this.TAG;
                LogUtil.m3315d(str2, "code: " + i + "   msg: " + str);
                LoginActivity.this.clearPushInfo();
                LoginActivity.this.checkNewVersion();
            }
        });
    }

    private void doLoginByAccount(String str, String str2) {
        this.progressDialog.show();
        AppYunManager.getInstance().login(str, str2, new IYunManagerLoginListener() {
            public void userLoginSuccess(String str, UserResponse userResponse) {
                LoginActivity.this.progressDialog.hide();
                LoginActivity.this.SaveLoginParams(str, userResponse);
                LoginActivity.this.setMqttClientId();
                AppYunManager.getInstance().initMqtt();
            }

            public void userLoginFailure(int i, String str) {
                LoginActivity.this.progressDialog.hide();
                String str2 = LoginActivity.this.TAG;
                LogUtil.m3315d(str2, "code: " + i + "   msg: " + str);
                LoginActivity.this.clearPushInfo();
                LoginActivity loginActivity = LoginActivity.this;
                ToastUtil.showShortToast(loginActivity, HttpError.getMessage(loginActivity, i));
            }
        });
    }

    /* access modifiers changed from: private */
    public void clearPushInfo() {
        TSmartPush.instance().setUserId("");
        TSmartPush.instance().setLoginStatus(false);
    }

    /* access modifiers changed from: private */
    public void SaveLoginParams(String str, UserResponse userResponse) {
        Constant.isLogin = true;
        TSmartPush.instance().setUserId(userResponse.getSlug());
        TSmartPush.instance().setLoginStatus(true);
        UserCenter.setUserInfo(userResponse);
        AppYunManager.getInstance().subscribeMsgFromServerNotify((IAppYunManagerActionListener) null);
        AppYunManager.getInstance().subscribeMsgFromServer((IAppYunManagerActionListener) null);
        AppYunManager.getInstance().subscribeMsgFromServerNotifyAll((IAppYunManagerActionListener) null);
        SharedPreferences.Editor edit = getSharedPreferences("ferroli_user", 0).edit();
        edit.putString("account", this.editTextUsername.getText().toString());
        edit.putString(com.p107tb.appyunsdk.Constant.APP_USERNAME, userResponse.getName());
        edit.putString("email", userResponse.getEmail());
        edit.putBoolean("keepSignedIn", this.checkBoxSignedIn.isChecked());
        edit.putString("password", this.editTextPassword.getText().toString());
        edit.putString("create_date", userResponse.getCreate_date());
        edit.putString("update_date", userResponse.getUpdate_date());
        edit.putString("token", str);
        edit.putString("authorization", AppYunManager.getInstance().getAuthorization());
        edit.putString("user_slug", userResponse.getSlug());
        edit.apply();
        PushManager.getInstance().registerPushIntentService(getApplicationContext(), GtIntentService.class);
        if (!PushManager.getInstance().isPushTurnedOn(this)) {
            Log.d(this.TAG, " gtPush is turn off, turn on gtPush");
            PushManager.getInstance().turnOnPush(this);
        }
        startActivity(new Intent(this, GatewayListActivity.class));
        finish();
    }

    /* access modifiers changed from: protected */
    public void checkNewVersion() {
        VersionChecker versionChecker = new VersionChecker();
        versionChecker.setListener(this);
        versionChecker.execute(new String[0]);
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
                intent.setData(Uri.parse("market://details?id=" + LoginActivity.this.getPackageName()));
                intent.setPackage("com.android.vending");
                if (intent.resolveActivity(LoginActivity.this.getPackageManager()) != null) {
                    LoginActivity.this.startActivity(intent);
                    return;
                }
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + LoginActivity.this.getPackageName()));
                LoginActivity.this.startActivity(intent);
            }
        });
        messageDialogBuilder.create().show();
    }

    public void OnFindViewVersion(boolean z) {
        if (!isFinishing() && z) {
            showUpdateDialog();
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        SpUtil.getInstance(MainApplication.getInstance()).getString("language");
        super.attachBaseContext(LanguageUtil.setLocal(context));
    }

    private void showChangeDoMainName() {
        new ChangeDomainNameDialog().show(getFragmentManager(), "changeDomainNameDialog");
    }

    /* access modifiers changed from: private */
    public void setMqttClientId() {
        String string = getSharedPreferences("ferroli_user", 0).getString(UserCenter.getUserInfo().getSlug(), "");
        String str = this.TAG;
        Log.d(str, " doLoginByToken mqttClientId = " + string);
        AppYunManager.getInstance().setMqttClientId(string);
    }

    private void showPrivacyDialog() {
        if (this.privacyDialog == null) {
            this.privacyDialog = new PrivacyDialog(this);
            this.privacyDialog.setPrivacyDialogListener(this);
        }
        this.privacyDialog.show();
    }

    public void onPrivacyContentClick() {
        if (this.privacyContentDialog == null) {
            this.privacyContentDialog = new PrivacyContentDialog();
        }
        this.privacyContentDialog.show(getFragmentManager(), "PrivacyContentDialog");
    }

    public void onAgreePrivacyClick() {
        SharedPreferences.Editor edit = this.preferences.edit();
        edit.putBoolean("ferroli_show_privacy", true);
        edit.apply();
        checkAutoLogin();
    }

    private void showPermission() {
        PackageManager packageManager = getPackageManager();
        boolean z = packageManager.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", getPackageName()) == 0;
        boolean z2 = packageManager.checkPermission("android.permission.READ_PHONE_STATE", getPackageName()) == 0;
        if (Build.VERSION.SDK_INT < 23 || (z && z2)) {
            PushManager.getInstance().setPrivacyPolicyStrategy(this, true);
        } else {
            requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_PHONE_STATE"}, 0);
        }
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 0) {
            PushManager.getInstance().setPrivacyPolicyStrategy(this, true);
        }
    }
}
