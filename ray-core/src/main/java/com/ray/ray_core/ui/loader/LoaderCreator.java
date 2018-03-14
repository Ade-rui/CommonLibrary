package com.ray.ray_core.ui.loader;

import android.content.Context;
import android.text.TextUtils;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.Indicator;

import java.util.WeakHashMap;

/**
 * Created by wrf on 2018/1/22.
 */

public class LoaderCreator {

    private static final WeakHashMap<String,Indicator> MAP_LOADER = new WeakHashMap<>();

    public static AVLoadingIndicatorView create(Context context,String type){
        AVLoadingIndicatorView avLoadingIndicatorView = new AVLoadingIndicatorView(context);
        Indicator loader = MAP_LOADER.get(type);
        if(loader == null){
            loader = getIndicatorView(type);
            MAP_LOADER.put(type,loader);
        }
        avLoadingIndicatorView.setIndicator(loader);
        return avLoadingIndicatorView;
    }

    private static Indicator getIndicatorView(String type) {
        if(TextUtils.isEmpty(type)){
            return null;
        }
        StringBuilder sb = new StringBuilder(AVLoadingIndicatorView.class.getPackage().getName());
        if(!type.contains(".")){
            sb.append(".indicators")
                .append(".");
        }
        sb.append(type);
        try {
            Class<?> clazz = Class.forName(sb.toString());
            return (Indicator) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
