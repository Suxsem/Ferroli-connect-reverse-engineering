package com.szacs.ferroliconnect.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.event.BaseEvent;
import com.szacs.ferroliconnect.event.Event;
import org.greenrobot.eventbus.EventBus;

public class SelectCameraDialog extends DialogFragment implements View.OnClickListener {
    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(C1683R.C1686layout.dialog_choose_pic_source, viewGroup);
        ((Button) inflate.findViewById(C1683R.C1685id.btFromCamera)).setOnClickListener(this);
        ((Button) inflate.findViewById(C1683R.C1685id.btFromAlbum)).setOnClickListener(this);
        return inflate;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case C1683R.C1685id.btFromAlbum:
                EventBus.getDefault().post(new BaseEvent(Event.EVENT_OPEN_ALBUM));
                break;
            case C1683R.C1685id.btFromCamera:
                EventBus.getDefault().post(new BaseEvent(Event.EVENT_OPEN_CAMERA));
                break;
        }
        dismiss();
    }
}
