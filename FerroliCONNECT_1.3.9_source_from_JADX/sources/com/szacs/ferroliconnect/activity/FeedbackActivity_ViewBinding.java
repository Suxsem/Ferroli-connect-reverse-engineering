package com.szacs.ferroliconnect.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import butterknife.internal.Utils;
import com.szacs.ferroliconnect.C1683R;

public class FeedbackActivity_ViewBinding extends MyNavigationActivity_ViewBinding {
    private FeedbackActivity target;

    @UiThread
    public FeedbackActivity_ViewBinding(FeedbackActivity feedbackActivity) {
        this(feedbackActivity, feedbackActivity.getWindow().getDecorView());
    }

    @UiThread
    public FeedbackActivity_ViewBinding(FeedbackActivity feedbackActivity, View view) {
        super(feedbackActivity, view);
        this.target = feedbackActivity;
        feedbackActivity.etFeedback = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.etFeedback, "field 'etFeedback'", EditText.class);
        feedbackActivity.etPhone = (EditText) Utils.findRequiredViewAsType(view, C1683R.C1685id.etPhone, "field 'etPhone'", EditText.class);
    }

    public void unbind() {
        FeedbackActivity feedbackActivity = this.target;
        if (feedbackActivity != null) {
            this.target = null;
            feedbackActivity.etFeedback = null;
            feedbackActivity.etPhone = null;
            super.unbind();
            return;
        }
        throw new IllegalStateException("Bindings already cleared.");
    }
}
