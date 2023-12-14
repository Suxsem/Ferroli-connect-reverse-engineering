package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class UserInfoActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private UserInfoActivity target;

    @UiThread
    public UserInfoActivity_ViewBinding(UserInfoActivity userInfoActivity) {
        this(userInfoActivity, userInfoActivity.getWindow().getDecorView());
    }

    @UiThread
    public UserInfoActivity_ViewBinding(UserInfoActivity userInfoActivity, View view) {
        super(userInfoActivity, view);
        this.target = userInfoActivity;
        userInfoActivity.tvUsername = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvUsername, "field 'tvUsername'", TextView.class);
        userInfoActivity.tvEmail = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvEmail, "field 'tvEmail'", TextView.class);
        userInfoActivity.tvPhone = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvPhone, "field 'tvPhone'", TextView.class);
        userInfoActivity.tvAddress = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.tvAddress, "field 'tvAddress'", TextView.class);
        userInfoActivity.llLocation = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.llLocation, "field 'llLocation'", LinearLayout.class);
        userInfoActivity.llLanguageSet = (LinearLayout) Utils.findRequiredViewAsType(view, C1683R.C1685id.language_set, "field 'llLanguageSet'", LinearLayout.class);
        userInfoActivity.civPortrait = (ImageView) Utils.findRequiredViewAsType(view, C1683R.C1685id.civPortrait, "field 'civPortrait'", ImageView.class);
        userInfoActivity.languageTv = (TextView) Utils.findRequiredViewAsType(view, C1683R.C1685id.language_tv, "field 'languageTv'", TextView.class);
    }

    public void unbind() {
        UserInfoActivity userInfoActivity = this.target;
        if (userInfoActivity != null) {
            this.target = null;
            userInfoActivity.tvUsername = null;
            userInfoActivity.tvEmail = null;
            userInfoActivity.tvPhone = null;
            userInfoActivity.tvAddress = null;
            userInfoActivity.llLocation = null;
            userInfoActivity.llLanguageSet = null;
            userInfoActivity.civPortrait = null;
            userInfoActivity.languageTv = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
