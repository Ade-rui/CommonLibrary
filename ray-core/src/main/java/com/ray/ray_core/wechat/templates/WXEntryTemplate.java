package com.ray.ray_core.wechat.templates;

import com.ray.ray_core.wechat.BaseWXEntryActivity;
import com.ray.ray_core.wechat.MammonWechat;

/**
 * Created by wrf on 2018/1/31.
 */

public class WXEntryTemplate extends BaseWXEntryActivity{

    @Override
    protected void onResume() {
        super.onResume();
        //防止停留在微信页
        finishWXAcitivty();
    }

    @Override
    protected void onSignInSuccess(String userInfo) {
        MammonWechat.getInstance().getmIWeChatSignInCallBack().onSignInSuccess(userInfo);
    }
}
