package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.viewInterface.BoilerTechnicalView;
import java.util.HashMap;
import java.util.List;

public class AlarmRecordFragment extends DialogFragment {
    private BoilerTechnicalView boilerTechnicalView;
    private AlertDialog dialog;
    private List<HashMap<String, Object>> list;
    private ListAdapter listAdapter;
    private ListView lvAlarmRecord;
    private TextView tvAlarmMsg;
    private View view;

    public Dialog onCreateDialog(Bundle bundle) {
        this.view = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_alarm_record, (ViewGroup) null);
        this.dialog = new AlertDialog.Builder(getActivity()).setView(this.view).create();
        this.boilerTechnicalView = (BoilerTechnicalView) getActivity();
        this.lvAlarmRecord = (ListView) this.view.findViewById(C1683R.C1685id.lvAlarmRecord);
        this.tvAlarmMsg = (TextView) this.view.findViewById(C1683R.C1685id.tvAlarmMsg);
        return this.dialog;
    }

    public void initListView() {
        this.tvAlarmMsg.setVisibility(0);
        this.lvAlarmRecord.setVisibility(8);
        this.tvAlarmMsg.setText(getString(C1683R.string.boiler_tech_no_alarm_record));
    }
}
