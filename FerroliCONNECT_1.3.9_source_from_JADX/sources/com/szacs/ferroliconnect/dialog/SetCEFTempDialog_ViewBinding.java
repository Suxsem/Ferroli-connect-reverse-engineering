package com.szacs.ferroliconnect.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class SetCEFTempDialog_ViewBinding implements Unbinder {
    private SetCEFTempDialog target;

    @UiThread
    public SetCEFTempDialog_ViewBinding(SetCEFTempDialog setCEFTempDialog, View view) {
        this.target = setCEFTempDialog;
        setCEFTempDialog.sbComf = (SeekBar) Utils.findRequiredViewAsType(view, C1683R.C1685id.sbComf, "field 'sbComf'", SeekBar.class);
        setCEFTempDialog.sbEcon = (SeekBar) Utils.findRequiredViewAsType(view, C1683R.C1685id.sbEcon, "field 'sbEcon'", SeekBar.class);
        setCEFTempDialog.sbFrost = (SeekBar) Utils.findRequiredViewAsType(view, C1683R.C1685id.sbFrost, "field 'sbFrost'", SeekBar.class);
        setCEFTempDialog.tvComf = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvComf, "field 'tvComf'", TextView.class);
        setCEFTempDialog.tvEcon = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvEcon, "field 'tvEcon'", TextView.class);
        setCEFTempDialog.tvFrost = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvFrost, "field 'tvFrost'", TextView.class);
        setCEFTempDialog.tvComfUnit = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvComfUnit, "field 'tvComfUnit'", TextView.class);
        setCEFTempDialog.tvEconUnit = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvEconUnit, "field 'tvEconUnit'", TextView.class);
        setCEFTempDialog.tvFrostUnit = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvFrostUnit, "field 'tvFrostUnit'", TextView.class);
        setCEFTempDialog.frostLayout = (RelativeLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.rlFrost, "field 'frostLayout'", RelativeLayout.class);
    }

    @CallSuper
    public void unbind() {
        SetCEFTempDialog setCEFTempDialog = this.target;
        if (setCEFTempDialog != null) {
            this.target = null;
            setCEFTempDialog.sbComf = null;
            setCEFTempDialog.sbEcon = null;
            setCEFTempDialog.sbFrost = null;
            setCEFTempDialog.tvComf = null;
            setCEFTempDialog.tvEcon = null;
            setCEFTempDialog.tvFrost = null;
            setCEFTempDialog.tvComfUnit = null;
            setCEFTempDialog.tvEconUnit = null;
            setCEFTempDialog.tvFrostUnit = null;
            setCEFTempDialog.frostLayout = null;
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
