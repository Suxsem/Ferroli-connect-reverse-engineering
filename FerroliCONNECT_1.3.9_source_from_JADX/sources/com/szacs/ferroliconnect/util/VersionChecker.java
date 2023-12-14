package com.szacs.ferroliconnect.util;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.szacs.ferroliconnect.BuildConfig;
import java.io.IOException;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class VersionChecker extends AsyncTask<String, String, String> {
    private static final String TAG = "VersionChecker";
    private UpdateListener listener;
    private String newVersion;

    public interface UpdateListener {
        void OnFindViewVersion(boolean z);
    }

    /* access modifiers changed from: protected */
    public String doInBackground(String... strArr) {
        try {
            Log.d(TAG, " version checker build config APPLICATION_ID = com.szacs.ferroliconnect");
            Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=com.szacs.ferroliconnect&hl=en").timeout(3000).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6").referrer("http://www.google.com").get();
            if (document != null) {
                Iterator it = document.getElementsContainingOwnText("Current Version").iterator();
                while (it.hasNext()) {
                    Element element = (Element) it.next();
                    if (element.siblingElements() != null) {
                        Iterator it2 = element.siblingElements().iterator();
                        while (it2.hasNext()) {
                            this.newVersion = ((Element) it2.next()).text();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.newVersion;
    }

    /* access modifiers changed from: protected */
    public void onPostExecute(String str) {
        super.onPostExecute(str);
        if (!TextUtils.isEmpty(str)) {
            try {
                String replace = BuildConfig.VERSION_NAME.replace(".", "");
                String replace2 = str.replace(".", "");
                LogUtil.m3315d(TAG, String.format("Current: %s , Webversion: %s", new Object[]{replace, replace2}));
                if (Integer.valueOf(replace).intValue() >= Integer.valueOf(replace2).intValue() || this.listener == null) {
                    this.listener.OnFindViewVersion(false);
                } else {
                    this.listener.OnFindViewVersion(true);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }

    public UpdateListener getListener() {
        return this.listener;
    }

    public void setListener(UpdateListener updateListener) {
        this.listener = updateListener;
    }
}
