package com.ray.ray_core.delegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.ray.ray_core.ui.camera.CameraImageBean;
import com.ray.ray_core.ui.camera.MammonCamera;
import com.ray.ray_core.ui.camera.RequestCodes;
import com.ray.ray_core.util.callback.CallbackManager;
import com.ray.ray_core.util.callback.CallbackType;
import com.ray.ray_core.util.callback.IGlobalCallBack;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created by wrf on 2018/1/18.
 */
@RuntimePermissions
public abstract class PermissionCheckerDelegate extends BaseDelegate {

    @NeedsPermission(value = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void startCamera(){
        MammonCamera.start(this);
    }

    public void startCameraWithCheck(){
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithPermissionCheck(this);
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionCheckerDelegatePermissionsDispatcher.onRequestPermissionsResult(this,requestCode,grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case RequestCodes.TAKE_PHOTO:
                    UCrop.of(CameraImageBean.getInstance().getUri(),CameraImageBean.getInstance().getUri())
                            .withMaxResultSize(400,400)
                            .start(getContext(),this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if(data != null){
                        Uri data1 = data.getData();
                        //从相册选择后需要有个路径存放剪裁过的图片
                        final String pickCropPath = MammonCamera.createCropFile().getPath();
                        UCrop.of(data1, Uri.parse(pickCropPath))
                                .withMaxResultSize(400, 400)
                                .start(getContext(), this);
                    }
                    break;
                case RequestCodes.CROP_PHOTO:
                    Uri output = UCrop.getOutput(data);
                    IGlobalCallBack callback = CallbackManager.getInstance().getCallback(CallbackType.ON_CROP);
                    if(callback != null){
                        callback.executeCallback(output);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
