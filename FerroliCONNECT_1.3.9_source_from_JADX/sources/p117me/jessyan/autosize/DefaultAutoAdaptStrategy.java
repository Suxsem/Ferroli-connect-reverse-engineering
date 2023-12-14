package p117me.jessyan.autosize;

import android.app.Activity;
import java.util.Locale;
import p117me.jessyan.autosize.external.ExternalAdaptInfo;
import p117me.jessyan.autosize.internal.CancelAdapt;
import p117me.jessyan.autosize.internal.CustomAdapt;
import p117me.jessyan.autosize.utils.LogUtils;

/* renamed from: me.jessyan.autosize.DefaultAutoAdaptStrategy */
public class DefaultAutoAdaptStrategy implements AutoAdaptStrategy {
    public void applyAdapt(Object obj, Activity activity) {
        if (AutoSizeConfig.getInstance().getExternalAdaptManager().isRun()) {
            if (AutoSizeConfig.getInstance().getExternalAdaptManager().isCancelAdapt(obj.getClass())) {
                LogUtils.m3886w(String.format(Locale.ENGLISH, "%s canceled the adaptation!", new Object[]{obj.getClass().getName()}));
                AutoSize.cancelAdapt(activity);
                return;
            }
            ExternalAdaptInfo externalAdaptInfoOfActivity = AutoSizeConfig.getInstance().getExternalAdaptManager().getExternalAdaptInfoOfActivity(obj.getClass());
            if (externalAdaptInfoOfActivity != null) {
                LogUtils.m3884d(String.format(Locale.ENGLISH, "%s used %s for adaptation!", new Object[]{obj.getClass().getName(), ExternalAdaptInfo.class.getName()}));
                AutoSize.autoConvertDensityOfExternalAdaptInfo(activity, externalAdaptInfoOfActivity);
                return;
            }
        }
        if (obj instanceof CancelAdapt) {
            LogUtils.m3886w(String.format(Locale.ENGLISH, "%s canceled the adaptation!", new Object[]{obj.getClass().getName()}));
            AutoSize.cancelAdapt(activity);
        } else if (obj instanceof CustomAdapt) {
            LogUtils.m3884d(String.format(Locale.ENGLISH, "%s implemented by %s!", new Object[]{obj.getClass().getName(), CustomAdapt.class.getName()}));
            AutoSize.autoConvertDensityOfCustomAdapt(activity, (CustomAdapt) obj);
        } else {
            LogUtils.m3884d(String.format(Locale.ENGLISH, "%s used the global configuration.", new Object[]{obj.getClass().getName()}));
            AutoSize.autoConvertDensityOfGlobal(activity);
        }
    }
}
