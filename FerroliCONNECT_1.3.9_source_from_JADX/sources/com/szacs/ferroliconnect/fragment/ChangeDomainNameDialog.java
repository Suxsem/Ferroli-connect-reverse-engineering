package com.szacs.ferroliconnect.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import com.p107tb.appyunsdk.AppYunManager;
import com.szacs.ferroliconnect.C1683R;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.adapter.ChangeDomainNameAdapter;
import com.szacs.ferroliconnect.bean.ServerBean;
import java.util.ArrayList;

public class ChangeDomainNameDialog extends DialogFragment {
    private static final String FLAG_EU = "eu";
    private static final String FLAG_NEW_EU = "new_eu";
    private static final String FLAG_TEST = "test";
    private static final String FLAG_ZH = "zh";
    private static final String SERVER_EU = "https://www.eu.topaiyun.com/";
    private static final String SERVER_NEW_EU = "https://eu-api.topband-cloud.com/";
    private static final String SERVER_TEST = "https://spider.topband-cloud.com:543/";
    private static final String SERVER_ZH = "https://topaiyun.com/";
    private static final String SLUG_EU = "d970e7bd-35f6-46ee-8943-c32c77bc67aa";
    private static final String SLUG_NEW_EU = "d970e7bd-35f6-46ee-8943-c32c77bc67aa";
    private static final String SLUG_TEST = "d22fffb4-e54f-4c90-88e0-87247eedd789";
    private static final String SLUG_ZH = "9c6aabc6-b588-44d4-84a4-a44e45037aa7";
    /* access modifiers changed from: private */
    public ArrayList<ServerBean> arrayList;
    private ChangeDomainNameAdapter changeDomainNameAdapter;
    private String curServerDomainName = "";
    private String curSlug = "";
    private AlertDialog dialog;
    private boolean isDebug = false;

    /* renamed from: sp */
    private Spinner f3145sp;
    private View view;

    public Dialog onCreateDialog(Bundle bundle) {
        this.view = getActivity().getLayoutInflater().inflate(C1683R.C1686layout.dialog_change_domain, (ViewGroup) null);
        initSp();
        bindListener();
        this.dialog = new AlertDialog.Builder(getActivity(), C1683R.style.mAlertDialog).setTitle(C1683R.string.dialog_change_domain_name).setView(this.view).setPositiveButton(C1683R.string.public_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                ChangeDomainNameDialog.this.confirm();
                dialogInterface.dismiss();
            }
        }).setNegativeButton(C1683R.string.public_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create();
        return this.dialog;
    }

    private void bindListener() {
        this.f3145sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long j) {
                ChangeDomainNameDialog changeDomainNameDialog = ChangeDomainNameDialog.this;
                changeDomainNameDialog.switchServer((ServerBean) changeDomainNameDialog.arrayList.get(i));
            }
        });
    }

    private void initSp() {
        this.f3145sp = (Spinner) this.view.findViewById(C1683R.C1685id.sp_select_domain);
        this.arrayList = new ArrayList<>();
        ServerBean serverBean = new ServerBean();
        serverBean.setFlag(FLAG_EU);
        serverBean.setServerName(getResources().getString(C1683R.string.public_server_eu));
        ServerBean serverBean2 = new ServerBean();
        serverBean2.setFlag(FLAG_NEW_EU);
        serverBean2.setServerName(getResources().getString(C1683R.string.public_server_new_eu));
        ServerBean serverBean3 = new ServerBean();
        serverBean3.setFlag("zh");
        serverBean3.setServerName(getResources().getString(C1683R.string.public_server_zh));
        ServerBean serverBean4 = new ServerBean();
        serverBean4.setFlag("test");
        serverBean4.setServerName(getResources().getString(C1683R.string.public_server_test));
        this.arrayList.add(serverBean3);
        this.arrayList.add(serverBean);
        this.arrayList.add(serverBean2);
        this.arrayList.add(serverBean4);
        this.changeDomainNameAdapter = new ChangeDomainNameAdapter(getActivity(), this.arrayList);
        this.f3145sp.setAdapter(this.changeDomainNameAdapter);
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007c  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void switchServer(com.szacs.ferroliconnect.bean.ServerBean r7) {
        /*
            r6 = this;
            java.lang.String r7 = r7.getFlag()
            int r0 = r7.hashCode()
            r1 = -1048845393(0xffffffffc17be3af, float:-15.743087)
            r2 = 3
            r3 = 2
            r4 = 0
            r5 = 1
            if (r0 == r1) goto L_0x003d
            r1 = 3248(0xcb0, float:4.551E-42)
            if (r0 == r1) goto L_0x0033
            r1 = 3886(0xf2e, float:5.445E-42)
            if (r0 == r1) goto L_0x0029
            r1 = 3556498(0x364492, float:4.983715E-39)
            if (r0 == r1) goto L_0x001f
            goto L_0x0047
        L_0x001f:
            java.lang.String r0 = "test"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0047
            r7 = 2
            goto L_0x0048
        L_0x0029:
            java.lang.String r0 = "zh"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0047
            r7 = 0
            goto L_0x0048
        L_0x0033:
            java.lang.String r0 = "eu"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0047
            r7 = 1
            goto L_0x0048
        L_0x003d:
            java.lang.String r0 = "new_eu"
            boolean r7 = r7.equals(r0)
            if (r7 == 0) goto L_0x0047
            r7 = 3
            goto L_0x0048
        L_0x0047:
            r7 = -1
        L_0x0048:
            if (r7 == 0) goto L_0x007c
            java.lang.String r0 = "d970e7bd-35f6-46ee-8943-c32c77bc67aa"
            if (r7 == r5) goto L_0x006f
            if (r7 == r3) goto L_0x0060
            if (r7 == r2) goto L_0x0053
            goto L_0x0088
        L_0x0053:
            java.lang.String r7 = "https://eu-api.topband-cloud.com/"
            com.szacs.ferroliconnect.MainApplication.baseUrl = r7
            r6.curServerDomainName = r7
            com.szacs.ferroliconnect.MainApplication.appSlug = r0
            r6.curSlug = r0
            r6.isDebug = r4
            goto L_0x0088
        L_0x0060:
            java.lang.String r7 = "https://spider.topband-cloud.com:543/"
            com.szacs.ferroliconnect.MainApplication.baseUrl = r7
            r6.curServerDomainName = r7
            java.lang.String r7 = "d22fffb4-e54f-4c90-88e0-87247eedd789"
            com.szacs.ferroliconnect.MainApplication.appSlug = r7
            r6.curSlug = r7
            r6.isDebug = r5
            goto L_0x0088
        L_0x006f:
            java.lang.String r7 = "https://www.eu.topaiyun.com/"
            com.szacs.ferroliconnect.MainApplication.baseUrl = r7
            r6.curServerDomainName = r7
            com.szacs.ferroliconnect.MainApplication.appSlug = r0
            r6.curSlug = r0
            r6.isDebug = r4
            goto L_0x0088
        L_0x007c:
            java.lang.String r7 = "https://topaiyun.com/"
            r6.curServerDomainName = r7
            java.lang.String r7 = "9c6aabc6-b588-44d4-84a4-a44e45037aa7"
            com.szacs.ferroliconnect.MainApplication.appSlug = r7
            r6.curSlug = r7
            r6.isDebug = r5
        L_0x0088:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.szacs.ferroliconnect.fragment.ChangeDomainNameDialog.switchServer(com.szacs.ferroliconnect.bean.ServerBean):void");
    }

    /* access modifiers changed from: private */
    public void confirm() {
        AppYunManager.getInstance().setUrl(this.curServerDomainName);
        AppYunManager.getInstance().setAppSlug(this.curSlug);
        AppYunManager.getInstance().setUseSsl(!this.isDebug);
        MainApplication.setBaseUrl(this.curServerDomainName);
        MainApplication.setAppSlug(this.curSlug);
        MainApplication.setbDebug(this.isDebug);
    }
}
