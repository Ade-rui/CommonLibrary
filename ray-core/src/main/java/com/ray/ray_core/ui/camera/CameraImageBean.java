package com.ray.ray_core.ui.camera;

import android.net.Uri;

/**
 * Created by wrf on 2018/3/10.
 */

public class CameraImageBean {

    private Uri uri;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
