package com.igexin.push.extension.distribution.basic.p064f;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.igexin.push.core.bean.BaseAction;
import com.igexin.push.extension.distribution.basic.p060b.C1406a;
import com.igexin.push.extension.distribution.basic.p061c.C1413c;
import com.igexin.push.extension.distribution.basic.p061c.C1416f;
import java.io.File;
import java.io.FileOutputStream;

/* renamed from: com.igexin.push.extension.distribution.basic.f.d */
public class C1425d extends C1427f {

    /* renamed from: g */
    private String f2465g;

    /* renamed from: h */
    private BaseAction f2466h;

    /* renamed from: i */
    private int f2467i;

    /* renamed from: j */
    private C1428g f2468j;

    /* renamed from: k */
    private String f2469k;

    public C1425d(String str, String str2, String str3, BaseAction baseAction, int i, C1428g gVar) {
        super(str);
        this.f2466h = baseAction;
        this.f2465g = str3;
        this.f2467i = i;
        this.f2468j = gVar;
        this.f2469k = str2;
    }

    /* renamed from: a */
    private void m2471a(String str) {
        File file = new File(C1416f.f2440r);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(C1416f.f2440r + str + "/");
        if (!file2.exists()) {
            file2.mkdirs();
        }
    }

    /* renamed from: b */
    private void m2472b(String str) {
        int i = this.f2467i;
        if (i == 2) {
            ((C1406a) this.f2466h).mo14897l(str);
        } else if (i == 3) {
            ((C1406a) this.f2466h).mo14900m(str);
        } else if (i == 8) {
            ((C1406a) this.f2466h).mo14877e(str);
        }
    }

    /* renamed from: a */
    public void mo14974a(Exception exc) {
        C1428g gVar = this.f2468j;
        if (gVar != null) {
            gVar.mo14854a(exc);
        }
    }

    /* renamed from: a */
    public void mo14975a(byte[] bArr) {
        this.f2475f = false;
        int parseInt = Integer.parseInt(this.f2466h.getActionId());
        m2471a(this.f2465g);
        String str = C1416f.f2440r + this.f2465g + "/" + parseInt + "_" + this.f2467i + ".bin";
        FileOutputStream fileOutputStream = new FileOutputStream(str);
        Bitmap.CompressFormat compressFormat = Bitmap.CompressFormat.PNG;
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        if (decodeByteArray != null) {
            decodeByteArray.compress(compressFormat, 100, fileOutputStream);
            fileOutputStream.close();
            decodeByteArray.recycle();
            m2472b(str);
            this.f2475f = true;
            ContentValues contentValues = new ContentValues();
            contentValues.put("imageurl", this.f2469k);
            contentValues.put("imagesrc", str);
            contentValues.put("taskid", this.f2465g);
            contentValues.put("createtime", Long.valueOf(System.currentTimeMillis()));
            C1413c.m2411a().mo14942b().mo14961a("image", contentValues);
        } else {
            fileOutputStream.close();
            this.f2475f = false;
        }
        if (this.f2468j == null) {
            return;
        }
        if (this.f2475f) {
            this.f2468j.mo14853a(this.f2466h);
        } else {
            this.f2468j.mo14854a(new Exception("no target existed or downloading bitmap failed!"));
        }
    }

    /* renamed from: b */
    public final int mo14231b() {
        return 65557;
    }
}
