package com.feicui.zh.network;

import com.feicui.zh.login.AccessTokenResult;
import com.feicui.zh.login.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2016/7/29.
 */
public interface GitHubApi {
    // 获取square公司retrofit仓库的所有参与者信息
    // 请求方式:Get
    // 请求路径:repos/square/retrofit/contributors
    // 请求参数：无
    // 请求头：无(其实OKHTTP内部会帮我们做一些基本数据补全)
    // 最终首次构建完成了一个Call模型
//    @GET("repos/square/retrofit/contributors")
//    Call<ResponseBody> getRetrofitContributors();

    String CALL_BACK = "feicui";
    String CLIENT_ID = "aa7a3fb1b42f8c07a232";
    String CLIENT_SECRET = "841a9cfd15ded1abb9d75ba51d2d8dd0189ebb77";

    String AUTH_SCOPE = "user,public_repo,repo";
    // 授权登陆页面(用WebView来加载)
    String AUTH_RUL = "https://github.com/login/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + AUTH_SCOPE;

    @Headers("Accept: application/json")
    @POST("https://github.com/login/oauth/access_token")
    Call<AccessTokenResult> getOAuthToken(
        @Field("client_id") String client,
        @Field("client_secret") String clientSecret,
        @Field("code") String code);

    @GET("user")
    Call<User> getUserInfo();
}
