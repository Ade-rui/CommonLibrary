package com.ray.ray_core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ray.ray_core.app.Mamoon;
import com.ray.ray_core.delegates.web.IPageLoadListener;
import com.ray.ray_core.delegates.web.WebDelegate;
import com.ray.ray_core.delegates.web.route.Router;
import com.ray.ray_core.ui.loader.MamoonLoader;
import com.ray.ray_core.util.log.MammonLogger;

/**
 * Created by wrf on 2018/2/9.
 */

public class WebViewClientImpl extends WebViewClient {

    private final WebDelegate delegate;
    private IPageLoadListener pageLoadListener;
    private static final Handler handler = Mamoon.getHandler();

    public WebViewClientImpl(WebDelegate delegate) {
        this.delegate = delegate;
    }

    public void setPageLoadListener(IPageLoadListener pageLoadListener) {
        this.pageLoadListener = pageLoadListener;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return Router.getInstance().handleWebUrl(delegate,url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(pageLoadListener != null){
            pageLoadListener.onLoadStart();
        }
        MamoonLoader.showDialog(view.getContext());
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if(pageLoadListener != null){
            pageLoadListener.onLoadEnd();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                MamoonLoader.stopDialog();
            }
        },1000);
    }
}
