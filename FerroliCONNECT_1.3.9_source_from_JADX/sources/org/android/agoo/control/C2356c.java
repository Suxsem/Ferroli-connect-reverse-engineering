package org.android.agoo.control;

import com.taobao.accs.base.TaoBaseService;
import com.taobao.accs.utl.ALog;
import java.util.ArrayList;
import java.util.Iterator;
import org.android.agoo.common.MsgDO;

/* renamed from: org.android.agoo.control.c */
/* compiled from: Taobao */
class C2356c implements Runnable {

    /* renamed from: a */
    final /* synthetic */ AgooFactory f4073a;

    C2356c(AgooFactory agooFactory) {
        this.f4073a = agooFactory;
    }

    public void run() {
        ArrayList<MsgDO> b = this.f4073a.messageService.mo25534b();
        if (b != null && b.size() > 0) {
            ALog.m3727e("AgooFactory", "reportCacheMsg", "size", Integer.valueOf(b.size()));
            Iterator<MsgDO> it = b.iterator();
            while (it.hasNext()) {
                MsgDO next = it.next();
                if (next != null) {
                    next.isFromCache = true;
                    this.f4073a.notifyManager.report(next, (TaoBaseService.ExtraInfo) null);
                }
            }
        }
    }
}
