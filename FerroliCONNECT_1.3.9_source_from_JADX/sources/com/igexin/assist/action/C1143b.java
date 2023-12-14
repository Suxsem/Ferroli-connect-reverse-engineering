package com.igexin.assist.action;

import android.text.TextUtils;
import com.igexin.assist.MessageBean;
import com.igexin.assist.sdk.AssistPushConsts;

/* renamed from: com.igexin.assist.action.b */
class C1143b extends Thread {

    /* renamed from: a */
    MessageBean f1525a;

    /* renamed from: b */
    final /* synthetic */ MessageManger f1526b;

    C1143b(MessageManger messageManger, MessageBean messageBean) {
        this.f1526b = messageManger;
        this.f1525a = messageBean;
    }

    public void run() {
        try {
            if (this.f1525a == null) {
                return;
            }
            if (this.f1525a.getMessageType().equals("token")) {
                this.f1526b.m1208a(this.f1525a.getContext(), this.f1525a.getStringMessage());
            } else if (this.f1525a.getMessageType().equals("payload")) {
                if (!TextUtils.isEmpty(this.f1525a.getStringMessage())) {
                    C1145d dVar = new C1145d();
                    dVar.mo14194a(this.f1525a);
                    if (dVar.mo14195a() && dVar.mo14200f().equals(AssistPushConsts.MSG_VALUE_PAYLOAD)) {
                        this.f1526b.m1212a(dVar, this.f1525a.getContext());
                    }
                }
            } else if (this.f1525a.getMessageType().equals(AssistPushConsts.MSG_TYPE_ACTIONS) && !TextUtils.isEmpty(this.f1525a.getStringMessage())) {
                C1145d dVar2 = new C1145d();
                dVar2.mo14194a(this.f1525a);
                if (dVar2.mo14195a() && dVar2.mo14200f().equals(AssistPushConsts.MSG_VALUE_PAYLOAD)) {
                    this.f1526b.m1207a(this.f1525a.getContext(), dVar2);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }
}
