package com.ray.ray_core.net;

import android.content.Context;

import com.ray.ray_core.net.callback.IError;
import com.ray.ray_core.net.callback.IFailure;
import com.ray.ray_core.net.callback.IRequest;
import com.ray.ray_core.net.callback.ISuccess;
import com.ray.ray_core.ui.loader.LoaderType;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.RequestBody;

/**
 * Created by wrf on 2018/1/19.
 */

public class RestClientBuilder {

    private  String mUrl;
    private  Map<String,Object> mParams = new WeakHashMap<>();
    private  IRequest mRequest;
    private  ISuccess mSuccess;
    private String download_dir;
    private String extension;
    private String name;
    private  IError mError;
    private  IFailure mFailure;
    private  RequestBody mBody;
    private LoaderType loader_type;
    private Context context;
    private File file;

    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        if(params!=null){
            this.mParams.putAll(params);
        }
        return this;
    }

    public final RestClientBuilder params(String key,Object value){
        this.mParams.put(key,value);
        return this;
    }

    public final RestClientBuilder request(IRequest request){
        this.mRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success){
        this.mSuccess = success;
        return this;
    }

    public final RestClientBuilder error(IError error){
        this.mError = error;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure){
        this.mFailure = failure;
        return this;
    }

    public final RestClientBuilder body(RequestBody body){
        mBody = body;
        return this;
    }

    public final RestClientBuilder loader(Context context,LoaderType type){
        this.context = context;
        this.loader_type = type;
        return this;
    }

    public final RestClientBuilder loader(Context context){
        this.context = context;
        this.loader_type = LoaderType.BallPulseIndicator;
        return this;
    }

    public final RestClientBuilder dir(String download_dir){
        this.download_dir = download_dir;
        return this;
    }

    public final RestClientBuilder extension(String extension){
        this.extension = extension;
        return this;
    }

    public final RestClientBuilder name(String name){
        this.name = name;
        return this;
    }

    public final RestClient build(){
        return new RestClient(mUrl,mParams, download_dir, extension, name, mRequest,mSuccess,mError,mFailure,mBody, loader_type, context, file);
    }
}
