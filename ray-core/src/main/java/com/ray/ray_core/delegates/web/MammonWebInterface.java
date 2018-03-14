package com.ray.ray_core.delegates.web;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.ray.ray_core.delegates.web.event.Event;
import com.ray.ray_core.delegates.web.event.EventManager;
import com.ray.ray_core.util.log.MammonLogger;

/**
 * Created by wrf on 2018/2/9.
 */

public final class MammonWebInterface {

    private final WebDelegate webDelegate;

    private MammonWebInterface(WebDelegate webDelegate) {
        this.webDelegate = webDelegate;
    }

    public static MammonWebInterface create(WebDelegate webDelegate){
        return new MammonWebInterface(webDelegate);
    }

    @JavascriptInterface
    public String event(String params){
        MammonLogger.i("mydata","params:"+params);
        final String action = JSON.parseObject(params).getString("action");
        Event event = EventManager.getInstance().create(action);
        if(event!=null){
            event.action = action;
            event.webDelegate = webDelegate;
            event.context = webDelegate.getContext();
            event.url = webDelegate.getUrl();
            return event.execute(params);
        }
        return null;
    }
}
