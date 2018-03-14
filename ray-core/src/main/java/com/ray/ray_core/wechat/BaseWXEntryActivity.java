package com.ray.ray_core.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ray.ray_core.net.RestClient;
import com.ray.ray_core.net.callback.ISuccess;
import com.ray.ray_core.util.log.MammonLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by wrf on 2018/1/31.
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {

    //用户登录成功后回调
    protected abstract void onSignInSuccess(String userInfo);

    //第三方应用发送请求到微信后的回调
    @Override
    public void onReq(BaseReq baseReq) {

    }
    //微信发送请求到第三方应用后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp) baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=")
                .append(MammonWechat.APP_ID)
                .append("&secret=")
                .append(MammonWechat.APP_SECRET)
                .append("&code=")
                .append(code)
                .append("&grant_type=authorization_code");

        MammonLogger.d("mydata",authUrl.toString());
        getAuth(authUrl.toString());
    }

    protected  void getAuth(String authUrl){
        RestClient.builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openid");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl
                                .append("https://api.weixin.qq.com/sns/userinfo?access_token=")
                                .append(accessToken)
                                .append("&openid=")
                                .append(openId)
                                .append("&lang=")
                                .append("zh_CN");

                        MammonLogger.d("mydata", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());

                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String s) {
        RestClient.builder()
                .url(s)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                    }
                })
                .build()
                .get();
    }
}
