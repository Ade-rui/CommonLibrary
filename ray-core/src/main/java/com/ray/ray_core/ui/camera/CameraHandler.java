package com.ray.ray_core.ui.camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;
import com.ray.ray_core.R;
import com.ray.ray_core.delegates.PermissionCheckerDelegate;
import com.ray.ray_core.util.file.FileUtil;

import java.io.File;

/**
 * Created by wrf on 2018/3/10.
 */

public class CameraHandler implements View.OnClickListener {

    private final AlertDialog dialog;
    private final PermissionCheckerDelegate delegate;

    public CameraHandler(PermissionCheckerDelegate delegate) {
        this.delegate = delegate;
        dialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    final void beginCameraDialog(){
        dialog.show();
        Window window = dialog.getWindow();
        if(window!= null){
            window.setContentView(R.layout.dialog_camera_panel);
            window.setWindowAnimations(R.style.dialog_from_bottom_to_up);
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            //设置属性
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            attributes.dimAmount = 0.5f;
            window.setAttributes(attributes);

            window.findViewById(R.id.bt_take_photo).setOnClickListener(this);
            window.findViewById(R.id.bt_native_photo).setOnClickListener(this);
            window.findViewById(R.id.bt_cancel).setOnClickListener(this);

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.bt_take_photo){
            takePhoto();
            dialog.cancel();
        }else if(id == R.id.bt_native_photo){
            pickPhoto();
            dialog.cancel();
        }else if(id == R.id.bt_cancel){
            dialog.cancel();
        }
    }

    private void takePhoto(){
        String fileName = getFlieName();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR,fileName);

        //兼容7.0的写法
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            final ContentValues contentValue = new ContentValues(1);
            contentValue.put(MediaStore.Images.Media.DATA,tempFile.getPath());
            Uri insert = delegate.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValue);
            //将URI转为真实路径
            String realFilePath = FileUtil.getRealFilePath(delegate.getContext(), insert);
            File fileByPath = FileUtils.getFileByPath(realFilePath);
            CameraImageBean.getInstance().setUri(Uri.fromFile(fileByPath));
            intent.putExtra(MediaStore.EXTRA_OUTPUT,CameraImageBean.getInstance().getUri());
        }else {
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setUri(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        }

        delegate.startActivityForResult(intent, RequestCodes.TAKE_PHOTO);
    }

    public String getFlieName() {
        return FileUtil.getFileNameByTime("IMG", "jpg");
    }

    private void pickPhoto(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        delegate.startActivityForResult(Intent.createChooser(intent,"选择获取图片的方式"),RequestCodes.PICK_PHOTO);
    }

}
