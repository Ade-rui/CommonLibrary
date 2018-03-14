package com.ray.ray_core.delegates.web;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ray.ray_core.app.ConfigType;
import com.ray.ray_core.delegates.web.chromeclient.WebChromeClientImple;
import com.ray.ray_core.delegates.web.client.WebViewClientImpl;
import com.ray.ray_core.delegates.web.route.RouteKeys;
import com.ray.ray_core.delegates.web.route.Router;

/**
 * Created by wrf on 2018/2/9.
 */

public class WebDelegateImpl extends WebDelegate {

    private IPageLoadListener mIPageLoadListener = null;

    public static WebDelegateImpl create(String url){
        final Bundle bundle = new Bundle();
        bundle.putString(RouteKeys.URL.name(),url);
        final WebDelegateImpl webDelegate = new WebDelegateImpl();
        webDelegate.setArguments(bundle);
        return webDelegate;
    }

    @Override
    protected IWebViewInitializer setInitializer() {
        return this;
    }

    @Override
    public Object setLayout() {
        return getWebView();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        if (getUrl() != null) {
            //用原生的方式模拟web跳转并进行页面加载
            Router.getInstance().loadPage(this,getUrl());
        }
    }

    public void setmIPageLoadListener(IPageLoadListener mIPageLoadListener) {
        this.mIPageLoadListener = mIPageLoadListener;
    }

    @Override
    public WebView initWebView(WebView webView) {
        return new WebViewInitializer().createWebView(webView);
    }

    @Override
    public WebViewClient webViewClient() {
        final WebViewClientImpl webViewClient = new WebViewClientImpl(this);
        webViewClient.setPageLoadListener(mIPageLoadListener);
        return webViewClient;
    }

    @Override
    public WebChromeClient webChromeClient() {
        return new WebChromeClientImple();
    }
}
