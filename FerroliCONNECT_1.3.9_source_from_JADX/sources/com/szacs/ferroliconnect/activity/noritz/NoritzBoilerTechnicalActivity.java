package com.szacs.ferroliconnect.activity.noritz;

import android.os.Bundle;
import android.support.p003v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.activity.BoilerTechnicalActivity;
import com.szacs.ferroliconnect.widget.wheel.WheelView;

public class NoritzBoilerTechnicalActivity extends BoilerTechnicalActivity {
    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C1683R.C1685id.llAlarmCode) {
            this.alarmRecordFragment.show(getFragmentManager(), "alarmRecordFragment");
        } else if (id == C1683R.C1685id.llMaxHeatingSetPoint) {
            View inflate = LayoutInflater.from(this).inflate(C1683R.C1686layout.dialog_adjust_max_heating_target_temp, (ViewGroup) null);
            final AlertDialog show = new AlertDialog.Builder(this).setView(inflate).show();
            initWheel((WheelView) inflate.findViewById(C1683R.C1685id.passw_1), new String[]{"40", "45", "50", "55", "60", "65", "70", "75", "80"}, -1);
            ((ImageView) inflate.findViewById(C1683R.C1685id.ivCancel)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    show.cancel();
                }
            });
            ((ImageView) inflate.findViewById(C1683R.C1685id.ivConfirm)).setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    show.cancel();
                }
            });
        }
    }
}
