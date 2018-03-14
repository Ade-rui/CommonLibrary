package com.ray.ray_core.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;

import com.ray.ray_core.app.ConfigType;
import com.ray.ray_core.app.Mamoon;
import com.ray.ray_core.delegates.MammonDelegate;
import com.ray.ray_core.delegates.web.route.RouteKeys;
import com.ray.ray_core.ui.loader.MamoonLoader;
import com.ray.ray_core.util.log.MammonLogger;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Created by wrf on 2018/2/9.
 */

public abstract class WebDelegate extends MammonDelegate implements IWebViewInitializer{

    private WebView webView;
    private final ReferenceQueue<WebView> WEB_VIEW_QUEUE = new ReferenceQueue<>();
    private String url;
    private boolean isWebViewAvailable;
    private MammonDelegate topDelegate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        url = args.getString(RouteKeys.URL.name());
        initWebView();
    }

    private void initWebView() {
        if(webView!=null){
            webView.removeAllViews();
            webView.destroy();
        }else {
            final IWebViewInitializer initializer = setInitializer();
            if(initializer != null){
                final WeakReference<WebView> webViewWeakReference = new WeakReference<WebView>(new WebView(getContext()),WEB_VIEW_QUEUE);
                webView = webViewWeakReference.get();
                webView = initializer.initWebView(webView);
                webView.setWebViewClient(initializer.webViewClient());
                webView.setWebChromeClient(initializer.webChromeClient());
                final String name = Mamoon.getConfigurations(ConfigType.JAVASCRIPT_INTERFACE);
                MammonLogger.i("mydata",name);
                webView.addJavascriptInterface(MammonWebInterface.create(this),name);
                isWebViewAvailable = true;
            }else {
                throw new NullPointerException("INITIALIZER IS NULL");
            }
        }
    }

    protected abstract IWebViewInitializer setInitializer();

    public void setTopDelegate(MammonDelegate delegate) {
        topDelegate = delegate;
    }

    public MammonDelegate getTopDelegate() {
        if (topDelegate == null) {
            topDelegate = this;
        }
        return topDelegate;
    }

    public WebView getWebView() {
        if(webView == null){
            throw new NullPointerException("WEBVIEW IS NULL");
        }
        return webView;
    }

    public String getUrl() {
        if(url == null){
            throw new NullPointerException("URL IS NULL");
        }

        return url;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(webView!=null){
            webView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(webView != null){
            webView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isWebViewAvailable = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(webView !=null){
            webView.removeAllViews();
            webView.destroy();
            webView = null;
        }
    }
}
