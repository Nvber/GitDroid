package com.feicui.zh.login;

import com.feicui.zh.common.LogUtils;
import com.feicui.zh.network.GitHubApi;
import com.feicui.zh.network.GitHubClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2016/7/29.
 */
public class LoginPresent {

    private Call<AccessTokenResult> tokencall;
    private Call<User> userCall;
    private LoginView loginView;

    public LoginPresent(LoginView loginView) {
        this.loginView = loginView;
    }

    // 登陆(先用code换token,再用token换用户信息)
    public void login(String code){
        if (tokencall !=null)tokencall.cancel();

        tokencall = GitHubClient.getInstance().getOAuthToken(GitHubApi.CLIENT_ID,
                GitHubApi.CLIENT_SECRET,code);///
        tokencall.enqueue(tokenCallback);
    }

    private Callback<AccessTokenResult> tokenCallback = new Callback<AccessTokenResult>() {
        @Override
        // token接口响应
        public void onResponse(Call<AccessTokenResult> call, Response<AccessTokenResult> response) {
            // 响应体内数据(结果)
            AccessTokenResult result = response.body();
            String token = result.getAccesstoken();
            LogUtils.d("token = " + token);
            // 再将用此token执行获取用户信息接口,拿到用户信息
            UserRepo.setAccessToken(token);
            // 再将用此token执行获取用户信息接口,拿到用户信息
            if (userCall != null) userCall.cancel();
            userCall = GitHubClient.getInstance().getUserInfo();
            userCall.enqueue(userCallback);
        }
        @Override
        public void onFailure(Call<AccessTokenResult> call, Throwable t) {
            LogUtils.d("tokenCallback onFailure");
            loginView.showMessage(t.getMessage());
            loginView.showProgress();
            loginView.resetWeb();
        }
    };

    private Callback<User> userCallback = new Callback<User>() {
        @Override public void onResponse(Call<User> call, Response<User> response) {
            User user = response.body();
            // 缓存user
            UserRepo.setUser(user);
            loginView.showMessage("登陆成功");
            loginView.navigateToMain();
        }

        @Override public void onFailure(Call<User> call, Throwable t) {
            loginView.showMessage(t.getMessage());
            loginView.showProgress();
            loginView.resetWeb();
        }
    };
}
