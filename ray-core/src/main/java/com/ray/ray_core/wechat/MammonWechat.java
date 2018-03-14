package com.ray.ray_core.wechat;

import android.app.Activity;

import com.ray.ray_core.app.ConfigType;
import com.ray.ray_core.app.Mamoon;
import com.ray.ray_core.wechat.callbacks.IWeChatSignInCallBack;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by wrf on 2018/1/31.
 */

public class MammonWechat {

    public static final String APP_ID = Mamoon.getConfigurations(ConfigType.WE_CHAT_APP_ID);
    public static final String APP_SECRET = Mamoon.getConfigurations(ConfigType.WE_CHAT_APP_SECRET);
    private IWXAPI mIWXAPI;
    private IWeChatSignInCallBack mIWeChatSignInCallBack;

    private MammonWechat(){
        final Activity activity = Mamoon.getConfigurations(ConfigType.ACTIVITY);
        mIWXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        mIWXAPI.registerApp(APP_ID);
    }

    private final static class Holder{
        private final static MammonWechat INSTANCE = new MammonWechat();
    }

    public static MammonWechat getInstance(){
        return Holder.INSTANCE;
    }

    public final IWXAPI getWXAPI() {
        return mIWXAPI;
    }

    public MammonWechat onSignSuccess(IWeChatSignInCallBack iWeChatSignInCallBack){
        this.mIWeChatSignInCallBack = iWeChatSignInCallBack;
        return this;
    }

    public IWeChatSignInCallBack getmIWeChatSignInCallBack() {
        return mIWeChatSignInCallBack;
    }

    public final void signIn(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "random_state";
        mIWXAPI.sendReq(req);
    }
}
