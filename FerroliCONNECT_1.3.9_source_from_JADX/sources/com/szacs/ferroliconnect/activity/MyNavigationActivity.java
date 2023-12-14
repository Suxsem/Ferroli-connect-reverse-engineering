package com.szacs.ferroliconnect.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.p000v4.view.GravityCompat;
import android.support.p000v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.Constant;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.p107tb.appyunsdk.listener.IAppYunResponseListener;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.fragment.AlarmNotificationFragment;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.FileUtil;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.viewInterface.MyNavigationView;
import java.io.File;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class MyNavigationActivity extends MyAppCompatActivity implements MyNavigationView, NavigationView.OnNavigationItemSelectedListener {
    protected AlarmNotificationFragment alarmNotificationFragment;
    protected String authorzation;
    protected String city;
    protected String cityID;
    protected ImageView civNavPortrait;
    @BindView(2131296413)
    DrawerLayout drawer;
    protected String email;
    protected View headerView;
    protected String imagePath;
    protected LinearLayout llMain;
    @BindView(2131296626)
    NavigationView navigationView;
    protected TextView tvNavEmail;
    protected TextView tvNavUsername;
    protected String username;

    private void initPortrait() {
    }

    /* access modifiers changed from: protected */
    public abstract int mainLayoutId();

    public void onGetPortraitFailed(int i, boolean z) {
    }

    /* access modifiers changed from: protected */
    public void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) C1683R.C1686layout.activity_base_new);
        LayoutInflater layoutInflater = getLayoutInflater();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        this.llMain = (LinearLayout) findViewById(C1683R.C1685id.llMain);
        this.llMain.removeAllViews();
        this.llMain.addView(layoutInflater.inflate(mainLayoutId(), (ViewGroup) null), layoutParams);
        ButterKnife.bind((Activity) this);
        this.headerView = this.navigationView.getHeaderView(0);
        this.civNavPortrait = (ImageView) this.headerView.findViewById(C1683R.C1685id.civNavPortrait);
        this.tvNavUsername = (TextView) this.headerView.findViewById(C1683R.C1685id.tvNavUsername);
        this.tvNavEmail = (TextView) this.headerView.findViewById(C1683R.C1685id.tvNavEmail);
        SharedPreferences sharedPreferences = getSharedPreferences("ferroli_user", 0);
        this.username = sharedPreferences.getString(Constant.APP_USERNAME, "default");
        this.email = sharedPreferences.getString("email", "");
        this.city = sharedPreferences.getString("city", "ShenZhen");
        this.cityID = sharedPreferences.getString("cityid", "");
        this.authorzation = getSharedPreferences("ferroli_user", 0).getString("authorization", "");
        this.imagePath = FileUtil.CLOUDWARM_ROOT_PATH + this.username;
        this.alarmNotificationFragment = new AlarmNotificationFragment();
        this.tvNavUsername.setText(this.username);
        this.tvNavEmail.setText(this.email);
        this.navigationView.setNavigationItemSelectedListener(this);
        initToolbar();
        this.drawer.setDrawerLockMode(1);
        getUserInformation();
    }

    public boolean onNavigationItemSelected(MenuItem menuItem) {
        int itemId = menuItem.getItemId();
        Intent intent = new Intent();
        switch (itemId) {
            case C1683R.C1685id.nav_account:
                if (!(this instanceof UserInfoActivity)) {
                    intent.setClass(this, UserInfoActivity.class);
                    startActivityImmediately(intent);
                    break;
                }
                break;
            case C1683R.C1685id.nav_help:
                if (!(this instanceof FAQActivity)) {
                    intent.setClass(this, HelpActivity.class);
                    intent.putExtra("PDF_TYPE", 0);
                    startActivityImmediately(intent);
                    break;
                }
                break;
            case C1683R.C1685id.nav_info:
                intent.setClass(this, AppInfoActivity.class);
                startActivityImmediately(intent);
                break;
            case C1683R.C1685id.nav_send:
                if (!(this instanceof FeedbackActivity)) {
                    intent.setClass(this, FeedbackActivity.class);
                    startActivityImmediately(intent);
                    break;
                }
                break;
            case C1683R.C1685id.nav_wifi:
                intent.setClass(this, ConfigWiFiActivity.class);
                intent.putExtra("FindDevice", false);
                startActivityImmediately(intent);
                break;
        }
        this.drawer.closeDrawer((int) GravityCompat.END);
        return false;
    }

    public void onGetPortraitSuccess() {
        Log.d(this.TAG, " onGetPortraitSuccess setUserInfo");
    }

    /* access modifiers changed from: private */
    public void loadLocalLatestPortrait(File file) {
        if (file == null) {
            file = FileUtil.getLatestFile(this.imagePath, "jpg");
        }
        if (file != null && file.exists()) {
            this.civNavPortrait.setImageURI(Uri.parse(file.getPath()));
        }
    }

    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        initPortrait();
        getUserInformation();
    }

    public void startActivityWithIdler(final Intent intent) {
        if (intent != null && !isFinishing()) {
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                public boolean queueIdle() {
                    MyNavigationActivity.this.startActivity(intent);
                    return false;
                }
            });
        }
    }

    public void startActivityWithIdlerForResult(final Intent intent, final int i) {
        if (intent != null && !isFinishing()) {
            Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
                public boolean queueIdle() {
                    MyNavigationActivity.this.startActivityForResult(intent, i);
                    return false;
                }
            });
        }
    }

    public void startActivityImmediately(Intent intent) {
        if (intent != null && !isFinishing()) {
            startActivity(intent);
        }
    }

    public void startActivityImmediatelyForResult(Intent intent, int i) {
        if (intent != null && !isFinishing()) {
            startActivityForResult(intent, i);
        }
    }

    public void errorNotify(int i, boolean z) {
        AlarmNotificationFragment alarmNotificationFragment2;
        if (i == 4 && !z && (alarmNotificationFragment2 = this.alarmNotificationFragment) != null) {
            if (alarmNotificationFragment2.getDialog() == null) {
                this.alarmNotificationFragment.show(getFragmentManager(), "alarmNotificationFragment");
            } else if (!this.alarmNotificationFragment.getDialog().isShowing()) {
                try {
                    this.alarmNotificationFragment.show(getFragmentManager(), "alarmNotificationFragment");
                } catch (Exception unused) {
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void attachBaseContext(Context context) {
        super.attachBaseContext(LanguageUtil.setLocal(context));
    }

    private void getUserInformation() {
        AppYunManager.getInstance().getUserInfo(new IAppYunResponseListener<UserResponse>() {
            public void onSuccess(UserResponse userResponse) {
                Log.d(MyNavigationActivity.this.TAG, " onGet user info success");
                MyNavigationActivity.this.username = userResponse.getName();
                String str = MyNavigationActivity.this.TAG;
                Log.d(str, " getUserInformation username = " + MyNavigationActivity.this.username);
                MyNavigationActivity.this.tvNavUsername.setText(userResponse.getName());
                MyNavigationActivity.this.tvNavEmail.setText(userResponse.getEmail());
            }

            public void onFailure(int i, String str) {
                LogUtil.m3315d(MyNavigationActivity.this.TAG, "onGet user info fail");
            }
        });
    }

    private void getAvatar(String str) {
        final String substring = str.substring(str.lastIndexOf("/") + 1);
        String str2 = this.TAG;
        LogUtil.m3315d(str2, "image name== " + substring);
        File file = FileUtil.getFile(this.username, substring);
        if (file != null) {
            Log.d(this.TAG, " findFile setIntoView");
            loadLocalLatestPortrait(file);
            return;
        }
        HttpUtils.getRetrofit().getAvatar(str).enqueue(new Callback<ResponseBody>() {
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    boolean unused = MyNavigationActivity.this.writeResponseBodyToDisk(response.body(), substring);
                    MyNavigationActivity myNavigationActivity = MyNavigationActivity.this;
                    myNavigationActivity.loadLocalLatestPortrait(FileUtil.getFile(myNavigationActivity.username, substring));
                    Log.d(MyNavigationActivity.this.TAG, "下载成功");
                    return;
                }
                Log.d(MyNavigationActivity.this.TAG, "下载失败");
            }

            public void onFailure(Call<ResponseBody> call, Throwable th) {
                Log.e(MyNavigationActivity.this.TAG, "error");
            }
        });
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00eb A[SYNTHETIC, Splitter:B:40:0x00eb] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f0 A[Catch:{ IOException -> 0x0100 }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00f8 A[Catch:{ IOException -> 0x0100 }] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00fd A[Catch:{ IOException -> 0x0100 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean writeResponseBodyToDisk(okhttp3.ResponseBody r10, java.lang.String r11) {
        /*
            r9 = this;
            r0 = 0
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0100 }
            r1.<init>()     // Catch:{ IOException -> 0x0100 }
            java.lang.String r2 = com.szacs.ferroliconnect.util.FileUtil.CLOUDWARM_ROOT_PATH     // Catch:{ IOException -> 0x0100 }
            r1.append(r2)     // Catch:{ IOException -> 0x0100 }
            java.lang.String r2 = r9.username     // Catch:{ IOException -> 0x0100 }
            r1.append(r2)     // Catch:{ IOException -> 0x0100 }
            java.lang.String r1 = r1.toString()     // Catch:{ IOException -> 0x0100 }
            r9.imagePath = r1     // Catch:{ IOException -> 0x0100 }
            java.io.File r1 = new java.io.File     // Catch:{ IOException -> 0x0100 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0100 }
            r2.<init>()     // Catch:{ IOException -> 0x0100 }
            java.lang.String r3 = r9.imagePath     // Catch:{ IOException -> 0x0100 }
            r2.append(r3)     // Catch:{ IOException -> 0x0100 }
            java.lang.String r3 = "/"
            r2.append(r3)     // Catch:{ IOException -> 0x0100 }
            r2.append(r11)     // Catch:{ IOException -> 0x0100 }
            java.lang.String r11 = r2.toString()     // Catch:{ IOException -> 0x0100 }
            r1.<init>(r11)     // Catch:{ IOException -> 0x0100 }
            java.io.File r11 = r1.getParentFile()     // Catch:{ IOException -> 0x0100 }
            boolean r11 = r11.exists()     // Catch:{ IOException -> 0x0100 }
            if (r11 != 0) goto L_0x0045
            java.lang.String r11 = r9.TAG     // Catch:{ IOException -> 0x0100 }
            java.lang.String r2 = " writeResponseBodyToDisk 创建目录"
            android.util.Log.d(r11, r2)     // Catch:{ IOException -> 0x0100 }
            r1.mkdirs()     // Catch:{ IOException -> 0x0100 }
        L_0x0045:
            boolean r11 = r1.exists()     // Catch:{ IOException -> 0x0059 }
            if (r11 == 0) goto L_0x004e
            r1.delete()     // Catch:{ IOException -> 0x0059 }
        L_0x004e:
            java.lang.String r11 = r9.TAG     // Catch:{ IOException -> 0x0059 }
            java.lang.String r2 = " writeResponseBodyToDisk 创建图片"
            android.util.Log.d(r11, r2)     // Catch:{ IOException -> 0x0059 }
            r1.createNewFile()     // Catch:{ IOException -> 0x0059 }
            goto L_0x005d
        L_0x0059:
            r11 = move-exception
            r11.printStackTrace()     // Catch:{ IOException -> 0x0100 }
        L_0x005d:
            java.lang.String r11 = r9.TAG     // Catch:{ IOException -> 0x0100 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0100 }
            r2.<init>()     // Catch:{ IOException -> 0x0100 }
            java.lang.String r3 = "File path: "
            r2.append(r3)     // Catch:{ IOException -> 0x0100 }
            java.lang.String r3 = r1.getPath()     // Catch:{ IOException -> 0x0100 }
            r2.append(r3)     // Catch:{ IOException -> 0x0100 }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x0100 }
            com.szacs.ferroliconnect.util.LogUtil.m3315d((java.lang.String) r11, (java.lang.String) r2)     // Catch:{ IOException -> 0x0100 }
            java.lang.String r11 = r9.TAG     // Catch:{ IOException -> 0x0100 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0100 }
            r2.<init>()     // Catch:{ IOException -> 0x0100 }
            java.lang.String r3 = "File name :"
            r2.append(r3)     // Catch:{ IOException -> 0x0100 }
            java.lang.String r3 = r1.getName()     // Catch:{ IOException -> 0x0100 }
            r2.append(r3)     // Catch:{ IOException -> 0x0100 }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x0100 }
            com.szacs.ferroliconnect.util.LogUtil.m3315d((java.lang.String) r11, (java.lang.String) r2)     // Catch:{ IOException -> 0x0100 }
            r11 = 4096(0x1000, float:5.74E-42)
            r2 = 0
            byte[] r11 = new byte[r11]     // Catch:{ IOException -> 0x00f4, all -> 0x00e6 }
            long r3 = r10.contentLength()     // Catch:{ IOException -> 0x00f4, all -> 0x00e6 }
            r5 = 0
            java.io.InputStream r10 = r10.byteStream()     // Catch:{ IOException -> 0x00f4, all -> 0x00e6 }
            java.io.FileOutputStream r7 = new java.io.FileOutputStream     // Catch:{ IOException -> 0x00e4, all -> 0x00e1 }
            r7.<init>(r1)     // Catch:{ IOException -> 0x00e4, all -> 0x00e1 }
        L_0x00a5:
            int r1 = r10.read(r11)     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            r2 = -1
            if (r1 != r2) goto L_0x00b9
            r7.flush()     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            r11 = 1
            if (r10 == 0) goto L_0x00b5
            r10.close()     // Catch:{ IOException -> 0x0100 }
        L_0x00b5:
            r7.close()     // Catch:{ IOException -> 0x0100 }
            return r11
        L_0x00b9:
            r7.write(r11, r0, r1)     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            long r1 = (long) r1     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            long r5 = r5 + r1
            java.lang.String r1 = r9.TAG     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            r2.<init>()     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            java.lang.String r8 = "file download: "
            r2.append(r8)     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            r2.append(r5)     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            java.lang.String r8 = " of "
            r2.append(r8)     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            r2.append(r3)     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            java.lang.String r2 = r2.toString()     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            android.util.Log.d(r1, r2)     // Catch:{ IOException -> 0x00df, all -> 0x00dd }
            goto L_0x00a5
        L_0x00dd:
            r11 = move-exception
            goto L_0x00e9
        L_0x00df:
            goto L_0x00f6
        L_0x00e1:
            r11 = move-exception
            r7 = r2
            goto L_0x00e9
        L_0x00e4:
            r7 = r2
            goto L_0x00f6
        L_0x00e6:
            r11 = move-exception
            r10 = r2
            r7 = r10
        L_0x00e9:
            if (r10 == 0) goto L_0x00ee
            r10.close()     // Catch:{ IOException -> 0x0100 }
        L_0x00ee:
            if (r7 == 0) goto L_0x00f3
            r7.close()     // Catch:{ IOException -> 0x0100 }
        L_0x00f3:
            throw r11     // Catch:{ IOException -> 0x0100 }
        L_0x00f4:
            r10 = r2
            r7 = r10
        L_0x00f6:
            if (r10 == 0) goto L_0x00fb
            r10.close()     // Catch:{ IOException -> 0x0100 }
        L_0x00fb:
            if (r7 == 0) goto L_0x0100
            r7.close()     // Catch:{ IOException -> 0x0100 }
        L_0x0100:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.activity.MyNavigationActivity.writeResponseBodyToDisk(okhttp3.ResponseBody, java.lang.String):boolean");
    }

    public void setMqttClientId() {
        String string = getSharedPreferences("ferroli_user", 0).getString(UserCenter.getUserInfo().getSlug(), "");
        String str = this.TAG;
        Log.d(str, " doLoginByToken mqttClientId = " + string);
        AppYunManager.getInstance().setMqttClientId(string);
    }

    /* access modifiers changed from: protected */
    public void setBaseDrawerItemVisible(boolean z) {
        this.navigationView.getMenu().findItem(C1683R.C1685id.nav_account).setVisible(z);
        this.navigationView.getMenu().findItem(C1683R.C1685id.nav_wifi).setVisible(z);
        this.navigationView.getMenu().findItem(C1683R.C1685id.nav_help).setVisible(z);
        this.navigationView.getMenu().findItem(C1683R.C1685id.nav_info).setVisible(z);
        this.navigationView.getMenu().findItem(C1683R.C1685id.nav_send).setVisible(z);
    }
}
