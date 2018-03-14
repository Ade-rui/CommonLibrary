package com.ray.ray_core.net;

import android.support.v4.media.MediaMetadataCompat;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by wrf on 2018/1/19.
 */

public interface RestService {

    @GET
    Call<String> get(@Url String url, @QueryMap Map<String,Object> params);

    @FormUrlEncoded
    @POST
    Call<String>post(@Url String url, @FieldMap Map<String,Object> params);

    @POST
    Call<String>postRaw(@Url String url, @Body RequestBody body);

    @FormUrlEncoded
    @PUT
    Call<String>put(@Url String url, @FieldMap Map<String,Object> params);

    @PUT
    Call<String>putRaw(@Url String url, @Body RequestBody body);

    @DELETE
    Call<String>delete(@Url String url,@FieldMap Map<String,Object> params);

    @Streaming //retrofit默认把所有数据写入到内存中，这样会导致内存溢出的，streaming会让他变为下载一点写入文件一点
    @GET
    Call<ResponseBody>download(@Url String url,@QueryMap Map<String,Object> params);

    @Multipart
    @POST
    Call<String>upload(@Url String url, @Part MultipartBody.Part body);
}
