package com.ray.ray_core.net.callback;

import android.os.Handler;

import com.ray.ray_core.ui.loader.LoaderType;
import com.ray.ray_core.ui.loader.MamoonLoader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by wrf on 2018/1/19.
 */

public class RequestCallBack implements Callback<String> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IError ERROR;
    private final IFailure FAILURE;
    private final LoaderType LOADER_TYPE;

    public RequestCallBack(IRequest REQUEST, ISuccess SUCCESS, IError ERROR, IFailure FAILURE, LoaderType LOADER_TYPE) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
        this.LOADER_TYPE = LOADER_TYPE;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful()){
            if (call.isExecuted()) {
                if(SUCCESS != null){
                        SUCCESS.onSuccess(response.body());
                }
            }
        }else {
            if(ERROR != null){
                ERROR.onError(response.code(),response.message());
            }
        }

        stopDialog();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(FAILURE != null){
            FAILURE.onFailure();
        }

        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }

        stopDialog();
    }

    private void stopDialog(){
        if(LOADER_TYPE!=null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    MamoonLoader.stopDialog();
                }
            },2000);
        }
    }
}
