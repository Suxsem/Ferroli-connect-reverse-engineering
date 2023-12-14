package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.igexin.assist.sdk.AssistPushConsts;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.viewInterface.ThermostatProgramView;
import com.szacs.ferroliconnect.widget.wheel.OnWheelChangedListener;
import com.szacs.ferroliconnect.widget.wheel.StrericWheelAdapter;
import com.szacs.ferroliconnect.widget.wheel.WheelView;
import org.android.agoo.common.AgooConstants;
import org.android.agoo.message.MessageService;

public class EditProgramFragment extends DialogFragment {
    /* access modifiers changed from: private */
    public LinearLayout llTitle;
    /* access modifiers changed from: private */
    public LinearLayout llWheel;

    public Dialog onCreateDialog(Bundle bundle) {
        AlertDialog alertDialog;
        int i;
        View inflate = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_edit_program, (ViewGroup) null);
        AlertDialog create = new AlertDialog.Builder(getActivity()).setView(inflate).create();
        final WheelView wheelView = (WheelView) inflate.findViewById(C1683R.C1685id.wvTempInt);
        final WheelView wheelView2 = (WheelView) inflate.findViewById(C1683R.C1685id.wvTempFloat);
        final WheelView wheelView3 = (WheelView) inflate.findViewById(C1683R.C1685id.wvStartHour);
        final WheelView wheelView4 = (WheelView) inflate.findViewById(C1683R.C1685id.wvStartMin);
        final WheelView wheelView5 = (WheelView) inflate.findViewById(C1683R.C1685id.wvEndHour);
        WheelView wheelView6 = (WheelView) inflate.findViewById(C1683R.C1685id.wvEndMin);
        this.llWheel = (LinearLayout) inflate.findViewById(C1683R.C1685id.llWheel);
        this.llTitle = (LinearLayout) inflate.findViewById(C1683R.C1685id.llTitle);
        wheelView.setVisibleItems(3);
        wheelView2.setVisibleItems(3);
        wheelView3.setVisibleItems(3);
        wheelView4.setVisibleItems(3);
        wheelView5.setVisibleItems(3);
        wheelView6.setVisibleItems(3);
        String[] strArr = {AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_OPPO, AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_VIVO, "7", MessageService.MSG_ACCS_NOTIFY_CLICK, MessageService.MSG_ACCS_NOTIFY_DISMISS, AgooConstants.ACK_REMOVE_PACKAGE, AgooConstants.ACK_BODY_NULL, AgooConstants.ACK_PACK_NULL, AgooConstants.ACK_FLAG_NULL, AgooConstants.ACK_PACK_NOBIND, AgooConstants.ACK_PACK_ERROR, "16", "17", "18", "19", "20", AgooConstants.REPORT_MESSAGE_NULL, AgooConstants.REPORT_ENCRYPT_FAIL, AgooConstants.REPORT_DUPLICATE_FAIL, AgooConstants.REPORT_NOT_ENCRYPT, "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35"};
        View view = inflate;
        String[] strArr2 = {"0", "2", "4", AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_VIVO, MessageService.MSG_ACCS_NOTIFY_CLICK};
        String[] strArr3 = {"0", "1", "2", "3", "4", AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_OPPO, AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_VIVO, "7", MessageService.MSG_ACCS_NOTIFY_CLICK, MessageService.MSG_ACCS_NOTIFY_DISMISS, AgooConstants.ACK_REMOVE_PACKAGE, AgooConstants.ACK_BODY_NULL, AgooConstants.ACK_PACK_NULL, AgooConstants.ACK_FLAG_NULL, AgooConstants.ACK_PACK_NOBIND, AgooConstants.ACK_PACK_ERROR, "16", "17", "18", "19", "20", AgooConstants.REPORT_MESSAGE_NULL, AgooConstants.REPORT_ENCRYPT_FAIL, AgooConstants.REPORT_DUPLICATE_FAIL};
        String[] strArr4 = {"0", "1", "2", "3", "4", AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_OPPO, AssistPushConsts.PUSHMESSAGE_ACTION_MULTI_BRAND_RECEIVE_VIVO, "7", MessageService.MSG_ACCS_NOTIFY_CLICK, MessageService.MSG_ACCS_NOTIFY_DISMISS, AgooConstants.ACK_REMOVE_PACKAGE, AgooConstants.ACK_BODY_NULL, AgooConstants.ACK_PACK_NULL, AgooConstants.ACK_FLAG_NULL, AgooConstants.ACK_PACK_NOBIND, AgooConstants.ACK_PACK_ERROR, "16", "17", "18", "19", "20", AgooConstants.REPORT_MESSAGE_NULL, AgooConstants.REPORT_ENCRYPT_FAIL, AgooConstants.REPORT_DUPLICATE_FAIL, AgooConstants.REPORT_NOT_ENCRYPT};
        String[] strArr5 = {"00", "30"};
        ThermostatProgramView thermostatProgramView = (ThermostatProgramView) getActivity();
        float[] programEditData = thermostatProgramView.getProgramEditData();
        ImageView imageView = (ImageView) inflate.findViewById(C1683R.C1685id.ivConfirm);
        float f = programEditData[0];
        ThermostatProgramView thermostatProgramView2 = thermostatProgramView;
        float f2 = programEditData[1];
        float f3 = programEditData[2];
        ImageView imageView2 = (ImageView) inflate.findViewById(C1683R.C1685id.ivCancel);
        int i2 = (int) f;
        if ((f * 2.0f) % 2.0f == 0.0f) {
            alertDialog = create;
            i = 0;
        } else {
            alertDialog = create;
            i = 1;
        }
        int i3 = (int) f2;
        int i4 = (f2 * 2.0f) % 2.0f == 0.0f ? 0 : 1;
        initWheel(wheelView, strArr, (int) (f3 - 5.0f));
        initWheel(wheelView2, strArr2, (int) (((f3 * 10.0f) % 10.0f) / 2.0f));
        initWheel(wheelView3, strArr3, i2);
        initWheel(wheelView4, strArr5, i);
        initWheel(wheelView5, strArr4, i3);
        final WheelView wheelView7 = wheelView6;
        initWheel(wheelView7, strArr5, i4);
        wheelView7.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                if (wheelView5.getCurrentItemValue().equals(AgooConstants.REPORT_NOT_ENCRYPT)) {
                    wheelView7.setCurrentItem(0, false);
                }
            }
        });
        wheelView5.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                if (wheelView5.getCurrentItemValue().equals(AgooConstants.REPORT_NOT_ENCRYPT)) {
                    wheelView7.setCurrentItem(0, false);
                }
            }
        });
        wheelView.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                if (wheelView.getCurrentItemValue().equals("35")) {
                    wheelView2.setCurrentItem(0, false);
                }
            }
        });
        wheelView2.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheelView, int i, int i2) {
                if (wheelView.getCurrentItemValue().equals("35")) {
                    wheelView2.setCurrentItem(0, false);
                }
            }
        });
        final AlertDialog alertDialog2 = alertDialog;
        imageView2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                alertDialog2.cancel();
            }
        });
        final ThermostatProgramView thermostatProgramView3 = thermostatProgramView2;
        final AlertDialog alertDialog3 = alertDialog2;
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String access$000 = EditProgramFragment.this.getWheelValue(wheelView);
                String access$0002 = EditProgramFragment.this.getWheelValue(wheelView2);
                String access$0003 = EditProgramFragment.this.getWheelValue(wheelView3);
                String access$0004 = EditProgramFragment.this.getWheelValue(wheelView4);
                String access$0005 = EditProgramFragment.this.getWheelValue(wheelView5);
                String access$0006 = EditProgramFragment.this.getWheelValue(wheelView7);
                int parseInt = (Integer.parseInt(access$0003) * 2) + (access$0004.equals("00") ^ true ? 1 : 0);
                int parseInt2 = (Integer.parseInt(access$0005) * 2) + (access$0006.equals("00") ^ true ? 1 : 0);
                if (parseInt2 > parseInt) {
                    thermostatProgramView3.setProgramEditData(parseInt, parseInt2, Integer.parseInt(access$000), Integer.parseInt(access$0002));
                    alertDialog3.cancel();
                    return;
                }
                Toast.makeText(EditProgramFragment.this.getActivity(), C1683R.string.dialog_program_set_error, 0).show();
            }
        });
        final View view2 = view;
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) EditProgramFragment.this.llTitle.getLayoutParams();
                layoutParams.setMargins(EditProgramFragment.this.llWheel.getLeft(), layoutParams.topMargin, EditProgramFragment.this.llWheel.getLeft(), layoutParams.bottomMargin);
                EditProgramFragment.this.llTitle.setLayoutParams(layoutParams);
                Log.d("llWheel location", "left:" + EditProgramFragment.this.llWheel.getLeft() + "right:" + EditProgramFragment.this.llWheel.getRight());
                view2.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        return alertDialog2;
    }

    private void initWheel(WheelView wheelView, String[] strArr, int i) {
        wheelView.setAdapter(new StrericWheelAdapter(strArr));
        wheelView.setCurrentItem(i);
        wheelView.setCyclic(true);
        wheelView.setInterpolator(new AnticipateOvershootInterpolator());
    }

    /* access modifiers changed from: private */
    public String getWheelValue(WheelView wheelView) {
        return wheelView.getCurrentItemValue();
    }
}
