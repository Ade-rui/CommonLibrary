package com.ray.ray_core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ProviderInfo;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.ray.ray_core.delegates.MammonDelegate;
import com.ray.ray_core.delegates.web.WebDelegate;
import com.ray.ray_core.delegates.web.WebDelegateImpl;

import java.net.URL;

/**
 * Created by wrf on 2018/2/9.
 */

public class Router {

    private Router() {
    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance() {
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(WebDelegate delegate,String url){

        if(url.contains("tel:")){
            callPhone(delegate.getContext(),url);
            return true;
        }

        MammonDelegate topDelegate = delegate.getTopDelegate();
        WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.getSupportDelegate().start(webDelegate);

        return true;
    }

    private void callPhone(Context context, String url) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(url);
        intent.setData(data);
        ContextCompat.startActivities(context,new Intent[]{intent},null);
    }

    private void loadWebPage(WebView webView,String url){
        if(webView != null){
            webView.loadUrl(url);
        }else {
            throw new NullPointerException("WEBVIEW IS NULL");
        }
    }

    private void loadLocalPage(WebView webView,String url){
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView,String url){
        if(URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)){
            loadWebPage(webView,url);
        }else {
            loadLocalPage( webView,url);
        }
    }

    public final void loadPage(WebDelegate webDelegate,String url){
        loadPage(webDelegate.getWebView(),url);
    }

}
