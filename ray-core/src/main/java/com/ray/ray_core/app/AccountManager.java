package com.ray.ray_core.app;

import com.ray.ray_core.util.storage.MammoonPreference;

/**
 * Created by wrf on 2018/1/30.
 * 管理用户状态
 */

public class AccountManager {

    private enum SignTag{
        SIGN_TAG;
    }

    public static void setSignState(boolean state){
        MammoonPreference.setAppFlag(SignTag.SIGN_TAG.name(),state);
    }

    public static boolean isSignIn(){
        return MammoonPreference.getAppFlag(SignTag.SIGN_TAG.name());
    }

    public static void checkAccount(IUserChecker iUserChecker){
        if(isSignIn()){
            iUserChecker.onSignIn();
        }else {
            iUserChecker.onNotSignIn();
        }
    }
}
