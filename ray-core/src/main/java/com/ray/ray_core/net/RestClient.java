package com.ray.ray_core.net;

import android.content.Context;

import com.ray.ray_core.net.callback.IError;
import com.ray.ray_core.net.callback.IFailure;
import com.ray.ray_core.net.callback.IRequest;
import com.ray.ray_core.net.callback.ISuccess;
import com.ray.ray_core.net.callback.RequestCallBack;
import com.ray.ray_core.net.download.DownLoadHandler;
import com.ray.ray_core.ui.loader.LoaderType;
import com.ray.ray_core.ui.loader.MamoonLoader;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by wrf on 2018/1/19.
 */

public class RestClient {

    private final String URL;
    private final Map<String,Object> PARAMS;
    private final String DOWNLOAD_DIR;
    private final String EXTENSION;
    private final String NAME;
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final RequestBody BODY;
    private final LoaderType LOADER_TYPE;
    private final Context CONTEXT;
    private final File FILE;

    public RestClient(String URL, Map<String, Object> PARAMS, String download_dir, String extension, String name, IRequest REQUEST, ISuccess SUCCESS, IError ERROR, IFailure FAILURE, RequestBody BODY, LoaderType loader_type, Context context, File FILE) {
        this.URL = URL;
        this.PARAMS = PARAMS;
        DOWNLOAD_DIR = download_dir;
        EXTENSION = extension;
        NAME = name;
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
        this.BODY = BODY;
        LOADER_TYPE = loader_type;
        CONTEXT = context;
        this.FILE = FILE;
    }

    public static RestClientBuilder builder(){
        return new RestClientBuilder();
    }

    public void request(HttpMethod method){
        final RestService service = RestCeator.getRestService();
        Call<String> call = null;
        if(REQUEST !=null){
            REQUEST.onRequestStart();
        }

        if(LOADER_TYPE != null){
            MamoonLoader.showDialog(CONTEXT,LOADER_TYPE);
        }

        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case POST_RAW:
                call = service.postRaw(URL,BODY);
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case PUT_RAW:
                call = service.putRaw(URL,BODY);
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            case UPLOAD:
                final RequestBody body = RequestBody.create(MediaType.parse(MultipartBody.FORM.toString()),FILE);
                final MultipartBody.Part part = MultipartBody.Part.createFormData("file",FILE.getName(),body);
                call = RestCeator.getRestService().upload(URL,part);
                break;
            default:
                break;
        }

        if(call!= null){
            call.enqueue(getRequestCallBack());
        }
    }

    private Callback<String> getRequestCallBack(){
        return new RequestCallBack(REQUEST,SUCCESS,ERROR,FAILURE, LOADER_TYPE);
    }

    public final void get(){
        request(HttpMethod.GET);
    }
    public final void post(){
        if(BODY == null){
            request(HttpMethod.POST);
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.POST_RAW);
        }
    }
    public final void put(){
        if(BODY == null){
            request(HttpMethod.PUT);
        }else {
            if(!PARAMS.isEmpty()){
                throw new RuntimeException("params must be null!");
            }
            request(HttpMethod.PUT_RAW);
        }
    }
    public final void delete(){
        request(HttpMethod.DELETE);
    }
    public final void upload(){
        request(HttpMethod.UPLOAD);
    }

    public final void download(){
        new DownLoadHandler(URL,PARAMS,REQUEST,SUCCESS,DOWNLOAD_DIR,EXTENSION,NAME,ERROR,FAILURE).handleDownLoad();
    }
}
