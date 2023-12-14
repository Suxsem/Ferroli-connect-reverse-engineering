package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.p000v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.aigestudio.wheelpicker.core.AbstractWheelDecor;
import com.aigestudio.wheelpicker.core.AbstractWheelPicker;
import com.aigestudio.wheelpicker.view.WheelCurvedPicker;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.viewInterface.BoilerTechnicalView;
import java.util.ArrayList;
import java.util.List;

public class TSPFragment extends DialogFragment {
    /* access modifiers changed from: private */
    public BoilerTechnicalView boilerTechnicalView;
    /* access modifiers changed from: private */
    public AlertDialog dialog;
    private ImageView ivCancel;
    private ImageView ivConfirm;
    /* access modifiers changed from: private */
    public String tspIndexTemp;
    /* access modifiers changed from: private */
    public String tspValueTemp;
    /* access modifiers changed from: private */
    public TextView tvLoading;
    private View view;
    /* access modifiers changed from: private */
    public WheelCurvedPicker wvTSPIndex;
    /* access modifiers changed from: private */
    public WheelCurvedPicker wvTSPValue;

    public Dialog onCreateDialog(Bundle bundle) {
        this.view = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_tsp, (ViewGroup) null);
        this.dialog = new AlertDialog.Builder(getActivity()).setView(this.view).create();
        this.boilerTechnicalView = (BoilerTechnicalView) getActivity();
        this.wvTSPIndex = (WheelCurvedPicker) this.view.findViewById(C1683R.C1685id.wvTSPIndex);
        this.wvTSPValue = (WheelCurvedPicker) this.view.findViewById(C1683R.C1685id.wvTSPValue);
        this.tvLoading = (TextView) this.view.findViewById(C1683R.C1685id.tvLoading);
        this.ivCancel = (ImageView) this.view.findViewById(C1683R.C1685id.ivCancel);
        this.ivConfirm = (ImageView) this.view.findViewById(C1683R.C1685id.ivConfirm);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i <= 255; i++) {
            arrayList.add(String.valueOf(i));
        }
        initWheel(this.wvTSPIndex, arrayList, 0);
        initWheel(this.wvTSPValue, arrayList, 50);
        this.wvTSPIndex.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            public void onWheelScrolling(float f, float f2) {
            }

            public void onWheelSelected(int i, String str) {
                TSPFragment.this.boilerTechnicalView.getTSP(str);
                String unused = TSPFragment.this.tspIndexTemp = str;
            }

            public void onWheelScrollStateChanged(int i) {
                if (i != 0) {
                    TSPFragment.this.tvLoading.setVisibility(0);
                    TSPFragment.this.wvTSPValue.setVisibility(8);
                }
            }
        });
        this.wvTSPValue.setOnWheelChangeListener(new AbstractWheelPicker.OnWheelChangeListener() {
            public void onWheelScrollStateChanged(int i) {
            }

            public void onWheelScrolling(float f, float f2) {
            }

            public void onWheelSelected(int i, String str) {
                String unused = TSPFragment.this.tspValueTemp = str;
            }
        });
        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TSPFragment.this.wvTSPIndex.clearCache();
                TSPFragment.this.wvTSPValue.clearCache();
                TSPFragment.this.dialog.cancel();
            }
        });
        this.ivConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                TSPFragment.this.boilerTechnicalView.setTSP(TSPFragment.this.tspIndexTemp, TSPFragment.this.tspValueTemp);
                TSPFragment.this.wvTSPIndex.clearCache();
                TSPFragment.this.wvTSPValue.clearCache();
                TSPFragment.this.dialog.cancel();
            }
        });
        return this.dialog;
    }

    private void initWheel(WheelCurvedPicker wheelCurvedPicker, List<String> list, int i) {
        wheelCurvedPicker.setData(list);
        wheelCurvedPicker.setItemIndex(i);
        wheelCurvedPicker.setTextColor(ContextCompat.getColor(getActivity(), C1683R.color.cloudwarm_green));
        wheelCurvedPicker.setCurrentTextColor(ContextCompat.getColor(getActivity(), C1683R.color.cloudwarm_orange));
        wheelCurvedPicker.setWheelDecor(true, new AbstractWheelDecor() {
            public void drawDecor(Canvas canvas, Rect rect, Rect rect2, Paint paint) {
                canvas.drawColor(ContextCompat.getColor(TSPFragment.this.getActivity(), C1683R.color.cloudwarm_orange_lite));
            }
        });
    }

    public void showTSPValue(int i) {
        this.tvLoading.setVisibility(8);
        this.wvTSPValue.setVisibility(0);
        this.wvTSPValue.setItemIndex(i);
    }
}
