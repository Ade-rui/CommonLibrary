package com.ray.ray_core.net.download;

import com.ray.ray_core.net.RestCeator;
import com.ray.ray_core.net.callback.IError;
import com.ray.ray_core.net.callback.IFailure;
import com.ray.ray_core.net.callback.IRequest;
import com.ray.ray_core.net.callback.ISuccess;

import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wrf on 2018/1/22.
 */

public class DownLoadHandler {
    private  String mUrl;
    private Map<String,Object> mParams = new WeakHashMap<>();
    private IRequest mRequest;
    private ISuccess mSuccess;
    private String download_dir;
    private String extension;
    private String name;
    private IError mError;
    private IFailure mFailure;

    public DownLoadHandler(String mUrl, Map<String, Object> mParams, IRequest mRequest, ISuccess mSuccess, String download_dir, String extension, String name, IError mError, IFailure mFailure) {
        this.mUrl = mUrl;
        this.mParams = mParams;
        this.mRequest = mRequest;
        this.mSuccess = mSuccess;
        this.download_dir = download_dir;
        this.extension = extension;
        this.name = name;
        this.mError = mError;
        this.mFailure = mFailure;
    }

    public void handleDownLoad(){
        if(mRequest!=null){
            mRequest.onRequestStart();
        }

        RestCeator.getRestService().download(mUrl,mParams)
        .enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
