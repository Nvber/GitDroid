package com.feicui.zh.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/7/29.
 */
public class GitHubClient {

    GitHubApi gitHubApi;
    public GitHubClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .build();
        gitHubApi = retrofit.create(GitHubApi.class);
    }

    public GitHubApi getGitHubApi() {
        return gitHubApi;
    }
}
