package com.ray.ray_core.app;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by wrf on 2018/1/17.
 */

public class Configurator {

    private static final HashMap<Object,Object> Ray_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTOR = new ArrayList<>();
    private static final Handler HANDLER = new Handler();

    private Configurator() {
        Ray_CONFIGS.put(ConfigType.CONFIG_READY.name(),false);
        Ray_CONFIGS.put(ConfigType.HANDLER,HANDLER);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    private static final class Holder{
        public static final Configurator INSTANCE = new Configurator();
    }

    public final void configure(){
        initIcons();
        Ray_CONFIGS.put(ConfigType.CONFIG_READY,true);
    }

    public final HashMap<Object, Object> getRay_CONFIGS() {
        return Ray_CONFIGS;
    }

    public final Configurator withApiHost(String host){
        Ray_CONFIGS.put(ConfigType.API_HOST,host);
        return this;
    }

    private void initIcons(){
        if(ICONS.size()>0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for(int i=1;i<ICONS.size();i++){
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTOR.add(interceptor);
        Ray_CONFIGS.put(ConfigType.INTERCEPTOR,INTERCEPTOR);
        return this;
    }

    public final Configurator withInterceptor(ArrayList<Interceptor> list){
        INTERCEPTOR.addAll(list);
        Ray_CONFIGS.put(ConfigType.INTERCEPTOR,INTERCEPTOR);
        return this;
    }

    public final Configurator withWeChatAppId(String weChatAppId){
        Ray_CONFIGS.put(ConfigType.WE_CHAT_APP_ID,weChatAppId);
        return this;
    }

    public final Configurator withWeChatAppSecret(String weChatAppSecret){
        Ray_CONFIGS.put(ConfigType.WE_CHAT_APP_SECRET,weChatAppSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity){
        Ray_CONFIGS.put(ConfigType.ACTIVITY,activity);
        return this;
    }

    public final Configurator withJavascriptInterface(String jsInterface){
        Ray_CONFIGS.put(ConfigType.JAVASCRIPT_INTERFACE,jsInterface);
        return this;
    }

    private void checkConfiguration(){
        final boolean isChecked = (boolean) Ray_CONFIGS.get(ConfigType.CONFIG_READY);
        if(!isChecked){
            throw new RuntimeException("Configuration is not ready,please first call configure()");
        }
    }

    public final <T> T getConfiguration(Object key){
        checkConfiguration();
        return (T) Ray_CONFIGS.get(key);
    }

}
