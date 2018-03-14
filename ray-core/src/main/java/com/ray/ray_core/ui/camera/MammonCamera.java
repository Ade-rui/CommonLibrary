package com.ray.ray_core.ui.camera;

import android.net.Uri;

import com.ray.ray_core.delegates.PermissionCheckerDelegate;
import com.ray.ray_core.util.file.FileUtil;

/**
 * Created by wrf on 2018/3/10.
 */

public class MammonCamera {

    public static Uri createCropFile(){
        return Uri.parse(FileUtil.createFile("crop_image",FileUtil.getFileNameByTime("IMG","jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate){
        new CameraHandler(delegate).beginCameraDialog();
    }

}
