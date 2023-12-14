package com.igexin.push.extension.distribution.gbd.stub;

import android.content.Context;
import com.igexin.push.core.C1294c;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.core.bean.PushTaskBean;
import com.igexin.push.extension.distribution.gbd.p078d.C1494d;
import com.igexin.push.extension.distribution.gbd.p086i.C1540h;
import com.igexin.push.extension.stub.IPushExtension;
import org.json.JSONObject;

public class PushExtension implements IPushExtension {
    public boolean executeAction(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return false;
    }

    public boolean init(Context context) {
        try {
            C1540h.m2997b("GBD_PushExtension", "init gbd ...");
            C1494d.m2778a().mo15087a(context);
            return true;
        } catch (Exception e) {
            C1540h.m2996a(e);
            C1540h.m2997b("GBD_PushExtension", e.toString());
            return false;
        }
    }

    public boolean isActionSupported(String str) {
        return false;
    }

    public void onDestroy() {
        C1494d.m2778a().mo15090d();
    }

    public BaseAction parseAction(JSONObject jSONObject) {
        return null;
    }

    public C1294c prepareExecuteAction(PushTaskBean pushTaskBean, BaseAction baseAction) {
        return C1294c.stop;
    }
}
