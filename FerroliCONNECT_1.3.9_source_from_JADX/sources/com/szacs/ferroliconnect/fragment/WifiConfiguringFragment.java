package com.szacs.ferroliconnect.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.widget.WaterWaveView;

public class WifiConfiguringFragment extends Fragment {
    private WaterWaveView wwvConfiguring;

    @Nullable
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(C1683R.C1686layout.fragment_wifi_configuring, viewGroup, false);
        this.wwvConfiguring = (WaterWaveView) inflate.findViewById(C1683R.C1685id.wwvConfiguring);
        this.wwvConfiguring.setWaveColor(getResources().getColor(C1683R.color.ferroli_green));
        return inflate;
    }
}
