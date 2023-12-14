package com.szacs.ferroliconnect.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class SeekbarDialog_ViewBinding implements Unbinder {
    private SeekbarDialog target;

    @UiThread
    public SeekbarDialog_ViewBinding(SeekbarDialog seekbarDialog, View view) {
        this.target = seekbarDialog;
        seekbarDialog.seekBar = (SeekBar) Utils.findRequiredViewAsType(view, C1683R.C1685id.seekbar, "field 'seekBar'", SeekBar.class);
        seekbarDialog.TvValue = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvValue, "field 'TvValue'", TextView.class);
    }

    @CallSuper
    public void unbind() {
        SeekbarDialog seekbarDialog = this.target;
        if (seekbarDialog != null) {
            this.target = null;
            seekbarDialog.seekBar = null;
            seekbarDialog.TvValue = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
