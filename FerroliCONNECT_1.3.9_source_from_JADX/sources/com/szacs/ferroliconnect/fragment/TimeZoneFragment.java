package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.event.TimeZoneEvent;
import com.szacs.ferroliconnect.util.ACache;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.greenrobot.eventbus.EventBus;

public class TimeZoneFragment extends DialogFragment {
    private static final String TAG = "TimeZoneFragment";
    /* access modifiers changed from: private */
    public CheckBox cbAutoDaylight;
    private AlertDialog dialog;
    private boolean inDaylight;
    private boolean isDayLight = true;
    private String timeZone;
    private TextView tvTitle;
    private View view;

    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        if (bundle != null) {
            this.isDayLight = bundle.getBoolean("is_daylight");
        }
    }

    public Dialog onCreateDialog(Bundle bundle) {
        this.view = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_time_zone, (ViewGroup) null);
        this.cbAutoDaylight = (CheckBox) this.view.findViewById(C1683R.C1685id.cbAutoDaylight);
        this.tvTitle = (TextView) this.view.findViewById(C1683R.C1685id.tvTitle);
        TimeZone timeZone2 = TimeZone.getDefault();
        int rawOffset = (timeZone2.getRawOffset() / 1000) / ACache.TIME_HOUR;
        int rawOffset2 = timeZone2.getRawOffset() / 1000;
        long currentTimeMillis = System.currentTimeMillis() / 1000;
        this.cbAutoDaylight.setChecked(this.isDayLight);
        Log.d(TAG, " SYNC TimeZone isDayLight = " + this.isDayLight + " 时区tz = " + rawOffset);
        this.dialog = new AlertDialog.Builder(getActivity(), C1683R.style.mAlertDialog).setView(this.view).setPositiveButton(C1683R.string.public_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                EventBus.getDefault().post(new TimeZoneEvent(TimeZoneFragment.this.getTime(), TimeZoneFragment.this.cbAutoDaylight.isChecked()));
            }
        }).setNegativeButton(C1683R.string.public_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        }).create();
        return this.dialog;
    }

    public void show(FragmentManager fragmentManager, String str) {
        super.show(fragmentManager, str);
    }

    /* access modifiers changed from: private */
    public int getTime() {
        TimeZone timeZone2 = TimeZone.getDefault();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone2.getID()));
        String format = simpleDateFormat.format(new Date());
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (date.getTime() / 1000);
    }
}
