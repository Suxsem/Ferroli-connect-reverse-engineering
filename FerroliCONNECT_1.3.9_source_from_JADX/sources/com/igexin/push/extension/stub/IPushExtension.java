package com.igexin.push.extension.stub;

import android.content.Context;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.PushTaskBean;
import org.json.JSONObject;

public interface IPushExtension {
    boolean executeAction(PushTaskBean pushTaskBean, BaseAction baseAction);

    boolean init(Context context);

    boolean isActionSupported(String str);

    void onDestroy();

    BaseAction parseAction(JSONObject jSONObject);

    C1294c prepareExecuteAction(PushTaskBean pushTaskBean, BaseAction baseAction);
}
