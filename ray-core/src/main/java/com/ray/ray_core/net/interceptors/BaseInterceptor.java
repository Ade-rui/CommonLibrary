package com.ray.ray_core.net.interceptors;

import java.io.IOException;
import java.util.LinkedHashMap;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by wrf on 2018/1/23.
 */

public class BaseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }

    private LinkedHashMap<String,String> getUrlParameters(Chain chain){
        final HttpUrl httpUrl = chain.request().url();
        int size = httpUrl.querySize();
        final LinkedHashMap<String,String> params = new LinkedHashMap<>();
        for (int i = 0; i < size; i++) {
            params.put(httpUrl.queryParameterName(i),httpUrl.queryParameterValue(i));
        }
        return params;
    }

    private String getUrlParameters(Chain chain,String key){
        final HttpUrl httpUrl = chain.request().url();
        return httpUrl.queryParameter(key);
    }

    private LinkedHashMap<String,String> getBodyParameters(Chain chain){
        final FormBody body = (FormBody) chain.request().body();
        LinkedHashMap<String,String> params = new LinkedHashMap<>();
        int size = body.size();
        for (int i = 0; i < size; i++) {
            params.put(body.name(i),body.value(i));
        }
        return params;
    }

    private String getBodyParameters(Chain chain, String key){
        return getBodyParameters(chain).get(key);
    }
}
