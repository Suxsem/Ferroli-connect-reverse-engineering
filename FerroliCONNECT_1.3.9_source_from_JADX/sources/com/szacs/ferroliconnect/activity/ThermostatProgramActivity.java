package com.szacs.ferroliconnect.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.p000v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.Spinner;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.p107tb.appyunsdk.AppYunManager;
import com.p107tb.appyunsdk.listener.IAppYunManagerActionListener;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.bean.BoilerInfoBean;
import com.szacs.ferroliconnect.bean.MsgBean;
import com.szacs.ferroliconnect.bean.UserCenter;
import com.szacs.ferroliconnect.dialog.SetCEFTempDialog;
import com.szacs.ferroliconnect.event.ApplyEvent;
import com.szacs.ferroliconnect.event.BaseEvent;
import com.szacs.ferroliconnect.event.Event;
import com.szacs.ferroliconnect.fragment.ApplyProgramFragment;
import com.szacs.ferroliconnect.net.HttpUtils;
import com.szacs.ferroliconnect.util.Centigrade;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.util.SpUtil;
import com.szacs.ferroliconnect.util.ToastUtil;
import com.szacs.ferroliconnect.widget.wheel.BarViewBeSmart196;
import com.topband.sdk.boiler.MessageDataBuilder;
import com.topband.sdk.boiler.util.BinaryUtils;
import java.lang.ref.WeakReference;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class ThermostatProgramActivity extends MyNavigationActivity implements SetCEFTempDialog.TempChangeListenner {
    private static final int CHANGE_MODE = 2;
    private static final int CHANGE_TEMP = 1;
    private static final int HIDE_PROGRESS = 3;
    private static final int NETWORK_ERROR = 4;
    private static final String TAG = "ProgramActivity";
    /* access modifiers changed from: private */
    public BoilerInfoBean boilerInfoBean;
    private BarViewBeSmart196 bvProgram;
    private Centigrade cenComf;
    private Centigrade cenEcon;
    private Centigrade cenFrost;
    private List<Centigrade> centigradeList;
    /* access modifiers changed from: private */
    public int day = 0;
    private String devCode;
    private int hour = 0;
    private HorizontalScrollView hsvProgram;
    /* access modifiers changed from: private */
    public boolean lock = false;
    private boolean mIsNewWifiVersion;
    private Disposable mSubscribe;
    /* access modifiers changed from: private */
    public MyHandler mhandler;
    private int min = 0;
    private Spinner navigationSpinner;
    private int[] oldPrograms = new int[48];
    private String productCode;
    /* access modifiers changed from: private */
    public int[][] programs = ((int[][]) Array.newInstance(int.class, new int[]{7, 48}));
    private int serverTz = 0;
    private MsgBean therBean;
    private String therid;

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_thermostat_program;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setRequestedOrientation(0);
        EventBus.getDefault().register(this);
        this.mIsNewWifiVersion = getIntent().getBooleanExtra("isNewWifiVersion", false);
        this.mhandler = new MyHandler(this);
        this.centigradeList = new ArrayList();
        this.cenComf = new Centigrade("20.0");
        this.cenEcon = new Centigrade("10.0");
        this.cenFrost = new Centigrade(DispatchConstants.VER_CODE);
        this.centigradeList.add(this.cenComf);
        this.centigradeList.add(this.cenEcon);
        this.centigradeList.add(this.cenFrost);
        this.hsvProgram = (HorizontalScrollView) findViewById(C1683R.C1685id.hsvProgram);
        this.bvProgram = (BarViewBeSmart196) findViewById(C1683R.C1685id.bvProgram);
        if (UserCenter.getResultsBean() != null) {
            this.productCode = UserCenter.getResultsBean().getProduct_code();
            this.devCode = UserCenter.getResultsBean().getDevice_code();
        }
        if (UserCenter.getChildDevicesBean() != null) {
            this.therid = UserCenter.getChildDevicesBean().getSdid();
        }
        this.programs = UserCenter.getMsgBean().getProgram();
        System.arraycopy(this.programs[this.day], 0, this.oldPrograms, 0, 48);
        onMessage(UserCenter.getMsgBean());
        setTitle(getString(C1683R.string.menu_program));
        this.drawer.setDrawerLockMode(1);
        this.bvProgram.setOnBarClickListener(new BarViewBeSmart196.OnBarClickListener() {
            public void onBarClick(int i, int i2, int i3) {
                boolean unused = ThermostatProgramActivity.this.lock = true;
                LogUtil.m3315d(ThermostatProgramActivity.TAG, "index = " + i + ", currentTempMode=" + i2 + ",clickTempMode=" + i3);
                if (i3 == 2) {
                    if (i2 == 2) {
                        ThermostatProgramActivity.this.programs[ThermostatProgramActivity.this.day][i] = 1;
                    } else {
                        ThermostatProgramActivity.this.programs[ThermostatProgramActivity.this.day][i] = 2;
                    }
                } else if (i3 == 1) {
                    if (i2 == 2 || i2 == 0) {
                        ThermostatProgramActivity.this.programs[ThermostatProgramActivity.this.day][i] = 1;
                    } else {
                        ThermostatProgramActivity.this.programs[ThermostatProgramActivity.this.day][i] = 0;
                    }
                } else if (i3 != 0) {
                } else {
                    if (i2 == 2 || i2 == 1) {
                        ThermostatProgramActivity.this.programs[ThermostatProgramActivity.this.day][i] = 0;
                    } else {
                        ThermostatProgramActivity.this.programs[ThermostatProgramActivity.this.day][i] = 1;
                    }
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
        Calendar instance = Calendar.getInstance();
        this.day = Integer.parseInt(String.valueOf(instance.get(7))) - 1;
        this.hour = instance.get(11);
        this.min = instance.get(12);
        ArrayList arrayList = new ArrayList();
        arrayList.add(getResources().getString(C1683R.string.public_sunday));
        arrayList.add(getResources().getString(C1683R.string.public_monday));
        arrayList.add(getResources().getString(C1683R.string.public_tuesday));
        arrayList.add(getResources().getString(C1683R.string.public_wednesday));
        arrayList.add(getResources().getString(C1683R.string.public_thursday));
        arrayList.add(getResources().getString(C1683R.string.public_friday));
        arrayList.add(getResources().getString(C1683R.string.public_saturday));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, C1683R.C1686layout.spinner_cloudwarm, arrayList);
        this.navigationSpinner = new Spinner(getSupportActionBar().getThemedContext());
        this.navigationSpinner.setAdapter(arrayAdapter);
        this.navigationSpinner.setSelection(this.day, true);
        this.myToolbar.addView(this.navigationSpinner, 0);
        this.navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                int unused = ThermostatProgramActivity.this.day = i;
                Log.d(ThermostatProgramActivity.TAG, " navigationSpinner 选择星期 day = " + ThermostatProgramActivity.this.day);
                ThermostatProgramActivity.this.ShowProgressDialog((String) null);
                ThermostatProgramActivity thermostatProgramActivity = ThermostatProgramActivity.this;
                thermostatProgramActivity.getProgram(thermostatProgramActivity.day);
                ThermostatProgramActivity.this.mhandler.sendEmptyMessageDelayed(4, 5000);
            }
        });
        this.myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ThermostatProgramActivity.this.finishProgram();
            }
        });
    }

    /* access modifiers changed from: private */
    public void finishProgram() {
        if (!checkChange()) {
            finish();
        } else {
            showSaveDialog();
        }
    }

    /* access modifiers changed from: private */
    public void getProgram(int i) {
        LogUtil.m3321i(TAG, "getProgram day: " + i);
        sendMessage(new MessageDataBuilder().thermostat(this.therid).TheromstatProgram(i).daylightTime(true).inDaylightTime(0).query());
    }

    private void sendMessage(final byte[] bArr) {
        if (bArr != null) {
            AppYunManager.getInstance().sendMsgToDevice(this.productCode, this.devCode, bArr, new IAppYunManagerActionListener() {
                public void onSuccess(IMqttToken iMqttToken) {
                    boolean unused = ThermostatProgramActivity.this.lock = false;
                    Log.d(ThermostatProgramActivity.TAG, "sendMessage " + BinaryUtils.bytes2String(bArr));
                }

                public void onFailure(IMqttToken iMqttToken, Throwable th) {
                    boolean unused = ThermostatProgramActivity.this.lock = false;
                    ToastUtil.showShortToast(ThermostatProgramActivity.this.mContext, ThermostatProgramActivity.this.getString(C1683R.string.public_failed_to_set));
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBaseEvent(BaseEvent baseEvent) {
        if (baseEvent instanceof ApplyEvent) {
            boolean[] days = ((ApplyEvent) baseEvent).getDays();
            MessageDataBuilder thermostat = new MessageDataBuilder().thermostat(this.therid);
            for (int i = 0; i < days.length; i++) {
                if (days[i]) {
                    Log.i(TAG, "i: " + i + ",programs: " + Arrays.toString(this.programs[this.day]));
                    thermostat.TheromstatProgram(i, this.programs[this.day]);
                }
            }
            ShowProgressDialog((String) null);
            LogUtil.m3321i(TAG, "onBaseEvent baseEvent");
            sendMessage(thermostat.control());
            this.mhandler.sendEmptyMessageDelayed(4, 5000);
        } else if (baseEvent.getEvent() == Event.SERVER_RESPONSE_SUCCESS) {
            Log.d(TAG, "onBaseEvent  SERVER_RESPONSE_SUCCESS ");
            HideProgressDialog();
            this.mhandler.removeMessages(4);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(MsgBean msgBean) {
        if (msgBean != null && !this.lock) {
            this.lock = true;
            this.therBean = msgBean;
            Log.i("testHide", "hide21");
            HideProgressDialog();
            this.programs = msgBean.getProgram();
            System.arraycopy(this.programs[this.day], 0, this.oldPrograms, 0, 48);
            Centigrade centigrade = this.cenComf;
            centigrade.setCenF(msgBean.getComfortTemp() + "", true);
            Centigrade centigrade2 = this.cenEcon;
            centigrade2.setCenF(msgBean.getEconTemp() + "", true);
            Centigrade centigrade3 = this.cenFrost;
            centigrade3.setCenF(msgBean.getFrostTemp() + "", true);
            TimeZone timeZone = TimeZone.getTimeZone("UTC");
            Log.d(TAG, " setCurProgram UTC timeZone offset = " + timeZone.getRawOffset());
            timeZone.setRawOffset(this.serverTz * 3600000);
            Log.d(TAG, " BoilerInfo timezone = " + this.serverTz);
            long j = this.therBean.getmWifiStamp();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            simpleDateFormat.setTimeZone(timeZone);
            String format = simpleDateFormat.format(Long.valueOf(j));
            Log.d(TAG, " Device Time data = " + format + " Device time stamp = " + j);
            Calendar instance = Calendar.getInstance();
            StringBuilder sb = new StringBuilder();
            sb.append(" onMessage 获取星期 day = ");
            sb.append(this.day);
            Log.d(TAG, sb.toString());
            instance.setTimeZone(timeZone);
            instance.setTimeInMillis(j);
            this.hour = instance.get(11);
            this.min = instance.get(12);
            Log.d(TAG, " setCurProgram day = " + this.day + " hour = " + this.hour + " min = " + this.min);
            int[] program = this.therBean.getProgram(this.day);
            for (int i = 0; i < program.length; i++) {
                this.bvProgram.setTemperature(i, program[i]);
            }
            this.bvProgram.clearBarStatus();
            this.bvProgram.setBarColors(((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 2.0f, ContextCompat.getColor(this, C1683R.color.cloudwarm_orange));
            this.bvProgram.setBarTextHidden(((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 2.0f, false);
            this.bvProgram.setBarTextColor(ContextCompat.getColor(this, C1683R.color.cloudwarm_orange));
            BarViewBeSmart196 barViewBeSmart196 = this.bvProgram;
            barViewBeSmart196.setSideText(new String[]{"T1 " + this.therBean.getFrostTemp(), "T2 " + this.therBean.getEconTemp(), "T3 " + this.therBean.getComfortTemp()});
            getBoilerInfo();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C1683R.C1687menu.menu_program, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (super.onOptionsItemSelected(menuItem)) {
            return true;
        }
        int itemId = menuItem.getItemId();
        if (itemId == C1683R.C1685id.muApplyTo) {
            new ApplyProgramFragment().show(getFragmentManager(), "applyProgramDialog");
        } else if (itemId == C1683R.C1685id.save) {
            saveChange();
        } else if (itemId == C1683R.C1685id.setting) {
            new SetCEFTempDialog(this.mContext, true, this.centigradeList, this.mIsNewWifiVersion).setListenner(this).show();
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void saveChange() {
        ShowProgressDialog((String) null);
        LogUtil.m3321i(TAG, "saveChange");
        MessageDataBuilder thermostat = new MessageDataBuilder().thermostat(this.therid);
        int i = this.day;
        sendMessage(thermostat.TheromstatProgram(i, this.programs[i]).control());
        this.mhandler.sendEmptyMessageDelayed(4, 5000);
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        Disposable disposable = this.mSubscribe;
        if (disposable != null) {
            disposable.dispose();
            this.mSubscribe = null;
        }
        super.onDestroy();
    }

    public void onTempChange(List<Centigrade> list) {
        LogUtil.m3321i(TAG, "onTempChange comfortTemp: " + list.get(0).getCenFloat() + ",econTemp: " + list.get(1).getCenFloat());
        if (this.mIsNewWifiVersion) {
            LogUtil.m3321i(TAG, "frostTemp: " + list.get(2).getCenFloat());
            sendMessage(new MessageDataBuilder().thermostat(this.therid).TheromstatComfortTemp(list.get(0).getCenFloat()).TheromstatEconTemp(list.get(1).getCenFloat()).TheromstatFrostTemp(list.get(2).getCenFloat()).control());
        } else {
            sendMessage(new MessageDataBuilder().thermostat(this.therid).TheromstatComfortTemp(list.get(0).getCenFloat()).TheromstatEconTemp(list.get(1).getCenFloat()).control());
        }
        ShowProgressDialog((String) null);
        this.mhandler.sendEmptyMessageDelayed(4, 5000);
    }

    private class MyHandler extends Handler {
        private WeakReference<ThermostatProgramActivity> activityWeakReference;

        public MyHandler(ThermostatProgramActivity thermostatProgramActivity) {
            this.activityWeakReference = new WeakReference<>(thermostatProgramActivity);
        }

        public void handleMessage(Message message) {
            int i;
            super.handleMessage(message);
            ThermostatProgramActivity thermostatProgramActivity = (ThermostatProgramActivity) this.activityWeakReference.get();
            if (thermostatProgramActivity != null && (i = message.what) != 1) {
                if (i == 2) {
                    int i2 = message.arg1;
                } else if (i != 3 && i == 4 && !thermostatProgramActivity.isFinishing()) {
                    thermostatProgramActivity.showToast(thermostatProgramActivity.getString(C1683R.string.dialog_device_not_response));
                    ThermostatProgramActivity thermostatProgramActivity2 = ThermostatProgramActivity.this;
                    thermostatProgramActivity2.getProgram(thermostatProgramActivity2.day);
                }
            }
        }
    }

    public void showToast(String str) {
        if (!isFinishing()) {
            HideProgressDialog();
            ToastUtil.showShortToast(this.mContext, str);
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        configuration.locale = LanguageUtil.getLocaleByLanguage(SpUtil.getInstance(this).getString("language"));
        super.onConfigurationChanged(configuration);
        LanguageUtil.changeAppLanguage(this, SpUtil.getInstance(this).getString("language"));
    }

    private boolean checkChange() {
        for (int i = 0; i < 48; i++) {
            if (this.oldPrograms[i] != this.programs[this.day][i]) {
                return true;
            }
        }
        return false;
    }

    private void showSaveDialog() {
        Log.d(TAG, " showSaveDialog ");
        QMUIDialog.MessageDialogBuilder messageDialogBuilder = new QMUIDialog.MessageDialogBuilder(this);
        messageDialogBuilder.setMessage((int) C1683R.string.dialog_save_program_data);
        messageDialogBuilder.setTitle((int) C1683R.string.dialog_title_tip);
        messageDialogBuilder.setCanceledOnTouchOutside(true);
        messageDialogBuilder.setCancelable(true);
        messageDialogBuilder.addAction((int) C1683R.string.public_cancel, (QMUIDialogAction.ActionListener) new QMUIDialogAction.ActionListener() {
            public void onClick(QMUIDialog qMUIDialog, int i) {
                qMUIDialog.dismiss();
                ThermostatProgramActivity.this.finish();
            }
        });
        messageDialogBuilder.addAction((int) C1683R.string.public_confirm, (QMUIDialogAction.ActionListener) new QMUIDialogAction.ActionListener() {
            public void onClick(QMUIDialog qMUIDialog, int i) {
                ThermostatProgramActivity.this.saveChange();
                qMUIDialog.dismiss();
                ThermostatProgramActivity.this.finish();
            }
        });
        messageDialogBuilder.create().show();
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4) {
            finishProgram();
        }
        return super.onKeyUp(i, keyEvent);
    }

    private void getBoilerInfo() {
        this.mSubscribe = HttpUtils.getRetrofit().getBoilerInfo(this.authorzation, MainApplication.appSlug, UserCenter.getResultsBean().getSlug()).subscribeOn(Schedulers.m3877io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<BoilerInfoBean>() {
            public void accept(BoilerInfoBean boilerInfoBean) throws Exception {
                LogUtil.m3315d(ThermostatProgramActivity.TAG, "onGet boiler info success");
                ThermostatProgramActivity.this.setCurProgram(TextUtils.isEmpty(boilerInfoBean.getTimezone()) ? 12 : Integer.parseInt(boilerInfoBean.getTimezone()));
                BoilerInfoBean unused = ThermostatProgramActivity.this.boilerInfoBean = boilerInfoBean;
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable th) throws Exception {
                Log.e(ThermostatProgramActivity.TAG, "onGet boiler info fail", th);
                th.printStackTrace();
            }
        });
    }

    /* access modifiers changed from: private */
    public void setCurProgram(int i) {
        this.serverTz = (!this.therBean.isDayLightTime() || !this.therBean.isInDayLightTime()) ? i - 12 : (i - 12) + 1;
        Log.d(TAG, " isDayLightTime = " + this.therBean.isDayLightTime() + " tz = " + this.serverTz);
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        StringBuilder sb = new StringBuilder();
        sb.append(" setCurProgram UTC timeZone offset = ");
        sb.append(timeZone.getRawOffset());
        Log.d(TAG, sb.toString());
        timeZone.setRawOffset(this.serverTz * 3600000);
        Log.d(TAG, " BoilerInfo timezone = " + this.serverTz);
        long j = this.therBean.getmWifiStamp();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(timeZone);
        String format = simpleDateFormat.format(Long.valueOf(j));
        Log.d(TAG, " Device Time data = " + format + " Device time stamp = " + j);
        Calendar instance = Calendar.getInstance();
        instance.setTimeZone(timeZone);
        instance.setTimeInMillis(j);
        this.hour = instance.get(11);
        this.min = instance.get(12);
        Log.d(TAG, " setCurProgram day = " + this.day + " hour = " + this.hour + " min = " + this.min);
        int[] program = this.therBean.getProgram(this.day);
        for (int i2 = 0; i2 < program.length; i2++) {
            this.bvProgram.setTemperature(i2, program[i2]);
        }
        this.bvProgram.clearBarStatus();
        this.bvProgram.setBarColors(((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 2.0f, ContextCompat.getColor(this, C1683R.color.cloudwarm_orange));
        this.bvProgram.setBarTextHidden(((float) ((this.hour * 2) + (this.min > 30 ? 1 : 0))) / 2.0f, false);
        this.bvProgram.setBarTextColor(ContextCompat.getColor(this, C1683R.color.cloudwarm_orange));
        BarViewBeSmart196 barViewBeSmart196 = this.bvProgram;
        barViewBeSmart196.setSideText(new String[]{"T1 " + this.therBean.getFrostTemp(), "T2 " + this.therBean.getEconTemp(), "T3 " + this.therBean.getComfortTemp()});
    }
}
