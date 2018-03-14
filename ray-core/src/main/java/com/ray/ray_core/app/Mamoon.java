package com.ray.ray_core.app;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
 * Created by wrf on 2018/1/17.
 */

public final class Mamoon {

    public static Configurator init(Context context){
        getConfigurator()
                .getRay_CONFIGS()
                .put(ConfigType.APPLICATION_CONTEXT,context.getApplicationContext());
        return getConfigurator();
    }

    public static Configurator getConfigurator(){
        return Configurator.getInstance();
    }

    public static <T> T getConfigurations(Object key){
        return getConfigurator().getConfiguration(key);
    }

    public static Context getApplicationContext(){
        return getConfigurations(ConfigType.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfigurations(ConfigType.HANDLER);
    }


}
