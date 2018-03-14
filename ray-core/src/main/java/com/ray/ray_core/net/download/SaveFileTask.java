package com.ray.ray_core.net.download;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.ray.ray_core.app.Mamoon;
import com.ray.ray_core.net.callback.IRequest;
import com.ray.ray_core.net.callback.ISuccess;
import com.ray.ray_core.util.file.FileUtil;

import java.io.File;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by wrf on 2018/1/22.
 */

public class SaveFileTask extends AsyncTask<Object,Void,File> {

    public static final String FILE_DEFAULT_DIR = "download_dir";

    private final IRequest request;
    private final ISuccess success;

    public SaveFileTask(IRequest request, ISuccess success) {
        this.request = request;
        this.success = success;
    }

    @Override
    protected File doInBackground(Object... params) {
        String downloadDir = (String) params[0];
        String extension = (String) params[1];
        final ResponseBody body = (ResponseBody) params[2];
        final String name = (String) params[3];
        final InputStream inputStream = body.byteStream();

        if(TextUtils.isEmpty(downloadDir)){
            downloadDir = FILE_DEFAULT_DIR;
        }
        if(TextUtils.isEmpty(extension)){
            extension = "";
        }
        if(TextUtils.isEmpty(name)){
            return FileUtil.writeToDisk(inputStream,downloadDir,extension.toUpperCase(),extension);
        }else {
            return FileUtil.writeToDisk(inputStream,downloadDir,extension);
        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (success!=null) {
            success.onSuccess(file.getPath());
        }
        if(request!=null){
            request.onRequestEnd();
        }

        autoInstallApk(file);
    }

    private void autoInstallApk(File file) {
        if (FileUtil.getExtension(file.getPath()).equals("apk")) {
            final Intent install = null;
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setAction(Intent.ACTION_VIEW);
            install.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
            Mamoon.getApplicationContext().getApplicationContext().startActivity(install);
        }
    }
}
