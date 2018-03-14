package com.ray.ray_core.util.callback;

import java.util.WeakHashMap;

/**
 * Created by wrf on 2018/3/10.
 */

public class CallbackManager {

    private WeakHashMap<CallbackType,IGlobalCallBack> callbacks = new WeakHashMap<>();

    private static class Holder {
        private static final CallbackManager INSTANCE = new CallbackManager();
    }

    public static CallbackManager getInstance() {
        return Holder.INSTANCE;
    }

    public CallbackManager addCallback(CallbackType callbackType,IGlobalCallBack callBack){
        callbacks.put(callbackType,callBack);
        return this;
    }

    public IGlobalCallBack getCallback(CallbackType callbackType) {
        return callbacks.get(callbackType);
    }
}
