package com.feicui.zh.network;

import com.feicui.zh.login.AccessTokenResult;
import com.feicui.zh.login.User;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;

/**
 * Created by Administrator on 2016/7/29.
 */
public class GitHubClient implements GitHubApi{

    GitHubApi gitHubApi;

    //单例
    private static GitHubClient gitHubClient;
    public static GitHubClient getInstance(){
        if (gitHubClient ==null){
            gitHubClient = new GitHubClient();
        }
        return gitHubClient;
    }
    public GitHubClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                // 添加token拦截器
                .addInterceptor(new TokenInterceptor())
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                // Gson转换器
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        gitHubApi = retrofit.create(GitHubApi.class);
    }

    @Override
    public Call<AccessTokenResult> getOAuthToken(@Field("client_id") String client, @Field("client_secret") String clientSecret, @Field("code") String code) {
        return gitHubApi.getOAuthToken(client,clientSecret,code);//

//    public GitHubApi getGitHubApi() {
//        return gitHubApi;
    }

    @Override
    public Call<User> getUserInfo() {
        return gitHubApi.getUserInfo();
    }
}
