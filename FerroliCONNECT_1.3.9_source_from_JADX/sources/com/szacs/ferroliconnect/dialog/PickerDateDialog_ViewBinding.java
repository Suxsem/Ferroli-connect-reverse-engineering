package com.szacs.ferroliconnect.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.widget.wheel.WheelView;

public class PickerDateDialog_ViewBinding implements Unbinder {
    private PickerDateDialog target;

    @UiThread
    public PickerDateDialog_ViewBinding(PickerDateDialog pickerDateDialog, View view) {
        this.target = pickerDateDialog;
        pickerDateDialog.wheelView = (WheelView) Utils.findRequiredViewAsType(view, C1683R.C1685id.wheelview, "field 'wheelView'", WheelView.class);
    }

    @CallSuper
    public void unbind() {
        PickerDateDialog pickerDateDialog = this.target;
        if (pickerDateDialog != null) {
            this.target = null;
            pickerDateDialog.wheelView = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
