package com.szacs.ferroliconnect.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.p000v4.content.FileProvider;
import anetwork.channel.util.RequestConstant;
import com.szacs.ferroliconnect.util.FileUtil;
import com.szacs.ferroliconnect.util.LogUtil;
import com.szacs.ferroliconnect.viewInterface.CameraRequestView;
import java.io.File;
import java.io.IOException;

public abstract class MyCameraRequestActivity extends MyNavigationActivity implements CameraRequestView {
    static final int MY_PERMISSIONS_REQUEST_ALBUM = 2;
    static final int MY_PERMISSIONS_REQUEST_CAMERA_ALBUM = 1;
    static final int REQUEST_CODE_CROP = 1;
    static final int REQUEST_CODE_PICK = 3;
    static final int REQUEST_CODE_TAKE = 2;
    private static final String TAG = "MyCameraRequestActivity";
    private Uri cropUri;
    protected String storePath = FileUtil.CLOUDWARM_ROOT_PATH;
    private Uri uri;
    protected Uri uritempFile;

    /* access modifiers changed from: package-private */
    public abstract void setPicToView(Uri uri2);

    public void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        boolean z = true;
        for (int i2 = 0; i2 < strArr.length; i2++) {
            if (iArr[i2] != 0) {
                z = false;
            }
        }
        if (i != 1) {
            if (i == 2 && z) {
                openAlbum();
            }
        } else if (z) {
            openCamera();
        }
    }

    public void openCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.addFlags(1);
        File file = new File(this.storePath + System.currentTimeMillis() + "temp.jpg");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        LogUtil.m3315d(TAG, " FILEPROVIDER AUTHORITY = " + getPackageName() + ".provider");
        StringBuilder sb = new StringBuilder();
        sb.append("Uri: ");
        sb.append(FileProvider.getUriForFile(this, getPackageName() + ".provider", file));
        LogUtil.m3315d(TAG, sb.toString());
        if (Build.VERSION.SDK_INT >= 23) {
            this.uri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        } else {
            this.uri = Uri.fromFile(file);
        }
        for (ResolveInfo resolveInfo : getPackageManager().queryIntentActivities(intent, 65536)) {
            grantUriPermission(resolveInfo.activityInfo.packageName, this.uri, 3);
        }
        intent.putExtra("output", this.uri);
        startActivityForResult(intent, 2);
    }

    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.PICK", (Uri) null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 3);
    }

    public void cropPhoto(Uri uri2) {
        LogUtil.m3315d(TAG, "packagename: " + getPackageName());
        File file = new File(this.storePath + System.currentTimeMillis() + "cropPhoto.jpg");
        if (file.isDirectory() && !file.exists()) {
            file.mkdirs();
        }
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(1);
        intent.addFlags(2);
        intent.setDataAndType(uri2, "image/*");
        if (Build.VERSION.SDK_INT >= 23) {
            this.cropUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
        } else {
            this.cropUri = Uri.fromFile(file);
        }
        for (ResolveInfo resolveInfo : getPackageManager().queryIntentActivities(intent, 65536)) {
            grantUriPermission(resolveInfo.activityInfo.packageName, this.cropUri, 3);
        }
        intent.putExtra("output", this.cropUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("crop", RequestConstant.TRUE);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        startActivityForResult(intent, 1);
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 1) {
            setPicToView(this.cropUri);
        } else if (i == 2) {
            new File(this.storePath + "temp.jpg");
            cropPhoto(this.uri);
        } else if (i == 3) {
            try {
                cropPhoto(intent.getData());
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    }
}
