package com.szacs.ferroliconnect.widget;

import android.content.Context;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.util.CustomerTagHandler;
import com.szacs.ferroliconnect.util.HtmlParser;
import com.szacs.ferroliconnect.widget.TemperView;

public class TemperLayout extends RelativeLayout implements TemperView.onValueChangeListenner {
    private ImageView icon;
    private TempChangeListener tempChangeListener;
    private TextView tempTv;
    private TemperView temperView;
    private int textSize;
    private TextView tipsTv;
    private TextView unitTv;

    public interface TempChangeListener {
        void onTemperatureChange(String str);
    }

    public TemperLayout(Context context) {
        this(context, (AttributeSet) null);
    }

    public TemperLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TemperLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.textSize = 60;
        init(context);
    }

    private void init(Context context) {
        inflate(context, C1683R.C1686layout.temper_layout, this);
    }

    /* access modifiers changed from: protected */
    public void onFinishInflate() {
        super.onFinishInflate();
        this.temperView = (TemperView) findViewById(C1683R.C1685id.temperView);
        this.tempTv = (TextView) findViewById(C1683R.C1685id.tvTempSet);
        this.tipsTv = (TextView) findViewById(C1683R.C1685id.tips);
        this.icon = (ImageView) findViewById(C1683R.C1685id.ivTempSettingIcon);
        this.unitTv = (TextView) findViewById(C1683R.C1685id.unit);
        this.temperView.setOnValueChangeListener(this);
    }

    public void onValueChange(String str) {
        this.tempTv.setText(format(str, this.textSize));
    }

    public void onEndValueChange(String str) {
        TempChangeListener tempChangeListener2 = this.tempChangeListener;
        if (tempChangeListener2 != null) {
            tempChangeListener2.onTemperatureChange(str);
        }
    }

    private Spanned format(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            throw new NumberFormatException("Input must contains a .");
        } else if (str.contains(".")) {
            String[] split = str.split("\\.");
            return HtmlParser.buildSpannedText(String.format("<font size=\"%d\">%s.</font><font size=\"%d\">%s</font>", new Object[]{Integer.valueOf(i), split[0].trim(), Integer.valueOf(i / 2), split[1].trim()}), new CustomerTagHandler());
        } else {
            return HtmlParser.buildSpannedText(String.format("<font size=\"%d\">%s.</font><font size=\"%d\">0</font>", new Object[]{Integer.valueOf(i), str, Integer.valueOf(i / 2)}), new CustomerTagHandler());
        }
    }

    public void setText(String str) {
        this.tempTv.setText(format(str, this.textSize));
        this.temperView.setValue(str);
    }

    public TempChangeListener getTempChangeListener() {
        return this.tempChangeListener;
    }

    public void setTempChangeListener(TempChangeListener tempChangeListener2) {
        this.tempChangeListener = tempChangeListener2;
    }

    public void setCanTouch(boolean z) {
        this.temperView.setCanTouchAble(z);
    }

    public void setMaxValue(float f) {
        this.temperView.setMaxValue(f);
    }

    public void setMinValue(float f) {
        this.temperView.setMinValue(f);
    }
}
