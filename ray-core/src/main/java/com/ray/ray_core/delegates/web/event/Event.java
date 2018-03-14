package com.ray.ray_core.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.ray.ray_core.delegates.web.WebDelegate;

/**
 * Created by wrf on 2018/2/9.
 */

public abstract class Event implements IEvent{

    public Context context;
    public String action;
    public WebDelegate webDelegate;
    public String url;
    public WebView webView;

}
