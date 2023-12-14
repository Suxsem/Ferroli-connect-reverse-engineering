package com.szacs.ferroliconnect.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
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

public class ChooseLocationDialog extends Dialog implements View.OnClickListener {
    private static final String TAG = "ChooseLocationDialog";
    /* access modifiers changed from: private */
    public LocationGroup curLocation;
    private ImageView ivCancel;
    private ImageView ivConfirm;
    /* access modifiers changed from: private */
    public LocationPicker locationPicker;
    private ArrayList<LocationGroup> locations;
    private TextView tvTitle;

    public ChooseLocationDialog(@NonNull Context context) {
        super(context);
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1683R.C1686layout.fragment_choose_address);
        this.ivCancel = (ImageView) findViewById(C1683R.C1685id.ivCancel);
        this.ivConfirm = (ImageView) findViewById(C1683R.C1685id.ivConfirm);
        this.tvTitle = (TextView) findViewById(C1683R.C1685id.tvTitle);
        this.locationPicker = (LocationPicker) findViewById(C1683R.C1685id.lp_location);
        this.ivCancel.setOnClickListener(this);
        this.ivConfirm.setOnClickListener(this);
        this.locationPicker.setLanguage(LanguageUtil.getSetLanguageLocale(getContext()).getLanguage());
        parseLocationJson();
        initPicker();
    }

    private void parseLocationJson() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(getContext().getAssets().open("location/seniverse_city_data.json"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                } else {
                    inputStreamReader.close();
                    bufferedReader.close();
                    this.locations = new ArrayList<>(Arrays.asList((LocationGroup[]) new Gson().fromJson(sb.toString(), LocationGroup[].class)));
                    Log.d(TAG, "locations.size() = " + this.locations.size());
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View view) {
        int id = view.getId();
        if (id == C1683R.C1685id.ivCancel) {
            dismiss();
        } else if (id == C1683R.C1685id.ivConfirm) {
            if (this.curLocation.getGroup() == null || this.curLocation.getGroup().size() <= 0) {
                EventBus.getDefault().post(this.curLocation);
                dismiss();
                return;
            }
            setData(this.curLocation.getGroup());
        }
    }

    private void initPicker() {
        this.locationPicker.setOnSelectListener(new LocationPicker.onSelectListener() {
            public void onSelect(LocationGroup locationGroup) {
                LocationGroup unused = ChooseLocationDialog.this.curLocation = locationGroup;
            }
        });
        setData(this.locations);
    }

    private void setData(final ArrayList<LocationGroup> arrayList) {
        Observable.create(new ObservableOnSubscribe<ArrayList<LocationGroup>>() {
            public void subscribe(ObservableEmitter<ArrayList<LocationGroup>> observableEmitter) throws Exception {
                observableEmitter.onNext(ChooseLocationDialog.this.sort(arrayList));
            }
        }).subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ArrayList<LocationGroup>>() {
            public void accept(ArrayList<LocationGroup> arrayList) throws Exception {
                ChooseLocationDialog.this.locationPicker.setData(arrayList);
            }
        });
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
}
