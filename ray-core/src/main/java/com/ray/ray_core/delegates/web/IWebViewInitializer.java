package com.ray.ray_core.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by wrf on 2018/2/9.
 * 初始化webView接口
 */

public interface IWebViewInitializer {

    WebView initWebView(WebView webView);

    WebViewClient webViewClient();

    WebChromeClient webChromeClient();

}
