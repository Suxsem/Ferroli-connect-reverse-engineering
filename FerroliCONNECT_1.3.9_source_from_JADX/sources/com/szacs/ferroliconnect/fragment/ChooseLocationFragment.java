package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.bean.LocationGroup;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.szacs.ferroliconnect.widget.LocationPicker;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import org.greenrobot.eventbus.EventBus;
import p110io.reactivex.Observable;
import p110io.reactivex.ObservableEmitter;
import p110io.reactivex.ObservableOnSubscribe;
import p110io.reactivex.android.schedulers.AndroidSchedulers;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.schedulers.Schedulers;

public class ChooseLocationFragment extends DialogFragment {
    private static final String TAG = "ChooseLocationFragment";
    /* access modifiers changed from: private */
    public LocationGroup curLocation;
    private ImageView ivCancel;
    private ImageView ivConfirm;
    /* access modifiers changed from: private */
    public LocationPicker locationPicker;
    private ArrayList<LocationGroup> locations;
    private TextView tvTitle;

    public Dialog onCreateDialog(Bundle bundle) {
        View inflate = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.fragment_choose_address, (ViewGroup) null);
        final AlertDialog create = new AlertDialog.Builder(getActivity()).setView(inflate).create();
        this.ivCancel = (ImageView) inflate.findViewById(C1683R.C1685id.ivCancel);
        this.ivConfirm = (ImageView) inflate.findViewById(C1683R.C1685id.ivConfirm);
        this.tvTitle = (TextView) inflate.findViewById(C1683R.C1685id.tvTitle);
        this.locationPicker = (LocationPicker) inflate.findViewById(C1683R.C1685id.lp_location);
        this.locationPicker.setLanguage(LanguageUtil.getSetLanguageLocale(getActivity().getApplicationContext()).getLanguage());
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getActivity().getAssets().open("location/seniverse_city_data.json"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    break;
                }
                sb.append(readLine);
            }
            inputStreamReader.close();
            bufferedReader.close();
            this.locations = new ArrayList<>(Arrays.asList((LocationGroup[]) new Gson().fromJson(sb.toString(), LocationGroup[].class)));
            Log.d(TAG, "locations.size() = " + this.locations.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.ivCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                create.cancel();
            }
        });
        this.ivConfirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ChooseLocationFragment.this.curLocation.getGroup() == null || ChooseLocationFragment.this.curLocation.getGroup().size() <= 0) {
                    EventBus.getDefault().post(ChooseLocationFragment.this.curLocation);
                    create.cancel();
                    return;
                }
                ChooseLocationFragment chooseLocationFragment = ChooseLocationFragment.this;
                chooseLocationFragment.setData(chooseLocationFragment.curLocation.getGroup());
            }
        });
        initPicker();
        return create;
    }

    private void initPicker() {
        this.locationPicker.setOnSelectListener(new LocationPicker.onSelectListener() {
            public void onSelect(LocationGroup locationGroup) {
                LocationGroup unused = ChooseLocationFragment.this.curLocation = locationGroup;
            }
        });
        setData(this.locations);
    }

    public void show(FragmentManager fragmentManager, String str) {
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.add(this, str);
        beginTransaction.commitAllowingStateLoss();
    }

    /* access modifiers changed from: private */
    public ArrayList<LocationGroup> sort(ArrayList<LocationGroup> arrayList) {
        ArrayList<LocationGroup> arrayList2 = new ArrayList<>();
        arrayList2.addAll(arrayList);
        if (arrayList2.size() > 0) {
            Collections.sort(arrayList2, new Comparator<LocationGroup>() {
                public int compare(LocationGroup locationGroup, LocationGroup locationGroup2) {
                    if (locationGroup.getName().getEn().charAt(0) < locationGroup2.getName().getEn().charAt(0)) {
                        return -1;
                    }
                    if (locationGroup.getName().getEn().charAt(0) == locationGroup2.getName().getEn().charAt(0)) {
                        return 0;
                    }
                    return 1;
                }
            });
        }
        return arrayList2;
    }

    /* access modifiers changed from: private */
    public void setData(final ArrayList<LocationGroup> arrayList) {
        Observable.create(new ObservableOnSubscribe<ArrayList<LocationGroup>>() {
            public void subscribe(ObservableEmitter<ArrayList<LocationGroup>> observableEmitter) throws Exception {
                observableEmitter.onNext(ChooseLocationFragment.this.sort(arrayList));
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ArrayList<LocationGroup>>() {
            public void accept(ArrayList<LocationGroup> arrayList) throws Exception {
                ChooseLocationFragment.this.locationPicker.setData(arrayList);
            }
        });
    }
}
