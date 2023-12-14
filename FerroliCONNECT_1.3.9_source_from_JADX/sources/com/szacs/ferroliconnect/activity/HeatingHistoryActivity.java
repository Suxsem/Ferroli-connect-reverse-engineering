package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.viewInterface.HeatingHistoryView;
import com.szacs.ferroliconnect.widget.MyHistoryChart;
import com.szacs.ferroliconnect.widget.MyProgressDialog;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class HeatingHistoryActivity extends MyNavigationActivity implements HeatingHistoryView {
    /* access modifiers changed from: private */
    public ArrayList<CharSequence> dateList;
    private int gatewayPosition;
    private MyHistoryChart lcHeatingHistory;
    /* access modifiers changed from: private */
    public MyProgressDialog loadingDialog;
    private Spinner navigationSpinner;
    private int thermostatPosition;

    private void initWidgetFunction() {
    }

    /* access modifiers changed from: protected */
    public int mainLayoutId() {
        return C1683R.C1686layout.activity_heating_history;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        this.gatewayPosition = intent.getIntExtra("gatewayPosition", 0);
        this.thermostatPosition = intent.getIntExtra("thermostatPosition", 0);
        initWidget();
        this.navigationSpinner.setSelection(0, true);
        setTitle(getString(C1683R.string.menu_historical_data));
        this.drawer.setDrawerLockMode(1);
    }

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
        this.dateList = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        for (int i = 0; i < 7; i++) {
            if (i > 0) {
                gregorianCalendar.add(5, -1);
            }
            this.dateList.add(simpleDateFormat.format(gregorianCalendar.getTime()));
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, C1683R.C1686layout.spinner_cloudwarm, this.dateList);
        this.navigationSpinner = new Spinner(getSupportActionBar().getThemedContext());
        this.navigationSpinner.setAdapter(arrayAdapter);
        this.myToolbar.addView(this.navigationSpinner, -1, new FrameLayout.LayoutParams(-2, -2, 5));
        this.navigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                HeatingHistoryActivity.this.loadingDialog.show();
                String charSequence = ((CharSequence) HeatingHistoryActivity.this.dateList.get(i)).toString();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                new GregorianCalendar();
                try {
                    long time = simpleDateFormat.parse(charSequence + " 00:00:00").getTime();
                    Log.d("Device start time stamp", time + "");
                } catch (ParseException unused) {
                }
            }
        });
    }

    private void initWidget() {
        this.lcHeatingHistory = (MyHistoryChart) findViewById(C1683R.C1685id.lcHeatingHistory);
        this.lcHeatingHistory.setDescription("");
        this.lcHeatingHistory.setNoDataTextDescription("No heating history data");
        this.lcHeatingHistory.setDrawGridBackground(false);
        this.lcHeatingHistory.setTouchEnabled(false);
        this.lcHeatingHistory.setDragDecelerationFrictionCoef(0.9f);
        this.lcHeatingHistory.setDragEnabled(true);
        this.lcHeatingHistory.setScaleEnabled(true);
        this.lcHeatingHistory.setHighlightPerDragEnabled(true);
        this.lcHeatingHistory.setPinchZoom(false);
        this.loadingDialog = new MyProgressDialog(this);
        this.loadingDialog.setMessage(getString(C1683R.string.public_loading));
        initWidgetFunction();
    }

    public void onGetHeatingHistoryFailed(int i, boolean z) {
        try {
            this.loadingDialog.dismiss();
        } catch (Exception unused) {
        }
    }

    public int getGatewayPosition() {
        return this.gatewayPosition;
    }

    public int getThermostatPosition() {
        return this.thermostatPosition;
    }
}
