package com.szacs.ferroliconnect.viewInterface;

import android.net.Uri;

public interface CameraRequestView {
    void cropPhoto(Uri uri);

    void openAlbum();

    void openCamera();
}
