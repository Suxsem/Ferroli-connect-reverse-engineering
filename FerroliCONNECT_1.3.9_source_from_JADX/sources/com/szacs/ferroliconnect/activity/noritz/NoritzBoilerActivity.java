package com.szacs.ferroliconnect.activity.noritz;

import android.content.Intent;
import android.support.p003v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.activity.BoilerActivity;
import com.szacs.ferroliconnect.activity.GatewayInfoActivity;

public class NoritzBoilerActivity extends BoilerActivity {
    protected ImageView ivDHWSet;

    /* access modifiers changed from: protected */
    public void initToolbar() {
        super.initToolbar();
        this.myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == C1683R.C1685id.muGatewayInfo) {
                    Intent intent = new Intent();
                    intent.setClass(NoritzBoilerActivity.this, GatewayInfoActivity.class);
                    NoritzBoilerActivity.this.startActivity(intent);
                    return false;
                } else if (itemId != C1683R.C1685id.muTechnical) {
                    return false;
                } else {
                    Intent intent2 = new Intent();
                    intent2.setClass(NoritzBoilerActivity.this, NoritzBoilerTechnicalActivity.class);
                    NoritzBoilerActivity.this.startActivity(intent2);
                    return false;
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void initWidget() {
        super.initWidget();
        this.ivDHWSet = (ImageView) findViewById(C1683R.C1685id.ivDHWSet);
        this.ivDHWSet.setVisibility(4);
    }

    /* access modifiers changed from: protected */
    public void initWidgetFunction() {
        super.initWidgetFunction();
    }
}
