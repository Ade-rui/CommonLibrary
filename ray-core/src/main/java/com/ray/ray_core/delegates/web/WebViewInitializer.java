package com.ray.ray_core.delegates.web;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.ray.ray_core.app.ConfigType;
import com.ray.ray_core.app.Mamoon;

/**
 * Created by wrf on 2018/2/9.
 */

public class WebViewInitializer  {

    @SuppressLint("SetJavaScriptEnabled")
    public WebView createWebView(WebView webView){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            webView.setWebContentsDebuggingEnabled(true);
        }
        //隐藏横向滚动轴
        webView.setHorizontalScrollBarEnabled(false);
        //隐藏竖向滚动轴
        webView.setVerticalScrollBarEnabled(false);
        //允许截图
        webView.setDrawingCacheEnabled(true);
        //屏蔽长按事件
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        //初始化websetting
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        String userAgentString = settings.getUserAgentString();
        settings.setUserAgentString(userAgentString + Mamoon.getConfigurations(ConfigType.JAVASCRIPT_INTERFACE));
        //隐藏缩放控件
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        //禁止缩放
        settings.setSupportZoom(false);
        //文件权限
        settings.setAllowFileAccess(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }

        settings.setAllowContentAccess(true);

        //缓存相关
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        return webView;
    }

}
