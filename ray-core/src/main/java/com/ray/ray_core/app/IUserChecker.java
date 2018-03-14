package com.ray.ray_core.app;

/**
 * Created by wrf on 2018/1/30.
 * 用户状态回调接口
 */

public interface IUserChecker {

    void onSignIn();

    void onNotSignIn();

}
